package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.inventory.CraftingInventoryBehaviour;
import net.minecraft.server.ItemStack;
import org.junit.Assert;
import org.junit.Test;

public class CraftingInventoryServiceTest {
    @Test
    public void createGridStorageMatchesWidthTimesHeight() {
        CraftingInventoryBehaviour service = CraftingInventoryBehaviour.getInstance();

        ItemStack[] storage = service.createGridStorage(3, 2);

        Assert.assertEquals(6, storage.length);
    }

    @Test
    public void getByGridPositionResolvesIndexAndBounds() {
        CraftingInventoryBehaviour service = CraftingInventoryBehaviour.getInstance();
        ItemStack[] storage = service.createGridStorage(2, 2);
        ItemStack stack = new ItemStack(1, 4, 0);
        storage[3] = stack;

        Assert.assertSame(stack, service.getByGridPosition(storage, 2, 1, 1));
        Assert.assertNull(service.getByGridPosition(storage, 2, -1, 0));
        Assert.assertNull(service.getByGridPosition(storage, 2, 3, 0));
    }

    @Test
    public void splitRemovesWholeStackWhenAmountIsEnough() {
        CraftingInventoryBehaviour service = CraftingInventoryBehaviour.getInstance();
        ItemStack[] storage = service.createGridStorage(1, 1);
        ItemStack stack = new ItemStack(1, 2, 0);
        storage[0] = stack;

        CraftingInventoryBehaviour.SplitResult result = service.split(storage, 0, 2);

        Assert.assertTrue(result.isChanged());
        Assert.assertSame(stack, result.getItemStack());
        Assert.assertNull(storage[0]);
    }

    @Test
    public void splitPartiallyReducesStackCount() {
        CraftingInventoryBehaviour service = CraftingInventoryBehaviour.getInstance();
        ItemStack[] storage = service.createGridStorage(1, 1);
        storage[0] = new ItemStack(1, 5, 0);

        CraftingInventoryBehaviour.SplitResult result = service.split(storage, 0, 2);

        Assert.assertTrue(result.isChanged());
        Assert.assertEquals(2, result.getItemStack().count);
        Assert.assertEquals(3, storage[0].count);
    }
}
