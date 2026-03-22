package com.projectposeidon;

/**
 * @deprecated Use {@link com.legacyminecraft.poseidon.api.network.ConnectionType}.
 */
@Deprecated
public enum ConnectionType {
    NORMAL,
    RELEASE2BETA,
    RELEASE2BETA_ONLINE_MODE_IP_FORWARDING,
    RELEASE2BETA_OFFLINE_MODE_IP_FORWARDING,
    BUNGEECORD,
    BUNGEECORD_OFFLINE_MODE_IP_FORWARDING,
    BUNGEECORD_ONLINE_MODE_IP_FORWARDING;

    public com.legacyminecraft.poseidon.api.network.ConnectionType toCanonical() {
        return com.legacyminecraft.poseidon.api.network.ConnectionType.valueOf(this.name());
    }

    public static ConnectionType fromCanonical(com.legacyminecraft.poseidon.api.network.ConnectionType type) {
        return ConnectionType.valueOf(type.name());
    }
}
