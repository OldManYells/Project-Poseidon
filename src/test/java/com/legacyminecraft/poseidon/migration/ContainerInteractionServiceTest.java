package com.legacyminecraft.poseidon.migration;

import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ICrafting;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Slot;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ContainerInteractionServiceTest {

    @Test
    public void addSlotTracksIndexAndSnapshot() {
        TestContainer container = new TestContainer();
        TestInventory inventory = new TestInventory(1);
        Slot slot = new Slot(inventory, 0, 0, 0);

        container.addTestSlot(slot);

        Assert.assertEquals(0, slot.a);
        Assert.assertEquals(1, container.e.size());
        Assert.assertEquals(1, container.d.size());
        Assert.assertNull(container.d.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void duplicateListenerRegistrationIsRejected() {
        TestContainer container = new TestContainer();
        RecordingCrafter crafter = new RecordingCrafter();

        container.a(crafter);
        container.a(crafter);
    }

    @Test
    public void slotChangeBroadcastSendsUpdatedItemCopy() {
        TestContainer container = new TestContainer();
        TestInventory inventory = new TestInventory(1);
        Slot slot = new Slot(inventory, 0, 0, 0);
        container.addTestSlot(slot);
        inventory.setItem(0, new ItemStack(1, 2, 0));

        RecordingCrafter crafter = new RecordingCrafter();
        container.a(crafter);
        int baselineUpdates = crafter.slotUpdateCalls;

        inventory.setItem(0, new ItemStack(1, 5, 0));
        container.a();

        Assert.assertEquals(baselineUpdates + 1, crafter.slotUpdateCalls);
        Assert.assertNotNull(crafter.lastItem);
        Assert.assertEquals(5, crafter.lastItem.count);
    }

    private static final class TestContainer extends Container {
        public void addTestSlot(Slot slot) {
            super.a(slot);
        }

        @Override
        public boolean b(EntityHuman entityhuman) {
            return true;
        }
    }

    private static final class RecordingCrafter implements ICrafting {
        private int slotUpdateCalls;
        private ItemStack lastItem;

        @Override
        public void a(Container container, List list) {
        }

        @Override
        public void a(Container container, int i, ItemStack itemstack) {
            slotUpdateCalls++;
            lastItem = itemstack;
        }

        @Override
        public void a(Container container, int i, int j) {
        }
    }

    private static final class TestInventory implements IInventory {
        private final ItemStack[] contents;

        private TestInventory(int size) {
            this.contents = new ItemStack[size];
        }

        @Override
        public int getSize() {
            return contents.length;
        }

        @Override
        public ItemStack getItem(int i) {
            return contents[i];
        }

        @Override
        public ItemStack splitStack(int i, int j) {
            ItemStack itemStack = contents[i];
            if (itemStack == null) {
                return null;
            }
            if (itemStack.count <= j) {
                contents[i] = null;
                return itemStack;
            }

            ItemStack split = itemStack.a(j);
            if (itemStack.count == 0) {
                contents[i] = null;
            }
            return split;
        }

        @Override
        public void setItem(int i, ItemStack itemstack) {
            contents[i] = itemstack;
        }

        @Override
        public String getName() {
            return "test";
        }

        @Override
        public int getMaxStackSize() {
            return 64;
        }

        @Override
        public void update() {
        }

        @Override
        public boolean a_(EntityHuman entityhuman) {
            return true;
        }

        @Override
        public ItemStack[] getContents() {
            return contents;
        }
    }
}
