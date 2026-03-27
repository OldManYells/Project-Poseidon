package com.legacyminecraft.poseidon.world.player;


/**
 * Canonical behaviour for chunk tile-entity packet extraction.
 */
public final class PlayerChunkTileEntityPacketBehaviour {
    private static final PlayerChunkTileEntityPacketBehaviour INSTANCE = new PlayerChunkTileEntityPacketBehaviour();

    private PlayerChunkTileEntityPacketBehaviour() {
    }

    public static PlayerChunkTileEntityPacketBehaviour getInstance() {
        return INSTANCE;
    }

    public Object extractUpdatePacket(Object tileEntity) {
        if (tileEntity == null) {
            return null;
        }

        return Bridge.invoke(tileEntity, "f");
    }

    private static final class Bridge {
        private static Object invoke(Object target, String methodName, Object... args) {
            try {
                for (java.lang.reflect.Method method : target.getClass().getMethods()) {
                    if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                        method.setAccessible(true);
                        return method.invoke(target, args);
                    }
                }
                throw new IllegalStateException("Method not found: " + methodName);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }
    }
}
