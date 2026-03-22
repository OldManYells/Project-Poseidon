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
import java.util.stream.Stream;

public class LegacyNbtWrapperThinnessTest {
    private static final Path NBT_ROOT = Paths.get("src/main/java/net/minecraft/server");

    @Test
    public void nbtTagWrappersDelegateCodecLogicToCanonicalServices() throws IOException {
        List<String> violations = new ArrayList<String>();

        try (Stream<Path> stream = Files.walk(NBT_ROOT)) {
            stream.filter(path -> path.getFileName().toString().startsWith("NBTTag"))
                    .filter(path -> path.getFileName().toString().endsWith(".java"))
                    .forEach(path -> checkTagWrapper(path, violations));
        }

        if (!violations.isEmpty()) {
            Assert.fail("NBT wrapper thinness violations:\n" + String.join("\n", violations));
        }
    }

    private void checkTagWrapper(Path path, List<String> violations) {
        try {
            String text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            if (!text.contains("com.legacyminecraft.poseidon.nbt")) {
                violations.add(path + " does not import canonical NBT service");
            }
            if (text.contains("datainput.read")) {
                violations.add(path + " reads payload directly in legacy wrapper");
            }
            if (text.contains("dataoutput.write")) {
                violations.add(path + " writes payload directly in legacy wrapper");
            }
        } catch (IOException e) {
            violations.add(path + " could not be inspected: " + e.getMessage());
        }
    }
}
