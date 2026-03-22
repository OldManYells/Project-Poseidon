package com.legacyminecraft.poseidon.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Canonical legacy packet-string codec.
 */
public final class PacketStringCodec {
    public void writeString(String value, DataOutputStream output) throws IOException {
        if (value.length() > 32767) {
            throw new IOException("String too big");
        }

        output.writeShort(value.length());
        output.writeChars(value);
    }

    public String readString(DataInputStream input, int maxLength) throws IOException {
        short length = input.readShort();

        if (length > maxLength) {
            throw new IOException("Received string length longer than maximum allowed (" + length + " > " + maxLength + ")");
        } else if (length < 0) {
            throw new IOException("Received string length is less than zero! Weird string!");
        } else {
            StringBuilder stringBuilder = new StringBuilder();

            for (int index = 0; index < length; ++index) {
                stringBuilder.append(input.readChar());
            }

            return stringBuilder.toString();
        }
    }
}
