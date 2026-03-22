package net.minecraft.server;

import com.legacyminecraft.poseidon.block.NonSolidMaterialBehaviour;

public class MaterialTransparent extends Material {
    private static final NonSolidMaterialBehaviour NON_SOLID_MATERIAL_BEHAVIOUR = NonSolidMaterialBehaviour.getInstance();

    public MaterialTransparent(MaterialMapColor materialmapcolor) {
        super(materialmapcolor);
        this.f();
    }

    public boolean isBuildable() {
        return NON_SOLID_MATERIAL_BEHAVIOUR.isBuildable();
    }

    public boolean blocksLight() {
        return NON_SOLID_MATERIAL_BEHAVIOUR.blocksLight();
    }

    public boolean isSolid() {
        return NON_SOLID_MATERIAL_BEHAVIOUR.isSolid();
    }
}
