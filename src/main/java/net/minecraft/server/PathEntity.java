package net.minecraft.server;

import com.legacyminecraft.poseidon.world.path.PathEntityTraversalBehaviour;

public class PathEntity {
    private static final PathEntityTraversalBehaviour PATH_ENTITY_TRAVERSAL_BEHAVIOUR = PathEntityTraversalBehaviour.getInstance();

    private final PathPoint[] b;
    public final int a;
    private int c;

    public PathEntity(PathPoint[] apathpoint) {
        this.b = apathpoint;
        this.a = apathpoint.length;
    }

    public void a() {
        this.c = PATH_ENTITY_TRAVERSAL_BEHAVIOUR.advance(this.c);
    }

    public boolean b() {
        return PATH_ENTITY_TRAVERSAL_BEHAVIOUR.isFinished(this.c, this.b);
    }

    public PathPoint c() {
        return PATH_ENTITY_TRAVERSAL_BEHAVIOUR.getLastPoint(this.b, this.a);
    }

    public Vec3D a(Entity entity) {
        return PATH_ENTITY_TRAVERSAL_BEHAVIOUR.getCurrentPosition(this.b, this.c, entity);
    }
}
