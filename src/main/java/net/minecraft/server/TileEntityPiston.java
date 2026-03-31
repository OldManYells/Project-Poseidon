package net.minecraft.server;

import org.bukkit.craftbukkit.entity.Entity;
import org.bukkit.util.AxisAlignedBB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TileEntityPiston extends TileEntity {

    private int storedBlockId;
    private int storedBlockData;
    private int facing;
    private boolean extending;
    private boolean shouldHeadBeRendered;
    private float progress;
    private float lastProgress;
    private static List movedEntities = new ArrayList();

    public TileEntityPiston() {}

    public TileEntityPiston(int blockId, int blockData, int facing, boolean extending, boolean shouldHeadBeRendered) {
        this.storedBlockId = blockId;
        this.storedBlockData = blockData;
        this.facing = facing;
        this.extending = extending;
        this.shouldHeadBeRendered = shouldHeadBeRendered;
    }

    public int getStoredBlockId() {
        return this.storedBlockId;
    }

    public int getStoredBlockData() {
        return this.storedBlockData;
    }

    public boolean isExtending() {
        return this.extending;
    }

    public int getFacing() {
        return this.facing;
    }

    public float getProgress(float partialTick) {
        if (partialTick > 1.0F) {
            partialTick = 1.0F;
        }

        return this.lastProgress + (this.progress - this.lastProgress) * partialTick;
    }

    private void moveCollidedEntities(float progress, float deltaProgress) {
        if (!this.extending) {
            --progress;
        } else {
            progress = 1.0F - progress;
        }

        AxisAlignedBB axisAlignedBB = CraftBlock.PISTON_MOVING.a(this.world, this.x, this.y, this.z, this.storedBlockId, progress, this.facing);

        if (axisAlignedBB != null) {
            List collidingEntities = this.world.b((Entity) null, axisAlignedBB);

            if (!collidingEntities.isEmpty()) {
                movedEntities.addAll(collidingEntities);
                Iterator iterator = movedEntities.iterator();

                while (iterator.hasNext()) {
                    Entity entity = (Entity) iterator.next();
                    entity.move((double) (deltaProgress * (float) PistonBlockTextures.b[this.facing]), (double) (deltaProgress * (float) PistonBlockTextures.c[this.facing]), (double) (deltaProgress * (float) PistonBlockTextures.d[this.facing]));
                }

                movedEntities.clear();
            }
        }
    }

    public void clearPistonTileEntity() {
        if (this.lastProgress < 1.0F) {
            this.lastProgress = this.progress = 1.0F;
            this.world.o(this.x, this.y, this.z);
            this.invalidate();
            if (this.world.getTypeId(this.x, this.y, this.z) == CraftBlock.PISTON_MOVING.id) {
                this.world.setTypeIdAndData(this.x, this.y, this.z, this.storedBlockId, this.storedBlockData);
            }
        }
    }

    public void updateEntity() {
        if (this.world == null) {
            return;
        }

        this.lastProgress = this.progress;
        if (this.lastProgress >= 1.0F) {
            this.moveCollidedEntities(1.0F, 0.25F);
            this.world.o(this.x, this.y, this.z);
            this.invalidate();
            if (this.world.getTypeId(this.x, this.y, this.z) == CraftBlock.PISTON_MOVING.id) {
                this.world.setTypeIdAndData(this.x, this.y, this.z, this.storedBlockId, this.storedBlockData);
            }
        } else {
            this.progress += 0.5F;
            if (this.progress >= 1.0F) {
                this.progress = 1.0F;
            }

            if (this.extending) {
                this.moveCollidedEntities(this.progress, this.progress - this.lastProgress + 0.0625F);
            }
        }
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.storedBlockId = tag.getInt("blockId");
        this.storedBlockData = tag.getInt("blockData");
        this.facing = tag.getInt("facing");
        this.lastProgress = this.progress = tag.getFloat("progress");
        this.extending = tag.getBoolean("extending");
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInt("blockId", this.storedBlockId);
        tag.setInt("blockData", this.storedBlockData);
        tag.setInt("facing", this.facing);
        tag.setFloat("progress", this.lastProgress);
        tag.setBoolean("extending", this.extending);
    }

    @Deprecated
    public int a() {
        return this.getStoredBlockId();
    }

    @Deprecated
    public int e() {
        return this.getStoredBlockData();
    }

    @Deprecated
    public boolean c() {
        return this.isExtending();
    }

    @Deprecated
    public int d() {
        return this.getFacing();
    }

    @Deprecated
    public float a(float partialTick) {
        return this.getProgress(partialTick);
    }

    @Deprecated
    private void a(float progress, float deltaProgress) {
        this.moveCollidedEntities(progress, deltaProgress);
    }

    @Deprecated
    public void k() {
        this.clearPistonTileEntity();
    }

    @Deprecated
    public void g_() {
        this.updateEntity();
    }

    @Deprecated
    public void a(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Deprecated
    public void b(NBTTagCompound tag) {
        this.writeToNBT(tag);
    }
}
