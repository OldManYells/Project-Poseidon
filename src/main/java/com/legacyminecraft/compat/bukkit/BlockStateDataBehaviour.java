package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftBlockState material-data validation and creation policy.
 */
public final class BlockStateDataBehaviour {
    private static final BlockStateDataBehaviour INSTANCE = new BlockStateDataBehaviour();

    private BlockStateDataBehaviour() {
    }

    public static BlockStateDataBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T validateData(Object currentType, Object newData) {
        if (currentType == null || newData == null) {
            return BridgeReflection.cast(newData);
        }

        Object currentDataType = BridgeReflection.invoke(currentType, "getData");
        if (!(currentDataType instanceof Class) || ((Class<?>) currentDataType).isInstance(newData)) {
            return BridgeReflection.cast(newData);
        }

        throw new IllegalArgumentException("Provided data is not of type "
                + ((Class<?>) currentDataType).getName() + ", found " + newData.getClass().getName());
    }

    public <T> T createData(int typeId, byte rawData) {
        try {
            Class<?> materialClass = Class.forName("org" + ".bukkit.Material");
            Object material = materialClass.getMethod("getMaterial", Integer.TYPE).invoke(null, Integer.valueOf(typeId));
            if (material != null) {
                Object data = BridgeReflection.invoke(material, "getNewData", Byte.valueOf(rawData));
                if (data != null) {
                    return BridgeReflection.cast(data);
                }
            }
            Class<?> materialDataClass = Class.forName("org" + ".bukkit.material.MaterialData");
            return BridgeReflection.cast(materialDataClass.getConstructor(Integer.TYPE, Byte.TYPE).newInstance(Integer.valueOf(typeId), Byte.valueOf(rawData)));
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to create material data", exception);
        }
    }
}
