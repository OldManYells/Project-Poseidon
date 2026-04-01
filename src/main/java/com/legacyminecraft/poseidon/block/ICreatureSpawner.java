package com.legacyminecraft.poseidon.block;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

import org.bukkit.entity.CreatureType;

/**
 * Represents a creature spawner.
 *
 * @author sk89q
 * @author Cogito
 */
public interface ICreatureSpawner {

    /**
     * Get the spawner's creature type.
     *
     * @return
     */
    public CreatureType getCreatureType();

    /**
     * Set the spawner creature type.
     *
     * @param mobType
     */
    public void setCreatureType(CreatureType creatureType);

    /**
     * Get the spawner's creature type.
     *
     * @return
     */
    public String getCreatureTypeId();

    /**
     * Set the spawner mob type.
     *
     * @param creatureType
     */
    public void setCreatureTypeId(String creatureType);

    /**
     * Get the spawner's delay.
     *
     * @return
     */
    public int getDelay();

    /**
     * Set the spawner's delay.
     *
     * @param delay
     */
    public void setDelay(int delay);
}
