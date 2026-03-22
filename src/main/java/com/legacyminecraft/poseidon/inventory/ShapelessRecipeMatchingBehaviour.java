package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.InventoryCrafting;
import net.minecraft.server.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ShapelessRecipeMatchingBehaviour {
    private static final ShapelessRecipeMatchingBehaviour INSTANCE = new ShapelessRecipeMatchingBehaviour();

    private ShapelessRecipeMatchingBehaviour() {
    }

    public static ShapelessRecipeMatchingBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean matches(List ingredients, InventoryCrafting inventorycrafting) {
        ArrayList remaining = new ArrayList(ingredients);

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 3; ++column) {
                ItemStack itemstack = inventorycrafting.b(column, row);

                if (itemstack != null) {
                    boolean matched = false;
                    Iterator iterator = remaining.iterator();

                    while (iterator.hasNext()) {
                        ItemStack ingredient = (ItemStack) iterator.next();

                        if (itemstack.id == ingredient.id && (ingredient.getData() == -1 || itemstack.getData() == ingredient.getData())) {
                            matched = true;
                            remaining.remove(ingredient);
                            break;
                        }
                    }

                    if (!matched) {
                        return false;
                    }
                }
            }
        }

        return remaining.isEmpty();
    }

    public ItemStack craftResult(ItemStack resultTemplate) {
        return resultTemplate.cloneItemStack();
    }

    public int ingredientCount(List ingredients) {
        return ingredients.size();
    }
}
