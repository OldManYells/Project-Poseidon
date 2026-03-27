package net.minecraft.server;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.block.TorchPlacementAndBoundsBehaviour;
import com.legacyminecraft.poseidon.world.WorldFeatureConfigPolicy;

import java.util.Random;

public class BlockTorch extends Block {
    private final TorchPlacementAndBoundsBehaviour torchPlacementAndBoundsService = TorchPlacementAndBoundsBehaviour.getInstance();
    private static final WorldFeatureConfigPolicy WORLD_FEATURE_CONFIG_POLICY = WorldFeatureConfigPolicy.getInstance();

    protected BlockTorch(int i, int j) {
        super(i, j, Material.ORIENTABLE);
        this.a(true);
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return null;
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }

    private boolean g(World world, int i, int j, int k) {
        return torchPlacementAndBoundsService.hasFloorSupport(this.supportQuery(world), i, j, k);
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return torchPlacementAndBoundsService.canPlace(this.supportQuery(world), i, j, k);
    }

    public void postPlace(World world, int i, int j, int k, int l) {
        if (PoseidonConfig.getInstance().getConfigBoolean(
                WORLD_FEATURE_CONFIG_POLICY.pistonTransmutationFixEnabledKey(),
                WORLD_FEATURE_CONFIG_POLICY.pistonTransmutationFixEnabledDefault()
        ) && world.getTypeId(i, j, k) != this.id) return;
        world.setData(i, j, k, torchPlacementAndBoundsService.resolvePostPlaceData(
                this.supportQuery(world),
                world.getData(i, j, k),
                l,
                i,
                j,
                k
        ));
    }

    public void a(World world, int i, int j, int k, Random random) {
        super.a(world, i, j, k, random);
        if (world.getData(i, j, k) == 0) {
            this.c(world, i, j, k);
        }
    }

    public void c(World world, int i, int j, int k) {
        world.setData(i, j, k, torchPlacementAndBoundsService.resolveFallbackData(this.supportQuery(world), i, j, k));

        this.h(world, i, j, k);
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (this.h(world, i, j, k)) {
            int i1 = world.getData(i, j, k);
            if (torchPlacementAndBoundsService.isAttachmentMissing(this.supportQuery(world), i, j, k, i1)) {
                this.g(world, i, j, k, world.getData(i, j, k));
                world.setTypeId(i, j, k, 0);
            }
        }
    }

    private boolean h(World world, int i, int j, int k) {
        if (torchPlacementAndBoundsService.shouldDropForInvalidPlacement(
                this.canPlace(world, i, j, k),
                PoseidonConfig.getInstance().getConfigBoolean(
                        WORLD_FEATURE_CONFIG_POLICY.pistonOtherFixesEnabledKey(),
                        WORLD_FEATURE_CONFIG_POLICY.pistonOtherFixesEnabledDefault()
                ),
                world.getTypeId(i, j, k) == this.id
        )) {
            this.g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
            return false;
        } else {
            return true;
        }
    }

    public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
        TorchPlacementAndBoundsBehaviour.Bounds bounds = torchPlacementAndBoundsService.resolveRaytraceBounds(world.getData(i, j, k));
        this.a(
                bounds.getMinX(),
                bounds.getMinY(),
                bounds.getMinZ(),
                bounds.getMaxX(),
                bounds.getMaxY(),
                bounds.getMaxZ()
        );

        return super.a(world, i, j, k, vec3d, vec3d1);
    }

    private TorchPlacementAndBoundsBehaviour.SupportQuery supportQuery(final World world) {
        return new TorchPlacementAndBoundsBehaviour.SupportQuery() {
            public boolean isBlockSolid(int x, int y, int z) {
                return world.e(x, y, z);
            }

            public int getTypeId(int x, int y, int z) {
                return world.getTypeId(x, y, z);
            }
        };
    }
}
