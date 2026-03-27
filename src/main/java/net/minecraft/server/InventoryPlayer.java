package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.PlayerInventoryStorageBehaviour;
import com.legacyminecraft.poseidon.inventory.PlayerInventoryNbtCodecBehaviour;
import com.legacyminecraft.poseidon.inventory.PlayerInventoryEquipmentBehaviour;

public class InventoryPlayer implements IInventory {

    public ItemStack[] items = new ItemStack[36];
    public ItemStack[] armor = new ItemStack[4];
    public int itemInHandIndex = 0;
    public EntityHuman d; // CraftBukkit - private -> public
    private ItemStack f;
    public boolean e = false;
    private final PlayerInventoryStorageBehaviour playerInventoryStorageService = PlayerInventoryStorageBehaviour.getInstance();
    private final PlayerInventoryNbtCodecBehaviour playerInventoryNbtCodecService = PlayerInventoryNbtCodecBehaviour.getInstance();
    private final PlayerInventoryEquipmentBehaviour playerInventoryEquipmentService = PlayerInventoryEquipmentBehaviour.getInstance();

    // CraftBukkit start
    public ItemStack[] getContents() {
        return this.items;
    }

    public ItemStack[] getArmorContents() {
        return this.armor;
    }
    // CraftBukkit end

    public InventoryPlayer(EntityHuman entityhuman) {
        this.d = entityhuman;
    }

    public ItemStack getItemInHand() {
        return (ItemStack) playerInventoryStorageService.getItemInHand(this.items, this.itemInHandIndex);
    }

    public static int e() {
        return PlayerInventoryStorageBehaviour.getInstance().hotbarSize();
    }

    // CraftBukkit start - watch method above! :D
    public int canHold(ItemStack itemstack) {
        return playerInventoryStorageService.canHold(this.items, itemstack, this.getMaxStackSize());
    }
    // CraftBukkit end

    public void f() {
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] != null) {
                this.items[i].a(this.d.world, this.d, i, this.itemInHandIndex == i);
            }
        }
    }

    public boolean b(int i) {
        return playerInventoryStorageService.consumeByItemId(this.items, i);
    }

    public boolean pickup(ItemStack itemstack) {
        return playerInventoryStorageService.pickup(this.items, itemstack, this.getMaxStackSize());
    }

    public ItemStack splitStack(int i, int j) {
        return (ItemStack) playerInventoryStorageService.splitCombined(this.items, this.armor, i, j);
    }

    public void setItem(int i, ItemStack itemstack) {
        playerInventoryStorageService.setCombined(this.items, this.armor, i, itemstack);
    }

    public float a(Block block) {
        float f = 1.0F;

        if (this.items[this.itemInHandIndex] != null) {
            f *= this.items[this.itemInHandIndex].a(block);
        }

        return f;
    }

    public NBTTagList a(NBTTagList nbttaglist) {
        return (NBTTagList) playerInventoryNbtCodecService.writeInventory(nbttaglist, this.items, this.armor);
    }

    public void b(NBTTagList nbttaglist) {
        PlayerInventoryNbtCodecBehaviour.InventoryState inventoryState =
                playerInventoryNbtCodecService.readInventory(nbttaglist, 36, 4);
        this.items = (ItemStack[]) inventoryState.getItems();
        this.armor = (ItemStack[]) inventoryState.getArmor();
    }

    public int getSize() {
        return playerInventoryStorageService.combinedSize(this.items, this.armor);
    }

    public ItemStack getItem(int i) {
        return (ItemStack) playerInventoryStorageService.getCombined(this.items, this.armor, i);
    }

    public String getName() {
        return "Inventory";
    }

    public int getMaxStackSize() {
        return 64;
    }

    public int a(Entity entity) {
        ItemStack itemstack = this.getItem(this.itemInHandIndex);

        return itemstack != null ? itemstack.a(entity) : 1;
    }

    public boolean b(Block block) {
        if (block.material.i()) {
            return true;
        } else {
            ItemStack itemstack = this.getItem(this.itemInHandIndex);

            return itemstack != null ? itemstack.b(block) : false;
        }
    }

    public int g() {
        return playerInventoryEquipmentService.calculateArmorValue(
                this.armor,
                new PlayerInventoryEquipmentBehaviour.ArmorStatsResolver() {
                    @Override
                    public boolean isArmor(Object stack) {
                        ItemStack itemStack = (ItemStack) stack;
                        return itemStack.getItem() instanceof ItemArmor;
                    }

                    @Override
                    public int getMaxDurability(Object stack) {
                        ItemStack itemStack = (ItemStack) stack;
                        return itemStack.i();
                    }

                    @Override
                    public int getCurrentDamage(Object stack) {
                        ItemStack itemStack = (ItemStack) stack;
                        return itemStack.g();
                    }

                    @Override
                    public int getArmorReduction(Object stack) {
                        ItemStack itemStack = (ItemStack) stack;
                        return ((ItemArmor) itemStack.getItem()).bl;
                    }
                }
        );
    }

    public void c(int i) {
        playerInventoryEquipmentService.damageArmor(
                this.armor,
                i,
                new PlayerInventoryEquipmentBehaviour.ArmorDamageCallbacks() {
                    @Override
                    public boolean isArmor(Object stack) {
                        ItemStack itemStack = (ItemStack) stack;
                        return itemStack.getItem() instanceof ItemArmor;
                    }

                    @Override
                    public void damage(Object stack, int amount) {
                        ItemStack itemStack = (ItemStack) stack;
                        itemStack.damage(amount, InventoryPlayer.this.d);
                    }

                    @Override
                    public void onBroken(Object stack) {
                        ItemStack itemStack = (ItemStack) stack;
                        itemStack.a(InventoryPlayer.this.d);
                    }
                }
        );
    }

    public void h() {
        playerInventoryEquipmentService.dropAll(
                this.items,
                this.armor,
                new PlayerInventoryEquipmentBehaviour.DropSink() {
                    @Override
                    public void drop(Object stack) {
                        InventoryPlayer.this.d.a((ItemStack) stack, true);
                    }
                }
        );
    }

    public void update() {
        this.e = true;
    }

    public void b(ItemStack itemstack) {
        this.f = itemstack;
        this.d.a(itemstack);
    }

    public ItemStack j() {
        return this.f;
    }

    public boolean a_(EntityHuman entityhuman) {
        return this.d.dead ? false : entityhuman.g(this.d) <= 64.0D;
    }

    public boolean c(ItemStack itemstack) {
        return playerInventoryEquipmentService.contains(this.armor, this.items, itemstack);
    }
}
