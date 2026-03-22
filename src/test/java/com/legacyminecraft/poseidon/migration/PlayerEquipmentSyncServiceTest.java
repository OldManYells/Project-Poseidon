package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.player.PlayerEquipmentSyncSystem;
import net.minecraft.server.ItemStack;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PlayerEquipmentSyncServiceTest {
    @Test
    public void findChangesReturnsSlotsWithReferenceChangesOnly() {
        PlayerEquipmentSyncSystem service = PlayerEquipmentSyncSystem.getInstance();

        ItemStack shared = new ItemStack(1, 1, 0);
        ItemStack[] tracked = new ItemStack[]{shared, null, null, null, null};
        ItemStack[] current = new ItemStack[]{shared, null, new ItemStack(2, 1, 0), null, null};

        List<PlayerEquipmentSyncSystem.EquipmentChange> changes = service.findChanges(current, tracked);

        Assert.assertEquals(1, changes.size());
        Assert.assertEquals(2, changes.get(0).getSlot());
        Assert.assertEquals(2, changes.get(0).getItemStack().id);
    }

    @Test
    public void identicalValuesWithDifferentReferenceStillCountAsChange() {
        PlayerEquipmentSyncSystem service = PlayerEquipmentSyncSystem.getInstance();

        ItemStack[] tracked = new ItemStack[]{new ItemStack(1, 1, 0), null, null, null, null};
        ItemStack[] current = new ItemStack[]{new ItemStack(1, 1, 0), null, null, null, null};

        List<PlayerEquipmentSyncSystem.EquipmentChange> changes = service.findChanges(current, tracked);
        Assert.assertEquals(1, changes.size());
        Assert.assertEquals(0, changes.get(0).getSlot());
    }
}
