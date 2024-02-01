package com.aspen.core.foundation.context;


import com.aspen.core.foundation.base.UserDetailsDTO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AspenContext {

    private Long userId;

    private UserDetailsDTO user;

    private Map<String, Object> context = new ConcurrentHashMap<>();

    public AspenContext() {
    }

    public Object getContext(String key) {
        return this.context.get(key);
    }

    public void addContext(String key, Object value) {
        this.context.put(key, value);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserDetailsDTO getUser() {
        return user;
    }

    public void setUser(UserDetailsDTO user) {
        this.user = user;
    }
}