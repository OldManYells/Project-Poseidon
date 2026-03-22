package com.legacyminecraft.poseidon.compat.projectposeidon;

import com.legacyminecraft.poseidon.api.network.ConnectionType;
import com.legacyminecraft.poseidon.api.uuid.UUIDType;

/**
 * Conversion helpers for legacy com.projectposeidon API types.
 */
public final class ProjectPoseidonCompat {
    private ProjectPoseidonCompat() {
    }

    public static ConnectionType toCanonical(com.projectposeidon.ConnectionType legacy) {
        return ConnectionType.valueOf(legacy.name());
    }

    public static com.projectposeidon.ConnectionType toLegacy(ConnectionType canonical) {
        return com.projectposeidon.ConnectionType.valueOf(canonical.name());
    }

    public static UUIDType toCanonical(com.projectposeidon.api.UUIDType legacy) {
        return UUIDType.valueOf(legacy.name());
    }

    public static com.projectposeidon.api.UUIDType toLegacy(UUIDType canonical) {
        return com.projectposeidon.api.UUIDType.valueOf(canonical.name());
    }
}
