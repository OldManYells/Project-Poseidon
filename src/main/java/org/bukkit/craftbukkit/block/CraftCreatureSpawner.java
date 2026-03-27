package org.bukkit.craftbukkit.block;

import com.legacyminecraft.compat.bukkit.SpawnerStateBehaviour;
import com.legacyminecraft.compat.bukkit.TileEntityLookupBehaviour;
import net.minecraft.server.TileEntityMobSpawner;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.CreatureType;

public class CraftCreatureSpawner extends CraftBlockState implements CreatureSpawner {
    private static final SpawnerStateBehaviour SPAWNER_STATE_BEHAVIOUR =
            SpawnerStateBehaviour.getInstance();
    private static final TileEntityLookupBehaviour TILE_ENTITY_LOOKUP_BEHAVIOUR =
            TileEntityLookupBehaviour.getInstance();

    private final TileEntityMobSpawner spawner;

    public CraftCreatureSpawner(final Block block) {
        super(block);
        spawner = TILE_ENTITY_LOOKUP_BEHAVIOUR.resolveMobSpawner(block, getX(), getY(), getZ());
    }

    public CreatureType getCreatureType() {
        return SPAWNER_STATE_BEHAVIOUR.getCreatureType(spawner);
    }

    public void setCreatureType(CreatureType creatureType) {
        SPAWNER_STATE_BEHAVIOUR.setCreatureType(spawner, creatureType);
    }

    public String getCreatureTypeId() {
        return SPAWNER_STATE_BEHAVIOUR.getCreatureTypeId(spawner);
    }

    public void setCreatureTypeId(String creatureType) {
        SPAWNER_STATE_BEHAVIOUR.setCreatureTypeId(spawner, creatureType);
    }

    public int getDelay() {
        return SPAWNER_STATE_BEHAVIOUR.getDelay(spawner);
    }

    public void setDelay(int delay) {
        SPAWNER_STATE_BEHAVIOUR.setDelay(spawner, delay);
    }

}
