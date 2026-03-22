package net.minecraft.server;

import com.legacyminecraft.poseidon.world.stats.CounterVariantBehaviour;

final class DistancesCounter implements Counter {
    private static final CounterVariantBehaviour COUNTER_VARIANT_BEHAVIOUR = CounterVariantBehaviour.getInstance();

    DistancesCounter() {
        COUNTER_VARIANT_BEHAVIOUR.initializeDistancesCounter();
    }
}
