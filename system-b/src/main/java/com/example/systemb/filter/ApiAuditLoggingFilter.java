package com.example.systemb.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Component
public class ApiAuditLoggingFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(ApiAuditLoggingFilter.class);

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getRequestURI();
        return uri == null || !uri.contains("/api/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Instant start = Instant.now();
        try {
            filterChain.doFilter(request, response);
        } finally {
            Instant end = Instant.now();
            long ms = Duration.between(start, end).toMillis();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String sub = null, user = null, scope = null, azp = null;
            if (auth instanceof JwtAuthenticationToken jwtAuth) {
                var jwt = jwtAuth.getToken();
                sub = jwt.getSubject();
                user = jwt.getClaimAsString("preferred_username");
                scope = jwt.getClaimAsString("scope");
                azp = Optional.ofNullable(jwt.getClaimAsString("azp")).orElse(null);
            } else if (auth != null) {
                user = auth.getName();
            }
            log.info("api_audit method={} uri={} status={} duration_ms={} user={} sub={} scope={} azp={} ip={}",
                    request.getMethod(), request.getRequestURI(), response.getStatus(), ms,
                    safe(user), safe(sub), safe(scope), safe(azp), request.getRemoteAddr());
        }
    }

    private String safe(String v) { return v == null ? "-" : v; }
}

