package com.legacyminecraft.poseidon.packet;


/**
 * Canonical packet instance factory.
 */
public final class PacketFactory {
    public Packet createPacket(Class packetClass, int packetId) {
        try {
            return packetClass == null ? null : (Packet) packetClass.newInstance();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Skipping packet with id " + packetId);
            return null;
        }
    }
}
