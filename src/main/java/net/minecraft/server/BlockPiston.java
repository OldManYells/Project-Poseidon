package net.minecraft.server;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.block.PistonMovementChainBehaviour;
import com.legacyminecraft.poseidon.block.PistonPowerQueryBehaviour;
import com.legacyminecraft.poseidon.block.PistonPowerTransitionBehaviour;
import com.legacyminecraft.poseidon.block.PistonPushabilityBehaviour;
import com.legacyminecraft.poseidon.block.PistonStateBehaviour;
import com.legacyminecraft.poseidon.block.PistonStickyRetractionBehaviour;
import com.legacyminecraft.compat.bukkit.PistonEventBridgeBehaviour;
import com.legacyminecraft.poseidon.world.WorldFeatureConfigPolicy;

import java.util.ArrayList;

// CraftBukkit start
// CraftBukkit end

public class BlockPiston extends Block {

    private boolean a;
    private boolean b;
    private static final PistonStateBehaviour PISTON_STATE_BEHAVIOUR = PistonStateBehaviour.getInstance();
    private static final PistonPowerQueryBehaviour PISTON_POWER_QUERY_BEHAVIOUR = PistonPowerQueryBehaviour.getInstance();
    private static final PistonPushabilityBehaviour PISTON_PUSHABILITY_BEHAVIOUR = PistonPushabilityBehaviour.getInstance();
    private static final PistonMovementChainBehaviour PISTON_MOVEMENT_CHAIN_BEHAVIOUR = PistonMovementChainBehaviour.getInstance();
    private static final PistonPowerTransitionBehaviour PISTON_POWER_TRANSITION_BEHAVIOUR = PistonPowerTransitionBehaviour.getInstance();
    private static final PistonStickyRetractionBehaviour PISTON_STICKY_RETRACTION_BEHAVIOUR = PistonStickyRetractionBehaviour.getInstance();
    private static final PistonEventBridgeBehaviour PISTON_EVENT_BRIDGE_BEHAVIOUR = PistonEventBridgeBehaviour.getInstance();
    private static final WorldFeatureConfigPolicy WORLD_FEATURE_CONFIG_POLICY = WorldFeatureConfigPolicy.getInstance();
    private static final PistonMovementChainBehaviour.PushabilityQuery MOVEMENT_CHAIN_PUSHABILITY_QUERY =
            new PistonMovementChainBehaviour.PushabilityQuery() {
                public boolean canPush(int blockId, World queryWorld, int x, int y, int z, boolean allowDestroy) {
                    return a(blockId, queryWorld, x, y, z, allowDestroy);
                }
            };
    private static final PistonStickyRetractionBehaviour.PushabilityQuery STICKY_RETRACTION_PUSHABILITY_QUERY =
            new PistonStickyRetractionBehaviour.PushabilityQuery() {
                public boolean canPush(int blockId, World queryWorld, int x, int y, int z, boolean allowDestroy) {
                    return a(blockId, queryWorld, x, y, z, allowDestroy);
                }
            };
    private static final PistonMovementChainBehaviour.BlockClearAction RAW_AIR_CLEAR_ACTION =
            new PistonMovementChainBehaviour.BlockClearAction() {
                public void clear(World clearWorld, int x, int y, int z) {
                    clearWorld.setRawTypeId(x, y, z, 0);
                }
            };
    private static final PistonMovementChainBehaviour.BlockClearAction TYPE_AIR_CLEAR_ACTION =
            new PistonMovementChainBehaviour.BlockClearAction() {
                public void clear(World clearWorld, int x, int y, int z) {
                    clearWorld.setTypeId(x, y, z, 0);
                }
            };

    public BlockPiston(int i, int j, boolean flag) {
        super(i, j, Material.PISTON);
        this.a = flag;
        this.a(h);
        this.c(0.5F);
    }

    public int a(int i, int j) {
        return PISTON_STATE_BEHAVIOUR.resolveTextureIndex(
                i,
                j,
                this.textureId,
                this.minX <= 0.0D && this.minY <= 0.0D && this.minZ <= 0.0D
                        && this.maxX >= 1.0D && this.maxY >= 1.0D && this.maxZ >= 1.0D
        );
    }

    public boolean a() {
        return false;
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        return false;
    }

