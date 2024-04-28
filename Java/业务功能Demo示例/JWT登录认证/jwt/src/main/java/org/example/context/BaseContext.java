package org.example.context;

public class BaseContext {
    public static ThreadLocal<Long> currentUserId = new ThreadLocal<>();

    public static Long getCurrentUserId() {
        return currentUserId.get();
    }

    public static void setCurrentUserId(Long id) {
        BaseContext.currentUserId.set(id);
    }

    public static void removeCurrentUserId() {
        BaseContext.currentUserId.remove();
    }
}
