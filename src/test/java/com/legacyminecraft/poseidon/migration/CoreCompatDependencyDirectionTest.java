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

public class CoreCompatDependencyDirectionTest {
    private static final Path CORE_ROOT = Paths.get("src/main/java/com/legacyminecraft/poseidon/core");
    private static final Path POSEIDON_ROOT = Paths.get("src/main/java/com/legacyminecraft/poseidon");

    @Test
    public void corePackageDoesNotImportLegacyCompatLayers() throws IOException {
        if (!Files.exists(CORE_ROOT)) {
            return;
        }

        List<Path> offenders = new ArrayList<Path>();
        try (Stream<Path> pathStream = Files.walk(CORE_ROOT)) {
            pathStream
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        try {
                            String text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                            if (text.contains("import net.minecraft.server.")
                                    || text.contains("import org.bukkit.craftbukkit.")
                                    || text.contains("import com.legacyminecraft.poseidon.compat.")) {
                                offenders.add(path);
                            }
                        } catch (IOException exception) {
                            throw new RuntimeException(exception);
                        }
                    });
        }

        Assert.assertTrue("Core package imports compat/legacy layers: " + offenders, offenders.isEmpty());
    }

    @Test
    public void nonCompatPoseidonPackagesDoNotImportCraftBukkit() throws IOException {
        if (!Files.exists(POSEIDON_ROOT)) {
            return;
        }

        List<Path> offenders = new ArrayList<Path>();
        try (Stream<Path> pathStream = Files.walk(POSEIDON_ROOT)) {
            pathStream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .filter(path -> !normalizeSeparators(path).contains("/compat/"))
                    .forEach(path -> {
                        try {
                            String text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                            if (text.contains("import org.bukkit.craftbukkit.")) {
                                offenders.add(path);
                            }
                        } catch (IOException exception) {
                            throw new RuntimeException(exception);
                        }
                    });
        }

        Assert.assertTrue("Non-compat Poseidon packages import CraftBukkit directly: " + offenders, offenders.isEmpty());
    }

    private static String normalizeSeparators(Path path) {
        return path.toString().replace('\\', '/');
    }
}
