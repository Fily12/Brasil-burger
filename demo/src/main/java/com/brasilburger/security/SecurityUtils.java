package com.brasilburger.security;

import com.brasilburger.model.User;
import com.brasilburger.model.enums.Role;

public final class SecurityUtils {
    private SecurityUtils() {}

    public static void requireRole(Role role) {
        User u = SecurityContext.getCurrentUser();
        if (u == null || u.getRole() != role) {
            throw new SecurityException("Accès refusé: rôle requis = " + role);
        }
    }

    public static boolean hasRole(Role role) {
        User u = SecurityContext.getCurrentUser();
        return u != null && u.getRole() == role;
    }
}
