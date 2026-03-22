package com.legacyminecraft.poseidon.world.player;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EntityTracker;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Packet5EntityEquipment;

import java.util.ArrayList;
import java.util.List;

/**
 * Canonical equipment sync orchestration for EntityPlayer wrapper ticks.
 */
public final class PlayerEquipmentSyncSystem {
    private static final int EQUIPMENT_SLOT_COUNT = 5;
    private static final PlayerEquipmentSyncSystem INSTANCE = new PlayerEquipmentSyncSystem();

    private PlayerEquipmentSyncSystem() {
    }

    public static PlayerEquipmentSyncSystem getInstance() {
        return INSTANCE;
    }

    public void syncTrackedEquipment(EntityPlayer player, ItemStack[] trackedEquipment) {
        ItemStack[] currentEquipment = captureEquipment(player);
        List<EquipmentChange> changes = findChanges(currentEquipment, trackedEquipment);
        if (changes.isEmpty()) {
            return;
        }

        EntityTracker tracker = player.b.getTracker(player.dimension);
        for (int i = 0; i < changes.size(); ++i) {
            EquipmentChange change = changes.get(i);
            tracker.a(player, new Packet5EntityEquipment(player.id, change.getSlot(), change.getItemStack()));
            trackedEquipment[change.getSlot()] = change.getItemStack();
        }
    }

    public ItemStack[] captureEquipment(EntityPlayer player) {
        ItemStack[] equipment = new ItemStack[EQUIPMENT_SLOT_COUNT];
        for (int i = 0; i < EQUIPMENT_SLOT_COUNT; ++i) {
            equipment[i] = player.c_(i);
        }
        return equipment;
    }

    public List<EquipmentChange> findChanges(ItemStack[] currentEquipment, ItemStack[] trackedEquipment) {
        List<EquipmentChange> changes = new ArrayList<EquipmentChange>();
        int slotCount = Math.min(currentEquipment.length, trackedEquipment.length);
        for (int i = 0; i < slotCount; ++i) {
            if (currentEquipment[i] != trackedEquipment[i]) {
                changes.add(new EquipmentChange(i, currentEquipment[i]));
            }
        }
        return changes;
    }

    public static final class EquipmentChange {
        private final int slot;
        private final ItemStack itemStack;

        private EquipmentChange(int slot, ItemStack itemStack) {
            this.slot = slot;
            this.itemStack = itemStack;
        }

        public int getSlot() {
            return slot;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }
    }
}
