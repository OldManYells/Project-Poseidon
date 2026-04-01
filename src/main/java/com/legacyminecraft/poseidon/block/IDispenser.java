package com.legacyminecraft.poseidon.block;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

/**
 * Represents a dispenser.
 *
 * @author sk89q
 */
public interface IDispenser {

    /**
     * Attempts to dispense the contents of this block<br />
     * <br />
     * If the block is no longer a dispenser, this will return false
     *
     * @return true if successful, otherwise false
     */
    public boolean dispense();
}
