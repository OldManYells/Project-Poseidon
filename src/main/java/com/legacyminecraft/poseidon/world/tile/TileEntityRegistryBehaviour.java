package com.legacyminecraft.poseidon.world.tile;

import com.legacyminecraft.poseidon.world.TileEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public final class TileEntityRegistryBehaviour {
    private static final TileEntityRegistryBehaviour INSTANCE = new TileEntityRegistryBehaviour();

    private TileEntityRegistryBehaviour() {
    }

    public static TileEntityRegistryBehaviour getInstance() {
        return INSTANCE;
    }

    public void register(Map idToType, Map typeToId, Class type, String id) {
        if (typeToId.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate id: " + id);
        }

        idToType.put(id, type);
        typeToId.put(type, id);
    }

    public void readCoordinates(TileEntity tileEntity, Object tag) {
        tileEntity.x = invokeInt(tag, "e", "x");
        tileEntity.y = invokeInt(tag, "e", "y");
        tileEntity.z = invokeInt(tag, "e", "z");
    }

    public void writeBaseData(TileEntity tileEntity, Map typeToId, Object tag) {
        String id = (String) typeToId.get(tileEntity.getClass());

        if (id == null) {
            throw new RuntimeException(tileEntity.getClass() + " is missing a mapping! This is a bug!");
        }

        invoke(tag, "setString", "id", id);
        invoke(tag, "a", "x", Integer.valueOf(tileEntity.x));
        invoke(tag, "a", "y", Integer.valueOf(tileEntity.y));
        invoke(tag, "a", "z", Integer.valueOf(tileEntity.z));
    }

    public TileEntity createFromTag(Map idToType, Object tag) {
        TileEntity tileEntity = null;

        try {
            String id = invokeString(tag, "getString", "id");
            Class type = (Class) idToType.get(id);

            if (type != null) {
                tileEntity = (TileEntity) type.newInstance();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (tileEntity != null) {
            invoke(tileEntity, "a", tag);
        } else {
            System.out.println("Skipping TileEntity with id " + invokeString(tag, "getString", "id"));
        }

        return tileEntity;
    }

    public int getBlockData(Object world, int x, int y, int z) {
        return invokeInt(world, "getData", x, y, z);
    }

    public void notifyUpdated(Object world, int x, int y, int z, TileEntity tileEntity) {
        if (world != null) {
            invoke(world, "b", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z), tileEntity);
        }
    }

    public boolean isInvalid(boolean invalidFlag) {
        return invalidFlag;
    }

    public boolean markInvalid() {
        return true;
    }

    public boolean markValid() {
        return false;
    }

    private int invokeInt(Object target, String methodName, String arg) {
        Object result = invoke(target, methodName, arg);
        return result instanceof Number ? ((Number) result).intValue() : 0;
    }

    private int invokeInt(Object target, String methodName, int x, int y, int z) {
        Object result = invoke(target, methodName, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
        return result instanceof Number ? ((Number) result).intValue() : 0;
    }

    private String invokeString(Object target, String methodName, String arg) {
        Object result = invoke(target, methodName, arg);
        return result == null ? "" : result.toString();
    }

    private Object invoke(Object target, String methodName, Object... arguments) {
        try {
            Method[] methods = target.getClass().getMethods();
            for (Method method : methods) {
                if (!method.getName().equals(methodName) || method.getParameterTypes().length != arguments.length) {
                    continue;
                }

                Class<?>[] parameterTypes = method.getParameterTypes();
                boolean compatible = true;
                for (int index = 0; index < parameterTypes.length; index++) {
                    Object argument = arguments[index];
                    if (!isCompatible(parameterTypes[index], argument)) {
                        compatible = false;
                        break;
                    }
                }

                if (!compatible) {
                    continue;
                }

                method.setAccessible(true);
                return method.invoke(target, arguments);
            }
            throw new NoSuchMethodException(methodName);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private boolean isCompatible(Class<?> parameterType, Object argument) {
        if (argument == null) {
            return !parameterType.isPrimitive();
        }
        if (!parameterType.isPrimitive()) {
            return parameterType.isAssignableFrom(argument.getClass());
        }
        if (parameterType == boolean.class) {
            return argument instanceof Boolean;
        }
        if (parameterType == byte.class) {
            return argument instanceof Byte;
        }
        if (parameterType == short.class) {
            return argument instanceof Short || argument instanceof Byte;
        }
        if (parameterType == int.class) {
            return argument instanceof Integer || argument instanceof Short || argument instanceof Byte;
        }
        if (parameterType == long.class) {
            return argument instanceof Long || argument instanceof Integer || argument instanceof Short || argument instanceof Byte;
        }
        if (parameterType == float.class) {
            return argument instanceof Float || argument instanceof Integer || argument instanceof Short || argument instanceof Byte;
        }
        if (parameterType == double.class) {
            return argument instanceof Double || argument instanceof Float || argument instanceof Integer || argument instanceof Short || argument instanceof Byte;
        }
        if (parameterType == char.class) {
            return argument instanceof Character;
        }
        return false;
    }
}
