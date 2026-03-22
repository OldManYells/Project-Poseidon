package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.AchievementList;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;

public final class ItemEntityLifecycleBehaviour {
    private static final ItemEntityLifecycleBehaviour INSTANCE = new ItemEntityLifecycleBehaviour();

    private ItemEntityLifecycleBehaviour() {
    }

    public static ItemEntityLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public TickClockState updatePickupDelayClock(int pickupDelay, int currentTick, int lastTick) {
        int deltaTicks = currentTick - lastTick;
        int updatedPickupDelay = pickupDelay - deltaTicks;
        return new TickClockState(updatedPickupDelay, currentTick);
    }

    public PickupWindow computePickupWindow(int pickupDelay, int stackCount, int canHold) {
        int remaining = stackCount - canHold;
        boolean shouldCallPickupEvent = pickupDelay <= 0 && canHold > 0;
        return new PickupWindow(shouldCallPickupEvent, canHold, remaining);
    }

    public int restoreStackCountAfterPickupProbe(PickupWindow window) {
        return window.canHold + window.remaining;
    }

    public int normalizePickupDelayAfterEvent(boolean eventCancelled, int pickupDelay) {
        return eventCancelled ? pickupDelay : 0;
    }

    public boolean shouldTryInventoryPickup(int pickupDelay) {
        return pickupDelay == 0;
    }

    public void grantPickupAchievements(EntityHuman player, int itemId) {
        if (itemId == Block.LOG.id) {
            player.a(AchievementList.g);
        }
        if (itemId == Item.LEATHER.id) {
            player.a(AchievementList.t);
        }
    }

    public float pickupSoundPitch(float randomA, float randomB) {
        return ((randomA - randomB) * 0.7F + 1.0F) * 2.0F;
    }

    public boolean shouldDieAfterPickup(ItemStack itemStack) {
        return itemStack.count <= 0;
    }

    public static final class TickClockState {
        public final int pickupDelay;
        public final int lastTick;

        public TickClockState(int pickupDelay, int lastTick) {
            this.pickupDelay = pickupDelay;
            this.lastTick = lastTick;
        }
    }

    public static final class PickupWindow {
        public final boolean shouldCallPickupEvent;
        public final int canHold;
        public final int remaining;

        public PickupWindow(boolean shouldCallPickupEvent, int canHold, int remaining) {
            this.shouldCallPickupEvent = shouldCallPickupEvent;
            this.canHold = canHold;
            this.remaining = remaining;
        }
    }
}
