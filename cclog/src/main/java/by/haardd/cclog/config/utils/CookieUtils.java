package by.haardd.cclog.config.utils;

import org.springframework.http.ResponseCookie;
import org.springframework.lang.Nullable;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CookieUtils {

    public static ResponseCookie generateCookie(String name, String value, String path, Duration maxAge) {
        return ResponseCookie.from(name, value).path(path).sameSite("Strict").maxAge(maxAge.getSeconds()).build();
    }

    @Nullable
    public static String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    public static String getCookiesAsString(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .map(cookie -> cookie.getName() + "=" + cookie.getValue())
                .collect(Collectors.joining("; "));
    }

    public static ResponseCookie getCleanCookie(String cookieName, String path) {
        return ResponseCookie.from(cookieName, "").path(path).sameSite("Strict").maxAge(0).build();
    }
}
