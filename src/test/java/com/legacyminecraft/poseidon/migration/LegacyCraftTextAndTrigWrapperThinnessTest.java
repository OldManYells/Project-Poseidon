package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftTextAndTrigWrapperThinnessTest {
    private static final Path TEXT_WRAPPER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/TextWrapper.java");
    private static final Path TRIG_MATH_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/TrigMath.java");

    @Test
    public void textWrapperDelegatesChatWrappingAndPixelWidthLogic() throws IOException {
        String text = new String(Files.readAllBytes(TEXT_WRAPPER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ChatTextWrapBehaviour"));
        Assert.assertTrue(text.contains("CHAT_TEXT_WRAP_BEHAVIOUR.wrapText(text)"));
        Assert.assertTrue(text.contains("CHAT_TEXT_WRAP_BEHAVIOUR.widthInPixels(text)"));
        Assert.assertFalse(text.contains("for (int"));
    }

    @Test
    public void trigMathDelegatesAtanAndAtan2Logic() throws IOException {
        String text = new String(Files.readAllBytes(TRIG_MATH_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("TrigAtanBehaviour"));
        Assert.assertTrue(text.contains("TRIG_ATAN_BEHAVIOUR.atan(arg)"));
        Assert.assertTrue(text.contains("TRIG_ATAN_BEHAVIOUR.atan2(arg1, arg2)"));
        Assert.assertFalse(text.contains("Math.atan("));
        Assert.assertFalse(text.contains("Math.atan2("));
    }
}

