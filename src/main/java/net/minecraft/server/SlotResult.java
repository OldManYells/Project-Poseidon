package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.CraftingResultSlotBehaviour;

public class SlotResult extends Slot {
    private static final CraftingResultSlotBehaviour CRAFTING_RESULT_SLOT_BEHAVIOUR = CraftingResultSlotBehaviour.getInstance();

    private final IInventory d;
    private EntityHuman e;

    public SlotResult(EntityHuman entityhuman, IInventory iinventory, IInventory iinventory1, int i, int j, int k) {
        super(iinventory1, i, j, k);
        this.e = entityhuman;
        this.d = iinventory;
    }

    public boolean isAllowed(ItemStack itemstack) {
        return CRAFTING_RESULT_SLOT_BEHAVIOUR.isAllowed(itemstack);
    }

    public void a(ItemStack itemstack) {
        CRAFTING_RESULT_SLOT_BEHAVIOUR.onCrafted(itemstack, this.e, this.d);
    }
}
