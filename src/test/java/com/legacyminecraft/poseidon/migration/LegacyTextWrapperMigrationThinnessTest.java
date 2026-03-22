package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyTextWrapperMigrationThinnessTest {
    private static final Path TEXT_WRAPPER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/TextWrapper.java");
    private static final Path OUTBOUND_PACKET_DISPATCH_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/network/OutboundPacketDispatchSystem.java");

    @Test
    public void textWrappingIsCanonicalAndCraftBukkitWrapperIsThin() throws IOException {
        String textWrapperText = read(TEXT_WRAPPER_PATH);
        String outboundPacketDispatchText = read(OUTBOUND_PACKET_DISPATCH_PATH);

        Assert.assertTrue(textWrapperText.contains("ChatTextWrapBehaviour"));
        Assert.assertTrue(textWrapperText.contains("CHAT_TEXT_WRAP_BEHAVIOUR"));
        Assert.assertTrue(textWrapperText.contains("return CHAT_TEXT_WRAP_BEHAVIOUR.wrapText(text);"));
        Assert.assertTrue(textWrapperText.contains("return CHAT_TEXT_WRAP_BEHAVIOUR.widthInPixels(text);"));
        Assert.assertFalse(textWrapperText.contains("characterWidths"));
        Assert.assertFalse(textWrapperText.contains("for (int i = 0; i < text.length(); i++)"));

        Assert.assertTrue(outboundPacketDispatchText.contains("ChatTextWrapBehaviour"));
        Assert.assertTrue(outboundPacketDispatchText.contains("chatTextWrapBehaviour.wrapText(message)"));
        Assert.assertFalse(outboundPacketDispatchText.contains("org.bukkit.craftbukkit.TextWrapper"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}

