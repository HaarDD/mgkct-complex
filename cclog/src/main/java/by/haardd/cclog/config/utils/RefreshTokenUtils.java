package by.haardd.cclog.config.utils;

import by.haardd.cclog.exception.types.extended.TokenExpiredException;
import by.haardd.cclog.exception.types.extended.TokenInvalidException;
import by.haardd.cclog.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
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
    private final Duration refreshTokenExpiration;
    private final String refreshTokenCookieName;
    private final UserService userService;
    private final Duration authCookieExpiration;

    @SneakyThrows
    public RefreshTokenUtils(@Value("${jwt.refresh-expiration}") Duration refreshTokenExpiration,
                             @Value("${jwt.refresh-cookie-name}") String refreshTokenCookieName,
                             @Autowired UserService userService,
                             @Value("${jwt.auth-cookie-expiration}") Duration authCookieExpiration) {
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.refreshTokenCookieName = refreshTokenCookieName;
        this.userService = userService;
        this.authCookieExpiration = authCookieExpiration;
    }

    public String getRefreshTokenFromCookies(HttpServletRequest request) {
        return CookieUtils.getCookieValueByName(request, refreshTokenCookieName);
    }

    public ResponseCookie generateRefreshCookie(String login) {
        Timestamp currentTimestamp = Timestamp.from(Instant.now());
        String newRefreshToken = UUID.randomUUID().toString();
        userService.updateRefreshTokenByLogin(login, newRefreshToken, Timestamp.from(currentTimestamp.toInstant().plus(refreshTokenExpiration)));
        return CookieUtils.generateCookie(refreshTokenCookieName, newRefreshToken, REFRESH_URL, authCookieExpiration);
    }

    public ResponseCookie getCleanJwtRefreshCookie() {
        return CookieUtils.getCleanCookie(refreshTokenCookieName, REFRESH_URL);
    }

    public void verifyRefreshToken(String login, String refreshTokenFromCookies) throws TokenInvalidException, TokenExpiredException {
        String existedRefreshToken = userService.getRefreshTokenByLogin(login);
        if (!existedRefreshToken.equals(refreshTokenFromCookies)) {
            throw new TokenInvalidException("Invalid token!");
        }

        Timestamp refreshTokenExpiration = userService.getRefreshTokenExpirationDateByLogin(login);
        Timestamp currentTimestamp = Timestamp.from(Instant.now());
        if (refreshTokenExpiration.compareTo(currentTimestamp) <= 0) {
            userService.clearRefreshTokenByLogin(login);
            throw new TokenExpiredException("Token was expired. Reauthorization required");
        }

    }
}
