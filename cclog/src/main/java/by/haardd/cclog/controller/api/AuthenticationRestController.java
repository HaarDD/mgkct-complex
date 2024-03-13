package by.haardd.cclog.controller.api;

import by.haardd.cclog.config.utils.JwtUtils;
import by.haardd.cclog.config.utils.RefreshTokenUtils;
import by.haardd.cclog.dto.RegisterUserDto;
import by.haardd.cclog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static by.haardd.cclog.config.SecurityConfiguration.AUTH_URL;
import static by.haardd.cclog.config.SecurityConfiguration.LOGOUT_URL;
import static by.haardd.cclog.config.SecurityConfiguration.REFRESH_URL;
import static by.haardd.cclog.config.SecurityConfiguration.SIGNUP_ADMIN_WITH_CODE;


@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final JwtUtils jwtUtils;

    private final RefreshTokenUtils refreshTokenUtils;

    private final UserService userService;

    @PostMapping(AUTH_URL)
    public ResponseEntity<String> authenticateUser(@RequestParam String login, @RequestParam String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken) daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(login, password));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        ResponseCookie jwtCookie = jwtUtils.generateJwtTokenCookie(AuthenticationRestController.class.getSimpleName(),usernamePasswordAuthenticationToken);
        ResponseCookie jwtRefreshCookie = refreshTokenUtils.generateRefreshJwtCookieAndSaveTokenByAuth(userService, usernamePasswordAuthenticationToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body("User login successfully!");
    }

    @PostMapping(SIGNUP_ADMIN_WITH_CODE)
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto, @RequestParam(required = true) String code) {
        userService.saveWithAdminKey(registerUserDto, code);
        return ResponseEntity.ok("Admin registered successfully!");
    }

    @PostMapping(LOGOUT_URL)
    public ResponseEntity<String> logoutUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.clearRefreshToken(authentication);

        ResponseCookie jwtCookie = jwtUtils.getCleanJwtTokenCookie();
        ResponseCookie jwtRefreshCookie = refreshTokenUtils.getCleanJwtRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body("You've been signed out!");
    }

    @PostMapping(REFRESH_URL)
    public ResponseEntity<String> refreshToken(HttpServletRequest request) {

        String refreshToken = refreshTokenUtils.getRefreshTokenFromCookies(request);

        if (StringUtils.isNotBlank(refreshToken)) {

            ResponseCookie refreshCookie = refreshTokenUtils.generateRefreshCookieAndSaveTokenByRefresh(userService, refreshToken);
            ResponseCookie jwtCookie = jwtUtils.generateJwtTokenCookie(AuthenticationRestController.class.getSimpleName(), SecurityContextHolder.getContext().getAuthentication());

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                    .body("Tokens was successfully updated by refresh!");
        }

        return ResponseEntity.badRequest().body("Refresh Token is empty!");
    }

}
