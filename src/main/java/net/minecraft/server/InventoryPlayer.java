package net.minecraft.server;

import org.bukkit.craftbukkit.entity.Entity;
import org.bukkit.craftbukkit.entity.EntityHuman;
import org.bukkit.craftbukkit.item.ItemArmor;
import org.bukkit.craftbukkit.item.ItemStack;

public class InventoryPlayer implements IInventory {

    public ItemStack[] items = new ItemStack[36];
    public ItemStack[] armor = new ItemStack[4];

    // Better name would be "selectedSlot", but kept for compatibility.
    public int itemInHandIndex = 0;

    // Obfuscated public field kept for compatibility: player/owner
    public EntityHuman d;

    // Obfuscated private field: carried/cursor item
    private ItemStack f;

    // Obfuscated public field kept for compatibility: dirty flag
    public boolean e = false;

    // CraftBukkit start
    public ItemStack[] getContents() {
        return this.items;
    }

    public ItemStack[] getArmorContents() {
        return this.armor;
    }
    // CraftBukkit end

    public InventoryPlayer(EntityHuman player) {
        this.d = player;
    }

    public ItemStack getItemInHand() {
        return this.itemInHandIndex >= 0 && this.itemInHandIndex < 9
                ? this.items[this.itemInHandIndex]
                : null;
    }

    public static int getHotbarSize() {
        return 9;
    }

    private int findSlotWithItem(int itemId) {
        for (int slot = 0; slot < this.items.length; ++slot) {
            if (this.items[slot] != null && this.items[slot].id == itemId) {
                return slot;
            }
        }

        return -1;
    }

    private int findPartialStack(ItemStack stack) {
        for (int slot = 0; slot < this.items.length; ++slot) {
            if (this.items[slot] != null
                    && this.items[slot].id == stack.id
                    && this.items[slot].isStackable()
                    && this.items[slot].count < this.items[slot].getMaxStackSize()
                    && this.items[slot].count < this.getMaxStackSize()
                    && (!this.items[slot].usesData() || this.items[slot].getData() == stack.getData())) {
                return slot;
            }
        }

        return -1;
    }

    // CraftBukkit start - watch method above! :D
    public int canHold(ItemStack stack) {
        int remaining = stack.count;

        for (int slot = 0; slot < this.items.length; ++slot) {
            if (this.items[slot] == null) {
                return stack.count;
            }

            if (this.items[slot] != null
                    && this.items[slot].id == stack.id
                    && this.items[slot].isStackable()
                    && this.items[slot].count < this.items[slot].getMaxStackSize()
                    && this.items[slot].count < this.getMaxStackSize()
                    && (!this.items[slot].usesData() || this.items[slot].getData() == stack.getData())) {

                remaining -= Math.min(this.items[slot].getMaxStackSize(), this.getMaxStackSize()) - this.items[slot].count;
            }

            if (remaining <= 0) {
                return stack.count;
            }
        }

        return stack.count - remaining;
    }
    // CraftBukkit end

    private int getFirstEmptySlot() {
        for (int slot = 0; slot < this.items.length; ++slot) {
            if (this.items[slot] == null) {
                return slot;
            }
        }

        return -1;
    }

    private int storePartialItemStack(ItemStack stack) {
        int itemId = stack.id;
        int remaining = stack.count;
        int slot = this.findPartialStack(stack);

        if (slot < 0) {
            slot = this.getFirstEmptySlot();
        }

        if (slot < 0) {
            return remaining;
        }

        if (this.items[slot] == null) {
            this.items[slot] = new ItemStack(itemId, 0, stack.getData());
        }

        int moved = remaining;

        if (moved > this.items[slot].getMaxStackSize() - this.items[slot].count) {
            moved = this.items[slot].getMaxStackSize() - this.items[slot].count;
        }

        if (moved > this.getMaxStackSize() - this.items[slot].count) {
            moved = this.getMaxStackSize() - this.items[slot].count;
        }

        if (moved == 0) {
            return remaining;
        }

        remaining -= moved;
        this.items[slot].count += moved;
        this.items[slot].animationDelay = 5;
        return remaining;
    }

