package net.minecraft.server;

import com.legacyminecraft.poseidon.block.MaterialPropertyBehaviour;

public class Material {
    private static final MaterialPropertyBehaviour MATERIAL_PROPERTY_BEHAVIOUR = MaterialPropertyBehaviour.getInstance();

    public static final Material AIR = new MaterialTransparent(MaterialMapColor.b);
    public static final Material GRASS = new Material(MaterialMapColor.c);
    public static final Material EARTH = new Material(MaterialMapColor.l);
    public static final Material WOOD = (new Material(MaterialMapColor.o)).o();
    public static final Material STONE = (new Material(MaterialMapColor.m)).n();
    public static final Material ORE = (new Material(MaterialMapColor.h)).n();
    public static final Material WATER = (new MaterialLiquid(MaterialMapColor.n)).k();
    public static final Material LAVA = (new MaterialLiquid(MaterialMapColor.f)).k();
    public static final Material LEAVES = (new Material(MaterialMapColor.i)).o().m().k();
    public static final Material PLANT = (new MaterialLogic(MaterialMapColor.i)).k();
    public static final Material SPONGE = new Material(MaterialMapColor.e);
    public static final Material CLOTH = (new Material(MaterialMapColor.e)).o();
    public static final Material FIRE = (new MaterialTransparent(MaterialMapColor.b)).k();
    public static final Material SAND = new Material(MaterialMapColor.d);
    public static final Material ORIENTABLE = (new MaterialLogic(MaterialMapColor.b)).k();
    public static final Material SHATTERABLE = (new Material(MaterialMapColor.b)).m();
    public static final Material TNT = (new Material(MaterialMapColor.f)).o().m();
    public static final Material CORAL = (new Material(MaterialMapColor.i)).k();
    public static final Material ICE = (new Material(MaterialMapColor.g)).m();
    public static final Material SNOW_LAYER = (new MaterialLogic(MaterialMapColor.j)).f().m().n().k();
    public static final Material SNOW_BLOCK = (new Material(MaterialMapColor.j)).n();
    public static final Material CACTUS = (new Material(MaterialMapColor.i)).m().k();
    public static final Material CLAY = new Material(MaterialMapColor.k);
    public static final Material PUMPKIN = (new Material(MaterialMapColor.i)).k();
    public static final Material PORTAL = (new MaterialPortal(MaterialMapColor.b)).l();
    public static final Material CAKE = (new Material(MaterialMapColor.b)).k();
    public static final Material WEB = (new Material(MaterialMapColor.e)).n().k();
    public static final Material PISTON = (new Material(MaterialMapColor.m)).l();
    private boolean canBurn;
    private boolean E;
    private boolean F;
    public final MaterialMapColor C;
    private boolean G = true;
    private int H;

    public Material(MaterialMapColor materialmapcolor) {
        this.C = materialmapcolor;
    }

    public boolean isLiquid() {
        return MATERIAL_PROPERTY_BEHAVIOUR.isLiquidByDefault();
    }

    public boolean isBuildable() {
        return MATERIAL_PROPERTY_BEHAVIOUR.isBuildableByDefault();
    }

    public boolean blocksLight() {
        return MATERIAL_PROPERTY_BEHAVIOUR.blocksLightByDefault();
    }

    public boolean isSolid() {
        return MATERIAL_PROPERTY_BEHAVIOUR.isSolidByDefault();
    }

    private Material m() {
        this.F = true;
        return this;
    }

    private Material n() {
        this.G = false;
        return this;
    }

    private Material o() {
        this.canBurn = true;
        return this;
    }

    public boolean isBurnable() {
        return MATERIAL_PROPERTY_BEHAVIOUR.isBurnable(this.canBurn);
    }

    public Material f() {
        this.E = true;
        return this;
    }

    public boolean isReplacable() {
        return MATERIAL_PROPERTY_BEHAVIOUR.isReplaceable(this.E);
    }

    public boolean h() {
        return MATERIAL_PROPERTY_BEHAVIOUR.blocksMovement(this.F, this.isSolid());
    }

    public boolean i() {
        return MATERIAL_PROPERTY_BEHAVIOUR.isOpaqueToLight(this.G);
    }

    public int j() {
        return MATERIAL_PROPERTY_BEHAVIOUR.getPushReaction(this.H);
    }

    protected Material k() {
        this.H = 1;
        return this;
    }

    protected Material l() {
        this.H = 2;
        return this;
    }
}
