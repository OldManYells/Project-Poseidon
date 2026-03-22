package net.minecraft.server;

import com.legacyminecraft.poseidon.block.FluidBlockStateBehaviour;

import java.util.Random;

public abstract class BlockFluids extends Block {
    private static final FluidBlockStateBehaviour FLUID_BLOCK_STATE_SERVICE = FluidBlockStateBehaviour.getInstance();

    protected BlockFluids(int i, Material material) {
        super(i, (material == Material.LAVA ? 14 : 12) * 16 + 13, material);
        float f = 0.0F;
        float f1 = 0.0F;

        this.a(0.0F + f1, 0.0F + f, 0.0F + f1, 1.0F + f1, 1.0F + f, 1.0F + f1);
        this.a(true);
    }

    public static float c(int i) {
        return FLUID_BLOCK_STATE_SERVICE.normalizedFluidHeight(i);
    }

    public int a(int i) {
        return FLUID_BLOCK_STATE_SERVICE.resolveTextureBySide(i, this.textureId);
    }

    protected int g(World world, int i, int j, int k) {
        return FLUID_BLOCK_STATE_SERVICE.resolveRawFlowData(world.getMaterial(i, j, k) == this.material, world.getData(i, j, k));
    }

    protected int b(IBlockAccess iblockaccess, int i, int j, int k) {
        return FLUID_BLOCK_STATE_SERVICE.resolveFlowData(iblockaccess.getMaterial(i, j, k) == this.material, iblockaccess.getData(i, j, k));
    }

    public boolean b() {
        return FLUID_BLOCK_STATE_SERVICE.isNormalCube();
    }

    public boolean a() {
        return FLUID_BLOCK_STATE_SERVICE.isOpaqueCube();
    }

    public boolean a(int i, boolean flag) {
        return FLUID_BLOCK_STATE_SERVICE.canCollideCheck(i, flag);
    }

    public boolean b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        Material material = iblockaccess.getMaterial(i, j, k);
        return FLUID_BLOCK_STATE_SERVICE.shouldRenderSide(material == this.material, material == Material.ICE, l, super.b(iblockaccess, i, j, k, l));
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return null;
    }

    public int a(int i, Random random) {
        return FLUID_BLOCK_STATE_SERVICE.resolveDroppedItemId();
    }

    public int a(Random random) {
        return FLUID_BLOCK_STATE_SERVICE.resolveDroppedCount();
    }

    private Vec3D c(IBlockAccess iblockaccess, int i, int j, int k) {
        FluidBlockStateBehaviour.FlowVector flow = FLUID_BLOCK_STATE_SERVICE.computeFlowVector(new FluidBlockStateBehaviour.FlowQuery() {
            public int flowDataAt(int x, int y, int z) {
                return BlockFluids.this.b(iblockaccess, x, y, z);
            }

            public boolean isSolidMaterial(int x, int y, int z) {
                return iblockaccess.getMaterial(x, y, z).isSolid();
            }

            public int blockDataAt(int x, int y, int z) {
                return iblockaccess.getData(x, y, z);
            }

            public boolean canFlowOutside(int x, int y, int z, int side) {
                return BlockFluids.this.b(iblockaccess, x, y, z, side);
            }
        }, i, j, k);
        return Vec3D.create(flow.x, flow.y, flow.z);
    }

    public void a(World world, int i, int j, int k, Entity entity, Vec3D vec3d) {
        Vec3D vec3d1 = this.c((IBlockAccess) world, i, j, k);

        vec3d.a += vec3d1.a;
        vec3d.b += vec3d1.b;
        vec3d.c += vec3d1.c;
    }

    public int c() {
        return FLUID_BLOCK_STATE_SERVICE.resolveTickDelay(this.material == Material.WATER, this.material == Material.LAVA);
    }

    public void a(World world, int i, int j, int k, Random random) {
        super.a(world, i, j, k, random);
    }

    public void c(World world, int i, int j, int k) {
        this.i(world, i, j, k);
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        this.i(world, i, j, k);
    }

    private void i(World world, int i, int j, int k) {
        if (FLUID_BLOCK_STATE_SERVICE.shouldProcessLavaMix(world.getTypeId(i, j, k), this.id, this.material == Material.LAVA)) {
            boolean hasWaterNeighbor = FLUID_BLOCK_STATE_SERVICE.hasWaterNeighbor(new FluidBlockStateBehaviour.MaterialQuery() {
                public boolean isWater(int x, int y, int z) {
                    return world.getMaterial(x, y, z) == Material.WATER;
                }
            }, i, j, k);

            if (hasWaterNeighbor) {
                int resultBlockId = FLUID_BLOCK_STATE_SERVICE.resolveLavaMixResult(world.getData(i, j, k), Block.OBSIDIAN.id, Block.COBBLESTONE.id);
                if (resultBlockId > 0) {
                    world.setTypeId(i, j, k, resultBlockId);
                }
                this.h(world, i, j, k);
            }
        }
    }

    protected void h(World world, int i, int j, int k) {
        float randomDelta = world.random.nextFloat() - world.random.nextFloat();
        world.makeSound((double) ((float) i + 0.5F), (double) ((float) j + 0.5F), (double) ((float) k + 0.5F), "random.fizz", 0.5F, FLUID_BLOCK_STATE_SERVICE.resolveFizzPitch(randomDelta));

        for (int l = 0; l < FLUID_BLOCK_STATE_SERVICE.resolveFizzSmokeCount(); ++l) {
            world.a("largesmoke", (double) i + Math.random(), (double) j + 1.2D, (double) k + Math.random(), 0.0D, 0.0D, 0.0D);
        }
    }
}
