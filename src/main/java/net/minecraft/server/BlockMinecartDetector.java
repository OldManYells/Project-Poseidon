package net.minecraft.server;

import com.legacyminecraft.poseidon.block.DetectorRailStateBehaviour;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.List;
import java.util.Random;

public class BlockMinecartDetector extends BlockMinecartTrack {
    private final DetectorRailStateBehaviour detectorRailStateService = DetectorRailStateBehaviour.getInstance();

    public BlockMinecartDetector(int i, int j) {
        super(i, j, true);
        this.a(true);
    }

    public int c() {
        return detectorRailStateService.updateDelayTicks();
    }

    public boolean isPowerSource() {
        return true;
    }

    public void a(World world, int i, int j, int k, Entity entity) {
        int l = world.getData(i, j, k);
        if (detectorRailStateService.shouldEvaluateOnEntityCollision(world.isStatic, l)) {
            this.f(world, i, j, k, l);
        }
    }

    public void a(World world, int i, int j, int k, Random random) {
        int l = world.getData(i, j, k);
        if (detectorRailStateService.shouldEvaluateOnTick(world.isStatic, l)) {
            this.f(world, i, j, k, l);
        }
    }

    public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return detectorRailStateService.isPowered(iblockaccess.getData(i, j, k));
    }

    public boolean d(World world, int i, int j, int k, int l) {
        return detectorRailStateService.isTopSidePowered(world.getData(i, j, k), l);
    }

    private void f(World world, int i, int j, int k, int l) {
        boolean flag = detectorRailStateService.isPowered(l);
        boolean flag1 = false;
        List list = world.a(
                EntityMinecart.class,
                AxisAlignedBB.b(
                        (double) ((float) i + 0.125F),
                        (double) j,
                        (double) ((float) k + 0.125F),
                        (double) ((float) (i + 1) - 0.125F),
                        (double) j + 0.25D,
                        (double) ((float) (k + 1) - 0.125F)
                )
        );

        flag1 = detectorRailStateService.hasMinecart(list);

        // CraftBukkit start
        if (detectorRailStateService.shouldFireRedstoneTransitionEvent(flag, flag1)) {
            org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);

            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(
                    block,
                    detectorRailStateService.toRedstoneCurrent(flag),
                    detectorRailStateService.toRedstoneCurrent(flag1)
            );
            world.getServer().getPluginManager().callEvent(eventRedstone);

            flag1 = eventRedstone.getNewCurrent() > 0;
        }
        // CraftBukkit end

        if (detectorRailStateService.shouldPowerOn(flag, flag1)) {
            world.setData(i, j, k, detectorRailStateService.setPowered(l));
            world.applyPhysics(i, j, k, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            world.b(i, j, k, i, j, k);
        }

        if (detectorRailStateService.shouldPowerOff(flag, flag1)) {
            world.setData(i, j, k, detectorRailStateService.clearPowered(l));
            world.applyPhysics(i, j, k, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            world.b(i, j, k, i, j, k);
        }

        if (detectorRailStateService.shouldScheduleRecheck(flag1)) {
            world.c(i, j, k, this.id, detectorRailStateService.updateDelayTicks());
        }
    }
}
