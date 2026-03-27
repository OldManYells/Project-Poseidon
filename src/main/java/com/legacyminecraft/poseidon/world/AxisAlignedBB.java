package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.poseidon.world.AxisAlignedBoundingBoxBehaviour;

import java.util.ArrayList;
import java.util.List;

public class AxisAlignedBB {
    private static final AxisAlignedBoundingBoxBehaviour AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR = AxisAlignedBoundingBoxBehaviour.getInstance();

    private static List g = new ArrayList();
    private static int h = 0;
    public double a;
    public double b;
    public double c;
    public double d;
    public double e;
    public double f;

    public static AxisAlignedBB a(double d0, double d1, double d2, double d3, double d4, double d5) {
        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    public static void a() {
        h = AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.resetPoolIndex();
    }

    public static AxisAlignedBB b(double d0, double d1, double d2, double d3, double d4, double d5) {
        AxisAlignedBoundingBoxBehaviour.PoolState state = AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.createPooled(g, h, d0, d1, d2, d3, d4, d5);
        h = state.nextIndex;
        return state.value;
    }

    private AxisAlignedBB(double d0, double d1, double d2, double d3, double d4, double d5) {
        this.a = d0;
        this.b = d1;
        this.c = d2;
        this.d = d3;
        this.e = d4;
        this.f = d5;
    }

    public AxisAlignedBB c(double d0, double d1, double d2, double d3, double d4, double d5) {
        this.a = d0;
        this.b = d1;
        this.c = d2;
        this.d = d3;
        this.e = d4;
        this.f = d5;
        return this;
    }

    public AxisAlignedBB a(double d0, double d1, double d2) {
        return AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.directionalExpand(this, d0, d1, d2);
    }

    public AxisAlignedBB b(double d0, double d1, double d2) {
        return AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.expand(this, d0, d1, d2);
    }

    public AxisAlignedBB c(double d0, double d1, double d2) {
        return AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.offsetCopy(this, d0, d1, d2);
    }

    public double a(AxisAlignedBB axisalignedbb, double d0) {
        return AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.clipXCollide(this, axisalignedbb, d0);
    }

    public double b(AxisAlignedBB axisalignedbb, double d0) {
        return AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.clipYCollide(this, axisalignedbb, d0);
    }

    public double c(AxisAlignedBB axisalignedbb, double d0) {
        return AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.clipZCollide(this, axisalignedbb, d0);
    }

    public boolean a(AxisAlignedBB axisalignedbb) {
        return AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.intersects(this, axisalignedbb);
    }

    public AxisAlignedBB d(double d0, double d1, double d2) {
        return AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.move(this, d0, d1, d2);
    }

    public boolean a(Vec3D vec3d) {
        return AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.contains(this, vec3d);
    }

    public AxisAlignedBB shrink(double d0, double d1, double d2) {
        return AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.shrink(this, d0, d1, d2);
    }

    public AxisAlignedBB clone() {
        return AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.clone(this);
    }

    public MovingObjectPosition a(Vec3D vec3d, Vec3D vec3d1) {
        return AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.calculateIntercept(this, vec3d, vec3d1);
    }

    public void b(AxisAlignedBB axisalignedbb) {
        AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.copyBounds(this, axisalignedbb);
    }

    public String toString() {
        return AXIS_ALIGNED_BOUNDING_BOX_BEHAVIOUR.stringify(this);
    }
}
