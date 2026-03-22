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

public class ProjectPoseidonUsageBoundaryTest {
    @Test
    public void projectPoseidonReferencesStayInCompatibilityZones() throws IOException {
        Path root = Paths.get("src/main/java");
        List<String> violations = new ArrayList<String>();

        try (Stream<Path> stream = Files.walk(root)) {
            stream.filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> inspect(path, violations));
        }

        if (!violations.isEmpty()) {
            Assert.fail("Invalid com.projectposeidon references:\n" + String.join("\n", violations));
        }
    }

    private void inspect(Path file, List<String> violations) {
        String normalized = file.toString().replace('\\', '/');
        if (isAllowed(normalized)) {
            return;
        }

        try {
            String text = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
            if (text.contains("com.projectposeidon")) {
                violations.add(normalized);
            }
        } catch (IOException e) {
            violations.add(normalized + " (read error: " + e.getMessage() + ")");
        }
    }

    private boolean isAllowed(String normalizedPath) {
        return normalizedPath.startsWith("src/main/java/com/projectposeidon/")
                || normalizedPath.startsWith("src/main/java/com/legacyminecraft/poseidon/compat/projectposeidon/")
                || normalizedPath.startsWith("src/main/java/org/bukkit/")
                || normalizedPath.equals("src/main/java/net/minecraft/server/NetServerHandler.java");
    }
}
