package net.minecraft.server;

import com.legacyminecraft.poseidon.world.Vec3DBehaviour;

import java.util.ArrayList;
import java.util.List;

public class Vec3D {
    private static final Vec3DBehaviour VEC3D_BEHAVIOUR = Vec3DBehaviour.getInstance();

    private static List d = new ArrayList();
    private static int e = 0;
    public double a;
    public double b;
    public double c;

    public static Vec3D a(double d0, double d1, double d2) {
        return new Vec3D(d0, d1, d2);
    }

    public static void a() {
        e = VEC3D_BEHAVIOUR.resetPoolIndex();
    }

    public static Vec3D create(double d0, double d1, double d2) {
        Vec3DBehaviour.PoolState state = VEC3D_BEHAVIOUR.createPooled(d, e, d0, d1, d2);
        e = state.nextIndex;
        return state.value;
    }

    private Vec3D(double d0, double d1, double d2) {
        d0 = VEC3D_BEHAVIOUR.sanitizeNegativeZero(d0);
        d1 = VEC3D_BEHAVIOUR.sanitizeNegativeZero(d1);
        d2 = VEC3D_BEHAVIOUR.sanitizeNegativeZero(d2);

        this.a = d0;
        this.b = d1;
        this.c = d2;
    }

    private Vec3D e(double d0, double d1, double d2) {
        this.a = d0;
        this.b = d1;
        this.c = d2;
        return this;
    }

    public final Vec3D poseidonSet(double d0, double d1, double d2) {
        return this.e(d0, d1, d2);
    }

    public Vec3D b() {
        return VEC3D_BEHAVIOUR.normalize(this);
    }

    public Vec3D add(double d0, double d1, double d2) {
        return VEC3D_BEHAVIOUR.add(this, d0, d1, d2);
    }

    public double a(Vec3D vec3d) {
        return VEC3D_BEHAVIOUR.distance(this, vec3d);
    }

    public double b(Vec3D vec3d) {
        return VEC3D_BEHAVIOUR.distanceSquared(this, vec3d);
    }

    public double d(double d0, double d1, double d2) {
        return VEC3D_BEHAVIOUR.distanceSquared(this, d0, d1, d2);
    }

    public double c() {
        return VEC3D_BEHAVIOUR.length(this);
    }

    public Vec3D a(Vec3D vec3d, double d0) {
        return VEC3D_BEHAVIOUR.interpolateX(this, vec3d, d0);
    }

    public Vec3D b(Vec3D vec3d, double d0) {
        return VEC3D_BEHAVIOUR.interpolateY(this, vec3d, d0);
    }

    public Vec3D c(Vec3D vec3d, double d0) {
        return VEC3D_BEHAVIOUR.interpolateZ(this, vec3d, d0);
    }

    public String toString() {
        return VEC3D_BEHAVIOUR.stringify(this);
    }
}
