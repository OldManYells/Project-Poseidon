package org.bukkit.craftbukkit.item;

import net.minecraft.server.CraftBlock;
import net.minecraft.server.NBTTagCompound;
import org.bukkit.craftbukkit.server.StatisticList;
import org.bukkit.craftbukkit.world.World;
import org.bukkit.craftbukkit.entity.Entity;
import org.bukkit.craftbukkit.entity.EntityHuman;
import org.bukkit.craftbukkit.entity.EntityLiving;
import org.bukkit.craftbukkit.entity.EntityPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemDamageEvent;

public final class ItemStack {

    public int count;
    public int animationDelay;
    public int id;
    public int damage; // CraftBukkit - private -> public

    public ItemStack(CraftBlock baseBlock) {
        this(baseBlock, 1);
    }

    public ItemStack(CraftBlock baseBlock, int i) {
        this(baseBlock.id, i, 0);
    }

    public ItemStack(CraftBlock baseBlock, int i, int j) {
        this(baseBlock.id, i, j);
    }

    public ItemStack(Item item) {
        this(item.id, 1, 0);
    }

    public ItemStack(Item item, int i) {
        this(item.id, i, 0);
    }

    public ItemStack(Item item, int i, int j) {
        this(item.id, i, j);
    }

    public ItemStack(int i, int j, int k) {
        this.count = 0;
        this.id = i;
        this.count = j;
        this.damage = k;
    }

    public ItemStack(NBTTagCompound nbttagcompound) {
        this.count = 0;
        this.readFromNBT(nbttagcompound);
    }

    public ItemStack splitStack(int i) {
        this.count -= i;
        return new ItemStack(this.id, i, this.damage);
    }

    public Item getItem() {
        return Item.byId[this.id];
    }

    public boolean placeItem(EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        boolean flag = this.getItem().a(this, entityhuman, world, i, j, k, l);

        if (flag) {
            entityhuman.a(StatisticList.E[this.id], 1);
        }

        return flag;
    }

    public float getStrVsBlock(CraftBlock baseBlock) {
        return this.getItem().a(this, baseBlock);
    }

    public ItemStack useItemRightClick(World world, EntityHuman entityhuman) {
        return this.getItem().a(this, world, entityhuman);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setShort("id", (short) this.id);
        nbttagcompound.setByte("Count", (byte) this.count);
        nbttagcompound.setShort("Damage", (short) this.damage);
        return nbttagcompound;
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        this.id = nbttagcompound.getShort("id");
        this.count = nbttagcompound.getByte("Count");
        this.damage = nbttagcompound.getShort("Damage");
    }

    public int getMaxStackSize() {
        return this.getItem().getMaxStackSize();
    }

    public boolean isStackable() {
        return this.getMaxStackSize() > 1 && (!this.isDamageable() || !this.isDamaged());
    }

    public boolean isDamageable() {
        return Item.byId[this.id].e() > 0;
    }

    public boolean usesData() {
        return Item.byId[this.id].d();
    }

    public boolean isDamaged() {
        return this.isDamageable() && this.damage > 0;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getData() {
        return this.damage;
    }

    public void b(int i) {
        this.damage = i;
    }

    public int getMaxDamage() {
        return Item.byId[this.id].e();
    }

    @SuppressWarnings("deprecation")
    public void damage(int i, Entity entity) {
        if (this.isDamageable()) {
            if (entity instanceof EntityPlayer) {
                PlayerItemDamageEvent event = new PlayerItemDamageEvent((Player)entity.getBukkitEntity(), new CraftItemStack(this), i);
                event.getPlayer().getServer().getPluginManager().callEvent(event);
                if (i != event.getDamage() || event.isCancelled())
                    event.getPlayer().updateInventory(); 
                if (event.isCancelled())
                    return; 
                i = event.getDamage();
            }
            this.damage += i;
            if (this.damage > this.getMaxDamage()) {
                if (entity instanceof EntityHuman) {
                    ((EntityHuman) entity).a(StatisticList.F[this.id], 1);
                }

                --this.count;
                if (this.count < 0) {
                    this.count = 0;
                }

                this.damage = 0;
            }
        }
    }

    public void useOnEntity(EntityLiving entityliving, EntityHuman entityhuman) {
        boolean flag = Item.byId[this.id].a(this, entityliving, (EntityLiving) entityhuman);

        if (flag) {
            entityhuman.a(StatisticList.E[this.id], 1);
        }
    }

    public void useOnBlock(int i, int j, int k, int l, EntityHuman entityhuman) {
        boolean flag = Item.byId[this.id].a(this, i, j, k, l, entityhuman);

        if (flag) {
            entityhuman.a(StatisticList.E[this.id], 1);
        }
    }

    public int getDamageVsEntity(Entity entity) {
        return Item.byId[this.id].a(entity);
    }

    public boolean canHarvestBlock(CraftBlock baseBlock) {
        return Item.byId[this.id].a(baseBlock);
    }

    public void a(EntityHuman entityhuman) {}

    public void hitEntity(EntityLiving entityliving) {
        Item.byId[this.id].a(this, entityliving);
    }

    public ItemStack cloneItemStack() {
        return new ItemStack(this.id, this.count, this.damage);
    }

    public static boolean equals(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack == null && itemstack1 == null ? true : (itemstack != null && itemstack1 != null ? itemstack.isStackIdentical(itemstack1) : false);
    }

    private boolean isStackIdentical(ItemStack itemstack) {
        return this.count != itemstack.count ? false : (this.id != itemstack.id ? false : this.damage == itemstack.damage);
    }

    public boolean doMaterialsMatch(ItemStack itemstack) {
        return this.id == itemstack.id && this.damage == itemstack.damage;
    }

    public static ItemStack copyOrNull(ItemStack itemstack) {
        return itemstack == null ? null : itemstack.cloneItemStack();
    }

    public String toString() {
        return this.count + "x" + (this.id < 0 ||  this.id >= Item.byId.length ? "missingno" : Item.byId[this.id].a()) + "@" + this.damage; // Project Poseidon: Fixes ArrayIndexOutOfBoundsException
    }

    public void inventoryTick(World world, Entity entity, int i, boolean flag) {
        if (this.animationDelay > 0) {
            --this.animationDelay;
        }

        Item.byId[this.id].a(this, world, entity, i, flag);
    }

    public void onCrafted(World world, EntityHuman entityhuman) {
        entityhuman.a(StatisticList.D[this.id], this.count);
        Item.byId[this.id].c(this, world, entityhuman);
    }

    public boolean isStackExactlyEqual(ItemStack itemstack) {
        return this.id == itemstack.id && this.count == itemstack.count && this.damage == itemstack.damage;
    }
}
