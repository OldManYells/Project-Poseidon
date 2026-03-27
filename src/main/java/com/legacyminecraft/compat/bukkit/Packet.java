package com.legacyminecraft.compat.bukkit;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Canonical compat packet base.
 */
public abstract class Packet {
    public int a() {
        return 0;
    }

    public int b() {
        return 0;
    }

    public void a(NetHandler handler) {
    }

    public static Packet a(DataInputStream input, boolean isServerSide) throws IOException {
        return null;
    }

    public static void a(Packet packet, DataOutputStream output) throws IOException {
    }
}
