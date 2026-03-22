package net.minecraft.server;

import com.legacyminecraft.poseidon.block.JukeboxStateBehaviour;

public class BlockJukeBox extends BlockContainer {
    private final JukeboxStateBehaviour jukeboxStateService = JukeboxStateBehaviour.getInstance();

    protected BlockJukeBox(int i, int j) {
        super(i, j, Material.WOOD);
    }

    public int a(int i) {
        return jukeboxStateService.resolveTextureBySide(i, this.textureId);
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        if (!jukeboxStateService.hasRecord(world.getData(i, j, k))) {
            return false;
        } else {
            this.b_(world, i, j, k);
            return true;
        }
    }

    public void f(World world, int i, int j, int k, int l) {
        if (!jukeboxStateService.shouldIgnoreClientOperations(world.isStatic)) {
            TileEntityRecordPlayer tileentityrecordplayer = (TileEntityRecordPlayer) world.getTileEntity(i, j, k);

            tileentityrecordplayer.a = l;
            tileentityrecordplayer.update();
            world.setData(i, j, k, jukeboxStateService.insertedBlockData());
        }
    }

    public void b_(World world, int i, int j, int k) {
        if (!jukeboxStateService.shouldIgnoreClientOperations(world.isStatic)) {
            TileEntityRecordPlayer tileentityrecordplayer = (TileEntityRecordPlayer) world.getTileEntity(i, j, k);
            if (tileentityrecordplayer == null) return; // CraftBukkit
            int l = tileentityrecordplayer.a;

            if (jukeboxStateService.shouldEjectRecord(l)) {
                world.e(jukeboxStateService.stoppedRecordEffectId(), i, j, k, 0);
                world.a((String) null, i, j, k);
                tileentityrecordplayer.a = jukeboxStateService.clearedRecordItemId();
                tileentityrecordplayer.update();
                world.setData(i, j, k, jukeboxStateService.clearedRecordItemId());
                float f = jukeboxStateService.recordDropSpread();
                double d0 = jukeboxStateService.resolveDropXOffset(world.random, f);
                double d1 = jukeboxStateService.resolveDropYOffset(world.random, f);
                double d2 = jukeboxStateService.resolveDropZOffset(world.random, f);
                EntityItem entityitem = new EntityItem(world, (double) i + d0, (double) j + d1, (double) k + d2, new ItemStack(l, 1, 0));

                entityitem.pickupDelay = jukeboxStateService.recordPickupDelayTicks();
                world.addEntity(entityitem);
            }
        }
    }

    public void remove(World world, int i, int j, int k) {
        this.b_(world, i, j, k);
        super.remove(world, i, j, k);
    }

    public void dropNaturally(World world, int i, int j, int k, int l, float f) {
        if (!world.isStatic) {
            super.dropNaturally(world, i, j, k, l, f);
        }
    }

    protected TileEntity a_() {
        return new TileEntityRecordPlayer();
    }
}
