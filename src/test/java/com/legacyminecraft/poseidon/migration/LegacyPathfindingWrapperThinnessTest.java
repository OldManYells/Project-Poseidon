package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyPathfindingWrapperThinnessTest {
    private static final Path PATH_PATH = Paths.get("src/main/java/net/minecraft/server/Path.java");
    private static final Path PATH_ENTITY_PATH = Paths.get("src/main/java/net/minecraft/server/PathEntity.java");
    private static final Path PATHFINDER_PATH = Paths.get("src/main/java/net/minecraft/server/Pathfinder.java");

    @Test
    public void pathWrappersDelegateHeapTraversalAndReconstructionToCanonicalBehaviours() throws IOException {
        String path = new String(Files.readAllBytes(PATH_PATH), StandardCharsets.UTF_8);
        String pathEntity = new String(Files.readAllBytes(PATH_ENTITY_PATH), StandardCharsets.UTF_8);
        String pathfinder = new String(Files.readAllBytes(PATHFINDER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(path.contains("PathHeapBehaviour"));
        Assert.assertTrue(path.contains("PATH_HEAP_BEHAVIOUR.insert"));
        Assert.assertTrue(path.contains("PATH_HEAP_BEHAVIOUR.updatePriority"));

        Assert.assertTrue(pathEntity.contains("PathEntityTraversalBehaviour"));
        Assert.assertTrue(pathEntity.contains("PATH_ENTITY_TRAVERSAL_BEHAVIOUR.getCurrentPosition"));

        Assert.assertTrue(pathfinder.contains("PathReconstructionBehaviour"));
        Assert.assertTrue(pathfinder.contains("PATH_RECONSTRUCTION_BEHAVIOUR.build"));
        Assert.assertFalse(pathfinder.contains("for (pathpoint2 = pathpoint1; pathpoint2.h != null;"));
    }
}
