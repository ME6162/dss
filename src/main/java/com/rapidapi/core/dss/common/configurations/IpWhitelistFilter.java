package com.rapidapi.core.dss.common.configurations;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class IpWhitelistFilter extends OncePerRequestFilter {

    private static final Set<String> WHITELISTED_IPS = new HashSet<>();

    static {
        // Add whitelisted IPs here
        WHITELISTED_IPS.add("127.0.0.1"); // Localhost
        WHITELISTED_IPS.add("0:0:0:0:0:0:0:1"); // IP6 local
        // Add more IPs as needed
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String clientIp = getClientIp(request);

        if (!WHITELISTED_IPS.contains(clientIp)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
            response.getWriter().write("Forbidden: IP not whitelisted");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        } else {
            // In case of multiple IPs in the header, take the first one
            clientIp = clientIp.split(",")[0];
        }
        return clientIp.trim();
    }
}

