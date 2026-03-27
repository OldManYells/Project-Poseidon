package com.legacyminecraft.poseidon.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Canonical packet scaffold for migrated item/datawatcher behaviours.
 */
public abstract class Packet {
    public int a() {
        return 0;
    }

    public int b() {
        return 0;
    }

    public void a(DataInputStream input) throws IOException {
    }

    public void a(DataOutputStream output) throws IOException {
    }

    public static String a(DataInputStream input, int maxLength) throws IOException {
        int length = input.readShort();
        if (length < 0 || length > maxLength) {
            throw new IOException("Invalid string length: " + length);
        }
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = input.readChar();
        }
        return new String(chars);
    }

    public static void a(String value, DataOutputStream output) throws IOException {
        if (value == null) {
            value = "";
        }
        if (value.length() > Short.MAX_VALUE) {
            throw new IOException("String too long");
        }
        output.writeShort(value.length());
        output.writeChars(value);
    }
}
