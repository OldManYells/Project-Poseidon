package com.legacyminecraft.poseidon.world.tile;

import com.legacyminecraft.poseidon.compat.LegacyCompatGatewayRegistry;

public final class SignTileBehaviour {
    private static final SignTileBehaviour INSTANCE = new SignTileBehaviour();
    private static final int MAX_LINE_LENGTH = 15;

    private SignTileBehaviour() {
    }

    public static SignTileBehaviour getInstance() {
        return INSTANCE;
    }

    public void writeLines(Object tag, String[] lines) {
        setStringTag(tag, "Text1", lines[0]);
        setStringTag(tag, "Text2", lines[1]);
        setStringTag(tag, "Text3", lines[2]);
        setStringTag(tag, "Text4", lines[3]);
    }

    public void readLines(Object tag, String[] lines) {
        for (int i = 0; i < 4; ++i) {
            lines[i] = getStringTag(tag, "Text" + (i + 1));
            if (lines[i].length() > MAX_LINE_LENGTH) {
                lines[i] = lines[i].substring(0, MAX_LINE_LENGTH);
            }
        }
    }

    public Object createUpdatePacket(Object sign) {
        if (sign == null) {
            return null;
        }
        try {
            Class<?> signClass = sign.getClass();
            String[] lines = (String[]) signClass.getField("lines").get(sign);
            int x = signClass.getField("x").getInt(sign);
            int y = signClass.getField("y").getInt(sign);
            int z = signClass.getField("z").getInt(sign);
            String[] packetLines = new String[4];
            for (int i = 0; i < 4; ++i) {
                String value = lines[i] == null ? "" : lines[i];
                packetLines[i] = value.length() > MAX_LINE_LENGTH ? value.substring(0, MAX_LINE_LENGTH) : value;
            }
            return LegacyCompatGatewayRegistry.gateway().createPacket130UpdateSign(x, y, z, packetLines);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    public boolean markReadOnlyOnLoad() {
        return false;
    }

    private void setStringTag(Object tag, String key, String value) {
        if (tag == null) {
            return;
        }
        try {
            tag.getClass().getMethod("setString", String.class, String.class).invoke(tag, key, value);
        } catch (ReflectiveOperationException ignored) {
        }
    }

    private String getStringTag(Object tag, String key) {
        if (tag == null) {
            return "";
        }
        try {
            Object value = tag.getClass().getMethod("getString", String.class).invoke(tag, key);
            return value == null ? "" : value.toString();
        } catch (ReflectiveOperationException ignored) {
            return "";
        }
    }
}
