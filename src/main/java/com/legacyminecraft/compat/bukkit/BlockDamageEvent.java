package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat BlockDamageEvent scaffold.
 */
public class BlockDamageEvent extends PlayerEvent {
    private final Block block;
    private final ItemStack itemInHand;
    private final boolean instaBreak;
    private boolean cancelled;

    public BlockDamageEvent(Player player, Block block, ItemStack itemInHand, boolean instaBreak) {
        super(player);
        this.block = block;
        this.itemInHand = itemInHand;
        this.instaBreak = instaBreak;
    }

    public Block getBlock() {
        return block;
    }

    public ItemStack getItemInHand() {
        return itemInHand;
    }

    public boolean getInstaBreak() {
        return instaBreak;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
