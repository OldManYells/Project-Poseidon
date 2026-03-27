package com.legacyminecraft.compat.bukkit;


import java.util.Random;

/**
 * Canonical behaviour for CraftServer create-world overload bridge wrapper glue.
 */
public final class CraftServerWorldCreationBridgeBehaviour {
    public interface WorldCreationDelegate {
        World createWorld(String name, Environment environment, long seed, ChunkGenerator generator);
    }

    private static final CraftServerWorldCreationBridgeBehaviour INSTANCE =
            new CraftServerWorldCreationBridgeBehaviour();

    private CraftServerWorldCreationBridgeBehaviour() {
    }

    public static CraftServerWorldCreationBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public World createWorld(String name, Environment environment, WorldCreationDelegate delegate) {
        return delegate.createWorld(name, environment, this.createRandomSeed(), null);
    }

    public World createWorld(String name, Environment environment, long seed, WorldCreationDelegate delegate) {
        return delegate.createWorld(name, environment, seed, null);
    }

    public World createWorld(String name, Environment environment, ChunkGenerator generator, WorldCreationDelegate delegate) {
        return delegate.createWorld(name, environment, this.createRandomSeed(), generator);
    }

    public long createRandomSeed() {
        return (new Random()).nextLong();
    }
}
