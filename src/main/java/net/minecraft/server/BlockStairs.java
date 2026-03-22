package net.minecraft.server;

import com.legacyminecraft.poseidon.block.StairGeometryBehaviour;

import java.util.ArrayList;
import java.util.Random;

public class BlockStairs extends Block {

    private Block a;
    private final StairGeometryBehaviour stairGeometryService = StairGeometryBehaviour.getInstance();

    protected BlockStairs(int i, Block block) {
        super(i, block.textureId, block.material);
        this.a = block;
        this.c(block.strength);
        this.b(block.durability / 3.0F);
        this.a(block.stepSound);
        this.f(255);
    }

    public void a(IBlockAccess iblockaccess, int i, int j, int k) {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return super.e(world, i, j, k);
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public void a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, ArrayList arraylist) {
        int l = world.getData(i, j, k);
        StairGeometryBehaviour.CollisionShapePair shapes = stairGeometryService.resolveCollisionShapes(l);
        if (shapes != null) {
            applyBounds(shapes.getPrimary());
            super.a(world, i, j, k, axisalignedbb, arraylist);
            applyBounds(shapes.getSecondary());
            super.a(world, i, j, k, axisalignedbb, arraylist);
        }

        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void b(World world, int i, int j, int k, EntityHuman entityhuman) {
        this.a.b(world, i, j, k, entityhuman);
    }

    public void postBreak(World world, int i, int j, int k, int l) {
        this.a.postBreak(world, i, j, k, l);
    }

    public float a(Entity entity) {
        return this.a.a(entity);
    }

    public int a(int i, Random random) {
        return this.a.a(i, random);
    }

    public int a(Random random) {
        return this.a.a(random);
    }

    public int a(int i, int j) {
        return this.a.a(i, j);
    }

    public int a(int i) {
        return this.a.a(i);
    }

    public int c() {
        return this.a.c();
    }

    public void a(World world, int i, int j, int k, Entity entity, Vec3D vec3d) {
        this.a.a(world, i, j, k, entity, vec3d);
    }

    public boolean k_() {
        return this.a.k_();
    }

    public boolean a(int i, boolean flag) {
        return this.a.a(i, flag);
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return this.a.canPlace(world, i, j, k);
    }

    public void c(World world, int i, int j, int k) {
        this.doPhysics(world, i, j, k, 0);
        this.a.c(world, i, j, k);
    }

    public void remove(World world, int i, int j, int k) {
        this.a.remove(world, i, j, k);
    }

    public void dropNaturally(World world, int i, int j, int k, int l, float f) {
        this.a.dropNaturally(world, i, j, k, l, f);
    }

    public void b(World world, int i, int j, int k, Entity entity) {
        this.a.b(world, i, j, k, entity);
    }

    public void a(World world, int i, int j, int k, Random random) {
        this.a.a(world, i, j, k, random);
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        return this.a.interact(world, i, j, k, entityhuman);
    }

    public void d(World world, int i, int j, int k) {
        this.a.d(world, i, j, k);
    }

    public void postPlace(World world, int i, int j, int k, EntityLiving entityliving) {
        world.setData(i, j, k, stairGeometryService.resolvePlacementMetadata(entityliving.yaw));
    }

    private void applyBounds(StairGeometryBehaviour.Bounds bounds) {
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
