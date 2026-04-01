package org.bukkit.event.world;
import com.legacyminecraft.poseidon.world.core.*;

import org.bukkit.World;

public class WorldSaveEvent extends WorldEvent {
    public WorldSaveEvent(World world) {
        super(Type.WORLD_SAVE, world);
    }
}
