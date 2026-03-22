package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.PistonStickyRetractionBehaviour;
import net.minecraft.server.Block;
import net.minecraft.server.PistonBlockTextures;
import net.minecraft.server.World;
import org.junit.Assert;
import org.junit.Test;

public class PistonStickyRetractionBehaviourTest {
    private final PistonStickyRetractionBehaviour behaviour = PistonStickyRetractionBehaviour.getInstance();

    @Test
    public void resolveRetractionPositionsMatchesFacingOffsets() {
        int baseX = 10;
        int baseY = 20;
        int baseZ = 30;
        int facing = 5;
        PistonStickyRetractionBehaviour.RetractionPositions positions =
                behaviour.resolveRetractionPositions(baseX, baseY, baseZ, facing);

        Assert.assertEquals(baseX + PistonBlockTextures.b[facing], positions.getAdjacentX());
        Assert.assertEquals(baseY + PistonBlockTextures.c[facing], positions.getAdjacentY());
        Assert.assertEquals(baseZ + PistonBlockTextures.d[facing], positions.getAdjacentZ());
        Assert.assertEquals(baseX + PistonBlockTextures.b[facing] * 2, positions.getPullX());
        Assert.assertEquals(baseY + PistonBlockTextures.c[facing] * 2, positions.getPullY());
        Assert.assertEquals(baseZ + PistonBlockTextures.d[facing] * 2, positions.getPullZ());
    }

    @Test
    public void shouldPullBlockUsesHandledFlagAndPushabilityPolicy() {
        PistonStickyRetractionBehaviour.PulledBlockState handledState =
                new PistonStickyRetractionBehaviour.PulledBlockState(Block.PISTON.id, 0, true);
        PistonStickyRetractionBehaviour.PulledBlockState pullableState =
                new PistonStickyRetractionBehaviour.PulledBlockState(Block.PISTON.id, 0, false);

        Assert.assertFalse(behaviour.shouldPullBlock(
                handledState,
                null,
                0,
                0,
                0,
                new AllowAllPushabilityQuery()));
        Assert.assertTrue(behaviour.shouldPullBlock(
                pullableState,
                null,
                0,
                0,
                0,
                new AllowAllPushabilityQuery()));
    }

    private static final class AllowAllPushabilityQuery implements PistonStickyRetractionBehaviour.PushabilityQuery {
        public boolean canPush(int blockId, World world, int x, int y, int z, boolean allowDestroy) {
            return true;
        }
    }
}