    public void postPlace(World world, int i, int j, int k, EntityLiving entityliving) {
        int l = c(world, i, j, k, (EntityHuman) entityliving);

        world.setData(i, j, k, l);
        if (!world.isStatic) {
            this.g(world, i, j, k);
        }
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (!world.isStatic && !this.b) {
            this.g(world, i, j, k);
        }
    }

    public void c(World world, int i, int j, int k) {
        if (!world.isStatic && world.getTileEntity(i, j, k) == null) {
            this.g(world, i, j, k);
        }
    }

    private void g(World world, int i, int j, int k) {
        int l = world.getData(i, j, k);
        int i1 = c(l);
        boolean flag = this.f(world, i, j, k, i1);
        PistonPowerTransitionBehaviour.Transition transition =
                PISTON_POWER_TRANSITION_BEHAVIOUR.resolveTransition(l, flag, d(l));

        if (transition == PistonPowerTransitionBehaviour.Transition.EXTEND) {
                // CraftBukkit start
                int length;
                try {
                    length = h(world, i, j, k, i1);
                } catch (RuntimeException exception) {
                    System.out.println("[Poseidon] A piston crash attempt occurred at " + i + " " + j + " " + k + " in " + world.getWorld().getName());
                    return;
                }
                if (length >= 0) {
                    if (!PISTON_EVENT_BRIDGE_BEHAVIOUR.isExtendAllowed(world, i, j, k, length)) {
                        return;
                    }
                    // CraftBukkit end

                    world.setRawData(i, j, k, PISTON_POWER_TRANSITION_BEHAVIOUR.composeExtendedData(i1));
                    world.playNote(i, j, k, 0, i1);
                }
        } else if (transition == PistonPowerTransitionBehaviour.Transition.RETRACT) {
                // CraftBukkit start
                if (!PISTON_EVENT_BRIDGE_BEHAVIOUR.isRetractAllowed(world, i, j, k)) {
                    return;
                }
                // CraftBukkit end

                world.setRawData(i, j, k, PISTON_POWER_TRANSITION_BEHAVIOUR.composeRetractedData(i1));
                world.playNote(i, j, k, 1, i1);
        }
    }

    private boolean f(World world, int i, int j, int k, int l) {
        return PISTON_POWER_QUERY_BEHAVIOUR.isIndirectlyPoweredExceptFacing(
                new PistonPowerQueryBehaviour.IndirectPowerQuery() {
                    public boolean isBlockFaceIndirectlyPowered(int x, int y, int z, int face) {
                        return world.isBlockFaceIndirectlyPowered(x, y, z, face);
                    }
                },
                i,
                j,
                k,
                l
        );
    }

