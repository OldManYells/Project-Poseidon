package com.legacyminecraft.poseidon.runtime;

import joptsimple.OptionSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PropertyFileBehaviour {
    private static final PropertyFileBehaviour INSTANCE = new PropertyFileBehaviour();

    private PropertyFileBehaviour() {
    }

    public static PropertyFileBehaviour getInstance() {
        return INSTANCE;
    }

    public void initialize(File file, Properties properties, Logger logger, Runnable generateCallback) {
        if (file.exists()) {
            try {
                properties.load(new FileInputStream(file));
            } catch (Exception exception) {
                logger.log(Level.WARNING, "Failed to load " + file, exception);
                generateCallback.run();
            }
        } else {
            logger.log(Level.WARNING, file + " does not exist");
            generateCallback.run();
        }
    }

    public void generate(Logger logger, Runnable saveCallback) {
        logger.log(Level.INFO, "Generating new properties file");
        saveCallback.run();
    }

    public void save(File file, Properties properties, Logger logger, Runnable regenerateCallback) {
        try {
            properties.store(new FileOutputStream(file), "Minecraft server properties");
        } catch (Exception exception) {
            logger.log(Level.WARNING, "Failed to save " + file, exception);
            regenerateCallback.run();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getOverride(OptionSet options, String name, T value) {
        if ((options != null) && options.has(name)) {
            return (T) options.valueOf(name);
        }

        return value;
    }

    public String getString(Properties properties, OptionSet options, String key, String defaultValue, Runnable saveCallback) {
        if (!properties.containsKey(key)) {
            defaultValue = this.getOverride(options, key, defaultValue);
            properties.setProperty(key, defaultValue);
            saveCallback.run();
        }

        return this.getOverride(options, key, properties.getProperty(key, defaultValue));
    }

    public int getInt(Properties properties, OptionSet options, String key, int defaultValue, Runnable saveCallback) {
        try {
            return this.getOverride(options, key, Integer.parseInt(this.getString(properties, options, key, "" + defaultValue, saveCallback)));
        } catch (Exception exception) {
            defaultValue = this.getOverride(options, key, defaultValue);
            properties.setProperty(key, "" + defaultValue);
            return defaultValue;
        }
    }

    public boolean getBoolean(Properties properties, OptionSet options, String key, boolean defaultValue, Runnable saveCallback) {
        try {
            return this.getOverride(options, key, Boolean.parseBoolean(this.getString(properties, options, key, "" + defaultValue, saveCallback)));
        } catch (Exception exception) {
            defaultValue = this.getOverride(options, key, defaultValue);
            properties.setProperty(key, "" + defaultValue);
            return defaultValue;
        }
    }

    public void setBoolean(Properties properties, OptionSet options, String key, boolean value, Runnable saveCallback) {
        value = this.getOverride(options, key, value);
        properties.setProperty(key, "" + value);
        saveCallback.run();
    }
}
