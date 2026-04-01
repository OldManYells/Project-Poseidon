package com.legacyminecraft.poseidon.material;
import com.legacyminecraft.poseidon.world.block.material.*;

/**
 * Indicated a Material that may carry or create a Redstone current
 */
public interface IRedstone {

    /**
     * Gets the current state of this Material, indicating if it's powered or
     * unpowered
     *
     * @return true if powered, otherwise false
     */
    public boolean isPowered();
}
