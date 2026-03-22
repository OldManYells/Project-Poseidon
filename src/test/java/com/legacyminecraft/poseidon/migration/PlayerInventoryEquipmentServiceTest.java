package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.inventory.PlayerInventoryEquipmentBehaviour;
import net.minecraft.server.ItemStack;
import org.junit.Assert;
import org.junit.Test;

public class PlayerInventoryEquipmentServiceTest {
    @Test
    public void calculateArmorValueUsesLegacyFormula() {
        PlayerInventoryEquipmentBehaviour service = PlayerInventoryEquipmentBehaviour.getInstance();
        ItemStack[] armor = new ItemStack[4];
        ItemStack helmet = new ItemStack(1, 1, 0);
        ItemStack chest = new ItemStack(2, 1, 0);
        armor[0] = helmet;
        armor[1] = chest;

        int value = service.calculateArmorValue(armor, new PlayerInventoryEquipmentBehaviour.ArmorStatsResolver() {
            @Override
            public boolean isArmor(ItemStack stack) {
                return true;
            }

            @Override
            public int getMaxDurability(ItemStack stack) {
                return stack.id == 1 ? 10 : 20;
            }

            @Override
            public int getCurrentDamage(ItemStack stack) {
                return stack.id == 1 ? 2 : 5;
            }

            @Override
            public int getArmorReduction(ItemStack stack) {
                return stack.id == 1 ? 3 : 5;
            }
        });

        Assert.assertEquals(6, value);
    }

    @Test
    public void damageArmorBreaksAndClearsBrokenSlots() {
        PlayerInventoryEquipmentBehaviour service = PlayerInventoryEquipmentBehaviour.getInstance();
        ItemStack[] armor = new ItemStack[4];
        ItemStack helmet = new ItemStack(1, 1, 0);
        armor[0] = helmet;
        final int[] brokenCalls = new int[]{0};

        service.damageArmor(armor, 1, new PlayerInventoryEquipmentBehaviour.ArmorDamageCallbacks() {
            @Override
            public boolean isArmor(ItemStack stack) {
                return true;
            }

            @Override
            public void damage(ItemStack stack, int amount) {
                stack.count = 0;
            }

            @Override
            public void onBroken(ItemStack stack) {
                brokenCalls[0]++;
            }
        });

        Assert.assertEquals(1, brokenCalls[0]);
        Assert.assertNull(armor[0]);
    }

    @Test
    public void dropAllDropsAndClearsMainAndArmorArrays() {
        PlayerInventoryEquipmentBehaviour service = PlayerInventoryEquipmentBehaviour.getInstance();
        ItemStack[] items = new ItemStack[36];
        ItemStack[] armor = new ItemStack[4];
        items[0] = new ItemStack(1, 2, 0);
        armor[1] = new ItemStack(2, 1, 0);
        final int[] dropped = new int[]{0};

        service.dropAll(items, armor, new PlayerInventoryEquipmentBehaviour.DropSink() {
            @Override
            public void drop(ItemStack stack) {
                dropped[0]++;
            }
        });

        Assert.assertEquals(2, dropped[0]);
        Assert.assertNull(items[0]);
        Assert.assertNull(armor[1]);
    }

    @Test
    public void containsChecksArmorAndMainInventory() {
        PlayerInventoryEquipmentBehaviour service = PlayerInventoryEquipmentBehaviour.getInstance();
        ItemStack[] items = new ItemStack[36];
        ItemStack[] armor = new ItemStack[4];
        ItemStack target = new ItemStack(3, 1, 0);
        items[2] = target.cloneItemStack();

        Assert.assertTrue(service.contains(armor, items, target));
        Assert.assertFalse(service.contains(armor, items, new ItemStack(4, 1, 0)));
    }
}
