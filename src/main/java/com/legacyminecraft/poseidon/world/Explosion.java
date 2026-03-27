package com.legacyminecraft.poseidon.world;

import java.util.HashSet;
import java.util.Set;

/**
 * World-local explosion state scaffold.
 */
public class Explosion {
    public boolean wasCanceled;
    public final Set<ChunkPosition> blocks = new HashSet<ChunkPosition>();
}
