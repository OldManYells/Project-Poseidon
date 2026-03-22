package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.text.AllowedCharacterSetBehaviour;

public class FontAllowedCharacters {
    private static final AllowedCharacterSetBehaviour ALLOWED_CHARACTER_SET_BEHAVIOUR = AllowedCharacterSetBehaviour.getInstance();

    public static final String allowedCharacters = a();
    public static final char[] b = new char[] { '/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'};

    public FontAllowedCharacters() {}

    private static String a() {
        return ALLOWED_CHARACTER_SET_BEHAVIOUR.loadAllowedCharacters(FontAllowedCharacters.class, "/font.txt");
    }
}
