package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyWorldCoordinateWrapperThinnessTest {
    private static final Path CHUNK_COORDINATES_PATH = Paths.get("src/main/java/net/minecraft/server/ChunkCoordinates.java");
    private static final Path PATH_POINT_PATH = Paths.get("src/main/java/net/minecraft/server/PathPoint.java");
    private static final Path CHUNK_COORD_INT_PAIR_PATH = Paths.get("src/main/java/net/minecraft/server/ChunkCoordIntPair.java");
    private static final Path VEC3D_PATH = Paths.get("src/main/java/net/minecraft/server/Vec3D.java");
    private static final Path CHUNK_POSITION_PATH = Paths.get("src/main/java/net/minecraft/server/ChunkPosition.java");
    private static final Path MOVING_OBJECT_POSITION_PATH = Paths.get("src/main/java/net/minecraft/server/MovingObjectPosition.java");
    private static final Path AXIS_ALIGNED_BB_PATH = Paths.get("src/main/java/net/minecraft/server/AxisAlignedBB.java");
    private static final Path PATH_ENTITY_PATH = Paths.get("src/main/java/net/minecraft/server/PathEntity.java");
    private static final Path PATH_PATH = Paths.get("src/main/java/net/minecraft/server/Path.java");

    @Test
    public void coordinateWrappersDelegateMathAndOrderingToCanonicalBehaviour() throws IOException {
        String chunkCoordinates = new String(Files.readAllBytes(CHUNK_COORDINATES_PATH), StandardCharsets.UTF_8);
        String pathPoint = new String(Files.readAllBytes(PATH_POINT_PATH), StandardCharsets.UTF_8);
        String chunkPair = new String(Files.readAllBytes(CHUNK_COORD_INT_PAIR_PATH), StandardCharsets.UTF_8);
        String vec3d = new String(Files.readAllBytes(VEC3D_PATH), StandardCharsets.UTF_8);
        String chunkPosition = new String(Files.readAllBytes(CHUNK_POSITION_PATH), StandardCharsets.UTF_8);
        String movingObjectPosition = new String(Files.readAllBytes(MOVING_OBJECT_POSITION_PATH), StandardCharsets.UTF_8);
        String axisAlignedBb = new String(Files.readAllBytes(AXIS_ALIGNED_BB_PATH), StandardCharsets.UTF_8);
        String pathEntity = new String(Files.readAllBytes(PATH_ENTITY_PATH), StandardCharsets.UTF_8);
        String path = new String(Files.readAllBytes(PATH_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(chunkCoordinates.contains("CoordinateMathBehaviour"));
        Assert.assertTrue(chunkCoordinates.contains("COORDINATE_MATH_BEHAVIOUR.compare"));
        Assert.assertFalse(chunkCoordinates.contains("this.y == chunkcoordinates.y ?"));

        Assert.assertTrue(pathPoint.contains("CoordinateMathBehaviour"));
        Assert.assertTrue(pathPoint.contains("COORDINATE_MATH_BEHAVIOUR.pathPointKey"));
        Assert.assertTrue(pathPoint.contains("COORDINATE_MATH_BEHAVIOUR.distance"));
        Assert.assertTrue(pathPoint.contains("poseidonGetHeapIndex"));
        Assert.assertTrue(pathPoint.contains("poseidonSetPriority"));
        Assert.assertFalse(pathPoint.contains("MathHelper.c(f * f + f1 * f1 + f2 * f2)"));

        Assert.assertTrue(chunkPair.contains("CoordinateMathBehaviour"));
        Assert.assertTrue(chunkPair.contains("COORDINATE_MATH_BEHAVIOUR.chunkPairKey"));
        Assert.assertFalse(chunkPair.contains("(i < 0 ? Integer.MIN_VALUE : 0) | (i & 32767) << 16"));

        Assert.assertTrue(vec3d.contains("Vec3DBehaviour"));
        Assert.assertTrue(vec3d.contains("VEC3D_BEHAVIOUR.createPooled"));
        Assert.assertTrue(vec3d.contains("VEC3D_BEHAVIOUR.normalize"));
        Assert.assertTrue(vec3d.contains("VEC3D_BEHAVIOUR.interpolateX"));
        Assert.assertFalse(vec3d.contains("if (e >= d.size())"));
        Assert.assertFalse(vec3d.contains("MathHelper.a(this.a * this.a + this.b * this.b + this.c * this.c)"));

        Assert.assertTrue(chunkPosition.contains("ChunkPositionBehaviour"));
        Assert.assertTrue(chunkPosition.contains("CHUNK_POSITION_BEHAVIOUR.equals"));
        Assert.assertTrue(chunkPosition.contains("CHUNK_POSITION_BEHAVIOUR.hashCode"));
        Assert.assertFalse(chunkPosition.contains("chunkposition.x == this.x"));

        Assert.assertTrue(movingObjectPosition.contains("MovingObjectPositionBehaviour"));
        Assert.assertTrue(movingObjectPosition.contains("MOVING_OBJECT_POSITION_BEHAVIOUR.initializeTileHit"));
        Assert.assertTrue(movingObjectPosition.contains("MOVING_OBJECT_POSITION_BEHAVIOUR.initializeEntityHit"));
        Assert.assertFalse(movingObjectPosition.contains("this.type = EnumMovingObjectType.TILE"));
        Assert.assertFalse(movingObjectPosition.contains("this.type = EnumMovingObjectType.ENTITY"));

        Assert.assertTrue(axisAlignedBb.contains("AxisAlignedBoundingBoxBehaviour"));
        Assert.assertTrue(axisAlignedBb.contains("AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.createPooled"));
        Assert.assertTrue(axisAlignedBb.contains("AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.directionalExpand"));
        Assert.assertTrue(axisAlignedBb.contains("AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.calculateIntercept"));
        Assert.assertTrue(axisAlignedBb.contains("AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.stringify"));
        Assert.assertFalse(axisAlignedBb.contains("vec3d2 = vec3d.a(vec3d1, this.a)"));
        Assert.assertFalse(axisAlignedBb.contains("return \"box[\" + this.a + \", \" + this.b"));

        Assert.assertTrue(pathEntity.contains("PathEntityTraversalBehaviour"));
        Assert.assertTrue(pathEntity.contains("PATH_ENTITY_TRAVERSAL_BEHAVIOUR.advance"));
        Assert.assertTrue(pathEntity.contains("PATH_ENTITY_TRAVERSAL_BEHAVIOUR.getCurrentPosition"));
        Assert.assertFalse(pathEntity.contains("++this.c"));
        Assert.assertFalse(pathEntity.contains("double d0 = (double) this.b[this.c].a"));

        Assert.assertTrue(path.contains("PathHeapBehaviour"));
        Assert.assertTrue(path.contains("PATH_HEAP_BEHAVIOUR.insert"));
        Assert.assertTrue(path.contains("PATH_HEAP_BEHAVIOUR.pop"));
        Assert.assertTrue(path.contains("PATH_HEAP_BEHAVIOUR.updatePriority"));
        Assert.assertFalse(path.contains("if (pathpoint.d >= 0)"));
        Assert.assertFalse(path.contains("PathPoint[] apathpoint = new PathPoint[this.b << 1]"));
    }
}
