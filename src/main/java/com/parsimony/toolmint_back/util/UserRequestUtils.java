package com.parsimony.toolmint_back.util;

import jakarta.servlet.http.HttpServletRequest;

public final class UserRequestUtils {

    private UserRequestUtils() {} // NOTE 인스턴스화 방지

    public static String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        } else {
            return request.getRemoteAddr();
        }
    }

    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return userAgent != null ? userAgent : "";
    }

}