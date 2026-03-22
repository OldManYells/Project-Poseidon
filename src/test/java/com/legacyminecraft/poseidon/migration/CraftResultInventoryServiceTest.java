package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.inventory.CraftResultInventoryBehaviour;
import net.minecraft.server.ItemStack;
import org.junit.Assert;
import org.junit.Test;

public class CraftResultInventoryServiceTest {
    @Test
    public void splitReturnsAndClearsStoredItem() {
        CraftResultInventoryBehaviour service = CraftResultInventoryBehaviour.getInstance();
        ItemStack[] storage = service.createStorage();
        ItemStack result = new ItemStack(1, 1, 0);
        service.set(storage, 0, result);

        ItemStack split = service.split(storage, 0);

        Assert.assertSame(result, split);
        Assert.assertNull(service.get(storage, 0));
    }

    @Test
    public void splitReturnsNullWhenSlotIsEmpty() {
        CraftResultInventoryBehaviour service = CraftResultInventoryBehaviour.getInstance();
        ItemStack[] storage = service.createStorage();

        Assert.assertNull(service.split(storage, 0));
    }
}
