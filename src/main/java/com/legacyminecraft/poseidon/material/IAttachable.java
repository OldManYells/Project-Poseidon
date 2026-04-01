package com.legacyminecraft.poseidon.material;

import org.bukkit.block.BlockFace;

/**
 * Indicates that a block can be attached to another block
 */
public interface IAttachable {

    /**
     * Gets the face that this block is attached on
     *
     * @return BlockFace attached to
     */
    public BlockFace getAttachedFace();
}
