package com.legacyminecraft.poseidon.block;

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
