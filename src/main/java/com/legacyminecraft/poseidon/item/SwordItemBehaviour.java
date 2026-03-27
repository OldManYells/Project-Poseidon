package com.legacyminecraft.poseidon.item;


public final class SwordItemBehaviour {
    private static final SwordItemBehaviour INSTANCE = new SwordItemBehaviour();

    private SwordItemBehaviour() {
    }

    public static SwordItemBehaviour getInstance() {
        return INSTANCE;
    }

    public float resolveDestroySpeed(Object block) {
        return readBlockId(block) == Block.WEB.id ? 15.0F : 1.5F;
    }

    public int resolveAttackDamage(Object material) {
        return 4 + readMaterialAttackBonus(material) * 2;
    }

    public int resolveAttackDamageAgainstEntity(int attackDamage, Object entity) {
        return attackDamage;
    }

    public boolean canHarvest(Object block) {
        return readBlockId(block) == Block.WEB.id;
    }

    private int readMaterialAttackBonus(Object material) {
        if (material == null) {
            return 0;
        }
        try {
            Object value = material.getClass().getMethod("c").invoke(material);
            return value instanceof Number ? ((Number) value).intValue() : 0;
        } catch (ReflectiveOperationException ignored) {
            return 0;
        }
    }

    private int readBlockId(Object block) {
        if (block == null) {
            return -1;
        }
        try {
            return block.getClass().getField("id").getInt(block);
        } catch (ReflectiveOperationException ignored) {
            return -1;
        }
    }
}
