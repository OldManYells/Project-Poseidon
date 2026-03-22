package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.inventory.LargeChestInventoryBehaviour;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import org.junit.Assert;
import org.junit.Test;

public class LargeChestInventoryServiceTest {
    @Test
    public void getAndSetRouteAcrossCombinedIndexes() {
        LargeChestInventoryBehaviour service = LargeChestInventoryBehaviour.getInstance();
        FakeInventory left = new FakeInventory(2, true);
        FakeInventory right = new FakeInventory(2, true);
        ItemStack leftItem = new ItemStack(1, 1, 0);
        ItemStack rightItem = new ItemStack(2, 1, 0);

        service.setItem(left, right, 0, leftItem);
        service.setItem(left, right, 3, rightItem);

        Assert.assertEquals(4, service.getSize(left, right));
        Assert.assertSame(leftItem, service.getItem(left, right, 0));
        Assert.assertSame(rightItem, service.getItem(left, right, 3));
    }

    @Test
    public void splitAndUpdateDelegateToCorrectInventory() {
        LargeChestInventoryBehaviour service = LargeChestInventoryBehaviour.getInstance();
        FakeInventory left = new FakeInventory(1, true);
        FakeInventory right = new FakeInventory(1, true);
        ItemStack rightItem = new ItemStack(3, 2, 0);
        right.setItem(0, rightItem);

        ItemStack split = service.splitStack(left, right, 1, 1);
        service.update(left, right);

        Assert.assertSame(rightItem, split);
        Assert.assertEquals(1, left.updateCalls);
        Assert.assertEquals(1, right.updateCalls);
    }

    @Test
    public void canUseRequiresBothInventories() {
        LargeChestInventoryBehaviour service = LargeChestInventoryBehaviour.getInstance();
        FakeInventory allowed = new FakeInventory(1, true);
        FakeInventory denied = new FakeInventory(1, false);

        Assert.assertFalse(service.canUse(allowed, denied, null));
        Assert.assertTrue(service.canUse(allowed, allowed, null));
    }

    private static final class FakeInventory implements IInventory {
        private final ItemStack[] items;
        private final boolean canUse;
        private int updateCalls;

        private FakeInventory(int size, boolean canUse) {
            this.items = new ItemStack[size];
            this.canUse = canUse;
        }

        @Override
        public int getSize() {
            return items.length;
        }

        @Override
        public ItemStack getItem(int i) {
            return items[i];
        }

        @Override
        public ItemStack splitStack(int i, int j) {
            ItemStack item = items[i];
            items[i] = null;
            return item;
        }

        @Override
        public void setItem(int i, ItemStack itemstack) {
            items[i] = itemstack;
        }

        @Override
        public String getName() {
            return "fake";
        }

        @Override
        public int getMaxStackSize() {
            return 64;
        }

        @Override
        public void update() {
            updateCalls++;
        }

        @Override
        public boolean a_(EntityHuman entityhuman) {
            return canUse;
        }

        @Override
        public ItemStack[] getContents() {
            return items;
        }
    }
}
