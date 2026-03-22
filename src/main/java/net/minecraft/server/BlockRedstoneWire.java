package net.minecraft.server;

import com.legacyminecraft.poseidon.block.RedstoneWireStateBehaviour;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BlockRedstoneWire extends Block {

    private boolean a = true;
    private Set b = new HashSet();
    private static final RedstoneWireStateBehaviour REDSTONE_WIRE_STATE_SERVICE = RedstoneWireStateBehaviour.getInstance();

    public BlockRedstoneWire(int i, int j) {
        super(i, j, Material.ORIENTABLE);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
    }

    public int a(int i, int j) {
        return this.textureId;
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

    public boolean canPlace(World world, int i, int j, int k) {
        return REDSTONE_WIRE_STATE_SERVICE.canPlace(world.e(i, j - 1, k));
    }

    private void g(World world, int i, int j, int k) {
        this.a(world, i, j, k, i, j, k);
        ArrayList arraylist = new ArrayList(this.b);

        this.b.clear();

        for (int l = 0; l < arraylist.size(); ++l) {
            ChunkPosition chunkposition = (ChunkPosition) arraylist.get(l);

            world.applyPhysics(chunkposition.x, chunkposition.y, chunkposition.z, this.id);
        }
    }

    private void a(World world, int i, int j, int k, int l, int i1, int j1) {
        int k1 = world.getData(i, j, k);
        this.a = false;
        boolean flag = world.isBlockIndirectlyPowered(i, j, k);
        this.a = true;
        int l1 = REDSTONE_WIRE_STATE_SERVICE.computeTargetPower(new RedstoneWireStateBehaviour.PowerComputationQuery() {
            public int getPowerAt(int x, int y, int z, int currentMax) {
                return BlockRedstoneWire.this.getPower(world, x, y, z, currentMax);
            }

            public boolean isSolidTop(int x, int y, int z) {
                return world.e(x, y, z);
            }
        }, i, j, k, l, i1, j1, flag);

        // CraftBukkit start
        if (k1 != l1) {
            BlockRedstoneEvent event = new BlockRedstoneEvent(world.getWorld().getBlockAt(i, j, k), k1, l1);
            world.getServer().getPluginManager().callEvent(event);

            l1 = event.getNewCurrent();
        }
        // CraftBukkit end

        if (k1 != l1) {
            world.suppressPhysics = true;
            world.setData(i, j, k, l1);
            world.b(i, j, k, i, j, k);
            world.suppressPhysics = false;

            REDSTONE_WIRE_STATE_SERVICE.forEachPropagationTarget(new RedstoneWireStateBehaviour.PropagationQuery() {
                public int getPowerAt(int x, int y, int z, int currentMax) {
                    return BlockRedstoneWire.this.getPower(world, x, y, z, currentMax);
                }

                public int currentPower(int x, int y, int z) {
                    return world.getData(x, y, z);
                }

                public boolean isSolidTop(int x, int y, int z) {
                    return world.e(x, y, z);
                }
            }, i, j, k, new RedstoneWireStateBehaviour.PositionConsumer() {
                public void accept(int x, int y, int z) {
                    BlockRedstoneWire.this.a(world, x, y, z, i, j, k);
                }
            });

            if (k1 == 0 || l1 == 0) {
                REDSTONE_WIRE_STATE_SERVICE.forEachTransitionPhysicsPosition(i, j, k, new RedstoneWireStateBehaviour.PositionConsumer() {
                    public void accept(int x, int y, int z) {
                        BlockRedstoneWire.this.b.add(new ChunkPosition(x, y, z));
                    }
                });
            }
        }
    }

    private void h(World world, int i, int j, int k) {
        if (world.getTypeId(i, j, k) == this.id) {
            REDSTONE_WIRE_STATE_SERVICE.forEachTransitionPhysicsPosition(i, j, k, new RedstoneWireStateBehaviour.PositionConsumer() {
                public void accept(int x, int y, int z) {
                    world.applyPhysics(x, y, z, BlockRedstoneWire.this.id);
                }
            });
        }
    }

    public void c(World world, int i, int j, int k) {
        super.c(world, i, j, k);
        if (!world.isStatic) {
            this.g(world, i, j, k);
            world.applyPhysics(i, j + 1, k, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            REDSTONE_WIRE_STATE_SERVICE.forEachExtendedNeighborPosition(new RedstoneWireStateBehaviour.SolidQuery() {
                public boolean isSolidTop(int x, int y, int z) {
                    return world.e(x, y, z);
                }
            }, i, j, k, new RedstoneWireStateBehaviour.PositionConsumer() {
                public void accept(int x, int y, int z) {
                    BlockRedstoneWire.this.h(world, x, y, z);
                }
            });
        }
    }

    public void remove(World world, int i, int j, int k) {
        super.remove(world, i, j, k);
        if (!world.isStatic) {
            world.applyPhysics(i, j + 1, k, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            this.g(world, i, j, k);
            REDSTONE_WIRE_STATE_SERVICE.forEachExtendedNeighborPosition(new RedstoneWireStateBehaviour.SolidQuery() {
                public boolean isSolidTop(int x, int y, int z) {
                    return world.e(x, y, z);
                }
            }, i, j, k, new RedstoneWireStateBehaviour.PositionConsumer() {
                public void accept(int x, int y, int z) {
                    BlockRedstoneWire.this.h(world, x, y, z);
                }
            });
        }
    }

    // CraftBukkit - private -> public
    public int getPower(World world, int i, int j, int k, int l) {
        if (world.getTypeId(i, j, k) != this.id) {
            return l;
        } else {
            int i1 = world.getData(i, j, k);

            return i1 > l ? i1 : l;
        }
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (!world.isStatic) {
            int i1 = world.getData(i, j, k);
            boolean flag = REDSTONE_WIRE_STATE_SERVICE.canPlace(world.e(i, j - 1, k));

            if (!flag) {
                this.g(world, i, j, k, i1);
                world.setTypeId(i, j, k, 0);
            } else {
                this.g(world, i, j, k);
            }

            super.doPhysics(world, i, j, k, l);
        }
    }

    public int a(int i, Random random) {
        return Item.REDSTONE.id;
    }

    public boolean d(World world, int i, int j, int k, int l) {
        return !this.a ? false : this.a(world, i, j, k, l);
    }

    public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        if (!this.a) {
            return false;
        } else if (iblockaccess.getData(i, j, k) == 0) {
            return false;
        } else if (l == 1) {
            return true;
        } else {
            boolean flag = c(iblockaccess, i - 1, j, k, 1) || !iblockaccess.e(i - 1, j, k) && c(iblockaccess, i - 1, j - 1, k, -1);
            boolean flag1 = c(iblockaccess, i + 1, j, k, 3) || !iblockaccess.e(i + 1, j, k) && c(iblockaccess, i + 1, j - 1, k, -1);
            boolean flag2 = c(iblockaccess, i, j, k - 1, 2) || !iblockaccess.e(i, j, k - 1) && c(iblockaccess, i, j - 1, k - 1, -1);
            boolean flag3 = c(iblockaccess, i, j, k + 1, 0) || !iblockaccess.e(i, j, k + 1) && c(iblockaccess, i, j - 1, k + 1, -1);

            if (!iblockaccess.e(i, j + 1, k)) {
                if (iblockaccess.e(i - 1, j, k) && c(iblockaccess, i - 1, j + 1, k, -1)) {
                    flag = true;
                }

                if (iblockaccess.e(i + 1, j, k) && c(iblockaccess, i + 1, j + 1, k, -1)) {
                    flag1 = true;
                }

                if (iblockaccess.e(i, j, k - 1) && c(iblockaccess, i, j + 1, k - 1, -1)) {
                    flag2 = true;
                }

                if (iblockaccess.e(i, j, k + 1) && c(iblockaccess, i, j + 1, k + 1, -1)) {
                    flag3 = true;
                }
            }

            return !flag2 && !flag1 && !flag && !flag3 && l >= 2 && l <= 5 ? true : (l == 2 && flag2 && !flag && !flag1 ? true : (l == 3 && flag3 && !flag && !flag1 ? true : (l == 4 && flag && !flag2 && !flag3 ? true : l == 5 && flag1 && !flag2 && !flag3)));
        }
    }

    public boolean isPowerSource() {
        return this.a;
    }

    public static boolean c(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return REDSTONE_WIRE_STATE_SERVICE.isWireOrPowerSourceConnection(new RedstoneWireStateBehaviour.StaticConnectionQuery() {
            public int typeIdAt(int x, int y, int z) {
                return iblockaccess.getTypeId(x, y, z);
            }

            public int dataAt(int x, int y, int z) {
                return iblockaccess.getData(x, y, z);
            }

            public boolean isPowerSource(int typeId) {
                return Block.byId[typeId].isPowerSource();
            }
        }, i, j, k, l, Block.REDSTONE_WIRE.id, Block.DIODE_OFF.id, Block.DIODE_ON.id, BedBlockTextures.b);
    }
}
