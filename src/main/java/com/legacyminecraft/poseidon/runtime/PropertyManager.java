package com.legacyminecraft.poseidon.runtime;

import java.util.HashMap;
import java.util.Map;

/**
 * Runtime-local property manager used by startup systems.
 */
public class PropertyManager {
    public final Map<String, Object> properties = new HashMap<String, Object>();

    public PropertyManager() {
    }

    public PropertyManager(Object options) {
    }

    public String getString(String key, String fallback) {
        Object value = properties.get(key);
        return value instanceof String ? (String) value : fallback;
    }

    public boolean getBoolean(String key, boolean fallback) {
        Object value = properties.get(key);
        return value instanceof Boolean ? ((Boolean) value).booleanValue() : fallback;
    }

    public int getInt(String key, int fallback) {
        Object value = properties.get(key);
        return value instanceof Number ? ((Number) value).intValue() : fallback;
    }

    public void b(String key, boolean value) {
        properties.put(key, Boolean.valueOf(value));
    }

    public void savePropertiesFile() {
    }
}
