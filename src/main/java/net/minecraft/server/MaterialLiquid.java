package net.minecraft.server;

import com.legacyminecraft.poseidon.block.LiquidMaterialBehaviour;
import com.legacyminecraft.poseidon.block.NonSolidMaterialBehaviour;

public class MaterialLiquid extends Material {
    private static final LiquidMaterialBehaviour LIQUID_MATERIAL_BEHAVIOUR = LiquidMaterialBehaviour.getInstance();
    private static final NonSolidMaterialBehaviour NON_SOLID_MATERIAL_BEHAVIOUR = NonSolidMaterialBehaviour.getInstance();

    public MaterialLiquid(MaterialMapColor materialmapcolor) {
        super(materialmapcolor);
        this.f();
        this.k();
    }

    public boolean isLiquid() {
        return LIQUID_MATERIAL_BEHAVIOUR.isLiquid();
    }

    public boolean isSolid() {
        return NON_SOLID_MATERIAL_BEHAVIOUR.isSolid();
    }

    public boolean isBuildable() {
        return NON_SOLID_MATERIAL_BEHAVIOUR.isBuildable();
    }
}