    public void tick() {
        for (int slot = 0; slot < this.items.length; ++slot) {
            if (this.items[slot] != null) {
                this.items[slot].inventoryTick(this.d.world, this.d, slot, this.itemInHandIndex == slot);
            }
        }
    }

    public boolean consumeItem(int itemId) {
        int slot = this.findSlotWithItem(itemId);

        if (slot < 0) {
            return false;
        }

        if (--this.items[slot].count <= 0) {
            this.items[slot] = null;
        }

        return true;
    }

    public boolean pickup(ItemStack stack) {
        int previousCount;

        if (stack.isDamaged()) {
            int slot = this.getFirstEmptySlot();
            if (slot >= 0) {
                this.items[slot] = ItemStack.copyOrNull(stack);
                this.items[slot].animationDelay = 5;
                stack.count = 0;
                return true;
            }

            return false;
        }

        do {
            previousCount = stack.count;
            stack.count = this.storePartialItemStack(stack);
        } while (stack.count > 0 && stack.count < previousCount);

        return stack.count < previousCount;
    }

    public ItemStack splitStack(int slot, int amount) {
        ItemStack[] target = this.items;

        if (slot >= this.items.length) {
            target = this.armor;
            slot -= this.items.length;
        }

        if (target[slot] == null) {
            return null;
        }

        if (target[slot].count <= amount) {
            ItemStack result = target[slot];
            target[slot] = null;
            return result;
        }

        ItemStack result = target[slot].splitStack(amount);
        if (target[slot].count == 0) {
            target[slot] = null;
        }

        return result;
    }

    public void setItem(int slot, ItemStack stack) {
        ItemStack[] target = this.items;

        if (slot >= target.length) {
            slot -= target.length;
            target = this.armor;
        }

        target[slot] = stack;
    }

    public float getDestroySpeed(CraftBlock block) {
        float speed = 1.0F;

        if (this.items[this.itemInHandIndex] != null) {
            speed *= this.items[this.itemInHandIndex].getStrVsBlock(block);
        }

        return speed;
    }

    public NBTTagList writeToNBT(NBTTagList list) {
        for (int slot = 0; slot < this.items.length; ++slot) {
            if (this.items[slot] != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.a("Slot", (byte) slot);
                this.items[slot].writeToNBT(tag);
                list.a((NBTBase) tag);
            }
        }

        for (int slot = 0; slot < this.armor.length; ++slot) {
            if (this.armor[slot] != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.a("Slot", (byte) (slot + 100));
                this.armor[slot].writeToNBT(tag);
                list.a((NBTBase) tag);
            }
        }

        return list;
    }

    public void readFromNBT(NBTTagList list) {
        this.items = new ItemStack[36];
        this.armor = new ItemStack[4];

        for (int i = 0; i < list.c(); ++i) {
            NBTTagCompound tag = (NBTTagCompound) list.a(i);
            int slot = tag.c("Slot") & 255;
            ItemStack stack = new ItemStack(tag);

            if (stack.getItem() != null) {
                if (slot >= 0 && slot < this.items.length) {
                    this.items[slot] = stack;
                }

                if (slot >= 100 && slot < this.armor.length + 100) {
                    this.armor[slot - 100] = stack;
                }
            }
        }
    }

    public int getSize() {
        return this.items.length + 4;
    }

    public ItemStack getItem(int slot) {
        ItemStack[] target = this.items;

        if (slot >= target.length) {
            slot -= target.length;
            target = this.armor;
        }

        return target[slot];
    }

    public String getName() {
        return "Inventory";
    }

    public int getMaxStackSize() {
        return 64;
    }

    public int getDamageAgainstEntity(Entity entity) {
        ItemStack held = this.getItem(this.itemInHandIndex);
        return held != null ? held.getDamageVsEntity(entity) : 1;
    }

