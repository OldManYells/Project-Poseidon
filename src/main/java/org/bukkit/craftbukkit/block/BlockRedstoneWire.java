package org.bukkit.craftbukkit.block;

import net.minecraft.server.*;
import net.minecraft.server.CraftBlock;
import org.bukkit.craftbukkit.item.Item;
import org.bukkit.craftbukkit.server.ChunkPosition;
import org.bukkit.craftbukkit.world.World;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BlockRedstoneWire extends net.minecraft.server.CraftBlock {

    private boolean canProvidePower = true;
    private Set blocksNeedingUpdate = new HashSet();

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
        return world.e(i, j - 1, k);
    }

    private void updatePower(World world, int i, int j, int k) {
        this.propagatePower(world, i, j, k, i, j, k);
        ArrayList arraylist = new ArrayList(this.blocksNeedingUpdate);

        this.blocksNeedingUpdate.clear();

        for (int l = 0; l < arraylist.size(); ++l) {
            ChunkPosition chunkposition = (ChunkPosition) arraylist.get(l);

            world.applyPhysics(chunkposition.x, chunkposition.y, chunkposition.z, this.id);
        }
    }

    private void propagatePower(World world, int i, int j, int k, int l, int i1, int j1) {
        int k1 = world.getData(i, j, k);
        int l1 = 0;

        this.canProvidePower = false;
        boolean flag = world.isBlockIndirectlyPowered(i, j, k);

        this.canProvidePower = true;
        int i2;
        int j2;
        int k2;

        if (flag) {
            l1 = 15;
        } else {
            for (i2 = 0; i2 < 4; ++i2) {
                j2 = i;
                k2 = k;
                if (i2 == 0) {
                    j2 = i - 1;
                }

                if (i2 == 1) {
                    ++j2;
                }

                if (i2 == 2) {
                    k2 = k - 1;
                }

                if (i2 == 3) {
                    ++k2;
                }

                if (j2 != l || j != i1 || k2 != j1) {
                    l1 = this.getPower(world, j2, j, k2, l1);
                }

                if (world.e(j2, j, k2) && !world.e(i, j + 1, k)) {
                    if (j2 != l || j + 1 != i1 || k2 != j1) {
                        l1 = this.getPower(world, j2, j + 1, k2, l1);
                    }
                } else if (!world.e(j2, j, k2) && (j2 != l || j - 1 != i1 || k2 != j1)) {
                    l1 = this.getPower(world, j2, j - 1, k2, l1);
                }
            }

            if (l1 > 0) {
                --l1;
            } else {
                l1 = 0;
            }
        }

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

            for (i2 = 0; i2 < 4; ++i2) {
                j2 = i;
                k2 = k;
                int l2 = j - 1;

                if (i2 == 0) {
                    j2 = i - 1;
                }

                if (i2 == 1) {
                    ++j2;
                }

                if (i2 == 2) {
                    k2 = k - 1;
                }

                if (i2 == 3) {
                    ++k2;
                }

                if (world.e(j2, j, k2)) {
                    l2 += 2;
                }

                boolean flag1 = false;
                int i3 = this.getPower(world, j2, j, k2, -1);

                l1 = world.getData(i, j, k);
                if (l1 > 0) {
                    --l1;
                }

                if (i3 >= 0 && i3 != l1) {
                    this.propagatePower(world, j2, j, k2, i, j, k);
                }

                i3 = this.getPower(world, j2, l2, k2, -1);
                l1 = world.getData(i, j, k);
                if (l1 > 0) {
                    --l1;
                }

                if (i3 >= 0 && i3 != l1) {
                    this.propagatePower(world, j2, l2, k2, i, j, k);
                }
            }

            if (k1 == 0 || l1 == 0) {
                this.blocksNeedingUpdate.add(new ChunkPosition(i, j, k));
                this.blocksNeedingUpdate.add(new ChunkPosition(i - 1, j, k));
                this.blocksNeedingUpdate.add(new ChunkPosition(i + 1, j, k));
                this.blocksNeedingUpdate.add(new ChunkPosition(i, j - 1, k));
                this.blocksNeedingUpdate.add(new ChunkPosition(i, j + 1, k));
                this.blocksNeedingUpdate.add(new ChunkPosition(i, j, k - 1));
                this.blocksNeedingUpdate.add(new ChunkPosition(i, j, k + 1));
            }
        }
    }

    private void notifyNeighborWire(World world, int i, int j, int k) {
        if (world.getTypeId(i, j, k) == this.id) {
            world.applyPhysics(i, j, k, this.id);
            world.applyPhysics(i - 1, j, k, this.id);
            world.applyPhysics(i + 1, j, k, this.id);
            world.applyPhysics(i, j, k - 1, this.id);
            world.applyPhysics(i, j, k + 1, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            world.applyPhysics(i, j + 1, k, this.id);
        }
    }

    public void onBlockAdded(World world, int i, int j, int k) {
        super.c(world, i, j, k);
        if (!world.isStatic) {
            this.updatePower(world, i, j, k);
            world.applyPhysics(i, j + 1, k, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            this.notifyNeighborWire(world, i - 1, j, k);
            this.notifyNeighborWire(world, i + 1, j, k);
            this.notifyNeighborWire(world, i, j, k - 1);
            this.notifyNeighborWire(world, i, j, k + 1);
            if (world.e(i - 1, j, k)) {
                this.notifyNeighborWire(world, i - 1, j + 1, k);
            } else {
                this.notifyNeighborWire(world, i - 1, j - 1, k);
            }

            if (world.e(i + 1, j, k)) {
                this.notifyNeighborWire(world, i + 1, j + 1, k);
            } else {
                this.notifyNeighborWire(world, i + 1, j - 1, k);
            }

            if (world.e(i, j, k - 1)) {
                this.notifyNeighborWire(world, i, j + 1, k - 1);
            } else {
                this.notifyNeighborWire(world, i, j - 1, k - 1);
            }

            if (world.e(i, j, k + 1)) {
                this.notifyNeighborWire(world, i, j + 1, k + 1);
            } else {
                this.notifyNeighborWire(world, i, j - 1, k + 1);
            }
        }
    }

    public void remove(World world, int i, int j, int k) {
        super.remove(world, i, j, k);
        if (!world.isStatic) {
            world.applyPhysics(i, j + 1, k, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            this.updatePower(world, i, j, k);
            this.notifyNeighborWire(world, i - 1, j, k);
            this.notifyNeighborWire(world, i + 1, j, k);
            this.notifyNeighborWire(world, i, j, k - 1);
            this.notifyNeighborWire(world, i, j, k + 1);
            if (world.e(i - 1, j, k)) {
                this.notifyNeighborWire(world, i - 1, j + 1, k);
            } else {
                this.notifyNeighborWire(world, i - 1, j - 1, k);
            }

            if (world.e(i + 1, j, k)) {
                this.notifyNeighborWire(world, i + 1, j + 1, k);
            } else {
                this.notifyNeighborWire(world, i + 1, j - 1, k);
            }

            if (world.e(i, j, k - 1)) {
                this.notifyNeighborWire(world, i, j + 1, k - 1);
            } else {
                this.notifyNeighborWire(world, i, j - 1, k - 1);
            }

            if (world.e(i, j, k + 1)) {
                this.notifyNeighborWire(world, i, j + 1, k + 1);
            } else {
                this.notifyNeighborWire(world, i, j - 1, k + 1);
            }
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
            boolean flag = this.canPlace(world, i, j, k);

            if (!flag) {
                this.g(world, i, j, k, i1);
                world.setTypeId(i, j, k, 0);
            } else {
                this.updatePower(world, i, j, k);
            }

            super.doPhysics(world, i, j, k, l);
        }
    }

    public int getDropId(int i, Random random) {
        return Item.REDSTONE.id;
    }

    public boolean isProvidingStrongPower(World world, int i, int j, int k, int l) {
        return !this.canProvidePower ? false : this.isProvidingWeakPower(world, i, j, k, l);
    }

    public boolean isProvidingWeakPower(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        if (!this.canProvidePower) {
            return false;
        } else if (iblockaccess.getData(i, j, k) == 0) {
            return false;
        } else if (l == 1) {
            return true;
        } else {
            boolean flag = canConnectTo(iblockaccess, i - 1, j, k, 1) || !iblockaccess.e(i - 1, j, k) && canConnectTo(iblockaccess, i - 1, j - 1, k, -1);
            boolean flag1 = canConnectTo(iblockaccess, i + 1, j, k, 3) || !iblockaccess.e(i + 1, j, k) && canConnectTo(iblockaccess, i + 1, j - 1, k, -1);
            boolean flag2 = canConnectTo(iblockaccess, i, j, k - 1, 2) || !iblockaccess.e(i, j, k - 1) && canConnectTo(iblockaccess, i, j - 1, k - 1, -1);
            boolean flag3 = canConnectTo(iblockaccess, i, j, k + 1, 0) || !iblockaccess.e(i, j, k + 1) && canConnectTo(iblockaccess, i, j - 1, k + 1, -1);

            if (!iblockaccess.e(i, j + 1, k)) {
                if (iblockaccess.e(i - 1, j, k) && canConnectTo(iblockaccess, i - 1, j + 1, k, -1)) {
                    flag = true;
                }

                if (iblockaccess.e(i + 1, j, k) && canConnectTo(iblockaccess, i + 1, j + 1, k, -1)) {
                    flag1 = true;
                }

                if (iblockaccess.e(i, j, k - 1) && canConnectTo(iblockaccess, i, j + 1, k - 1, -1)) {
                    flag2 = true;
                }

                if (iblockaccess.e(i, j, k + 1) && canConnectTo(iblockaccess, i, j + 1, k + 1, -1)) {
                    flag3 = true;
                }
            }

            return !flag2 && !flag1 && !flag && !flag3 && l >= 2 && l <= 5 ? true : (l == 2 && flag2 && !flag && !flag1 ? true : (l == 3 && flag3 && !flag && !flag1 ? true : (l == 4 && flag && !flag2 && !flag3 ? true : l == 5 && flag1 && !flag2 && !flag3)));
        }
    }

    public boolean isPowerSource() {
        return this.canProvidePower;
    }

    public static boolean canConnectTo(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        int i1 = iblockaccess.getTypeId(i, j, k);

        if (i1 == net.minecraft.server.CraftBlock.REDSTONE_WIRE.id) {
            return true;
        } else if (i1 == 0) {
            return false;
        } else if (net.minecraft.server.CraftBlock.byId[i1].isPowerSource()) {
            return true;
        } else if (i1 != net.minecraft.server.CraftBlock.DIODE_OFF.id && i1 != CraftBlock.DIODE_ON.id) {
            return false;
        } else {
            int j1 = iblockaccess.getData(i, j, k);

            return l == BedBlockTextures.b[j1 & 3];
        }
    }
}
