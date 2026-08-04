package br.com.quickq.quickq_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final List<String> UNSAFE_METHODS = List.of("POST", "PUT", "DELETE", "PATCH");
    private static final List<String> CSRF_EXEMPT_PATHS = List.of("/api/usuarios/login");
    private static final String CSRF_HEADER = "X-CSRF-Token";

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                     @NonNull HttpServletResponse response,
                                     @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = extractCookie(request, CookieUtil.AUTH_COOKIE);

        if (token != null) {
            try {
                Claims claims = jwtUtil.validarToken(token);

                if (isUnsafeMethod(request) && !CSRF_EXEMPT_PATHS.contains(request.getRequestURI())
                        && !csrfTokenValido(request)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF token inválido ou ausente");
                    return;
                }

                String email = claims.getSubject();
                String perfil = claims.get("perfil", String.class);

                var authentication = new UsernamePasswordAuthenticationToken(
                        email, null, List.of(new SimpleGrantedAuthority("ROLE_" + perfil)));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException | IllegalArgumentException e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isUnsafeMethod(HttpServletRequest request) {
        return UNSAFE_METHODS.contains(request.getMethod());
    }

    // Double-submit cookie: o valor do cookie XSRF-TOKEN (legível por JS) precisa
    // bater com o header enviado pelo frontend. Um site de terceiros não consegue
    // ler esse cookie (Same-Origin Policy), então não tem como forjar o header.
    private boolean csrfTokenValido(HttpServletRequest request) {
        String csrfCookie = extractCookie(request, CookieUtil.CSRF_COOKIE);
        String csrfHeader = request.getHeader(CSRF_HEADER);
        return csrfCookie != null && Objects.equals(csrfCookie, csrfHeader);
    }

    private String extractCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
