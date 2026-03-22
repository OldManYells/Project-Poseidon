package com.legacyminecraft.poseidon.world.stats;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Canonical translation store for legacy statistic and achievement keys.
 */
public final class StatisticTranslationBehaviour {
    private static final StatisticTranslationBehaviour INSTANCE = new StatisticTranslationBehaviour();

    private final Properties translations = new Properties();

    private StatisticTranslationBehaviour() {
        try {
            loadResource("/lang/en_US.lang");
            loadResource("/lang/stats_US.lang");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static StatisticTranslationBehaviour getInstance() {
        return INSTANCE;
    }

    public String translate(String key) {
        return translations.getProperty(key, key);
    }

    public String format(String key, Object... arguments) {
        return String.format(translate(key), arguments);
    }

    private void loadResource(String resourcePath) throws IOException {
        InputStream stream = StatisticTranslationBehaviour.class.getResourceAsStream(resourcePath);
        if (stream == null) {
            return;
        }
        try (InputStream input = stream) {
            translations.load(input);
        }
    }
}
