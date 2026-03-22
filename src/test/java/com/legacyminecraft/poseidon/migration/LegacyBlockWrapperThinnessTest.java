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

public class LegacyBlockWrapperThinnessTest {
    private static final Path BLOCK_ROOT = Paths.get("src/main/java/net/minecraft/server");

    @Test
    public void blockWrappersDelegateToCanonicalPoseidonBehaviours() throws IOException {
        List<String> missingCanonicalDelegation = new ArrayList<String>();
        List<String> unexpectedProjectPoseidonDependency = new ArrayList<String>();

        try (Stream<Path> stream = Files.walk(BLOCK_ROOT)) {
            stream.filter(path -> path.getFileName().toString().startsWith("Block"))
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> inspectBlockWrapper(path, missingCanonicalDelegation,
                            unexpectedProjectPoseidonDependency));
        }

        if (!missingCanonicalDelegation.isEmpty()) {
            Assert.fail("Block wrappers without canonical poseidon delegation:\n" + String.join("\n", missingCanonicalDelegation));
        }
        if (!unexpectedProjectPoseidonDependency.isEmpty()) {
            Assert.fail("Block wrappers still depending on com.projectposeidon:\n" + String.join("\n", unexpectedProjectPoseidonDependency));
        }
    }

    private void inspectBlockWrapper(Path path,
                                     List<String> missingCanonicalDelegation,
                                     List<String> unexpectedProjectPoseidonDependency) {
        String normalized = path.toString().replace('\\', '/');
        try {
            String text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            if (!text.contains("com.legacyminecraft.poseidon")) {
                missingCanonicalDelegation.add(normalized);
            }
            if (text.contains("com.projectposeidon")) {
                unexpectedProjectPoseidonDependency.add(normalized);
            }
        } catch (IOException e) {
            missingCanonicalDelegation.add(normalized + " (unreadable: " + e.getMessage() + ")");
        }
    }
}
