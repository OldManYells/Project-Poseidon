package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.PlayerContainerBehaviour;

public class ContainerPlayer extends Container {
    private static final PlayerContainerBehaviour PLAYER_CONTAINER_BEHAVIOUR = PlayerContainerBehaviour.getInstance();

    public InventoryCrafting craftInventory = PLAYER_CONTAINER_BEHAVIOUR.createCraftInventory(this);
    public IInventory resultInventory = PLAYER_CONTAINER_BEHAVIOUR.createResultInventory();
    public boolean c;

    public ContainerPlayer(InventoryPlayer inventoryplayer) {
        this(inventoryplayer, true);
    }

    public ContainerPlayer(InventoryPlayer inventoryplayer, boolean flag) {
        this.c = false;
        this.c = flag;
        PLAYER_CONTAINER_BEHAVIOUR.initializeSlots(this, inventoryplayer, this.craftInventory, this.resultInventory, new PlayerContainerBehaviour.ArmorSlotFactory() {
            public Slot create(int index) {
                return new SlotArmor(ContainerPlayer.this, inventoryplayer, inventoryplayer.getSize() - 1 - index, 8, 8 + index * 18, index);
            }
        });

        this.a((IInventory) this.craftInventory);
    }

    public void a(IInventory iinventory) {
        PLAYER_CONTAINER_BEHAVIOUR.updateCraftResult(this.craftInventory, this.resultInventory, super.listeners);
    }

    public void a(EntityHuman entityhuman) {
        super.a(entityhuman);
        PLAYER_CONTAINER_BEHAVIOUR.onClose(this, entityhuman, this.craftInventory);
    }

    public boolean b(EntityHuman entityhuman) {
        return PLAYER_CONTAINER_BEHAVIOUR.canUse(entityhuman);
    }

    public ItemStack a(int i) {
        return PLAYER_CONTAINER_BEHAVIOUR.quickMove(this, this.e, i);
    }
}
