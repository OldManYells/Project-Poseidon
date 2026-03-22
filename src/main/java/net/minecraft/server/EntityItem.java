package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.ItemEntityLifecycleBehaviour;
import com.legacyminecraft.poseidon.entity.ItemEntityStateBehaviour;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class EntityItem extends Entity {
    private static final ItemEntityStateBehaviour ITEM_ENTITY_STATE_BEHAVIOUR = ItemEntityStateBehaviour.getInstance();
    private static final ItemEntityLifecycleBehaviour ITEM_ENTITY_LIFECYCLE_BEHAVIOUR = ItemEntityLifecycleBehaviour.getInstance();

    public ItemStack itemStack;
    private int e;
    public int b = 0;
    public int pickupDelay;
    private int f = 5;
    public float d = (float) (Math.random() * 3.141592653589793D * 2.0D);
    private int lastTick = (int) (System.currentTimeMillis() / 50); // CraftBukkit

    public EntityItem(World world, double d0, double d1, double d2, ItemStack itemstack) {
        super(world);
        ITEM_ENTITY_STATE_BEHAVIOUR.initializeDefaultBounds(this);
        this.setPosition(d0, d1, d2);
        this.itemStack = ITEM_ENTITY_STATE_BEHAVIOUR.sanitizeInitialItemStack(itemstack);
        // CraftBukkit start - infinite item fix
        this.itemStack = ITEM_ENTITY_STATE_BEHAVIOUR.sanitizeInitialItemStack(this.itemStack);
        // CraftBukkit end
        ItemEntityStateBehaviour.InitializationState initializationState = ITEM_ENTITY_STATE_BEHAVIOUR.initializeFromItemStack(this.itemStack, Item.byId.length);
        this.itemStack = initializationState.itemStack;
        if (initializationState.shouldDie) {
            this.die();
        }
        ItemEntityStateBehaviour.MotionState motionState = ITEM_ENTITY_STATE_BEHAVIOUR.createInitialMotion(Math.random(), Math.random(), Math.random());
        this.yaw = motionState.yaw;
        this.motX = motionState.motX;
        this.motY = motionState.motY;
        this.motZ = motionState.motZ;
    }

    protected boolean n() {
        return false;
    }

    public EntityItem(World world) {
        super(world);
        ITEM_ENTITY_STATE_BEHAVIOUR.initializeDefaultBounds(this);
    }

    protected void b() {}

    public void m_() {
        super.m_();
        // CraftBukkit start
        int currentTick = (int) (System.currentTimeMillis() / 50);
        ItemEntityLifecycleBehaviour.TickClockState tickClockState = ITEM_ENTITY_LIFECYCLE_BEHAVIOUR.updatePickupDelayClock(this.pickupDelay, currentTick, this.lastTick);
        this.pickupDelay = tickClockState.pickupDelay;
        this.lastTick = tickClockState.lastTick;
        // CraftBukkit end
        if (ITEM_ENTITY_STATE_BEHAVIOUR.isInvalidItemStack(this.itemStack, Item.byId.length)) {
            this.b = ITEM_ENTITY_STATE_BEHAVIOUR.invalidItemSentinelAge(); //TODO: Configurable lifetime of the EntityItem
            this.die();
        }

        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        this.motY -= 0.03999999910593033D;
        if (this.world.getMaterial(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) == Material.LAVA) {
            ITEM_ENTITY_STATE_BEHAVIOUR.applyLavaBounce(this, this.random.nextFloat(), this.random.nextFloat(), this.random.nextFloat());
        }

        this.g(this.locX, (this.boundingBox.b + this.boundingBox.e) / 2.0D, this.locZ);
        this.move(this.motX, this.motY, this.motZ);
        int i = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));
        float f = ITEM_ENTITY_STATE_BEHAVIOUR.resolveGroundFriction(this.onGround, i);

        this.motX *= (double) f;
        this.motY *= 0.9800000190734863D;
        this.motZ *= (double) f;
        if (this.onGround) {
            this.motY *= -0.5D;
        }

        ++this.e;
        ++this.b;
        if (ITEM_ENTITY_STATE_BEHAVIOUR.shouldAttemptDespawn(this.b)) {
            //Project Poseidon Start
            if (CraftEventFactory.callItemDespawnEvent(this).isCancelled()) {
                this.b = 0;
                return;
            }
            // CraftBukkit end
            this.die();
        }
    }

    public boolean f_() {
        return this.world.a(this.boundingBox, Material.WATER, this);
    }

    protected void burn(int i) {
        this.damageEntity((Entity) null, i);
    }

    public boolean damageEntity(Entity entity, int i) {
        this.af();
        this.f = ITEM_ENTITY_STATE_BEHAVIOUR.applyDamageAndGetRemainingHealth(this.f, i);
        if (ITEM_ENTITY_STATE_BEHAVIOUR.shouldDieFromHealth(this.f)) {
            this.die();
        }

        return false;
    }

    public void b(NBTTagCompound nbttagcompound) {
        ITEM_ENTITY_STATE_BEHAVIOUR.writeNbt(nbttagcompound, this.f, this.b, this.itemStack);
    }

    public void a(NBTTagCompound nbttagcompound) {
        ItemEntityStateBehaviour.LoadedNbtState loadedNbtState = ITEM_ENTITY_STATE_BEHAVIOUR.readNbt(nbttagcompound);
        this.f = loadedNbtState.health;
        this.b = loadedNbtState.age;
        this.itemStack = loadedNbtState.itemStack;
    }

    public void b(EntityHuman entityhuman) {
        if (!this.world.isStatic) {
            int i = this.itemStack.count;

            // CraftBukkit start
            int canHold = entityhuman.inventory.canHold(this.itemStack);
            ItemEntityLifecycleBehaviour.PickupWindow pickupWindow = ITEM_ENTITY_LIFECYCLE_BEHAVIOUR.computePickupWindow(this.pickupDelay, this.itemStack.count, canHold);
            if (pickupWindow.shouldCallPickupEvent) {
                this.itemStack.count = pickupWindow.canHold;
                PlayerPickupItemEvent event = new PlayerPickupItemEvent((org.bukkit.entity.Player) entityhuman.getBukkitEntity(), (org.bukkit.entity.Item) this.getBukkitEntity(), pickupWindow.remaining);
                this.world.getServer().getPluginManager().callEvent(event);
                this.itemStack.count = ITEM_ENTITY_LIFECYCLE_BEHAVIOUR.restoreStackCountAfterPickupProbe(pickupWindow);

                if (event.isCancelled()) {
                    return;
                }

                // Possibly < 0; fix here so we do not have to modify code below
                this.pickupDelay = ITEM_ENTITY_LIFECYCLE_BEHAVIOUR.normalizePickupDelayAfterEvent(event.isCancelled(), this.pickupDelay);
            }
            // CraftBukkit end

            if (ITEM_ENTITY_LIFECYCLE_BEHAVIOUR.shouldTryInventoryPickup(this.pickupDelay) && entityhuman.inventory.pickup(this.itemStack)) {
                ITEM_ENTITY_LIFECYCLE_BEHAVIOUR.grantPickupAchievements(entityhuman, this.itemStack.id);
                this.world.makeSound(this, "random.pop", 0.2F, ITEM_ENTITY_LIFECYCLE_BEHAVIOUR.pickupSoundPitch(this.random.nextFloat(), this.random.nextFloat()));
                entityhuman.receive(this, i);
                if (ITEM_ENTITY_LIFECYCLE_BEHAVIOUR.shouldDieAfterPickup(this.itemStack)) {
                    this.die();
                }
            }
        }
    }

    public void poseidonInitializeBounds() {
        this.b(0.25F, 0.25F);
        this.height = this.width / 2.0F;
    }
}
