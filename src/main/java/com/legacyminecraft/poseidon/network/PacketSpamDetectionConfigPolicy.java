package com.legacyminecraft.poseidon.network;

/**
 * Canonical config-key policy for packet-spam detection controls.
 */
public final class PacketSpamDetectionConfigPolicy {
    private static final PacketSpamDetectionConfigPolicy INSTANCE = new PacketSpamDetectionConfigPolicy();
    private static final String SPAM_DETECTION_ENABLED_KEY = "settings.packet-spam-detection.enabled";
    private static final boolean SPAM_DETECTION_ENABLED_DEFAULT = true;
    private static final String SPAM_DETECTION_THRESHOLD_KEY = "settings.packet-spam-detection.threshold";
    private static final int SPAM_DETECTION_THRESHOLD_DEFAULT = 1000;

    private PacketSpamDetectionConfigPolicy() {
    }

    public static PacketSpamDetectionConfigPolicy getInstance() {
        return INSTANCE;
    }

    public String spamDetectionEnabledKey() {
        return SPAM_DETECTION_ENABLED_KEY;
    }

    public boolean spamDetectionEnabledDefault() {
        return SPAM_DETECTION_ENABLED_DEFAULT;
    }

    public String spamDetectionThresholdKey() {
        return SPAM_DETECTION_THRESHOLD_KEY;
    }

    public int spamDetectionThresholdDefault() {
        return SPAM_DETECTION_THRESHOLD_DEFAULT;
    }
}
