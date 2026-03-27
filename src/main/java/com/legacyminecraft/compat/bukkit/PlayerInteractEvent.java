package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat PlayerInteractEvent scaffold.
 */
public class PlayerInteractEvent extends PlayerEvent {
    private final Action action;
    private final Block clickedBlock;
    private final BlockFace blockFace;
    private final ItemStack item;
    private Type useInteractedBlock = Type.DEFAULT;
    private Type useItemInHand = Type.DEFAULT;
    private boolean cancelled;

    public PlayerInteractEvent(Player player, Action action, ItemStack item, Block clickedBlock, BlockFace blockFace) {
        super(player);
        this.action = action;
        this.item = item;
        this.clickedBlock = clickedBlock;
        this.blockFace = blockFace;
    }

    public Action getAction() {
        return action;
    }

    public Block getClickedBlock() {
        return clickedBlock;
    }

    public BlockFace getBlockFace() {
        return blockFace;
    }

    public ItemStack getItem() {
        return item;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Type useInteractedBlock() {
        return useInteractedBlock;
    }

    public void setUseInteractedBlock(Type useInteractedBlock) {
        this.useInteractedBlock = useInteractedBlock;
    }

    public Type useItemInHand() {
        return useItemInHand;
    }

    public void setUseItemInHand(Type useItemInHand) {
        this.useItemInHand = useItemInHand;
    }
}
