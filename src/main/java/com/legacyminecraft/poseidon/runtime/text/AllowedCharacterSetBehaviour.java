package com.legacyminecraft.poseidon.runtime.text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class AllowedCharacterSetBehaviour {
    private static final AllowedCharacterSetBehaviour INSTANCE = new AllowedCharacterSetBehaviour();

    private AllowedCharacterSetBehaviour() {
    }

    public static AllowedCharacterSetBehaviour getInstance() {
        return INSTANCE;
    }

    public String loadAllowedCharacters(Class resourceHost, String resourcePath) {
        String characters = "";

        try {
            InputStream stream = resourceHost.getResourceAsStream(resourcePath);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line;

            while ((line = bufferedreader.readLine()) != null) {
                if (!line.startsWith("#")) {
                    characters = characters + line;
                }
            }

            bufferedreader.close();
        } catch (Exception exception) {
            // Legacy behavior ignores resource read failures.
        }

        return characters;
    }
}
