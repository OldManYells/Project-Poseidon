package net.minecraft.server;

import com.legacyminecraft.poseidon.item.ItemStackInteractionBehaviour;
import com.legacyminecraft.poseidon.item.ItemStackStateBehaviour;

public final class ItemStack {
    private static final ItemStackInteractionBehaviour ITEM_STACK_INTERACTION_BEHAVIOUR = ItemStackInteractionBehaviour.getInstance();
    private static final ItemStackStateBehaviour ITEM_STACK_STATE_BEHAVIOUR = ItemStackStateBehaviour.getInstance();

    public int count;
    public int b;
    public int id;
    public int damage; // CraftBukkit - private -> public

    public ItemStack(Block block) {
        this(block, 1);
    }

    public ItemStack(Block block, int i) {
        this(block.id, i, 0);
    }

    public ItemStack(Block block, int i, int j) {
        this(block.id, i, j);
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
        this.b(nbttagcompound);
    }

    public ItemStack a(int i) {
        return (ItemStack) (Object) ITEM_STACK_STATE_BEHAVIOUR.splitStack((com.legacyminecraft.poseidon.item.ItemStack) (Object) this, i);
    }

    public Item getItem() {
        return Item.byId[this.id];
    }

    public boolean placeItem(EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        return ITEM_STACK_INTERACTION_BEHAVIOUR.placeItem((com.legacyminecraft.poseidon.item.ItemStack) (Object) this, (com.legacyminecraft.poseidon.entity.EntityHuman) (Object) entityhuman, (com.legacyminecraft.poseidon.entity.World) (Object) world, i, j, k, l);
    }

    public float a(Block block) {
        return this.getItem().a(this, block);
    }

    public ItemStack a(World world, EntityHuman entityhuman) {
        return (ItemStack) (Object) ITEM_STACK_INTERACTION_BEHAVIOUR.useItem((com.legacyminecraft.poseidon.item.ItemStack) (Object) this, (com.legacyminecraft.poseidon.entity.World) (Object) world, (com.legacyminecraft.poseidon.entity.EntityHuman) (Object) entityhuman);
    }

    public NBTTagCompound a(NBTTagCompound nbttagcompound) {
        return (NBTTagCompound) (Object) ITEM_STACK_STATE_BEHAVIOUR.writeToNbt((com.legacyminecraft.poseidon.item.ItemStack) (Object) this, (com.legacyminecraft.poseidon.nbt.NBTTagCompound) (Object) nbttagcompound);
    }

    public void b(NBTTagCompound nbttagcompound) {
        ITEM_STACK_STATE_BEHAVIOUR.readFromNbt((com.legacyminecraft.poseidon.item.ItemStack) (Object) this, (com.legacyminecraft.poseidon.nbt.NBTTagCompound) (Object) nbttagcompound);
    }

    public int getMaxStackSize() {
        return this.getItem().getMaxStackSize();
    }

    public boolean isStackable() {
        return ITEM_STACK_STATE_BEHAVIOUR.isStackable((com.legacyminecraft.poseidon.item.ItemStack) (Object) this);
    }

    public boolean d() {
        return Item.byId[this.id].e() > 0;
    }

    public boolean usesData() {
        return Item.byId[this.id].d();
    }

    public boolean f() {
        return ITEM_STACK_STATE_BEHAVIOUR.isDamaged((com.legacyminecraft.poseidon.item.ItemStack) (Object) this);
    }

    public int g() {
        return this.damage;
    }

    public int getData() {
        return this.damage;
    }

    public void b(int i) {
        this.damage = i;
    }

    public int i() {
        return ITEM_STACK_STATE_BEHAVIOUR.maxDurability((com.legacyminecraft.poseidon.item.ItemStack) (Object) this);
    }

    @SuppressWarnings("deprecation")
    public void damage(int i, Entity entity) {
        ITEM_STACK_INTERACTION_BEHAVIOUR.damage((com.legacyminecraft.poseidon.item.ItemStack) (Object) this, i, (com.legacyminecraft.poseidon.world.Entity) (Object) entity);
    }

