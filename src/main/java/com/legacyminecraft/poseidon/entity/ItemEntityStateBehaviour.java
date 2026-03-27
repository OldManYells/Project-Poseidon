package com.legacyminecraft.poseidon.entity;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class ItemEntityStateBehaviour {
    private static final ItemEntityStateBehaviour INSTANCE = new ItemEntityStateBehaviour();
    private static final Logger LOGGER = Logger.getLogger(ItemEntityStateBehaviour.class.getName());
    private static final int DEFAULT_ITEM_HEALTH = 5;
    private static final int DESPAWN_AGE_TICKS = 6000;
    private static final int INVALID_ITEM_SENTINEL_AGE = 6000174;

    private ItemEntityStateBehaviour() {
    }

    public static ItemEntityStateBehaviour getInstance() {
        return INSTANCE;
    }

    public void initializeDefaultBounds(EntityItem itemEntity) {
        itemEntity.poseidonInitializeBounds();
    }

    public ItemStack sanitizeInitialItemStack(ItemStack itemStack) {
        if (itemStack.count <= -1) {
            itemStack.count = 1;
        }
        return itemStack;
    }

    public InitializationState initializeFromItemStack(ItemStack itemStack, int itemRegistryLength) {
        boolean invalid = itemStack.id < 0 || itemStack.id >= itemRegistryLength || Item.byId[itemStack.id] == null;
        if (invalid) {
            MinecraftException exception = new MinecraftException("Unknown item id " + itemStack.id);
            LOGGER.log(Level.WARNING, "Created the EntityItem object with an unknown item: " + itemStack, exception);
            return new InitializationState(new ItemStack(com.legacyminecraft.poseidon.block.Block.STONE.id, 1, 0), true);
        }
        return new InitializationState(itemStack, false);
    }

    public MotionState createInitialMotion(double randomYaw0to1, double randomMotionX0to1, double randomMotionZ0to1) {
        float yaw = (float) (randomYaw0to1 * 360.0D);
        double motionX = (double) ((float) (randomMotionX0to1 * 0.20000000298023224D - 0.10000000149011612D));
        double motionY = 0.20000000298023224D;
        double motionZ = (double) ((float) (randomMotionZ0to1 * 0.20000000298023224D - 0.10000000149011612D));
        return new MotionState(yaw, motionX, motionY, motionZ);
    }

    public boolean isInvalidItemStack(ItemStack itemStack, int itemRegistryLength) {
        return itemStack.id < 0 || itemStack.id >= itemRegistryLength || Item.byId[itemStack.id] == null;
    }

    public int invalidItemSentinelAge() {
        return INVALID_ITEM_SENTINEL_AGE;
    }

    public void applyLavaBounce(EntityItem itemEntity, float randomFloatA, float randomFloatB, float randomFloatC) {
        itemEntity.motY = 0.20000000298023224D;
        itemEntity.motX = (double) ((randomFloatA - randomFloatB) * 0.2F);
        itemEntity.motZ = (double) ((randomFloatB - randomFloatC) * 0.2F);
        itemEntity.world.makeSound(itemEntity, "random.fizz", 0.4F, 2.0F + randomFloatC * 0.4F);
    }

    public float resolveGroundFriction(boolean onGround, int blockIdBelow) {
        if (!onGround) {
            return 0.98F;
        }
        if (blockIdBelow > 0) {
            return com.legacyminecraft.poseidon.block.Block.byId[blockIdBelow].frictionFactor * 0.98F;
        }
        return 0.58800006F;
    }

    public boolean shouldAttemptDespawn(int ageTicks) {
        return ageTicks >= DESPAWN_AGE_TICKS;
    }

    public int applyDamageAndGetRemainingHealth(int health, int damage) {
        return health - damage;
    }

    public boolean shouldDieFromHealth(int health) {
        return health <= 0;
    }

    public void writeNbt(NBTTagCompound nbt, int health, int age, ItemStack itemStack) {
        nbt.a("Health", (short) ((byte) health));
        nbt.a("Age", (short) age);
        nbt.a("Item", itemStack.a(new NBTTagCompound()));
    }

    public LoadedNbtState readNbt(NBTTagCompound nbt) {
        int health = nbt.d("Health") & 255;
        int age = nbt.d("Age");
        ItemStack itemStack = new ItemStack(asEntityTag(nbt.k("Item")));
        return new LoadedNbtState(health, age, itemStack);
    }

    private static com.legacyminecraft.poseidon.entity.NBTTagCompound asEntityTag(Object value) {
        if (value instanceof com.legacyminecraft.poseidon.entity.NBTTagCompound) {
            return (com.legacyminecraft.poseidon.entity.NBTTagCompound) value;
        }
        return new com.legacyminecraft.poseidon.entity.NBTTagCompound();
    }

    public int defaultItemHealth() {
        return DEFAULT_ITEM_HEALTH;
    }

    public static final class InitializationState {
        public final ItemStack itemStack;
        public final boolean shouldDie;

        public InitializationState(ItemStack itemStack, boolean shouldDie) {
            this.itemStack = itemStack;
            this.shouldDie = shouldDie;
        }
    }

    public static final class MotionState {
        public final float yaw;
        public final double motX;
        public final double motY;
        public final double motZ;

        public MotionState(float yaw, double motX, double motY, double motZ) {
            this.yaw = yaw;
            this.motX = motX;
            this.motY = motY;
            this.motZ = motZ;
        }
    }

    public static final class LoadedNbtState {
        public final int health;
        public final int age;
        public final ItemStack itemStack;

        public LoadedNbtState(int health, int age, ItemStack itemStack) {
            this.health = health;
            this.age = age;
            this.itemStack = itemStack;
        }
    }
}
