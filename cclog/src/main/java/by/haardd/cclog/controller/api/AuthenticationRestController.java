package by.haardd.cclog.controller.api;

import by.haardd.cclog.config.utils.JwtUtils;
import by.haardd.cclog.config.utils.RefreshTokenUtils;
import by.haardd.cclog.config.security.AuthenticationRequest;
import by.haardd.cclog.dto.RegisterUserDto;
import by.haardd.cclog.dto.UserDto;
import by.haardd.cclog.exception.types.extended.TokenInvalidException;
import by.haardd.cclog.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static by.haardd.cclog.config.SecurityConfiguration.AUTH_URL;
import static by.haardd.cclog.config.SecurityConfiguration.CURRENT_USER_URL;
import static by.haardd.cclog.config.SecurityConfiguration.LOGOUT_URL;
import static by.haardd.cclog.config.SecurityConfiguration.REFRESH_URL;
import static by.haardd.cclog.config.SecurityConfiguration.SIGNUP_ADMIN_WITH_CODE_URL;
import static by.haardd.cclog.config.SecurityConfiguration.SIGNUP_USER_WITH_CODE_URL;


@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication control")
public class AuthenticationRestController {

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final JwtUtils jwtUtils;

    private final RefreshTokenUtils refreshTokenUtils;

    private final UserService userService;

    @PostMapping(AUTH_URL)
    public ResponseEntity<String> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken) daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getLogin(),
                        authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        ResponseCookie jwtCookie = jwtUtils.generateJwtTokenCookie(AuthenticationRestController.class.getSimpleName(), usernamePasswordAuthenticationToken);
        ResponseCookie jwtRefreshCookie = refreshTokenUtils.generateRefreshCookie(authenticationRequest.getLogin());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body("User login successfully!");
    }

    @PostMapping(SIGNUP_ADMIN_WITH_CODE_URL)
    public ResponseEntity<String> registerEngineer(@Valid @RequestBody RegisterUserDto registerUserDto, @RequestParam(required = true) String code) {
        userService.saveWithAdminKey(registerUserDto, code);
        return ResponseEntity.ok("Admin registered successfully!");
    }

    @PostMapping(SIGNUP_USER_WITH_CODE_URL)
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto, @RequestParam(required = true) String code) {
        userService.saveWithUserKey(registerUserDto, code);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping(LOGOUT_URL)
    public ResponseEntity<String> logoutUser() {
        userService.clearRefreshTokenByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

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

        if (StringUtils.isBlank(refreshToken)) {
            return ResponseEntity.badRequest().body("Refresh Token is empty");
        }
        UserDetails userDetails;
        try {
            userDetails = jwtUtils.getTokenClaimsWithoutValidation(jwtUtils.getJwtTokenFromCookies(request));
            if (userDetails == null) throw new RuntimeException();
        } catch (Exception e) {
            throw new TokenInvalidException("Invalid token");
        }

        refreshTokenUtils.verifyRefreshToken(userDetails.getUsername(), refreshTokenUtils.getRefreshTokenFromCookies(request));

        ResponseCookie refreshCookie = refreshTokenUtils.generateRefreshCookie(userDetails.getUsername());

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseCookie accessCookie = jwtUtils.generateJwtTokenCookie(AuthenticationRestController.class.getSimpleName(), authentication);


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .body("Tokens successfully updated");

    }

    @GetMapping(CURRENT_USER_URL)
    public UserDto currentAuth() {
        UserDetails authenticatedUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentAuthenticatedLogin = authenticatedUserDetails.getUsername();
        return userService.getByLogin(currentAuthenticatedLogin);
    }


}
