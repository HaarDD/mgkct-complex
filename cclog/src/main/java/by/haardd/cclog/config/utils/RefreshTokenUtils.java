package by.haardd.cclog.config.utils;

import by.haardd.cclog.exception.types.extended.TokenExpiredException;
import by.haardd.cclog.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static by.haardd.cclog.config.SecurityConfiguration.REFRESH_URL;

@Component
@Slf4j
public class RefreshTokenUtils {
    private final Duration jwtRefreshExpiration;
    private final String jwtRefreshCookieName;
    private final UserService userService;

    @SneakyThrows
    public RefreshTokenUtils(@Value("${jwt.refresh-expiration}") Duration jwtRefreshExpiration,
                             @Value("${jwt.refresh-cookie-name}") String jwtRefreshCookieName,
                             @Autowired UserService userService) {
        this.jwtRefreshExpiration = jwtRefreshExpiration;
        this.jwtRefreshCookieName = jwtRefreshCookieName;
        this.userService = userService;
    }

    public String getRefreshTokenFromCookies(HttpServletRequest request) {
        return CookieUtils.getCookieValueByName(request, jwtRefreshCookieName);
    }

    public ResponseCookie generateRefreshCookie(String login) {
        Timestamp currentTimestamp = Timestamp.from(Instant.now());
        String newRefreshToken = UUID.randomUUID().toString();
        userService.updateRefreshTokenByLogin(login, newRefreshToken, Timestamp.from(currentTimestamp.toInstant().plus(jwtRefreshExpiration)));
        return CookieUtils.generateCookie(jwtRefreshCookieName, newRefreshToken, REFRESH_URL, jwtRefreshExpiration);
    }


    public ResponseCookie generateRefreshCookieByVerifyingRefreshToken(String login) {
        Timestamp refreshTokenExpiration = userService.getRefreshTokenExpirationDateByLogin(login);
        Timestamp currentTimestamp = Timestamp.from(Instant.now());

        if (refreshTokenExpiration.compareTo(currentTimestamp) <= 0) {
            userService.clearRefreshTokenByLogin(login);
            throw new TokenExpiredException("Token was expired. Reauthorization required");
        } else {
            String newRefreshToken = UUID.randomUUID().toString();
            userService.updateRefreshTokenByLogin(login, newRefreshToken, Timestamp.from(currentTimestamp.toInstant().plus(jwtRefreshExpiration)));
            return CookieUtils.generateCookie(jwtRefreshCookieName, newRefreshToken, REFRESH_URL, jwtRefreshExpiration);
        }
    }

    public ResponseCookie getCleanJwtRefreshCookie() {
        return CookieUtils.getCleanCookie(jwtRefreshCookieName, REFRESH_URL);
    }

}
