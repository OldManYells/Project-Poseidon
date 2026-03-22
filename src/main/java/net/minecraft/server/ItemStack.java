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
        return ITEM_STACK_STATE_BEHAVIOUR.splitStack(this, i);
    }

    public Item getItem() {
        return Item.byId[this.id];
    }

    public boolean placeItem(EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        return ITEM_STACK_INTERACTION_BEHAVIOUR.placeItem(this, entityhuman, world, i, j, k, l);
    }

    public float a(Block block) {
        return this.getItem().a(this, block);
    }

    public ItemStack a(World world, EntityHuman entityhuman) {
        return ITEM_STACK_INTERACTION_BEHAVIOUR.useItem(this, world, entityhuman);
    }

    public NBTTagCompound a(NBTTagCompound nbttagcompound) {
        return ITEM_STACK_STATE_BEHAVIOUR.writeToNbt(this, nbttagcompound);
    }

    public void b(NBTTagCompound nbttagcompound) {
        ITEM_STACK_STATE_BEHAVIOUR.readFromNbt(this, nbttagcompound);
    }

    public int getMaxStackSize() {
        return this.getItem().getMaxStackSize();
    }

    public boolean isStackable() {
        return ITEM_STACK_STATE_BEHAVIOUR.isStackable(this);
    }

    public boolean d() {
        return Item.byId[this.id].e() > 0;
    }

    public boolean usesData() {
        return Item.byId[this.id].d();
    }

    public boolean f() {
        return ITEM_STACK_STATE_BEHAVIOUR.isDamaged(this);
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
        return ITEM_STACK_STATE_BEHAVIOUR.maxDurability(this);
    }

    @SuppressWarnings("deprecation")
    public void damage(int i, Entity entity) {
        ITEM_STACK_INTERACTION_BEHAVIOUR.damage(this, i, entity);
    }

    public void a(EntityLiving entityliving, EntityHuman entityhuman) {
        ITEM_STACK_INTERACTION_BEHAVIOUR.onHitEntity(this, entityliving, entityhuman);
    }

    public void a(int i, int j, int k, int l, EntityHuman entityhuman) {
        ITEM_STACK_INTERACTION_BEHAVIOUR.onDestroyBlock(this, i, j, k, l, entityhuman);
    }

    public int a(Entity entity) {
        return ITEM_STACK_INTERACTION_BEHAVIOUR.attackDamage(this, entity);
    }

    public boolean b(Block block) {
        return ITEM_STACK_INTERACTION_BEHAVIOUR.canHarvest(this, block);
    }

    public void a(EntityHuman entityhuman) {}

    public void a(EntityLiving entityliving) {
        ITEM_STACK_INTERACTION_BEHAVIOUR.useOnLiving(this, entityliving);
    }

    public ItemStack cloneItemStack() {
        return ITEM_STACK_STATE_BEHAVIOUR.cloneStack(this);
    }

    public static boolean equals(ItemStack itemstack, ItemStack itemstack1) {
        return ITEM_STACK_STATE_BEHAVIOUR.stackEqualsNullable(itemstack, itemstack1);
    }

    private boolean d(ItemStack itemstack) {
        return ITEM_STACK_STATE_BEHAVIOUR.countIdDamageEquals(this, itemstack);
    }

    public boolean countIdDamageEquals(ItemStack itemstack) {
        return ITEM_STACK_STATE_BEHAVIOUR.countIdDamageEquals(this, itemstack);
    }

    public boolean doMaterialsMatch(ItemStack itemstack) {
        return ITEM_STACK_STATE_BEHAVIOUR.materialsMatch(this, itemstack);
    }

    public static ItemStack b(ItemStack itemstack) {
        return ITEM_STACK_STATE_BEHAVIOUR.cloneOrNull(itemstack);
    }

    public String toString() {
        return ITEM_STACK_STATE_BEHAVIOUR.stringify(this); // Project Poseidon: Fixes ArrayIndexOutOfBoundsException
    }

    public void a(World world, Entity entity, int i, boolean flag) {
        ITEM_STACK_INTERACTION_BEHAVIOUR.onInventoryTick(this, world, entity, i, flag);
    }

    public void b(World world, EntityHuman entityhuman) {
        ITEM_STACK_INTERACTION_BEHAVIOUR.onCrafted(this, world, entityhuman);
    }

    public boolean c(ItemStack itemstack) {
        return ITEM_STACK_STATE_BEHAVIOUR.strictEquals(this, itemstack);
    }
}
