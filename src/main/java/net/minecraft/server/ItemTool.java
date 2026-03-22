package net.minecraft.server;

import com.legacyminecraft.poseidon.item.ToolItemCombatAndMiningBehaviour;

public class ItemTool extends Item {
    private static final ToolItemCombatAndMiningBehaviour TOOL_ITEM_COMBAT_AND_MINING_BEHAVIOUR = ToolItemCombatAndMiningBehaviour.getInstance();

    private Block[] bk;
    private float bl = 4.0F;
    private int bm;
    protected EnumToolMaterial a;

    protected ItemTool(int i, int j, EnumToolMaterial enumtoolmaterial, Block[] ablock) {
        super(i);
        this.a = enumtoolmaterial;
        this.bk = ablock;
        this.maxStackSize = 1;
        this.d(enumtoolmaterial.a());
        this.bl = enumtoolmaterial.b();
        this.bm = TOOL_ITEM_COMBAT_AND_MINING_BEHAVIOUR.resolveToolAttackDamage(j, enumtoolmaterial);
    }

    public float a(ItemStack itemstack, Block block) {
        return TOOL_ITEM_COMBAT_AND_MINING_BEHAVIOUR.resolveDestroySpeed(this.bk, this.bl, block);
    }

    public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        return TOOL_ITEM_COMBAT_AND_MINING_BEHAVIOUR.damageOnEntityHit(itemstack, entityliving1, 2);
    }

    public boolean a(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
        return TOOL_ITEM_COMBAT_AND_MINING_BEHAVIOUR.damageOnBlockBreak(itemstack, entityliving, 1);
    }

    public int a(Entity entity) {
        return TOOL_ITEM_COMBAT_AND_MINING_BEHAVIOUR.resolveAttackDamageAgainstEntity(this.bm, entity);
    }
}
