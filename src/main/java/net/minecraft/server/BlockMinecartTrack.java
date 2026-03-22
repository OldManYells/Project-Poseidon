package net.minecraft.server;

import com.legacyminecraft.poseidon.block.RailStateBehaviour;

import java.util.Random;

public class BlockMinecartTrack extends Block {

    private final boolean a;
    private static final RailStateBehaviour RAIL_STATE_SERVICE = RailStateBehaviour.getInstance();

    public static final boolean g(World world, int i, int j, int k) {
        return RAIL_STATE_SERVICE.isRailBlockId(world.getTypeId(i, j, k));
    }

    public static final boolean c(int i) {
        return RAIL_STATE_SERVICE.isRailBlockId(i);
    }

    protected BlockMinecartTrack(int i, int j, boolean flag) {
        super(i, j, Material.ORIENTABLE);
        this.a = flag;
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    public boolean f() {
        return this.a;
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return null;
    }

    public boolean a() {
        return false;
    }

    public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
        this.a(world, i, j, k);
        return super.a(world, i, j, k, vec3d, vec3d1);
    }

    public void a(IBlockAccess iblockaccess, int i, int j, int k) {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, RAIL_STATE_SERVICE.resolveCollisionHeight(iblockaccess.getData(i, j, k)), 1.0F);
    }

    public int a(int i, int j) {
        return RAIL_STATE_SERVICE.resolveTextureIndex(this.a, this.id, j, this.textureId, Block.GOLDEN_RAIL.id);
    }

    public boolean b() {
        return false;
    }

    public int a(Random random) {
        return 1;
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return RAIL_STATE_SERVICE.canPlace(world.e(i, j - 1, k));
    }

    public void c(World world, int i, int j, int k) {
        if (!world.isStatic) {
            this.a(world, i, j, k, true);
        }
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (!world.isStatic) {
            int i1 = world.getData(i, j, k);
            int j1 = RAIL_STATE_SERVICE.extractShape(i1, this.a);

            boolean flag = RAIL_STATE_SERVICE.shouldDropForMissingSupport(
                    world.e(i, j - 1, k),
                    j1,
                    world.e(i + 1, j, k),
                    world.e(i - 1, j, k),
                    world.e(i, j, k - 1),
                    world.e(i, j, k + 1)
            );

            if (flag) {
                this.g(world, i, j, k, world.getData(i, j, k));
                world.setTypeId(i, j, k, 0);
            } else if (this.id == Block.GOLDEN_RAIL.id) {
                boolean flag1 = world.isBlockIndirectlyPowered(i, j, k) || world.isBlockIndirectlyPowered(i, j + 1, k);

                flag1 = flag1 || this.a(world, i, j, k, i1, true, 0) || this.a(world, i, j, k, i1, false, 0);
                boolean flag2 = false;

                if (flag1 && !RAIL_STATE_SERVICE.isPowered(i1)) {
                    world.setData(i, j, k, RAIL_STATE_SERVICE.setPoweredState(j1, true));
                    flag2 = true;
                } else if (!flag1 && RAIL_STATE_SERVICE.isPowered(i1)) {
                    world.setData(i, j, k, RAIL_STATE_SERVICE.setPoweredState(j1, false));
                    flag2 = true;
                }

                if (flag2) {
                    world.applyPhysics(i, j - 1, k, this.id);
                    if (RAIL_STATE_SERVICE.shouldNotifyBlockAboveOnPoweredStateChange(j1)) {
                        world.applyPhysics(i, j + 1, k, this.id);
                    }
                }
            } else if (l > 0 && Block.byId[l].isPowerSource() && !this.a && MinecartTrackLogic.a(new MinecartTrackLogic(this, world, i, j, k)) == 3) {
                this.a(world, i, j, k, false);
            }
        }
    }

    private void a(World world, int i, int j, int k, boolean flag) {
        if (!world.isStatic) {
            (new MinecartTrackLogic(this, world, i, j, k)).a(world.isBlockIndirectlyPowered(i, j, k), flag);
        }
    }

    private boolean a(World world, int i, int j, int k, int l, boolean flag, int i1) {
        if (!RAIL_STATE_SERVICE.canContinuePropagation(i1)) {
            return false;
        } else {
            RailStateBehaviour.PropagationStep propagationStep =
                    RAIL_STATE_SERVICE.computePropagationStep(i, j, k, l & 7, flag);

            return this.a(
                    world,
                    propagationStep.getNextX(),
                    propagationStep.getNextY(),
                    propagationStep.getNextZ(),
                    flag,
                    i1,
                    propagationStep.getExpectedAxis()
            ) ? true
                    : propagationStep.shouldCheckBelowFallback()
                    && this.a(
                    world,
                    propagationStep.getNextX(),
                    propagationStep.getNextY() - 1,
                    propagationStep.getNextZ(),
                    flag,
                    i1,
                    propagationStep.getExpectedAxis()
            );
        }
    }

    private boolean a(World world, int i, int j, int k, boolean flag, int l, int i1) {
        int j1 = world.getTypeId(i, j, k);

        if (j1 == Block.GOLDEN_RAIL.id) {
            int k1 = world.getData(i, j, k);
            int l1 = k1 & 7;

            if (!RAIL_STATE_SERVICE.isPropagationShapeCompatible(i1, l1)) {
                return false;
            }

            if (RAIL_STATE_SERVICE.isPowered(k1)) {
                if (!world.isBlockIndirectlyPowered(i, j, k) && !world.isBlockIndirectlyPowered(i, j + 1, k)) {
                    return this.a(world, i, j, k, k1, flag, l + 1);
                }

                return true;
            }
        }

        return false;
    }

    public int e() {
        return 0;
    }

    static boolean a(BlockMinecartTrack blockminecarttrack) {
        return blockminecarttrack.a;
    }
}
