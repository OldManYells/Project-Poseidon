package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftBlockStateWrapperThinnessTest {
    private static final Path CRAFT_BLOCK_STATE_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/block/CraftBlockState.java");

    @Test
    public void craftBlockStateDelegatesDataValidationCreationAndUpdatePolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_BLOCK_STATE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BlockStateDataBehaviour"));
        Assert.assertTrue(text.contains("BlockStateUpdateBehaviour"));
        Assert.assertTrue(text.contains("BLOCK_STATE_DATA_BEHAVIOUR.validateData(getType(), data)"));
        Assert.assertTrue(text.contains("BLOCK_STATE_UPDATE_BEHAVIOUR.applyUpdate(getBlock(), this.getType(), this.getTypeId(), getRawData(), force)"));
        Assert.assertTrue(text.contains("BLOCK_STATE_DATA_BEHAVIOUR.createData(type, data)"));
        Assert.assertFalse(text.contains("if ((mat == null) || (mat.getData() == null))"));
        Assert.assertFalse(text.contains("synchronized (block)"));
        Assert.assertFalse(text.contains("this.data = mat.getNewData(data);"));
    }
}
