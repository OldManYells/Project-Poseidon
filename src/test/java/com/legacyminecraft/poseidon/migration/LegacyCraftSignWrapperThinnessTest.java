package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftSignWrapperThinnessTest {
    private static final Path CRAFT_SIGN_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/block/CraftSign.java");
    private static final Path SIGN_LINE_ACCESS_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/SignLineAccessBehaviour.java");

    @Test
    public void craftSignDelegatesLineAccessMutationAndUpdateToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_SIGN_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("TileEntityLookupBehaviour"));
        Assert.assertTrue(text.contains("TileEntityBlockStateUpdateBehaviour"));
        Assert.assertTrue(text.contains("SignLineAccessBehaviour"));
        Assert.assertTrue(text.contains("resolveSign(block, getX(), getY(), getZ())"));
        Assert.assertTrue(text.contains("getLines(sign)"));
        Assert.assertTrue(text.contains("getLine(sign, index)"));
        Assert.assertTrue(text.contains("setLine(sign, index, line)"));
        Assert.assertTrue(text.contains("finalizeUpdate(super.update(force), sign)"));
        Assert.assertFalse(text.contains("return sign.lines;"));
        Assert.assertFalse(text.contains("return sign.lines[index];"));
        Assert.assertFalse(text.contains("sign.lines[index] = line;"));
        Assert.assertFalse(text.contains("sign.update();"));
    }

    @Test
    public void signLineAccessBehaviourOwnsDirectSignLineStateAccessAndMutation() throws IOException {
        String text = new String(Files.readAllBytes(SIGN_LINE_ACCESS_BEHAVIOUR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("class SignLineAccessBehaviour"));
        Assert.assertTrue(text.contains("getLines(TileEntitySign sign)"));
        Assert.assertTrue(text.contains("getLine(TileEntitySign sign, int index)"));
        Assert.assertTrue(text.contains("setLine(TileEntitySign sign, int index, String line)"));
        Assert.assertTrue(text.contains("return sign.lines;"));
        Assert.assertTrue(text.contains("return sign.lines[index];"));
        Assert.assertTrue(text.contains("sign.lines[index] = line;"));
    }
}
