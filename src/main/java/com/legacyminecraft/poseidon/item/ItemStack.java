package com.legacyminecraft.poseidon.item;

/**
 * Canonical item-stack scaffold used by migrated item/map behaviour.
 */
public class ItemStack {
    public int count;
    public int b;
    public int id;
    public int damage;

    public ItemStack(int id, int count, int damage) {
        this.id = id;
        this.count = count;
        this.damage = damage;
    }

    public int getMaxStackSize() {
        Item item = getItem();
        return item == null ? 64 : item.getMaxStackSize();
    }

    public boolean d() {
        Item item = getItem();
        return item != null && item.e() > 0;
    }

    public boolean f() {
        return d() && damage > 0;
    }

    public boolean countIdDamageEquals(ItemStack other) {
        return other != null && this.count == other.count && this.id == other.id && this.damage == other.damage;
    }

    public ItemStack cloneItemStack() {
        return new ItemStack(this.id, this.count, this.damage);
    }

    public int getData() {
        return this.damage;
    }

    public void b(int data) {
        this.damage = data;
    }

    public Item getItem() {
        if (id < 0 || id >= Item.byId.length) {
            return null;
        }
        return Item.byId[id];
    }

    public int i() {
        Item item = getItem();
        return item == null ? 0 : item.e();
    }
}
