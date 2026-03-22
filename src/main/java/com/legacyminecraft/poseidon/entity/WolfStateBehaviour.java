package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemFood;
import net.minecraft.server.ItemStack;

public final class WolfStateBehaviour {
    private static final WolfStateBehaviour INSTANCE = new WolfStateBehaviour();
    private static final int FLAGS_WATCHER_INDEX = 16;
    private static final int OWNER_WATCHER_INDEX = 17;
    private static final int HEALTH_WATCHER_INDEX = 18;

    private static final int SITTING_MASK = 1;
    private static final int ANGRY_MASK = 2;
    private static final int TAMED_MASK = 4;

    private WolfStateBehaviour() {
    }

    public static WolfStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int getFlagsWatcherIndex() {
        return FLAGS_WATCHER_INDEX;
    }

    public int getOwnerWatcherIndex() {
        return OWNER_WATCHER_INDEX;
    }

    public int getHealthWatcherIndex() {
        return HEALTH_WATCHER_INDEX;
    }

    public byte createInitialFlags() {
        return (byte) 0;
    }

    public String createInitialOwnerName() {
        return "";
    }

    public int createInitialHealthValue(int currentHealth) {
        return currentHealth;
    }

    public String toStoredOwnerName(String ownerName) {
        return ownerName == null ? "" : ownerName;
    }

    public boolean shouldStayHostileToPlayers(boolean tamed) {
        return !tamed;
    }

    public String resolveAmbientSound(boolean angry, int randomRoll3, boolean tamed, int healthWatcherValue) {
        if (angry) {
            return "mob.wolf.growl";
        }
        if (randomRoll3 == 0) {
            return tamed && healthWatcherValue < 10 ? "mob.wolf.whine" : "mob.wolf.panting";
        }
        return "mob.wolf.bark";
    }

    public String getHurtSound() {
        return "mob.wolf.hurt";
    }

    public String getDeathSound() {
        return "mob.wolf.death";
    }

    public float getSoundVolume() {
        return 0.4F;
    }

    public int getDropItemId() {
        return -1;
    }

    public int updateTailAngleTimer(boolean isBegging, float tailAngleTimer) {
        return isBegging ? 10 : 0;
    }

    public boolean shouldBegForItem(boolean watchingPlayer, boolean hasPath, boolean angry, Entity lookedAtEntity, boolean tamed, ItemStack heldItem, int healthWatcherValue) {
        if (!watchingPlayer || hasPath || angry || !(lookedAtEntity instanceof EntityHuman) || heldItem == null) {
            return false;
        }
        if (!tamed && heldItem.id == Item.BONE.id) {
            return true;
        }
        if (tamed && Item.byId[heldItem.id] instanceof ItemFood) {
            return ((ItemFood) Item.byId[heldItem.id]).l();
        }
        return false;
    }

    public boolean isSitting(byte flags) {
        return (flags & SITTING_MASK) != 0;
    }

    public byte withSitting(byte flags, boolean sitting) {
        return sitting ? (byte) (flags | SITTING_MASK) : (byte) (flags & ~SITTING_MASK);
    }

    public boolean isAngry(byte flags) {
        return (flags & ANGRY_MASK) != 0;
    }

    public byte withAngry(byte flags, boolean angry) {
        return angry ? (byte) (flags | ANGRY_MASK) : (byte) (flags & ~ANGRY_MASK);
    }

    public boolean isTamed(byte flags) {
        return (flags & TAMED_MASK) != 0;
    }

    public byte withTamed(byte flags, boolean tamed) {
        return tamed ? (byte) (flags | TAMED_MASK) : (byte) (flags & ~TAMED_MASK);
    }
}
