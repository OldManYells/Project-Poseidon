package com.legacyminecraft.poseidon.material;
import com.legacyminecraft.poseidon.world.block.*;

import org.bukkit.block.BlockFace;

public interface IDirectional {

    /**
     * Sets the direction that this block is facing in
     */
    public void setFacingDirection(BlockFace face);

    /**
     * Gets the direction this block is facing
     *
     * @return the direction this block is facing
     */
    public BlockFace getFacing();
}
