package com.projectposeidon.api;

/**
 * @deprecated Use {@link com.legacyminecraft.poseidon.api.uuid.UUIDType}.
 */
@Deprecated
public enum UUIDType {
    ONLINE,
    OFFLINE,
    UNKNOWN;

    public com.legacyminecraft.poseidon.api.uuid.UUIDType toCanonical() {
        return com.legacyminecraft.poseidon.api.uuid.UUIDType.valueOf(this.name());
    }

    public static UUIDType fromCanonical(com.legacyminecraft.poseidon.api.uuid.UUIDType type) {
        return UUIDType.valueOf(type.name());
    }
}
