package com.legacyminecraft.compat.bukkit;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Canonical compat configuration scaffold.
 */
public class Configuration extends ConfigurationNode {
    private final Yaml yaml;
    private final File file;

    public Configuration(File file) {
        super(new HashMap<String, Object>());
        this.file = file;
        this.yaml = new Yaml(new SafeConstructor());
    }

    public void load() {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
            Object loaded = yaml.load(new UnicodeReader(stream));
            if (loaded instanceof Map) {
                values = castMap(loaded);
            } else {
                values = new HashMap<String, Object>();
            }
        } catch (IOException ignored) {
            values = new HashMap<String, Object>();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public boolean save() {
        FileOutputStream stream = null;
        try {
            File parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            stream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(stream, "UTF-8");
            yaml.dump(values, writer);
            writer.flush();
            return true;
        } catch (IOException ignored) {
            return false;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> castMap(Object value) {
        return (Map<String, Object>) value;
    }
}
