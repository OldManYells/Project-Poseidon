package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LegacyPacketWrapperThinnessTest {
    private static final Path PACKET_ROOT = Paths.get("src/main/java/net/minecraft/server");
    private static final Path PACKET_COUNTER_PATH = Paths.get("src/main/java/net/minecraft/server/PacketCounter.java");
    private static final Pattern LEGACY_CONSTANT_LENGTH_RETURN =
            Pattern.compile("public int a\\(\\) \\{\\s*return [0-9]+;", Pattern.MULTILINE);

    @Test
    public void packetWrappersDelegateCodecLogicToCanonicalService() throws IOException {
        List<String> violations = new ArrayList<String>();

        try (Stream<Path> stream = Files.walk(PACKET_ROOT)) {
            stream.filter(path -> path.getFileName().toString().startsWith("Packet"))
                    .filter(path -> path.getFileName().toString().endsWith(".java"))
                    .forEach(path -> checkPacketWrapper(path, violations));
        }

        if (!violations.isEmpty()) {
            Assert.fail("Packet wrapper thinness violations:\n" + String.join("\n", violations));
        }
    }

    @Test
    public void packetCounterDelegatesAccumulationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(PACKET_COUNTER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PacketCounterBehaviour"));
        Assert.assertTrue(text.contains("PACKET_COUNTER_BEHAVIOUR.increment"));
        Assert.assertFalse(text.contains("++this.a;"));
        Assert.assertFalse(text.contains("this.b += (long) i;"));
    }

    private void checkPacketWrapper(Path path, List<String> violations) {
        String fileName = path.getFileName().toString();
        if ("Packet.java".equals(fileName) || "PacketCounter.java".equals(fileName)) {
            return;
        }

        try {
            String text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            if (!text.contains("PacketDataCodec")) {
                violations.add(path + " does not delegate through PacketDataCodec");
            }
            if (text.contains("datainputstream.read")) {
                violations.add(path + " reads packet payload directly in legacy wrapper");
            }
            if (text.contains("dataoutputstream.write")) {
                violations.add(path + " writes packet payload directly in legacy wrapper");
            }
            if (text.contains("MathHelper.floor(")) {
                violations.add(path + " performs coordinate conversion logic in legacy wrapper");
            }
            if (text.contains("256.0F / 360.0F")) {
                violations.add(path + " performs angle packing logic in legacy wrapper");
            }
            if (text.contains("8000.0D")) {
                violations.add(path + " performs velocity scaling logic in legacy wrapper");
            }
            if (text.contains("128.0D")) {
                violations.add(path + " performs motion-byte scaling logic in legacy wrapper");
            }
            if (LEGACY_CONSTANT_LENGTH_RETURN.matcher(text).find()) {
                violations.add(path + " defines constant packet length in legacy wrapper");
            }
        } catch (IOException e) {
            violations.add(path + " could not be inspected: " + e.getMessage());
        }
    }
}
