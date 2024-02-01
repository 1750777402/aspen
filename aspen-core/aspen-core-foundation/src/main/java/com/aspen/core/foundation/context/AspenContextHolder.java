package com.aspen.core.foundation.context;

public class AspenContextHolder {

    private final static ThreadLocal<AspenContext> contextHolder = new ThreadLocal<>();

    public static AspenContext get() {
        return contextHolder.get();
    }

    public static void set(AspenContext context) {
        contextHolder.set(context);
    }

    public static void clear() {
        contextHolder.remove();
    }

}