package net.minecraft.server;

import com.legacyminecraft.poseidon.world.stats.CounterVariantBehaviour;

final class TimeCounter implements Counter {
    private static final CounterVariantBehaviour COUNTER_VARIANT_BEHAVIOUR = CounterVariantBehaviour.getInstance();

    TimeCounter() {
        COUNTER_VARIANT_BEHAVIOUR.initializeTimeCounter();
    }
}
