package com.lty.bookstore.system.backend.filter;

public class UserContextHolder {

    private static InheritableThreadLocal<UserContext> USER_CONTEXT = new InheritableThreadLocal<>();

    public static UserContext getUserContext() {
        UserContext userContext = USER_CONTEXT.get();
        if (userContext == null) {
            return new UserContext();
        }
        return userContext;
    }

    public static void setUserContext(UserContext userContext) {
        USER_CONTEXT.set(userContext);
    }

    public static String getUserId() {
        return getUserContext().getUserId();
    }

    public static void removeUserContext() {
        USER_CONTEXT.remove();
    }
}
