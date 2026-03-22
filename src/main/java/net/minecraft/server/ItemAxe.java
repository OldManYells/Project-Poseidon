package net.minecraft.server;

import com.legacyminecraft.poseidon.item.AxeToolProfileBehaviour;

public class ItemAxe extends ItemTool {
    private static final AxeToolProfileBehaviour AXE_TOOL_PROFILE_BEHAVIOUR = AxeToolProfileBehaviour.getInstance();

    private static Block[] bk = AXE_TOOL_PROFILE_BEHAVIOUR.effectiveBlocks();

    protected ItemAxe(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, AXE_TOOL_PROFILE_BEHAVIOUR.baseAttackOffset(), enumtoolmaterial, bk);
    }
}
