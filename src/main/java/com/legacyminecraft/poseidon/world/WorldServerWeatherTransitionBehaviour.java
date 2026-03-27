package com.legacyminecraft.poseidon.world;


import java.util.List;

/**
 * Canonical behaviour for world-server weather transition orchestration.
 */
public final class WorldServerWeatherTransitionBehaviour {
    private static final WorldServerWeatherTransitionBehaviour INSTANCE = new WorldServerWeatherTransitionBehaviour();

    private WorldServerWeatherTransitionBehaviour() {
    }

    public static WorldServerWeatherTransitionBehaviour getInstance() {
        return INSTANCE;
    }

    public void broadcastWeatherTransitionIfNeeded(
            WorldServerBehaviour worldServerBehaviour,
            WorldServerWeatherBroadcastBehaviour weatherBroadcastBehaviour,
            List players,
            WorldServer world,
            boolean previousRaining,
            boolean currentRaining
    ) {
        if (!worldServerBehaviour.shouldBroadcastWeatherStateChange(previousRaining, currentRaining)) {
            return;
        }

        weatherBroadcastBehaviour.broadcastWeatherStateChange(
                players,
                world,
                worldServerBehaviour.weatherStatePacketType(previousRaining)
        );
    }
}
