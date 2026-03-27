package com.legacyminecraft.compat.bukkit;

import java.util.HashMap;
import java.util.Map;

/**
 * Canonical compat property-manager scaffold.
 */
public class PropertyManager {
    private final Map<String, Object> values = new HashMap<String, Object>();

    public PropertyManager() {
    }

    public PropertyManager(Object options) {
    }

    public void b(String key, boolean value) {
        values.put(key, Boolean.valueOf(value));
    }

    public String getString(String key, String defaultValue) {
        Object value = values.get(key);
        return value instanceof String ? (String) value : defaultValue;
    }

    public int getInt(String key, int defaultValue) {
        Object value = values.get(key);
        return value instanceof Number ? ((Number) value).intValue() : defaultValue;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        Object value = values.get(key);
        return value instanceof Boolean ? ((Boolean) value).booleanValue() : defaultValue;
    }

    public void savePropertiesFile() {
    }
}
