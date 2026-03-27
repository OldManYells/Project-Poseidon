package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat BlockPlaceEvent scaffold.
 */
public class BlockPlaceEvent extends PlayerEvent {
    private final Block blockPlaced;
    private final BlockState replacedBlockState;
    private final Block blockAgainst;
    private final ItemStack itemInHand;
    private final boolean canBuild;
    private boolean cancelled;

    public BlockPlaceEvent(
            Block blockPlaced,
            BlockState replacedBlockState,
            Block blockAgainst,
            ItemStack itemInHand,
            Player player,
            boolean canBuild
    ) {
        super(player);
        this.blockPlaced = blockPlaced;
        this.replacedBlockState = replacedBlockState;
        this.blockAgainst = blockAgainst;
        this.itemInHand = itemInHand;
        this.canBuild = canBuild;
    }

    public Block getBlockPlaced() {
        return blockPlaced;
    }

    public BlockState getBlockReplacedState() {
        return replacedBlockState;
    }

    public Block getBlockAgainst() {
        return blockAgainst;
    }

    public ItemStack getItemInHand() {
        return itemInHand;
    }

    public boolean canBuild() {
        return canBuild;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
