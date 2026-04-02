package org.bukkit.event.world;
import com.legacyminecraft.poseidon.world.core.*;
import com.legacyminecraft.poseidon.world.block.*;

import org.bukkit.Chunk;
import org.bukkit.generator.BlockPopulator;

/**
 * Thrown when a new chunk has finished being populated.
 *
 * If your intent is to populate the chunk using this event, please see {@link BlockPopulator}
 */
public class ChunkPopulateEvent extends ChunkEvent {
    public ChunkPopulateEvent(final Chunk chunk) {
        super(Type.CHUNK_POPULATED, chunk);
    }
}
