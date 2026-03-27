package com.legacyminecraft.compat.bukkit;


import java.awt.Image;
import java.util.Arrays;

/**
 * Canonical behavior for CraftBukkit map canvas pixel and text rendering.
 */
public final class CraftMapCanvasBehaviour {
    private static final CraftMapCanvasBehaviour INSTANCE = new CraftMapCanvasBehaviour();
    private static final int MAP_WIDTH = 128;
    private static final int MAP_HEIGHT = 128;

    private CraftMapCanvasBehaviour() {
    }

    public static CraftMapCanvasBehaviour getInstance() {
        return INSTANCE;
    }

    public void initializeBuffer(byte[] buffer) {
        Arrays.fill(buffer, (byte) -1);
    }

    public void setPixel(Object worldMap, byte[] buffer, int x, int y, byte color) {
        if (!isWithinBounds(x, y)) {
            return;
        }

        int pixelIndex = y * MAP_WIDTH + x;
        if (buffer[pixelIndex] != color) {
            buffer[pixelIndex] = color;
            BridgeReflection.invoke(worldMap, "a", x, y, y);
        }
    }

    public byte getPixel(byte[] buffer, int x, int y) {
        if (!isWithinBounds(x, y)) {
            return 0;
        }

        return buffer[y * MAP_WIDTH + x];
    }

    public byte getBasePixel(byte[] base, int x, int y) {
        if (!isWithinBounds(x, y)) {
            return 0;
        }

        return base[y * MAP_WIDTH + x];
    }

    public void drawImage(Object worldMap, byte[] buffer, int x, int y, Image image) {
        byte[] bytes = MapPalette.imageToBytes(image);
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);

        for (int imageX = 0; imageX < imageWidth; ++imageX) {
            for (int imageY = 0; imageY < imageHeight; ++imageY) {
                setPixel(worldMap, buffer, x + imageX, y + imageY, bytes[imageY * imageWidth + imageX]);
            }
        }
    }

    public void drawText(Object worldMap, byte[] buffer, int x, int y, Object font, String text) {
        int startX = x;
        byte color = MapPalette.DARK_GRAY;

        if (!Boolean.TRUE.equals(BridgeReflection.invoke(font, "isValid", text))) {
            throw new IllegalArgumentException("text contains invalid characters");
        }

        for (int index = 0; index < text.length(); ++index) {
            char character = text.charAt(index);
            if (character == '\n') {
                x = startX;
                y += ((Number) BridgeReflection.invoke(font, "getHeight")).intValue() + 1;
                continue;
            } else if (character == '\u00A7') {
                int colorCodeEnd = text.indexOf(';', index);
                if (colorCodeEnd >= 0) {
                    try {
                        color = Byte.parseByte(text.substring(index + 1, colorCodeEnd));
                        index = colorCodeEnd;
                        continue;
                    } catch (NumberFormatException ex) {
                        // Fall through and render the section sign itself.
                    }
                }
            }

            Object sprite = BridgeReflection.invoke(font, "getChar", character);
            int fontHeight = ((Number) BridgeReflection.invoke(font, "getHeight")).intValue();
            int spriteWidth = ((Number) BridgeReflection.invoke(sprite, "getWidth")).intValue();
            for (int row = 0; row < fontHeight; ++row) {
                for (int column = 0; column < spriteWidth; ++column) {
                    if (Boolean.TRUE.equals(BridgeReflection.invoke(sprite, "get", row, column))) {
                        setPixel(worldMap, buffer, x + column, y + row, color);
                    }
                }
            }
            x += spriteWidth + 1;
        }
    }

    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < MAP_WIDTH && y < MAP_HEIGHT;
    }
}
