package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.player.PlayerDeathHandlingSystem;
import net.minecraft.server.ItemStack;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PlayerDeathHandlingServiceTest {
    @Test
    public void collectLootIncludesOnlyNonNullInventoryEntries() {
        PlayerDeathHandlingSystem service = PlayerDeathHandlingSystem.getInstance();
        ItemStack[] inventory = new ItemStack[]{new ItemStack(1, 2, 0), null};
        ItemStack[] armor = new ItemStack[]{null, new ItemStack(2, 1, 0)};

        List<org.bukkit.inventory.ItemStack> loot = service.collectLoot(inventory, armor);

        Assert.assertEquals(2, loot.size());
        Assert.assertEquals(1, loot.get(0).getTypeId());
        Assert.assertEquals(2, loot.get(0).getAmount());
        Assert.assertEquals(2, loot.get(1).getTypeId());
        Assert.assertEquals(1, loot.get(1).getAmount());
    }

    @Test
    public void clearInventoryRespectsKeepInventoryFlag() {
        PlayerDeathHandlingSystem service = PlayerDeathHandlingSystem.getInstance();
        ItemStack[] inventory = new ItemStack[]{new ItemStack(1, 1, 0)};
        ItemStack[] armor = new ItemStack[]{new ItemStack(2, 1, 0)};

        service.clearInventoryIfNeeded(true, inventory, armor);
        Assert.assertNotNull(inventory[0]);
        Assert.assertNotNull(armor[0]);

        service.clearInventoryIfNeeded(false, inventory, armor);
        Assert.assertNull(inventory[0]);
        Assert.assertNull(armor[0]);
    }

    @Test
    public void deathMessageBroadcastRuleSkipsBlankMessages() {
        PlayerDeathHandlingSystem service = PlayerDeathHandlingSystem.getInstance();

        Assert.assertFalse(service.shouldBroadcastDeathMessage(null));
        Assert.assertFalse(service.shouldBroadcastDeathMessage("   "));
        Assert.assertTrue(service.shouldBroadcastDeathMessage("Alex died"));
    }
}
