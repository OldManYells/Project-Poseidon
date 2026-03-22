package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.SignUpdateProcessor;
import org.junit.Assert;
import org.junit.Test;

public class SignUpdateProcessorTest {
    @Test
    public void sanitizeLinesReplacesInvalidEntries() {
        String[] lines = new String[]{"ab", "abcd", "a$", "c"};

        SignUpdateProcessor.getInstance().sanitizeLines(lines, 3, "abc", "!?");

        Assert.assertEquals("ab", lines[0]);
        Assert.assertEquals("!?", lines[1]);
        Assert.assertEquals("!?", lines[2]);
        Assert.assertEquals("c", lines[3]);
    }
}
