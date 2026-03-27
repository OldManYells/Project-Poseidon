package com.legacyminecraft.poseidon.compat;

import java.util.Objects;

public final class LegacyCompatGatewayRegistry {
    private static volatile LegacyCompatGateway gateway;

    private LegacyCompatGatewayRegistry() {
    }

    public static void install(LegacyCompatGateway compatGateway) {
        gateway = Objects.requireNonNull(compatGateway, "compatGateway");
    }

    public static LegacyCompatGateway gateway() {
        LegacyCompatGateway current = gateway;
        if (current == null) {
            throw new IllegalStateException("LegacyCompatGateway has not been installed");
        }
        return current;
    }
}
