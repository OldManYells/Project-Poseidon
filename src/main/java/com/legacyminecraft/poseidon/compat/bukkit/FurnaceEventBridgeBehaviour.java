package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.ItemStack;
import net.minecraft.server.World;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

/**
 * Canonical bridge for Furnace burn/smelt Bukkit event orchestration.
 */
public final class FurnaceEventBridgeBehaviour {
    private static final FurnaceEventBridgeBehaviour INSTANCE = new FurnaceEventBridgeBehaviour();

    private FurnaceEventBridgeBehaviour() {
    }

    public static FurnaceEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public BurnDecision fireBurnEvent(World world, int x, int y, int z, ItemStack fuelStack, int defaultBurnTime) {
        CraftItemStack fuelItem = new CraftItemStack(fuelStack);
        FurnaceBurnEvent burnEvent = new FurnaceBurnEvent(
                world.getWorld().getBlockAt(x, y, z),
                fuelItem,
                defaultBurnTime
        );
        world.getServer().getPluginManager().callEvent(burnEvent);
        return new BurnDecision(burnEvent.isCancelled(), burnEvent.getBurnTime(), burnEvent.isBurning());
    }

    public SmeltDecision fireSmeltEvent(World world, int x, int y, int z, ItemStack sourceStack, ItemStack proposedResult) {
        CraftItemStack sourceItem = new CraftItemStack(sourceStack);
        CraftItemStack resultItem = new CraftItemStack(proposedResult.cloneItemStack());

        FurnaceSmeltEvent smeltEvent = new FurnaceSmeltEvent(world.getWorld().getBlockAt(x, y, z), sourceItem, resultItem);
        world.getServer().getPluginManager().callEvent(smeltEvent);

        if (smeltEvent.isCancelled()) {
            return SmeltDecision.cancelled();
        }

        org.bukkit.inventory.ItemStack bukkitResult = smeltEvent.getResult();
        ItemStack resultStack = new ItemStack(
                bukkitResult.getTypeId(),
                bukkitResult.getAmount(),
                bukkitResult.getDurability()
        );
        return SmeltDecision.accepted(resultStack);
    }

    public static final class BurnDecision {
        private final boolean cancelled;
        private final int burnTime;
        private final boolean burning;

        public BurnDecision(boolean cancelled, int burnTime, boolean burning) {
            this.cancelled = cancelled;
            this.burnTime = burnTime;
            this.burning = burning;
        }

        public boolean isCancelled() {
            return cancelled;
        }

        public int getBurnTime() {
            return burnTime;
        }

        public boolean isBurning() {
            return burning;
        }
    }

    public static final class SmeltDecision {
        private final boolean cancelled;
        private final ItemStack result;

        private SmeltDecision(boolean cancelled, ItemStack result) {
            this.cancelled = cancelled;
            this.result = result;
        }

        public static SmeltDecision cancelled() {
            return new SmeltDecision(true, null);
        }

        public static SmeltDecision accepted(ItemStack result) {
            return new SmeltDecision(false, result);
        }

        public boolean isCancelled() {
            return cancelled;
        }

        public ItemStack getResult() {
            return result;
        }
    }
}

