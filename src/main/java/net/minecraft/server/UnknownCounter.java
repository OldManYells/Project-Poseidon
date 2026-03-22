package net.minecraft.server;

import com.legacyminecraft.poseidon.world.stats.CounterVariantBehaviour;

final class UnknownCounter implements Counter {
    private static final CounterVariantBehaviour COUNTER_VARIANT_BEHAVIOUR = CounterVariantBehaviour.getInstance();

    UnknownCounter() {
        COUNTER_VARIANT_BEHAVIOUR.initializeUnknownCounter();
    }
}
