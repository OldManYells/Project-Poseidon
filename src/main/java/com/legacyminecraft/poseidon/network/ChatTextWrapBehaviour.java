package com.legacyminecraft.poseidon.network;

/**
 * Canonical behavior for wrapping and measuring Minecraft chat text.
 */
public final class ChatTextWrapBehaviour {
    private static final ChatTextWrapBehaviour INSTANCE = new ChatTextWrapBehaviour();
    private static final int[] CHARACTER_WIDTHS = new int[] {
            1, 9, 9, 8, 8, 8, 8, 7, 9, 8, 9, 9, 8, 9, 9, 9,
            8, 8, 8, 8, 9, 9, 8, 9, 8, 8, 8, 8, 8, 9, 9, 9,
            4, 2, 5, 6, 6, 6, 6, 3, 5, 5, 5, 6, 2, 6, 2, 6,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 2, 2, 5, 6, 5, 6,
            7, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 4, 6, 6,
            3, 6, 6, 6, 6, 6, 5, 6, 6, 2, 6, 5, 3, 6, 6, 6,
            6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 5, 2, 5, 7, 6,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 3, 6, 6,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6,
            6, 3, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 2, 6, 6,
            8, 9, 9, 6, 6, 6, 8, 8, 6, 8, 8, 8, 8, 8, 6, 6,
            9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
            9, 9, 9, 9, 9, 9, 9, 9, 9, 6, 9, 9, 9, 5, 9, 9,
            8, 7, 7, 8, 7, 8, 8, 8, 7, 8, 8, 7, 9, 9, 6, 7,
            7, 7, 7, 7, 9, 6, 7, 8, 7, 6, 6, 9, 7, 6, 7, 1
    };

    public static final char COLOR_CHAR = '\u00A7';
    public static final int CHAT_WINDOW_WIDTH = 320;
    public static final int CHAT_STRING_LENGTH = 119;
    public static final String ALLOWED_CHARACTERS = net.minecraft.server.FontAllowedCharacters.allowedCharacters;

    private ChatTextWrapBehaviour() {
    }

    public static ChatTextWrapBehaviour getInstance() {
        return INSTANCE;
    }

    public String[] wrapText(final String text) {
        final StringBuilder wrappedOutput = new StringBuilder();
        char activeColor = 'f';
        int currentLineWidth = 0;
        int currentLineLength = 0;

        for (int index = 0; index < text.length(); index++) {
            char currentCharacter = text.charAt(index);

            if (currentCharacter == COLOR_CHAR && index < text.length() - 1) {
                if (currentLineLength + 2 > CHAT_STRING_LENGTH) {
                    wrappedOutput.append('\n');
                    currentLineLength = 0;
                    if (activeColor != 'f' && activeColor != 'F') {
                        wrappedOutput.append(COLOR_CHAR).append(activeColor);
                        currentLineLength += 2;
                    }
                }
                activeColor = text.charAt(++index);
                wrappedOutput.append(COLOR_CHAR).append(activeColor);
                currentLineLength += 2;
                continue;
            }

            int characterIndex = ALLOWED_CHARACTERS.indexOf(currentCharacter);
            if (characterIndex == -1) {
                continue;
            } else {
                characterIndex += 32;
            }

            final int characterWidth = CHARACTER_WIDTHS[characterIndex];

            if (currentLineLength + 1 > CHAT_STRING_LENGTH || currentLineWidth + characterWidth >= CHAT_WINDOW_WIDTH) {
                wrappedOutput.append('\n');
                currentLineLength = 0;

                if (activeColor != 'f' && activeColor != 'F') {
                    wrappedOutput.append(COLOR_CHAR).append(activeColor);
                    currentLineLength += 2;
                }
                currentLineWidth = characterWidth;
            } else {
                currentLineWidth += characterWidth;
            }
            wrappedOutput.append(currentCharacter);
            currentLineLength++;
        }

        return wrappedOutput.toString().split("\n");
    }

    public int widthInPixels(final String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        int outputWidth = 0;

        for (int index = 0; index < text.length(); index++) {
            char currentCharacter = text.charAt(index);

            if (currentCharacter == COLOR_CHAR && index < text.length() - 1) {
                index++;
                continue;
            }

            int characterIndex = ALLOWED_CHARACTERS.indexOf(currentCharacter);
            if (characterIndex == -1) {
                continue;
            }

            characterIndex += 32;
            outputWidth += CHARACTER_WIDTHS[characterIndex];
        }

        return outputWidth;
    }
}

