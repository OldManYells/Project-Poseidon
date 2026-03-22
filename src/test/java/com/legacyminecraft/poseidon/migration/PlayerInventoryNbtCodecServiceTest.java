package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.inventory.PlayerInventoryNbtCodecBehaviour;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import org.junit.Assert;
import org.junit.Test;

public class PlayerInventoryNbtCodecServiceTest {
    @Test
    public void writeInventoryStoresMainAndArmorSlotsWithLegacyOffsets() {
        PlayerInventoryNbtCodecBehaviour service = PlayerInventoryNbtCodecBehaviour.getInstance();
        ItemStack[] items = new ItemStack[36];
        ItemStack[] armor = new ItemStack[4];
        items[2] = new ItemStack(1, 4, 0);
        armor[1] = new ItemStack(3, 1, 0);

        NBTTagList encoded = service.writeInventory(new NBTTagList(), items, armor);

        Assert.assertEquals(2, encoded.c());
        NBTTagCompound first = (NBTTagCompound) encoded.a(0);
        NBTTagCompound second = (NBTTagCompound) encoded.a(1);
        Assert.assertEquals(2, first.c("Slot") & 255);
        Assert.assertEquals(101, second.c("Slot") & 255);
    }

    @Test
    public void readInventoryWithEmptyListCreatesFreshArrays() {
        PlayerInventoryNbtCodecBehaviour service = PlayerInventoryNbtCodecBehaviour.getInstance();
        PlayerInventoryNbtCodecBehaviour.InventoryState decoded = service.readInventory(new NBTTagList(), 36, 4);

        Assert.assertEquals(36, decoded.getItems().length);
        Assert.assertEquals(4, decoded.getArmor().length);
        Assert.assertNull(decoded.getItems()[0]);
        Assert.assertNull(decoded.getArmor()[0]);
    }
}
