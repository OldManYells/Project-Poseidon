package com.legacyminecraft.poseidon.inventory;


import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Canonical crafting-manager behaviour for recipe bootstrap and lookup.
 */
public final class CraftingManagerBehaviour {
    private static final CraftingManagerBehaviour INSTANCE = new CraftingManagerBehaviour();

    private CraftingManagerBehaviour() {
    }

    public static CraftingManagerBehaviour getInstance() {
        return INSTANCE;
    }

    public void bootstrapDefaultRecipes(Object manager, List recipes, Comparator sorter) {
        invokeRecipeRegistrar(manager, "RecipesTools");
        invokeRecipeRegistrar(manager, "RecipesWeapons");
        invokeRecipeRegistrar(manager, "RecipeIngots");
        invokeRecipeRegistrar(manager, "RecipesFood");
        invokeRecipeRegistrar(manager, "RecipesCrafting");
        invokeRecipeRegistrar(manager, "RecipesArmor");
        invokeRecipeRegistrar(manager, "RecipesDyes");
        Collections.sort(recipes, sorter);
        System.out.println(recipes.size() + " recipes");
    }

    public void registerShapedRecipe(Object manager, List recipes, ItemStack result, Object... recipeDefinition) {
        String pattern = "";
        int index = 0;
        int recipeWidth = 0;
        int recipeHeight = 0;

        if (recipeDefinition[index] instanceof String[]) {
            String[] rows = (String[]) recipeDefinition[index++];

            for (int rowIndex = 0; rowIndex < rows.length; ++rowIndex) {
                String row = rows[rowIndex];

                ++recipeHeight;
                recipeWidth = row.length();
                pattern = pattern + row;
            }
        } else {
            while (recipeDefinition[index] instanceof String) {
                String row = (String) recipeDefinition[index++];

                ++recipeHeight;
                recipeWidth = row.length();
                pattern = pattern + row;
            }
        }

        HashMap<Character, Object> ingredientBySymbol;

        for (ingredientBySymbol = new HashMap<Character, Object>(); index < recipeDefinition.length; index += 2) {
            Character symbol = (Character) recipeDefinition[index];
            ingredientBySymbol.put(symbol, recipeDefinition[index + 1]);
        }

        Object[] gridStacks = (Object[]) Array.newInstance(resolveNmsItemStackClass(manager), recipeWidth * recipeHeight);

        for (int gridIndex = 0; gridIndex < recipeWidth * recipeHeight; ++gridIndex) {
            char symbol = pattern.charAt(gridIndex);
            gridStacks[gridIndex] = ingredientBySymbol.containsKey(Character.valueOf(symbol)) ? toNmsIngredientStack(manager, ingredientBySymbol.get(Character.valueOf(symbol)), true) : null;
        }

        recipes.add(newRecipeInstance(manager, "ShapedRecipes", new Class[] {Integer.TYPE, Integer.TYPE, gridStacks.getClass(), resolveNmsItemStackClass(manager)}, new Object[] {Integer.valueOf(recipeWidth), Integer.valueOf(recipeHeight), gridStacks, toNmsItemStack(manager, result)}));
    }

    public void registerShapelessRecipe(Object manager, List recipes, ItemStack result, Object... ingredients) {
        ArrayList ingredientStacks = new ArrayList();
        Object[] ingredientArray = ingredients;
        int ingredientCount = ingredients.length;

        for (int ingredientIndex = 0; ingredientIndex < ingredientCount; ++ingredientIndex) {
            Object ingredient = ingredientArray[ingredientIndex];

            ingredientStacks.add(toNmsIngredientStack(manager, ingredient, false));
        }

        recipes.add(newRecipeInstance(manager, "ShapelessRecipes", new Class[] {resolveNmsItemStackClass(manager), List.class}, new Object[] {toNmsItemStack(manager, result), ingredientStacks}));
    }

    public Object craft(List recipes, Object inventoryCrafting) {
        for (int recipeIndex = 0; recipeIndex < recipes.size(); ++recipeIndex) {
            Object recipe = recipes.get(recipeIndex);

            if (invokeRecipeBoolean(recipe, inventoryCrafting, "a")) {
                return invokeRecipeObject(recipe, inventoryCrafting, "b");
            }
        }

        return null;
    }

