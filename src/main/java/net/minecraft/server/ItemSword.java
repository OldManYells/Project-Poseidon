package net.minecraft.server;

import com.legacyminecraft.poseidon.item.SwordItemBehaviour;
import com.legacyminecraft.poseidon.item.ToolItemCombatAndMiningBehaviour;

public class ItemSword extends Item {
    private static final SwordItemBehaviour SWORD_ITEM_BEHAVIOUR = SwordItemBehaviour.getInstance();
    private static final ToolItemCombatAndMiningBehaviour TOOL_ITEM_COMBAT_AND_MINING_BEHAVIOUR = ToolItemCombatAndMiningBehaviour.getInstance();

    private int a;

    public ItemSword(int i, EnumToolMaterial enumtoolmaterial) {
        super(i);
        this.maxStackSize = 1;
        this.d(enumtoolmaterial.a());
        this.a = SWORD_ITEM_BEHAVIOUR.resolveAttackDamage(enumtoolmaterial);
    }

    public float a(ItemStack itemstack, Block block) {
        return SWORD_ITEM_BEHAVIOUR.resolveDestroySpeed(block);
    }

    public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        return TOOL_ITEM_COMBAT_AND_MINING_BEHAVIOUR.damageOnEntityHit(itemstack, entityliving1, 1);
    }

    public boolean a(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
        return TOOL_ITEM_COMBAT_AND_MINING_BEHAVIOUR.damageOnBlockBreak(itemstack, entityliving, 2);
    }

    public int a(Entity entity) {
        return SWORD_ITEM_BEHAVIOUR.resolveAttackDamageAgainstEntity(this.a, entity);
    }

    public boolean a(Block block) {
        return SWORD_ITEM_BEHAVIOUR.canHarvest(block);
    }
}
