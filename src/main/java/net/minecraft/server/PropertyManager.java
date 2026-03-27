package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.PropertyFileBehaviour;

import java.io.File;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertyManager extends com.legacyminecraft.poseidon.runtime.PropertyManager {
    private static final PropertyFileBehaviour PROPERTY_FILE_BEHAVIOUR = PropertyFileBehaviour.getInstance();

    public static Logger a = Logger.getLogger("Minecraft");
    public Properties properties = new Properties(); // CraftBukkit - priv to pub
    private File c;

    public PropertyManager(File file1) {
        this.c = file1;
        PROPERTY_FILE_BEHAVIOUR.initialize(file1, this.properties, a, new Runnable() {
            public void run() {
                PropertyManager.this.a();
            }
        });
    }

    // CraftBukkit start
    private joptsimple.OptionSet options = null;

    public PropertyManager(final joptsimple.OptionSet options) {
        this((File) options.valueOf("config"));

        this.options = options;
    }

    private <T> T getOverride(String name, T value) {
        return PROPERTY_FILE_BEHAVIOUR.getOverride(this.options, name, value);
    }
    // CraftBukkit end

    public void a() {
        PROPERTY_FILE_BEHAVIOUR.generate(a, new Runnable() {
            public void run() {
                PropertyManager.this.savePropertiesFile();
            }
        });
    }

    public void savePropertiesFile() {
        PROPERTY_FILE_BEHAVIOUR.save(this.c, this.properties, a, new Runnable() {
            public void run() {
                PropertyManager.this.a();
            }
        });
    }

    public String getString(String s, String s1) {
        return PROPERTY_FILE_BEHAVIOUR.getString(this.properties, this.options, s, s1, new Runnable() {
            public void run() {
                PropertyManager.this.savePropertiesFile();
            }
        });
    }

    public int getInt(String s, int i) {
        return PROPERTY_FILE_BEHAVIOUR.getInt(this.properties, this.options, s, i, new Runnable() {
            public void run() {
                PropertyManager.this.savePropertiesFile();
            }
        });
    }

    public boolean getBoolean(String s, boolean flag) {
        return PROPERTY_FILE_BEHAVIOUR.getBoolean(this.properties, this.options, s, flag, new Runnable() {
            public void run() {
                PropertyManager.this.savePropertiesFile();
            }
        });
    }

    public void b(String s, boolean flag) {
        PROPERTY_FILE_BEHAVIOUR.setBoolean(this.properties, this.options, s, flag, new Runnable() {
            public void run() {
                PropertyManager.this.savePropertiesFile();
            }
        });
    }
}
