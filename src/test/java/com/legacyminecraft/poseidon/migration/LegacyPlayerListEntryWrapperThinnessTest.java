package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyPlayerListEntryWrapperThinnessTest {
    private static final Path PLAYER_LIST_ENTRY_PATH = Paths.get("src/main/java/net/minecraft/server/PlayerListEntry.java");

    @Test
    public void playerListEntryDelegatesEqualityAndStringFormatting() throws IOException {
        String text = new String(Files.readAllBytes(PLAYER_LIST_ENTRY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.entity.PlayerListEntryStateBehaviour;"));
        Assert.assertTrue(text.contains("PLAYER_LIST_ENTRY_STATE_BEHAVIOUR.equalsEntry(this, object)"));
        Assert.assertTrue(text.contains("PLAYER_LIST_ENTRY_STATE_BEHAVIOUR.toEntryString(this)"));
        Assert.assertFalse(text.contains("Long olong = Long.valueOf(this.a());"));
        Assert.assertFalse(text.contains("return this.a() + \"=\" + this.b();"));
    }
}
