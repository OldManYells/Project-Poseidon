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

public class CanonicalCraftServerImportBoundaryTest {
    private static final Path CANONICAL_ROOT =
            Paths.get("src/main/java/com/legacyminecraft/poseidon");
    private static final String CRAFTBUKKIT_IMPORT_PREFIX = "import org.bukkit.craftbukkit.";

    @Test
    public void canonicalPackagesOutsideCompatDoNotImportCraftBukkit() throws IOException {
        List<String> violations = new ArrayList<String>();

        try (Stream<Path> stream = Files.walk(CANONICAL_ROOT)) {
            stream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .filter(path -> !path.toString().contains("/compat/"))
                    .forEach(path -> {
                        try {
                            String text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                            if (text.contains(CRAFTBUKKIT_IMPORT_PREFIX)) {
                                violations.add(path.toString());
                            }
                        } catch (IOException exception) {
                            violations.add(path.toString() + " (read-failed)");
                        }
                    });
        }

        Assert.assertTrue("Found forbidden CraftBukkit imports in canonical code: " + violations, violations.isEmpty());
    }
}
