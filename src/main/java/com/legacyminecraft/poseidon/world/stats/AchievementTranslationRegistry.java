package com.legacyminecraft.poseidon.world.stats;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Canonical registry for statistic/achievement translation identifiers.
 */
public final class AchievementTranslationRegistry {
    private static final AchievementTranslationRegistry INSTANCE = new AchievementTranslationRegistry();

    private final Map<Integer, String> translations = new HashMap<Integer, String>();

    private AchievementTranslationRegistry() {
        loadTranslations();
    }

    public static AchievementTranslationRegistry getInstance() {
        return INSTANCE;
    }

    public String lookup(int statisticId) {
        return translations.get(Integer.valueOf(statisticId));
    }

    private void loadTranslations() {
        InputStream stream = AchievementTranslationRegistry.class.getResourceAsStream("/achievement/map.txt");
        if (stream == null) {
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length != 2) {
                        continue;
                    }
                    int statisticId = Integer.parseInt(parts[0]);
                    translations.put(Integer.valueOf(statisticId), parts[1]);
                }
            } finally {
                reader.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