    public void a(World world, int i, int j, int k, int l, int i1) {
        this.b = true;
        if (l == 0) {
            if (this.i(world, i, j, k, i1)) {
                world.setData(i, j, k, i1 | 8);
                world.makeSound((double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D, "tile.piston.out", 0.5F, world.random.nextFloat() * 0.25F + 0.6F);
            }
        } else if (l == 1) {
            TileEntity tileentity = world.getTileEntity(i + PistonBlockTextures.b[i1], j + PistonBlockTextures.c[i1], k + PistonBlockTextures.d[i1]);

            if (tileentity != null && tileentity instanceof TileEntityPiston) {
                ((TileEntityPiston) tileentity).k();
            }

            world.setRawTypeIdAndData(i, j, k, Block.PISTON_MOVING.id, i1);
            world.setTileEntity(i, j, k, BlockPistonMoving.a(this.id, i1, i1, false, true));
            if (this.a) {
                PistonStickyRetractionBehaviour.RetractionPositions retractionPositions =
                        PISTON_STICKY_RETRACTION_BEHAVIOUR.resolveRetractionPositions(i, j, k, i1);
                PistonStickyRetractionBehaviour.PulledBlockState pulledBlockState =
                        PISTON_STICKY_RETRACTION_BEHAVIOUR.resolvePulledBlockState(
                                world,
                                retractionPositions.getPullX(),
                                retractionPositions.getPullY(),
                                retractionPositions.getPullZ(),
                                i1
                        );

                if (PISTON_STICKY_RETRACTION_BEHAVIOUR.shouldPullBlock(
                        pulledBlockState,
                        world,
                        retractionPositions.getPullX(),
                        retractionPositions.getPullY(),
                        retractionPositions.getPullZ(),
                        STICKY_RETRACTION_PUSHABILITY_QUERY
                )) {
                    this.b = false;
                    world.setTypeId(retractionPositions.getPullX(), retractionPositions.getPullY(), retractionPositions.getPullZ(), 0);
                    this.b = true;
                    i += PistonBlockTextures.b[i1];
                    j += PistonBlockTextures.c[i1];
                    k += PistonBlockTextures.d[i1];
                    world.setRawTypeIdAndData(i, j, k, Block.PISTON_MOVING.id, pulledBlockState.getBlockData());
                    world.setTileEntity(i, j, k, BlockPistonMoving.a(pulledBlockState.getBlockId(), pulledBlockState.getBlockData(), i1, false, false));
                } else if (PISTON_STICKY_RETRACTION_BEHAVIOUR.shouldClearAdjacentBlock(pulledBlockState)) {
                    this.b = false;
                    world.setTypeId(
                            retractionPositions.getAdjacentX(),
                            retractionPositions.getAdjacentY(),
                            retractionPositions.getAdjacentZ(),
                            0
                    );
                    this.b = true;
                }
            } else {
                this.b = false;
                world.setTypeId(i + PistonBlockTextures.b[i1], j + PistonBlockTextures.c[i1], k + PistonBlockTextures.d[i1], 0);
                this.b = true;
            }

            world.makeSound((double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D, "tile.piston.in", 0.5F, world.random.nextFloat() * 0.15F + 0.6F);
        }

        this.b = false;
    }

    public void a(IBlockAccess iblockaccess, int i, int j, int k) {
        int l = iblockaccess.getData(i, j, k);

        PistonStateBehaviour.Bounds extendedBounds = PISTON_STATE_BEHAVIOUR.resolveExtendedBounds(l);
        if (extendedBounds == null) {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            return;
        }

        this.a(
                extendedBounds.getMinX(),
                extendedBounds.getMinY(),
                extendedBounds.getMinZ(),
                extendedBounds.getMaxX(),
                extendedBounds.getMaxY(),
                extendedBounds.getMaxZ()
        );
    }

    public void a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, ArrayList arraylist) {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.a(world, i, j, k, axisalignedbb, arraylist);
    }

    public boolean b() {
        return false;
    }

    public static int c(int i) {
        return PISTON_STATE_BEHAVIOUR.extractFacing(i);
    }

    public static boolean d(int i) {
        return PISTON_STATE_BEHAVIOUR.isExtended(i);
    }

    private static int c(World world, int i, int j, int k, EntityHuman entityhuman) {
        return PISTON_STATE_BEHAVIOUR.resolvePlacedFacing(entityhuman, i, j, k);
    }

    private static boolean a(int i, World world, int j, int k, int l, boolean flag) {
        return PISTON_PUSHABILITY_BEHAVIOUR.canPushBlock(i, world, j, k, l, flag);
    }

    // CraftBukkkit boolean -> int
    private static int h(World world, int i, int j, int k, int l) {
        return PISTON_MOVEMENT_CHAIN_BEHAVIOUR.calculateExtensionLength(world, i, j, k, l,
                MOVEMENT_CHAIN_PUSHABILITY_QUERY);
    }

    private boolean i(World world, int i, int j, int k, int l) {
        final boolean useRawAirClear = PoseidonConfig.getInstance().getConfigBoolean(
                WORLD_FEATURE_CONFIG_POLICY.pistonOtherFixesEnabledKey(),
                WORLD_FEATURE_CONFIG_POLICY.pistonOtherFixesEnabledDefault()
        );
        PistonMovementChainBehaviour.BlockClearAction blockClearAction = useRawAirClear
                ? RAW_AIR_CLEAR_ACTION
                : TYPE_AIR_CLEAR_ACTION;
        return PISTON_MOVEMENT_CHAIN_BEHAVIOUR.extendWithMovingBlocks(
                world,
                i,
                j,
                k,
                l,
                this.id,
                this.a,
                MOVEMENT_CHAIN_PUSHABILITY_QUERY,
                blockClearAction
        );
    }
}
