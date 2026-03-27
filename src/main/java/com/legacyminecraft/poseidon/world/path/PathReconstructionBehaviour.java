package com.legacyminecraft.poseidon.world.path;

import com.legacyminecraft.poseidon.entity.PathEntity;
import com.legacyminecraft.poseidon.world.PathPoint;

public final class PathReconstructionBehaviour {
    private static final PathReconstructionBehaviour INSTANCE = new PathReconstructionBehaviour();

    private PathReconstructionBehaviour() {
    }

    public static PathReconstructionBehaviour getInstance() {
        return INSTANCE;
    }

    public PathEntity build(PathPoint start, PathPoint end) {
        int length = 1;
        PathPoint current;

        for (current = end; current.poseidonGetPrevious() != null; current = current.poseidonGetPrevious()) {
            ++length;
        }

        PathPoint[] pathPoints = new PathPoint[length];
        current = end;
        --length;

        for (pathPoints[length] = end; current.poseidonGetPrevious() != null; pathPoints[length] = current) {
            current = current.poseidonGetPrevious();
            --length;
        }

        return new PathEntity(pathPoints);
    }
}
