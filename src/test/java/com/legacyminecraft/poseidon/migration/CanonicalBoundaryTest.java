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

public class CanonicalBoundaryTest {
    private static final String SRC_ROOT = "src/main/java/com/legacyminecraft/poseidon";

    @Test
    public void canonicalApiAuthAndKernelDoNotDependOnLegacyPackages() throws IOException {
        Path root = Paths.get(SRC_ROOT);
        List<String> violations = new ArrayList<String>();

        try (Stream<Path> stream = Files.walk(root)) {
            stream.filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> checkFile(path, violations));
        }

        if (!violations.isEmpty()) {
            Assert.fail("Legacy import boundary violations:\n" + String.join("\n", violations));
        }
    }

    private void checkFile(Path path, List<String> violations) {
        String normalized = path.toString().replace('\\', '/');
        if (!isRestrictedCanonicalArea(normalized)) {
            return;
        }

        try {
            String text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            if (text.contains("import com.projectposeidon.")) {
                violations.add(normalized + " imports com.projectposeidon.*");
            }
            if (text.contains("import net.minecraft.server.")) {
                violations.add(normalized + " imports net.minecraft.server.*");
            }
            if (text.contains("import org.bukkit.craftbukkit.")) {
                violations.add(normalized + " imports org.bukkit.craftbukkit.*");
            }
        } catch (IOException e) {
            violations.add(normalized + " could not be read: " + e.getMessage());
        }
    }

    private boolean isRestrictedCanonicalArea(String normalizedPath) {
        return normalizedPath.contains("/com/legacyminecraft/poseidon/api/")
                || normalizedPath.contains("/com/legacyminecraft/poseidon/auth/uuid/")
                || normalizedPath.contains("/com/legacyminecraft/poseidon/kernel/");
    }
}
