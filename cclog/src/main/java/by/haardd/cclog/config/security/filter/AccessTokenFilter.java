package by.haardd.cclog.config.security.filter;

import by.haardd.cclog.config.utils.JwtUtils;
import by.haardd.cclog.exception.types.extended.TokenInvalidException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccessTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String jwt = jwtUtils.getJwtTokenFromCookies(request);
        if (jwt != null && !jwt.isEmpty() && !jwt.isBlank()) {
            try {
                UserDetails userDetails = jwtUtils.getTokenClaims(jwt);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (TokenInvalidException tokenInvalidException) {
                log.info("JWT token was invalid by request: {}", request);
            }
        } else {
            log.info("JWT token was empty by request: {}", request);
        }

        filterChain.doFilter(request, response);
    }
}
