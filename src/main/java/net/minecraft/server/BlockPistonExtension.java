package net.minecraft.server;

import com.legacyminecraft.poseidon.block.PistonExtensionGeometryBehaviour;
import com.legacyminecraft.poseidon.block.PistonExtensionLifecycleBehaviour;

import java.util.ArrayList;
import java.util.Random;

public class BlockPistonExtension extends Block {

    private int a = -1;
    private final PistonExtensionGeometryBehaviour pistonExtensionGeometryService = PistonExtensionGeometryBehaviour.getInstance();
    private final PistonExtensionLifecycleBehaviour pistonExtensionLifecycleService = PistonExtensionLifecycleBehaviour.getInstance();

    public BlockPistonExtension(int i, int j) {
        super(i, j, Material.PISTON);
        this.a(h);
        this.c(0.5F);
    }

    public void remove(World world, int i, int j, int k) {
        super.remove(world, i, j, k);
        pistonExtensionLifecycleService.removeAttachedPistonBaseIfNecessary(world, i, j, k, world.getData(i, j, k));
    }

    public int a(int i, int j) {
        return pistonExtensionGeometryService.resolveTextureIndex(i, j, this.a, this.textureId);
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return false;
    }

    public boolean canPlace(World world, int i, int j, int k, int l) {
        return false;
    }

    public int a(Random random) {
        return 0;
    }

    public void a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, ArrayList arraylist) {
        PistonExtensionGeometryBehaviour.CollisionShapePair shapes =
                pistonExtensionGeometryService.resolveCollisionShapes(world.getData(i, j, k));
        if (shapes != null) {
            this.applyBounds(shapes.getPrimary());
            super.a(world, i, j, k, axisalignedbb, arraylist);
            this.applyBounds(shapes.getSecondary());
            super.a(world, i, j, k, axisalignedbb, arraylist);
        }

        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void a(IBlockAccess iblockaccess, int i, int j, int k) {
        PistonExtensionGeometryBehaviour.Bounds bounds =
                pistonExtensionGeometryService.resolveOutlineShape(iblockaccess.getData(i, j, k));
        if (bounds != null) {
            this.applyBounds(bounds);
        }
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        pistonExtensionLifecycleService.validateOrBreakExtension(world, i, j, k, l, world.getData(i, j, k));
    }

    public static int b(int i) {
        return PistonExtensionGeometryBehaviour.getInstance().extractFacing(i);
    }

    private void applyBounds(PistonExtensionGeometryBehaviour.Bounds bounds) {
        this.a(
                bounds.getMinX(),
                bounds.getMinY(),
                bounds.getMinZ(),
                bounds.getMaxX(),
                bounds.getMaxY(),
                bounds.getMaxZ()
        );
    }
}
