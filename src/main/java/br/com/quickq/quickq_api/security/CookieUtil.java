package br.com.quickq.quickq_api.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public static final String AUTH_COOKIE = "auth_token";
    public static final String CSRF_COOKIE = "XSRF-TOKEN";

    private final boolean secure;
    private final long expirationMs;

    public CookieUtil(@Value("${app.cookie.secure:true}") boolean secure,
                       @Value("${jwt.expiration-ms}") long expirationMs) {
        this.secure = secure;
        this.expirationMs = expirationMs;
    }

    public ResponseCookie buildAuthCookie(String token) {
        return ResponseCookie.from(AUTH_COOKIE, token)
                .httpOnly(true)
                .secure(secure)
                .sameSite("Lax")
                .path("/")
                .maxAge(expirationMs / 1000)
                .build();
    }

    // Não é HttpOnly de propósito: o frontend precisa ler o valor e ecoar no header
    // X-CSRF-Token (padrão double-submit cookie).
    public ResponseCookie buildCsrfCookie(String csrfToken) {
        return ResponseCookie.from(CSRF_COOKIE, csrfToken)
                .httpOnly(false)
                .secure(secure)
                .sameSite("Lax")
                .path("/")
                .maxAge(expirationMs / 1000)
                .build();
    }

    public ResponseCookie clearAuthCookie() {
        return ResponseCookie.from(AUTH_COOKIE, "")
                .httpOnly(true)
                .secure(secure)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();
    }

    public ResponseCookie clearCsrfCookie() {
        return ResponseCookie.from(CSRF_COOKIE, "")
                .httpOnly(false)
                .secure(secure)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();
    }
}
