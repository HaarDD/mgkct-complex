package by.haardd.cclog.config.utils;

import by.haardd.cclog.entity.enums.RoleNameEnum;
import by.haardd.cclog.exception.types.extended.TokenInvalidException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static by.haardd.cclog.config.SecurityConfiguration.API_URL;

@Slf4j
@Component
public class JwtUtils {

    private static final JWSHeader JWT_HEADER = new JWSHeader(JWSAlgorithm.HS256);
    private static final String JWT_TOKEN_CLAIM = "token";
    private static final String JWT_PRIVILEGE_CLAIM = "privilege";
    private final Duration accessTokenExpiration;
    private final JWSSigner jwtSigner;
    private final JWSVerifier jwtVerifier;
    private final String accessTokenCookieName;
    private final Duration authCookieExpiration;

    @SneakyThrows
    public JwtUtils(@Value("${jwt.secret}") String jwtSecret,
                    @Value("${jwt.access-expiration}") Duration accessTokenExpiration,
                    @Value("${jwt.access-cookie-name}") String accessTokenCookieName,
                    @Value("${jwt.auth-cookie-expiration}") Duration authCookieExpiration) {
        this.accessTokenExpiration = accessTokenExpiration;
        this.accessTokenCookieName = accessTokenCookieName;
        this.jwtSigner = new MACSigner(jwtSecret);
        this.jwtVerifier = new MACVerifier(jwtSecret);
        this.authCookieExpiration = authCookieExpiration;
    }

    public ResponseCookie generateJwtTokenCookie(String issuer, Authentication authentication) {
        if (authentication.getName().equals("anonymousUser")) {
            throw new RuntimeException("Token cannot be generated for an anonymous user!");
        }
        String jwt = generateToken(issuer, authentication, accessTokenExpiration);
        return CookieUtils.generateCookie(accessTokenCookieName, jwt, API_URL, authCookieExpiration);
    }

    public String getJwtTokenFromCookies(HttpServletRequest request) {
        return CookieUtils.getCookieValueByName(request, accessTokenCookieName);
    }

    public ResponseCookie getCleanJwtTokenCookie() {
        return CookieUtils.getCleanCookie(accessTokenCookieName, API_URL);
    }

    @SneakyThrows
    private String generateToken(String issuer, Authentication userInfo, TemporalAmount expiration) {
        final Pair<Date, Date> issueAndExpirationTimes = getIssueAndExpirationTimes(expiration);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .subject(userInfo.getName())
                .claim(JWT_TOKEN_CLAIM, UUID.randomUUID().toString())
                .claim(JWT_PRIVILEGE_CLAIM, userInfo.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
                )
                .issueTime(issueAndExpirationTimes.getLeft())
                .expirationTime(issueAndExpirationTimes.getRight())
                .build();
        SignedJWT signedJWT = new SignedJWT(JWT_HEADER, claimsSet);

        signedJWT.sign(jwtSigner);

        return signedJWT.serialize();
    }

    @Nullable
    public UserDetails getTokenClaims(String token) throws JOSEException {
        final JWTClaimsSet jwtClaims;
        try {
            final SignedJWT decodedJWT = SignedJWT.parse(token);

            if (decodedJWT.verify(jwtVerifier) && isValid(jwtClaims = decodedJWT.getJWTClaimsSet())) {
                String userName = decodedJWT.getJWTClaimsSet().getSubject();
                final Stream<RoleNameEnum> userRights = this.<List<String>>getClaim(jwtClaims, JWT_PRIVILEGE_CLAIM)
                        .map(list -> list.stream().map(RoleNameEnum::valueOf))
                        .orElse(Stream.empty());
                return new User(userName, StringUtils.EMPTY, userRights.map(RoleNameEnum::name)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
                );
            } else {
                throw new TokenInvalidException("Token was invalid!");
            }
        } catch (ParseException pe) {
            throw new TokenInvalidException("Token was invalid!");
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Optional<T> getClaim(JWTClaimsSet jwtClaims, String claim) {
        return Optional.ofNullable((T) jwtClaims.getClaim(claim));
    }

    private Pair<Date, Date> getIssueAndExpirationTimes(TemporalAmount expiration) {
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        Instant expirationAt = issuedAt.plus(expiration);
        return Pair.of(Date.from(issuedAt), Date.from(expirationAt));
    }

    public boolean isValid(JWTClaimsSet jwtClaims) {
        Date referenceTime = new Date();
        Date expirationTime = jwtClaims.getExpirationTime();
        Date notBeforeTime = jwtClaims.getNotBeforeTime();
        boolean expired = expirationTime != null && expirationTime.before(referenceTime);
        boolean yetValid = notBeforeTime == null || notBeforeTime.before(referenceTime);
        return !expired && yetValid;
    }

}