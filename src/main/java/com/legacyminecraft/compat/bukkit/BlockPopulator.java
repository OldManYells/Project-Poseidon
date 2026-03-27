package com.legacyminecraft.compat.bukkit;

import java.util.Random;

/**
 * Canonical compat block-populator scaffold.
 */
public abstract class BlockPopulator {
    public abstract void populate(World world, Random random, Chunk source);
}

