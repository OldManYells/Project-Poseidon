package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftBlock material-to-type mutation.
 */
public final class CraftBlockTypeMutationBehaviour {
    private static final CraftBlockTypeMutationBehaviour INSTANCE = new CraftBlockTypeMutationBehaviour();

    private CraftBlockTypeMutationBehaviour() {
    }

    public static CraftBlockTypeMutationBehaviour getInstance() {
        return INSTANCE;
    }

    public void setType(CraftBlock block, Material type) {
        block.setTypeId(type.getId());
    }
}
