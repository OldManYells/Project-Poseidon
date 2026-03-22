package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyMathWrapperThinnessTest {
    private static final Path MATH_HELPER_PATH = Paths.get("src/main/java/net/minecraft/server/MathHelper.java");

    @Test
    public void mathHelperDelegatesMathLogicToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(MATH_HELPER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("MathLookupBehaviour"));
        Assert.assertTrue(text.contains("MATH_LOOKUP_BEHAVIOUR.createSineLookupTable"));
        Assert.assertTrue(text.contains("MATH_LOOKUP_BEHAVIOUR.sin"));
        Assert.assertTrue(text.contains("MATH_LOOKUP_BEHAVIOUR.cos"));
        Assert.assertTrue(text.contains("MATH_LOOKUP_BEHAVIOUR.squareRoot"));
        Assert.assertTrue(text.contains("MATH_LOOKUP_BEHAVIOUR.floorFloat"));
        Assert.assertTrue(text.contains("MATH_LOOKUP_BEHAVIOUR.floorDouble"));
        Assert.assertTrue(text.contains("MATH_LOOKUP_BEHAVIOUR.absolute"));
        Assert.assertTrue(text.contains("MATH_LOOKUP_BEHAVIOUR.maxAbsolute"));
        Assert.assertFalse(text.contains("Math.sin((double) i"));
        Assert.assertFalse(text.contains("return f >= 0.0F ? f : -f;"));
    }
}