    public List recipes(List recipes) {
        return recipes;
    }

    private void invokeRecipeRegistrar(Object manager, String simpleName) {
        try {
            String bridgePackage = manager.getClass().getPackage().getName();
            Class registrarClass = Class.forName(bridgePackage + "." + simpleName);
            Object registrar = registrarClass.getDeclaredConstructor().newInstance();
            Method registerMethod = registrarClass.getMethod("a", manager.getClass());
            registerMethod.invoke(registrar, manager);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to bootstrap crafting recipes from " + simpleName, e);
        }
    }

    private Object newRecipeInstance(Object manager, String simpleName, Class[] parameterTypes, Object[] arguments) {
        try {
            String bridgePackage = manager.getClass().getPackage().getName();
            Class recipeClass = Class.forName(bridgePackage + "." + simpleName);
            Constructor constructor = recipeClass.getConstructor(parameterTypes);
            return constructor.newInstance(arguments);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to create crafting recipe " + simpleName, e);
        }
    }

    private boolean invokeRecipeBoolean(Object recipe, Object inventoryCrafting, String methodName) {
        try {
            Method method = findRecipeMethod(recipe, methodName);
            Object value = method.invoke(recipe, inventoryCrafting);
            return value instanceof Boolean ? (Boolean) value : false;
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to evaluate crafting recipe match", e);
        }
    }

    private Object invokeRecipeObject(Object recipe, Object inventoryCrafting, String methodName) {
        try {
            Method method = findRecipeMethod(recipe, methodName);
            return method.invoke(recipe, inventoryCrafting);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to evaluate crafting recipe result", e);
        }
    }

    private Method findRecipeMethod(Object recipe, String methodName) throws NoSuchMethodException {
        Method[] methods = recipe.getClass().getMethods();
        for (int methodIndex = 0; methodIndex < methods.length; ++methodIndex) {
            Method method = methods[methodIndex];
            if (method.getName().equals(methodName) && method.getParameterTypes().length == 1) {
                return method;
            }
        }

        throw new NoSuchMethodException(methodName);
    }

    private Class resolveNmsItemStackClass(Object manager) {
        try {
            return Class.forName(manager.getClass().getPackage().getName() + ".ItemStack");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Unable to resolve NMS ItemStack", e);
        }
    }

    private Object toNmsItemStack(Object manager, ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }

        return newNmsItemStack(manager, itemStack.id, itemStack.count, itemStack.damage);
    }

    private Object toNmsIngredientStack(Object manager, Object ingredient, boolean shapedRecipe) {
        if (ingredient == null) {
            return null;
        }

        if (ingredient instanceof ItemStack) {
            return newNmsItemStack(manager, ((ItemStack) ingredient).id, ((ItemStack) ingredient).count, ((ItemStack) ingredient).damage);
        }

        if (ingredient instanceof Number) {
            return newNmsItemStack(manager, ((Number) ingredient).intValue(), 1, 0);
        }

        int id = readIntField(ingredient, "id", -1);
        if (id < 0) {
            return null;
        }

        int damage = shapedRecipe && isBlockLike(ingredient) ? -1 : readIntField(ingredient, "damage", 0);
        return newNmsItemStack(manager, id, 1, damage);
    }

    private Object newNmsItemStack(Object manager, int id, int count, int damage) {
        try {
            Constructor constructor = resolveNmsItemStackClass(manager).getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);
            return constructor.newInstance(Integer.valueOf(id), Integer.valueOf(count), Integer.valueOf(damage));
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to create NMS ItemStack", e);
        }
    }

    private int readIntField(Object value, String fieldName, int defaultValue) {
        try {
            Field field = value.getClass().getField(fieldName);
            return field.getInt(value);
        } catch (ReflectiveOperationException e) {
            return defaultValue;
        }
    }

    private boolean isBlockLike(Object value) {
        return value.getClass().getSimpleName().equals("Block") || value.getClass().getName().endsWith(".Block");
    }
}