    public void a(EntityLiving entityliving, EntityHuman entityhuman) {
        ITEM_STACK_INTERACTION_BEHAVIOUR.onHitEntity((com.legacyminecraft.poseidon.item.ItemStack) (Object) this, (com.legacyminecraft.poseidon.item.EntityLiving) (Object) entityliving, (com.legacyminecraft.poseidon.entity.EntityHuman) (Object) entityhuman);
    }

    public void a(int i, int j, int k, int l, EntityHuman entityhuman) {
        ITEM_STACK_INTERACTION_BEHAVIOUR.onDestroyBlock((com.legacyminecraft.poseidon.item.ItemStack) (Object) this, i, j, k, l, (com.legacyminecraft.poseidon.entity.EntityHuman) (Object) entityhuman);
    }

    public int a(Entity entity) {
        return ITEM_STACK_INTERACTION_BEHAVIOUR.attackDamage((com.legacyminecraft.poseidon.item.ItemStack) (Object) this, (com.legacyminecraft.poseidon.world.Entity) (Object) entity);
    }

    public boolean b(Block block) {
        Item item = this.getItem();
        return item != null && item.a(block);
    }

    public void a(EntityHuman entityhuman) {}

    public void a(EntityLiving entityliving) {
        ITEM_STACK_INTERACTION_BEHAVIOUR.useOnLiving((com.legacyminecraft.poseidon.item.ItemStack) (Object) this, (com.legacyminecraft.poseidon.item.EntityLiving) (Object) entityliving);
    }

    public ItemStack cloneItemStack() {
        return (ItemStack) (Object) ITEM_STACK_STATE_BEHAVIOUR.cloneStack((com.legacyminecraft.poseidon.item.ItemStack) (Object) this);
    }

    public static boolean equals(ItemStack itemstack, ItemStack itemstack1) {
        return ITEM_STACK_STATE_BEHAVIOUR.stackEqualsNullable((com.legacyminecraft.poseidon.item.ItemStack) (Object) itemstack, (com.legacyminecraft.poseidon.item.ItemStack) (Object) itemstack1);
    }

    private boolean d(ItemStack itemstack) {
        return ITEM_STACK_STATE_BEHAVIOUR.countIdDamageEquals((com.legacyminecraft.poseidon.item.ItemStack) (Object) this, (com.legacyminecraft.poseidon.item.ItemStack) (Object) itemstack);
    }

    public boolean countIdDamageEquals(ItemStack itemstack) {
        return ITEM_STACK_STATE_BEHAVIOUR.countIdDamageEquals((com.legacyminecraft.poseidon.item.ItemStack) (Object) this, (com.legacyminecraft.poseidon.item.ItemStack) (Object) itemstack);
    }

    public boolean doMaterialsMatch(ItemStack itemstack) {
        return ITEM_STACK_STATE_BEHAVIOUR.materialsMatch((com.legacyminecraft.poseidon.item.ItemStack) (Object) this, (com.legacyminecraft.poseidon.item.ItemStack) (Object) itemstack);
    }

    public static ItemStack b(ItemStack itemstack) {
        return (ItemStack) (Object) ITEM_STACK_STATE_BEHAVIOUR.cloneOrNull((com.legacyminecraft.poseidon.item.ItemStack) (Object) itemstack);
    }

    public String toString() {
        return ITEM_STACK_STATE_BEHAVIOUR.stringify((com.legacyminecraft.poseidon.item.ItemStack) (Object) this); // Project Poseidon: Fixes ArrayIndexOutOfBoundsException
    }

    public void a(World world, Entity entity, int i, boolean flag) {
        Item item = this.getItem();
        if (item != null) {
            item.a(this, world, entity, i, flag);
        }
    }

    public void b(World world, EntityHuman entityhuman) {
        ITEM_STACK_INTERACTION_BEHAVIOUR.onCrafted((com.legacyminecraft.poseidon.item.ItemStack) (Object) this, (com.legacyminecraft.poseidon.entity.World) (Object) world, (com.legacyminecraft.poseidon.entity.EntityHuman) (Object) entityhuman);
    }

    public boolean c(ItemStack itemstack) {
        return ITEM_STACK_STATE_BEHAVIOUR.strictEquals((com.legacyminecraft.poseidon.item.ItemStack) (Object) this, (com.legacyminecraft.poseidon.item.ItemStack) (Object) itemstack);
    }
}
