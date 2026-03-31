package net.minecraft.server;

public class MaterialMapColor {

    public static final MaterialMapColor[] a = new MaterialMapColor[16];
    public static final MaterialMapColor b = new MaterialMapColor(0, 0);
    public static final MaterialMapColor c = new MaterialMapColor(1, 8368696);
    public static final MaterialMapColor d = new MaterialMapColor(2, 16247203);
    public static final MaterialMapColor e = new MaterialMapColor(3, 10987431);
    public static final MaterialMapColor f = new MaterialMapColor(4, 16711680);
    public static final MaterialMapColor g = new MaterialMapColor(5, 10526975);
    public static final MaterialMapColor h = new MaterialMapColor(6, 10987431);
    public static final MaterialMapColor i = new MaterialMapColor(7, 31744);
    public static final MaterialMapColor j = new MaterialMapColor(8, 16777215);
    public static final MaterialMapColor k = new MaterialMapColor(9, 10791096);
    public static final MaterialMapColor l = new MaterialMapColor(10, 12020271);
    public static final MaterialMapColor m = new MaterialMapColor(11, 7368816);
    public static final MaterialMapColor n = new MaterialMapColor(12, 4210943);
    public static final MaterialMapColor o = new MaterialMapColor(13, 6837042);
    public final int p;
    public final int q;

    private MaterialMapColor(int i, int j) {
        this.q = i;
        this.p = j;
        a[i] = this;
    }

    public static final MaterialMapColor[] BY_ID = a;
    public static final MaterialMapColor NONE = b;
    public static final MaterialMapColor GRASS = c;
    public static final MaterialMapColor SAND = d;
    public static final MaterialMapColor CLOTH = e;
    public static final MaterialMapColor TNT = f;
    public static final MaterialMapColor ICE = g;
    public static final MaterialMapColor IRON = h;
    public static final MaterialMapColor FOLIAGE = i;
    public static final MaterialMapColor SNOW = j;
    public static final MaterialMapColor CLAY = k;
    public static final MaterialMapColor DIRT = l;
    public static final MaterialMapColor STONE = m;
    public static final MaterialMapColor WATER = n;
    public static final MaterialMapColor WOOD = o;
    public int getColorValue() {
        return this.p;
    }

    public int getId() {
        return this.q;
    }

}
