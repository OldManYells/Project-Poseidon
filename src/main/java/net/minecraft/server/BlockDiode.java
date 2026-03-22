package net.minecraft.server;

import com.legacyminecraft.poseidon.block.DiodeStateBehaviour;

import java.util.Random;

public class BlockDiode extends Block {

    public static final double[] a = new double[] { -0.0625D, 0.0625D, 0.1875D, 0.3125D};
    private static final int[] b = new int[] { 1, 2, 3, 4};
    private final boolean c;
    private final DiodeStateBehaviour diodeStateService = DiodeStateBehaviour.getInstance();

    protected BlockDiode(int i, boolean flag) {
        super(i, 6, Material.ORIENTABLE);
        this.c = flag;
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    public boolean b() {
        return false;
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return diodeStateService.canPlaceOnSupport(world.e(i, j - 1, k), super.canPlace(world, i, j, k));
    }

    public boolean f(World world, int i, int j, int k) {
        return diodeStateService.canRemainOnSupport(world.e(i, j - 1, k), super.f(world, i, j, k));
    }

    public void a(World world, int i, int j, int k, Random random) {
        int l = world.getData(i, j, k);
        boolean flag = this.f(world, i, j, k, l);

        if (diodeStateService.shouldTurnOffOnTick(this.c, flag)) {
            world.setTypeIdAndData(i, j, k, Block.DIODE_OFF.id, l);
        } else if (diodeStateService.shouldTurnOnOnTick(this.c)) {
            world.setTypeIdAndData(i, j, k, Block.DIODE_ON.id, l);
            if (diodeStateService.shouldScheduleRecheckAfterTurnOn(flag)) {
                world.c(i, j, k, Block.DIODE_ON.id, diodeStateService.resolveTickDelayFromData(l, b));
            }
        }
    }

    public int a(int i, int j) {
        return diodeStateService.resolveTextureBySideAndLit(i, this.c);
    }

    public int a(int i) {
        return this.a(i, 0);
    }

    public boolean d(World world, int i, int j, int k, int l) {
        return this.a(world, i, j, k, l);
    }

    public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        if (!this.c) {
            return false;
        } else {
            int i1 = iblockaccess.getData(i, j, k) & 3;

            return diodeStateService.isPoweringSide(this.c, i1, l);
        }
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (diodeStateService.shouldDropForInvalidSupport(this.f(world, i, j, k))) {
            this.g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
        } else {
            int i1 = world.getData(i, j, k);
            boolean flag = this.f(world, i, j, k, i1);

            if (diodeStateService.shouldScheduleStateCheck(this.c, flag)) {
                world.c(i, j, k, this.id, diodeStateService.resolveTickDelayFromData(i1, b));
            }
        }
    }

    private boolean f(World world, int i, int j, int k, int l) {
        return diodeStateService.isInputPowered(new DiodeStateBehaviour.InputPowerQuery() {
            public boolean isBlockFaceIndirectlyPowered(int x, int y, int z, int face) {
                return world.isBlockFaceIndirectlyPowered(x, y, z, face);
            }

            public int getTypeId(int x, int y, int z) {
                return world.getTypeId(x, y, z);
            }

            public int getData(int x, int y, int z) {
                return world.getData(x, y, z);
            }
        }, i, j, k, l, Block.REDSTONE_WIRE.id);
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        world.setData(i, j, k, diodeStateService.cycleDelayBits(world.getData(i, j, k)));
        return true;
    }

    public boolean isPowerSource() {
        return false;
    }

    public void postPlace(World world, int i, int j, int k, EntityLiving entityliving) {
        int l = diodeStateService.resolvePlacementDataFromYaw(entityliving.yaw);

        world.setData(i, j, k, l);
        boolean flag = this.f(world, i, j, k, l);

        if (diodeStateService.shouldScheduleImmediateTickOnPlace(flag)) {
            world.c(i, j, k, this.id, 1);
        }
    }

    public void c(World world, int i, int j, int k) {
        diodeStateService.applyNeighborPhysics(new DiodeStateBehaviour.NeighborPhysicsApplier() {
            public void applyPhysics(int x, int y, int z, int blockId) {
                world.applyPhysics(x, y, z, blockId);
            }
        }, i, j, k, this.id);
    }

    public boolean a() {
        return false;
    }

    public int a(int i, Random random) {
        return diodeStateService.resolveDropItemId(Item.DIODE.id);
    }
}
