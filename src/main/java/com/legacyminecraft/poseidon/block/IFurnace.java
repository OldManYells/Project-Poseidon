package com.legacyminecraft.poseidon.block;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

/**
 * Represents a furnace.
 *
 * @author sk89q
 */
public interface IFurnace {

    /**
     * Get burn time.
     *
     * @return
     */
    public short getBurnTime();

    /**
     * Set burn time.
     *
     * @param burnTime
     */
    public void setBurnTime(short burnTime);

    /**
     * Get cook time.
     *
     * @return
     */
    public short getCookTime();

    /**
     * Set cook time.
     *
     * @param cookTime
     */
    public void setCookTime(short cookTime);
}
