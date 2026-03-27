package com.legacyminecraft.poseidon.network;

/**
 * Canonical config-key policy for packet-event enablement.
 */
public final class PacketEventConfigPolicy {
    private static final PacketEventConfigPolicy INSTANCE = new PacketEventConfigPolicy();
    private static final String PACKET_EVENTS_ENABLED_KEY = "settings.packet-events.enabled";
    private static final boolean PACKET_EVENTS_ENABLED_DEFAULT = false;

    private PacketEventConfigPolicy() {
    }

    public static PacketEventConfigPolicy getInstance() {
        return INSTANCE;
    }

    public String packetEventsEnabledKey() {
        return PACKET_EVENTS_ENABLED_KEY;
    }

    public boolean packetEventsEnabledDefault() {
        return PACKET_EVENTS_ENABLED_DEFAULT;
    }
}
