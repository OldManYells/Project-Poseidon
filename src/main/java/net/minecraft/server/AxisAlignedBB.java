package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;

public class AxisAlignedBB {
    private static final List g = new ArrayList();
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
        h = 0;
    }

    public static AxisAlignedBB b(double d0, double d1, double d2, double d3, double d4, double d5) {
        if (h >= g.size()) {
            g.add(new AxisAlignedBB(d0, d1, d2, d3, d4, d5));
        }

        AxisAlignedBB axisalignedbb = (AxisAlignedBB) g.get(h++);
        axisalignedbb.c(d0, d1, d2, d3, d4, d5);
        return axisalignedbb;
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
        double minX = this.a;
        double minY = this.b;
        double minZ = this.c;
        double maxX = this.d;
        double maxY = this.e;
        double maxZ = this.f;

        if (d0 < 0.0D) {
            minX += d0;
        } else if (d0 > 0.0D) {
            maxX += d0;
        }

        if (d1 < 0.0D) {
            minY += d1;
        } else if (d1 > 0.0D) {
            maxY += d1;
        }

        if (d2 < 0.0D) {
            minZ += d2;
        } else if (d2 > 0.0D) {
            maxZ += d2;
        }

        return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public AxisAlignedBB b(double d0, double d1, double d2) {
        return new AxisAlignedBB(this.a - d0, this.b - d1, this.c - d2, this.d + d0, this.e + d1, this.f + d2);
    }

    public AxisAlignedBB c(double d0, double d1, double d2) {
        return new AxisAlignedBB(this.a + d0, this.b + d1, this.c + d2, this.d + d0, this.e + d1, this.f + d2);
    }

    public double a(AxisAlignedBB axisalignedbb, double d0) {
        if (axisalignedbb.e > this.b && axisalignedbb.b < this.e && axisalignedbb.f > this.c && axisalignedbb.c < this.f) {
            if (d0 > 0.0D && axisalignedbb.d <= this.a) {
                double d1 = this.a - axisalignedbb.d;
                if (d1 < d0) {
                    d0 = d1;
                }
            } else if (d0 < 0.0D && axisalignedbb.a >= this.d) {
                double d2 = this.d - axisalignedbb.a;
                if (d2 > d0) {
                    d0 = d2;
                }
            }
        }
        return d0;
    }

    public double b(AxisAlignedBB axisalignedbb, double d0) {
        if (axisalignedbb.d > this.a && axisalignedbb.a < this.d && axisalignedbb.f > this.c && axisalignedbb.c < this.f) {
            if (d0 > 0.0D && axisalignedbb.e <= this.b) {
                double d1 = this.b - axisalignedbb.e;
                if (d1 < d0) {
                    d0 = d1;
                }
            } else if (d0 < 0.0D && axisalignedbb.b >= this.e) {
                double d2 = this.e - axisalignedbb.b;
                if (d2 > d0) {
                    d0 = d2;
                }
            }
        }
        return d0;
    }

    public double c(AxisAlignedBB axisalignedbb, double d0) {
        if (axisalignedbb.d > this.a && axisalignedbb.a < this.d && axisalignedbb.e > this.b && axisalignedbb.b < this.e) {
            if (d0 > 0.0D && axisalignedbb.f <= this.c) {
                double d1 = this.c - axisalignedbb.f;
                if (d1 < d0) {
                    d0 = d1;
                }
            } else if (d0 < 0.0D && axisalignedbb.c >= this.f) {
                double d2 = this.f - axisalignedbb.c;
                if (d2 > d0) {
                    d0 = d2;
                }
            }
        }
        return d0;
    }

    public boolean a(AxisAlignedBB axisalignedbb) {
        return axisalignedbb.d > this.a && axisalignedbb.a < this.d && axisalignedbb.e > this.b && axisalignedbb.b < this.e && axisalignedbb.f > this.c && axisalignedbb.c < this.f;
    }

    public AxisAlignedBB d(double d0, double d1, double d2) {
        this.a += d0;
        this.b += d1;
        this.c += d2;
        this.d += d0;
        this.e += d1;
        this.f += d2;
        return this;
    }

    public boolean a(Vec3D vec3d) {
        return vec3d.a > this.a && vec3d.a < this.d && vec3d.b > this.b && vec3d.b < this.e && vec3d.c > this.c && vec3d.c < this.f;
    }

    public AxisAlignedBB shrink(double d0, double d1, double d2) {
        return new AxisAlignedBB(this.a + d0, this.b + d1, this.c + d2, this.d - d0, this.e - d1, this.f - d2);
    }

    public AxisAlignedBB clone() {
        return new AxisAlignedBB(this.a, this.b, this.c, this.d, this.e, this.f);
    }

    public MovingObjectPosition a(Vec3D vec3d, Vec3D vec3d1) {
        return null;
    }

    public void b(AxisAlignedBB axisalignedbb) {
        this.c(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c, axisalignedbb.d, axisalignedbb.e, axisalignedbb.f);
    }

    public String toString() {
        return "box[" + this.a + ", " + this.b + ", " + this.c + " -> " + this.d + ", " + this.e + ", " + this.f + "]";
    }
}
