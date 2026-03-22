package net.minecraft.server;

import com.legacyminecraft.poseidon.block.BedStateBehaviour;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Iterator;
import java.util.Random;

public class BlockBed extends Block {

    public static final int[][] a = new int[][] { { 0, 1}, { -1, 0}, { 0, -1}, { 1, 0}};
    private static final BedStateBehaviour BED_STATE_SERVICE = BedStateBehaviour.getInstance();

    public BlockBed(int i) {
        super(i, 134, Material.CLOTH);
        this.o();
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        if (world.isStatic) {
            return true;
        } else {
            int l = world.getData(i, j, k);
            int headX = i;
            int headZ = k;

            if (!BED_STATE_SERVICE.isHead(l)) {
                int i1 = BED_STATE_SERVICE.orientation(l);

                headX = BED_STATE_SERVICE.resolveHeadXFromPart(i, i1, a, false);
                headZ = BED_STATE_SERVICE.resolveHeadZFromPart(k, i1, a, false);
                if (world.getTypeId(headX, j, headZ) != this.id) {
                    return true;
                }

                l = world.getData(headX, j, headZ);
            }

            if (BED_STATE_SERVICE.shouldExplodeInDimension(world.worldProvider.d())) {
                double d0 = BED_STATE_SERVICE.centeredCoordinate(headX);
                double d1 = BED_STATE_SERVICE.centeredCoordinate(j);
                double d2 = BED_STATE_SERVICE.centeredCoordinate(headZ);

                world.setTypeId(headX, j, headZ, 0);
                int j1 = BED_STATE_SERVICE.orientation(l);
                int otherX = BED_STATE_SERVICE.resolveOtherHalfXFromHead(headX, j1, a);
                int otherZ = BED_STATE_SERVICE.resolveOtherHalfZFromHead(headZ, j1, a);

                if (world.getTypeId(otherX, j, otherZ) == this.id) {
                    world.setTypeId(otherX, j, otherZ, 0);
                    d0 = BED_STATE_SERVICE.average(d0, BED_STATE_SERVICE.centeredCoordinate(otherX));
                    d1 = BED_STATE_SERVICE.average(d1, BED_STATE_SERVICE.centeredCoordinate(j));
                    d2 = BED_STATE_SERVICE.average(d2, BED_STATE_SERVICE.centeredCoordinate(otherZ));
                }

                world.createExplosion((Entity) null, BED_STATE_SERVICE.centeredCoordinate(otherX), BED_STATE_SERVICE.centeredCoordinate(j), BED_STATE_SERVICE.centeredCoordinate(otherZ), 5.0F, true, EntityDamageEvent.DamageCause.BED_EXPLOSION); //Project poseidon
                return true;
            } else {
                if (BED_STATE_SERVICE.isOccupied(l)) {
                    EntityHuman entityhuman1 = null;
                    Iterator iterator = world.players.iterator();

                    while (iterator.hasNext()) {
                        EntityHuman entityhuman2 = (EntityHuman) iterator.next();

                        if (entityhuman2.isSleeping()) {
                            ChunkCoordinates chunkcoordinates = entityhuman2.A;

                            if (chunkcoordinates.x == headX && chunkcoordinates.y == j && chunkcoordinates.z == headZ) {
                                entityhuman1 = entityhuman2;
                            }
                        }
                    }

                    if (entityhuman1 != null) {
                        entityhuman.a("tile.bed.occupied");
                        return true;
                    }

                    a(world, headX, j, headZ, false);
                }

                EnumBedError enumbederror = entityhuman.a(headX, j, headZ);

                if (enumbederror == EnumBedError.OK) {
                    a(world, headX, j, headZ, true);
                    return true;
                } else {
                    if (enumbederror == EnumBedError.NOT_POSSIBLE_NOW) {
                        entityhuman.a("tile.bed.noSleep");
                    }

                    return true;
                }
            }
        }
    }

    public int a(int i, int j) {
        return BED_STATE_SERVICE.resolveTextureBySide(i, j, this.textureId, Block.WOOD.textureId, BedBlockTextures.c);
    }

    public boolean b() {
        return false;
    }

    public boolean a() {
        return false;
    }

    public void a(IBlockAccess iblockaccess, int i, int j, int k) {
        this.o();
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        int i1 = world.getData(i, j, k);
        int j1 = BED_STATE_SERVICE.orientation(i1);

        if (BED_STATE_SERVICE.isHead(i1)) {
            int counterpartX = BED_STATE_SERVICE.resolveCounterpartXForPhysics(i, j1, a, true);
            int counterpartZ = BED_STATE_SERVICE.resolveCounterpartZForPhysics(k, j1, a, true);
            if (BED_STATE_SERVICE.shouldRemovePartForMissingCounterpart(world.getTypeId(counterpartX, j, counterpartZ), this.id)) {
                world.setTypeId(i, j, k, 0);
            }
        } else {
            int counterpartX = BED_STATE_SERVICE.resolveCounterpartXForPhysics(i, j1, a, false);
            int counterpartZ = BED_STATE_SERVICE.resolveCounterpartZForPhysics(k, j1, a, false);
            if (BED_STATE_SERVICE.shouldRemovePartForMissingCounterpart(world.getTypeId(counterpartX, j, counterpartZ), this.id)) {
                world.setTypeId(i, j, k, 0);
                if (!world.isStatic) {
                    this.g(world, i, j, k, i1);
                }
            }
        }
    }

    public int a(int i, Random random) {
        return BED_STATE_SERVICE.resolveDropItemId(i, Item.BED.id);
    }

    private void o() {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
    }

    public static int c(int i) {
        return BED_STATE_SERVICE.orientation(i);
    }

    public static boolean d(int i) {
        return BED_STATE_SERVICE.isHead(i);
    }

    public static boolean e(int i) {
        return BED_STATE_SERVICE.isOccupied(i);
    }

    public static void a(World world, int i, int j, int k, boolean flag) {
        world.setData(i, j, k, BED_STATE_SERVICE.setOccupied(world.getData(i, j, k), flag));
    }

    public static ChunkCoordinates f(World world, int i, int j, int k, int l) {
        BedStateBehaviour.ChunkCoord spawn = BED_STATE_SERVICE.findSpawnPosition(new BedStateBehaviour.SpawnQuery() {
            public boolean isSolidTopSurface(int x, int y, int z) {
                return world.e(x, y, z);
            }

            public boolean isEmpty(int x, int y, int z) {
                return world.isEmpty(x, y, z);
            }
        }, i, j, k, c(world.getData(i, j, k)), l, a);
        return spawn == null ? null : new ChunkCoordinates(spawn.x, spawn.y, spawn.z);
    }

    public void dropNaturally(World world, int i, int j, int k, int l, float f) {
        if (BED_STATE_SERVICE.shouldDropNaturally(l)) {
            super.dropNaturally(world, i, j, k, l, f);
        }
    }

    public int e() {
        return 1;
    }
}
