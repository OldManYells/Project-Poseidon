package com.legacyminecraft.compat.bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Canonical compat configuration-node scaffold.
 */
public class ConfigurationNode {
    protected Map<String, Object> values;

    public ConfigurationNode() {
        this(new HashMap<String, Object>());
    }

    public ConfigurationNode(Map<String, Object> values) {
        this.values = values == null ? new HashMap<String, Object>() : values;
    }

    public String getString(String key) {
        Object value = values.get(key);
        return value == null ? null : value.toString();
    }

    public String getString(String key, String defaultValue) {
        String value = getString(key);
        return value == null ? defaultValue : value;
    }

    public int getInt(String key, int defaultValue) {
        Object value = values.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return defaultValue;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        Object value = values.get(key);
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        }
        return defaultValue;
    }

    public void setProperty(String key, Object value) {
        if (!key.contains(".")) {
            values.put(key, value);
            return;
        }

        String[] parts = key.split("\\.");
        Map<String, Object> node = values;
        for (int i = 0; i < parts.length; i++) {
            if (i == parts.length - 1) {
                node.put(parts[i], value);
                return;
            }
            Object child = node.get(parts[i]);
            if (!(child instanceof Map)) {
                child = new HashMap<String, Object>();
                node.put(parts[i], child);
            }
            node = castMap(child);
        }
    }

    public ConfigurationNode getNode(String key) {
        Object value = values.get(key);
        if (value instanceof ConfigurationNode) {
            return (ConfigurationNode) value;
        }
        ConfigurationNode child = new ConfigurationNode();
        values.put(key, child);
        return child;
    }

    public Set<String> getKeys() {
        return new HashSet<String>(values.keySet());
    }

    public Object getProperty(String key) {
        if (!key.contains(".")) {
            return values.get(key);
        }
        String[] parts = key.split("\\.");
        Map<String, Object> node = values;
        for (int i = 0; i < parts.length; i++) {
            Object current = node.get(parts[i]);
            if (current == null) {
                return null;
            }
            if (i == parts.length - 1) {
                return current;
            }
            if (!(current instanceof Map)) {
                return null;
            }
            node = castMap(current);
        }
        return null;
    }

    public void removeProperty(String key) {
        if (!key.contains(".")) {
            values.remove(key);
            return;
        }
        String[] parts = key.split("\\.");
        Map<String, Object> node = values;
        for (int i = 0; i < parts.length - 1; i++) {
            Object current = node.get(parts[i]);
            if (!(current instanceof Map)) {
                return;
            }
            node = castMap(current);
        }
        node.remove(parts[parts.length - 1]);
    }

    @SuppressWarnings("unchecked")
    public List<String> getStringList(String key, List<String> defaultValue) {
        Object value = values.get(key);
        if (value instanceof List) {
            return (List<String>) value;
        }
        return defaultValue == null ? new ArrayList<String>() : defaultValue;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> castMap(Object value) {
        return (Map<String, Object>) value;
    }
}
