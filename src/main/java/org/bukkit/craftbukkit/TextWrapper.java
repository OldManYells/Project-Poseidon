package org.bukkit.craftbukkit;

import com.legacyminecraft.poseidon.network.ChatTextWrapBehaviour;

public class TextWrapper {
    private static final ChatTextWrapBehaviour CHAT_TEXT_WRAP_BEHAVIOUR = ChatTextWrapBehaviour.getInstance();
    public static final char COLOR_CHAR = ChatTextWrapBehaviour.COLOR_CHAR;
    public static final int CHAT_WINDOW_WIDTH = ChatTextWrapBehaviour.CHAT_WINDOW_WIDTH;
    public static final int CHAT_STRING_LENGTH = ChatTextWrapBehaviour.CHAT_STRING_LENGTH;
    public static final String allowedChars = ChatTextWrapBehaviour.ALLOWED_CHARACTERS;

    public static String[] wrapText(final String text) {
        return CHAT_TEXT_WRAP_BEHAVIOUR.wrapText(text);
    }

    public static int widthInPixels(final String text) {
        return CHAT_TEXT_WRAP_BEHAVIOUR.widthInPixels(text);
    }
}
