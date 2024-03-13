package by.haardd.cclog.config.utils;

import by.haardd.cclog.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;

import static by.haardd.cclog.config.SecurityConfiguration.REFRESH_URL;

@Component
@Slf4j
public class RefreshTokenUtils {
    private final Duration jwtRefreshExpiration;
    private final String jwtRefreshCookieName;

    @SneakyThrows
    public RefreshTokenUtils(@Value("${jwt.refresh-expiration}") Duration jwtRefreshExpiration,
                             @Value("${jwt.refresh-cookie-name}") String jwtRefreshCookieName) {
        this.jwtRefreshExpiration = jwtRefreshExpiration;
        this.jwtRefreshCookieName = jwtRefreshCookieName;
    }


    public ResponseCookie generateRefreshJwtCookieAndSaveTokenByAuth(UserService userService, Authentication authentication) {
        String refreshToken = userService.updateAndGetRefreshTokenByAuth(authentication, jwtRefreshExpiration);
        return CookieUtils.generateCookie(jwtRefreshCookieName, refreshToken, REFRESH_URL, jwtRefreshExpiration);
    }

    public String getRefreshTokenFromCookies(HttpServletRequest request) {
        return CookieUtils.getCookieValueByName(request, jwtRefreshCookieName);
    }


    public ResponseCookie generateRefreshCookieAndSaveTokenByRefresh(UserService userService, String refreshToken) {
        String newRefreshToken = userService.updateAndGetRefreshTokenByRefresh(refreshToken, jwtRefreshExpiration);
        return CookieUtils.generateCookie(jwtRefreshCookieName, newRefreshToken, REFRESH_URL, jwtRefreshExpiration);
    }

    public ResponseCookie getCleanJwtRefreshCookie() {
        return CookieUtils.getCleanCookie(jwtRefreshCookieName, REFRESH_URL);
    }

}
