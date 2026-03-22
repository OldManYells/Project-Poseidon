package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.inventory.PlayerInventoryStorageBehaviour;
import net.minecraft.server.ItemStack;
import org.junit.Assert;
import org.junit.Test;

public class PlayerInventoryStorageServiceTest {
    @Test
    public void itemInHandRespectsHotbarBounds() {
        PlayerInventoryStorageBehaviour service = PlayerInventoryStorageBehaviour.getInstance();
        ItemStack[] items = new ItemStack[36];
        ItemStack hand = new ItemStack(1, 1, 0);
        items[2] = hand;

        Assert.assertSame(hand, service.getItemInHand(items, 2));
        Assert.assertNull(service.getItemInHand(items, 12));
        Assert.assertNull(service.getItemInHand(items, -1));
    }

    @Test
    public void combinedSetGetSplitRouteAcrossItemsAndArmor() {
        PlayerInventoryStorageBehaviour service = PlayerInventoryStorageBehaviour.getInstance();
        ItemStack[] items = new ItemStack[36];
        ItemStack[] armor = new ItemStack[4];
        ItemStack chestplate = new ItemStack(2, 3, 0);
        service.setCombined(items, armor, 37, chestplate);

        Assert.assertEquals(40, service.combinedSize(items, armor));
        Assert.assertSame(chestplate, service.getCombined(items, armor, 37));

        ItemStack split = service.splitCombined(items, armor, 37, 2);
        Assert.assertEquals(2, split.count);
        Assert.assertEquals(1, armor[1].count);
    }

    @Test
    public void consumeByItemIdDecrementsAndClearsSlot() {
        PlayerInventoryStorageBehaviour service = PlayerInventoryStorageBehaviour.getInstance();
        ItemStack[] items = new ItemStack[36];
        items[0] = new ItemStack(5, 2, 0);

        Assert.assertTrue(service.consumeByItemId(items, 5));
        Assert.assertEquals(1, items[0].count);
        Assert.assertTrue(service.consumeByItemId(items, 5));
        Assert.assertNull(items[0]);
        Assert.assertFalse(service.consumeByItemId(items, 5));
    }
}
