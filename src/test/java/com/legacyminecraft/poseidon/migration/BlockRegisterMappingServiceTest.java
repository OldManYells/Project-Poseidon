package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.BlockRegisterMappingBehaviour;
import net.minecraft.server.Block;
import org.junit.Assert;
import org.junit.Test;

public class BlockRegisterMappingServiceTest {
    @Test
    public void sanitizeAndLookupInitializationRulesMatchLegacyBehavior() {
        BlockRegisterMappingBehaviour service = BlockRegisterMappingBehaviour.getInstance();
        byte[] lookup = new byte[256];

        service.initializeLookup(lookup, Block.byId);
        Assert.assertEquals(0, lookup[0]);
        Assert.assertEquals(1, lookup[1]);
        Assert.assertEquals(0, lookup[255]);
        Assert.assertEquals(0, service.sanitizeBlockId((byte) 0, Block.byId));
        Assert.assertEquals(1, service.sanitizeBlockId((byte) 1, Block.byId));
    }

    @Test
    public void remapChunkIdRulesMatchLegacyBehavior() {
        BlockRegisterMappingBehaviour service = BlockRegisterMappingBehaviour.getInstance();
        byte[] lookup = new byte[256];
        lookup[1] = 5;
        lookup[2] = 0;
        byte[] chunk = new byte[] {1, 2, 1};

        service.remapChunkIds(chunk, lookup);
        Assert.assertEquals(5, chunk[0]);
        Assert.assertEquals(0, chunk[1]);
        Assert.assertEquals(5, chunk[2]);
    }
}
