package org.lion.springsecurity.beforesecirity;

public class UserContext {
    public static final ThreadLocal<User> userThreadLocal = ThreadLocal.withInitial(() -> null);

    public static void setUser(User user) {
        userThreadLocal.set(user);

    }

    public static User getUser() {
        return userThreadLocal.get();
    }

    public static void removeUser() {
        userThreadLocal.remove();
    }
}
