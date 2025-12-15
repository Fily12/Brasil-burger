package com.brasilburger.security;

import com.brasilburger.model.User;

public final class SecurityContext {
    private static User currentUser;

    private SecurityContext() {}

    public static User getCurrentUser() { return currentUser; }
    public static void setCurrentUser(User u) { currentUser = u; }
    public static void clear() { currentUser = null; }
}