    public boolean canHarvestBlock(CraftBlock block) {
        if (block.material.i()) {
            return true;
        }

        ItemStack held = this.getItem(this.itemInHandIndex);
        return held != null ? held.canHarvestBlock(block) : false;
    }

    public int getArmorValue() {
        int armorPoints = 0;
        int remainingDurability = 0;
        int totalDurability = 0;

        for (int slot = 0; slot < this.armor.length; ++slot) {
            if (this.armor[slot] != null && this.armor[slot].getItem() instanceof ItemArmor) {
                int maxDamage = this.armor[slot].getMaxDamage();
                int currentDamage = this.armor[slot].getDamage();
                int durabilityLeft = maxDamage - currentDamage;

                remainingDurability += durabilityLeft;
                totalDurability += maxDamage;
                armorPoints += ((ItemArmor) this.armor[slot].getItem()).bl;
            }
        }

        if (totalDurability == 0) {
            return 0;
        }

        return (armorPoints - 1) * remainingDurability / totalDurability + 1;
    }

    public void damageArmor(int amount) {
        for (int slot = 0; slot < this.armor.length; ++slot) {
            if (this.armor[slot] != null && this.armor[slot].getItem() instanceof ItemArmor) {
                this.armor[slot].damage(amount, this.d);
                if (this.armor[slot].count == 0) {
                    this.armor[slot].a(this.d);
                    this.armor[slot] = null;
                }
            }
        }
    }

    public void dropAllItems() {
        for (int slot = 0; slot < this.items.length; ++slot) {
            if (this.items[slot] != null) {
                this.d.a(this.items[slot], true);
                this.items[slot] = null;
            }
        }

        for (int slot = 0; slot < this.armor.length; ++slot) {
            if (this.armor[slot] != null) {
                this.d.a(this.armor[slot], true);
                this.armor[slot] = null;
            }
        }
    }

    public void markDirty() {
        this.e = true;
    }

    public void setCarriedItem(ItemStack stack) {
        this.f = stack;
        this.d.a(stack);
    }

    public ItemStack getCarriedItem() {
        return this.f;
    }

    public boolean isUsableByPlayer(EntityHuman player) {
        return this.d.dead ? false : player.g(this.d) <= 64.0D;
    }

    public boolean containsExact(ItemStack stack) {
        for (int slot = 0; slot < this.armor.length; ++slot) {
            if (this.armor[slot] != null && this.armor[slot].isStackExactlyEqual(stack)) {
                return true;
            }
        }

        for (int slot = 0; slot < this.items.length; ++slot) {
            if (this.items[slot] != null && this.items[slot].isStackExactlyEqual(stack)) {
                return true;
            }
        }

        return false;
    }

    // ---------------------------------------------------------------------
    // Compatibility bridge methods for old obfuscated call sites
    // ---------------------------------------------------------------------

    public static int e() {
        return getHotbarSize();
    }

    public void f() {
        this.tick();
    }

    public boolean b(int itemId) {
        return this.consumeItem(itemId);
    }

    public float a(CraftBlock block) {
        return this.getDestroySpeed(block);
    }

    public NBTTagList a(NBTTagList list) {
        return this.writeToNBT(list);
    }

    public void b(NBTTagList list) {
        this.readFromNBT(list);
    }

    public int a(Entity entity) {
        return this.getDamageAgainstEntity(entity);
    }

    public boolean b(CraftBlock block) {
        return this.canHarvestBlock(block);
    }

    public int g() {
        return this.getArmorValue();
    }

    public void c(int amount) {
        this.damageArmor(amount);
    }

    public void h() {
        this.dropAllItems();
    }

    public void update() {
        this.markDirty();
    }

    public void b(ItemStack stack) {
        this.setCarriedItem(stack);
    }

    public ItemStack j() {
        return this.getCarriedItem();
    }

    public boolean a_(EntityHuman player) {
        return this.isUsableByPlayer(player);
    }

    public boolean c(ItemStack stack) {
        return this.containsExact(stack);
    }
}