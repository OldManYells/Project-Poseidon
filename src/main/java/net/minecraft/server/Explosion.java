package net.minecraft.server;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.compat.bukkit.ExplosionEventBridgeBehaviour;
import com.legacyminecraft.poseidon.world.ExplosionBlockDestructionBehaviour;
import com.legacyminecraft.poseidon.world.ExplosionEntityImpactSystem;
import com.legacyminecraft.poseidon.world.ExplosionEffectBehaviour;
import com.legacyminecraft.poseidon.world.ExplosionRaycastBehaviour;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.*;

// CraftBukkit start
// CraftBukkit end

public class Explosion {
    private static final ExplosionBlockDestructionBehaviour EXPLOSION_BLOCK_DESTRUCTION_BEHAVIOUR = ExplosionBlockDestructionBehaviour.getInstance();
    private static final ExplosionEntityImpactSystem EXPLOSION_ENTITY_IMPACT_SYSTEM = ExplosionEntityImpactSystem.getInstance();
    private static final ExplosionEffectBehaviour EXPLOSION_EFFECT_BEHAVIOUR = ExplosionEffectBehaviour.getInstance();
    private static final ExplosionRaycastBehaviour EXPLOSION_RAYCAST_BEHAVIOUR = ExplosionRaycastBehaviour.getInstance();
    private static final ExplosionEventBridgeBehaviour EXPLOSION_EVENT_BRIDGE_BEHAVIOUR = ExplosionEventBridgeBehaviour.getInstance();

    public boolean setFire = false;
    private final Random random = new Random();
    private final World world;
    public double posX;
    public double posY;
    public double posZ;
    public Entity source;
    public EntityDamageEvent.DamageCause customDamageCause = null; // Poseidon
    public float size;
    public Set<ChunkPosition> blocks = new HashSet<>(); // UberBukkit: Set -> Set<ChunkPosition>

    public boolean wasCanceled = false; // CraftBukkit

    public Explosion(World world, Entity entity, double d0, double d1, double d2, float f) {
        this.world = world;
        this.source = entity;
        this.size = f;
        this.posX = d0;
        this.posY = d1;
        this.posZ = d2;
    }

    public void a() {
        float f = this.size;
        int i;
        int j;
        int k;

        EXPLOSION_RAYCAST_BEHAVIOUR.collectAffectedBlocks(this.world, this.source, this.size, this.posX, this.posY, this.posZ, this.blocks);

        this.size *= 2.0F;
        i = MathHelper.floor(this.posX - (double) this.size - 1.0D);
        j = MathHelper.floor(this.posX + (double) this.size + 1.0D);
        k = MathHelper.floor(this.posY - (double) this.size - 1.0D);
        int l1 = MathHelper.floor(this.posY + (double) this.size + 1.0D);
        int i2 = MathHelper.floor(this.posZ - (double) this.size - 1.0D);
        int j2 = MathHelper.floor(this.posZ + (double) this.size + 1.0D);
        List list = this.world.b(this.source, AxisAlignedBB.b(i, k, i2, j, l1, j2));
        Vec3D vec3d = Vec3D.create(this.posX, this.posY, this.posZ);

        /*
         * Whether explosions should be optimized or not
         * A backport from PaperMC
         * Config option:
         * optimizedExplosions: false
         */
        boolean optimizeExplosions = (boolean) PoseidonConfig.getInstance().getProperty("world-settings.optimized-explosions");
        boolean sendMotion = (boolean) PoseidonConfig.getInstance().getProperty("world-settings.send-explosion-velocity");

        EXPLOSION_ENTITY_IMPACT_SYSTEM.applyImpacts(
                this.world,
                this.source,
                this.size,
                this.posX,
                this.posY,
                this.posZ,
                vec3d,
                list,
                optimizeExplosions,
                sendMotion,
                this.customDamageCause
        );

        this.size = f;

        ArrayList<ChunkPosition> arraylist = new ArrayList<>();
        arraylist.addAll(this.blocks);

        if (this.setFire) {
            for (int l2 = arraylist.size() - 1; l2 >= 0; --l2) {
                ChunkPosition chunkposition = arraylist.get(l2);
                int i3 = chunkposition.x;
                int j3 = chunkposition.y;
                int k3 = chunkposition.z;
                int l3 = this.world.getTypeId(i3, j3, k3);
                int i4 = this.world.getTypeId(i3, j3 - 1, k3);

                if (EXPLOSION_EFFECT_BEHAVIOUR.shouldIgniteBlock(l3, i4, this.random)) {
                    this.world.setTypeId(i3, j3, k3, Block.FIRE.id);
                }
            }
        }
    }

    public void a(boolean flag) {
        this.world.makeSound(this.posX, this.posY, this.posZ, "random.explode", 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);

        ArrayList<ChunkPosition> blocksCopy = new ArrayList<>(this.blocks);

        // CraftBukkit start
        ExplosionEventBridgeBehaviour.ExplosionEventResult explosionEventResult =
                EXPLOSION_EVENT_BRIDGE_BEHAVIOUR.callExplosionEvent(
                        this.world,
                        this.source,
                        this.posX,
                        this.posY,
                        this.posZ,
                        blocksCopy,
                        this.blocks
                );

        if (explosionEventResult.isCancelled()) {
            this.wasCanceled = true;
            return;
        }
        float explosionYield = explosionEventResult.getYield();
        // CraftBukkit end

        for (int i = blocksCopy.size() - 1; i >= 0; --i) {
            ChunkPosition chunkposition = blocksCopy.get(i);
            int j = chunkposition.x;
            int k = chunkposition.y;
            int l = chunkposition.z;
            int i1 = this.world.getTypeId(j, k, l);

            if (flag) {
                ExplosionEffectBehaviour.ParticleImpulse impulse = EXPLOSION_EFFECT_BEHAVIOUR.computeParticleImpulse(
                        this.world.random,
                        j,
                        k,
                        l,
                        this.posX,
                        this.posY,
                        this.posZ,
                        this.size
                );
                this.world.a(
                        "explode",
                        (impulse.getSampleX() + this.posX) / 2.0D,
                        (impulse.getSampleY() + this.posY) / 2.0D,
                        (impulse.getSampleZ() + this.posZ) / 2.0D,
                        impulse.getMotionX(),
                        impulse.getMotionY(),
                        impulse.getMotionZ()
                );
                this.world.a(
                        "smoke",
                        impulse.getSampleX(),
                        impulse.getSampleY(),
                        impulse.getSampleZ(),
                        impulse.getMotionX(),
                        impulse.getMotionY(),
                        impulse.getMotionZ()
                );
            }

            // CraftBukkit - stop explosions from putting out fire
            if (EXPLOSION_BLOCK_DESTRUCTION_BEHAVIOUR.shouldDestroyBlock(i1)) {
                EXPLOSION_BLOCK_DESTRUCTION_BEHAVIOUR.destroyAndDrop(this.world, j, k, l, i1, explosionYield);
            }
        }
    }

}
