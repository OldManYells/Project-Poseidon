package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behaviour for CraftBukkit wrapper handle-mutation sequencing.
 */
public final class EntityHandleMutationBehaviour {
    private static final EntityHandleMutationBehaviour INSTANCE = new EntityHandleMutationBehaviour();

    private EntityHandleMutationBehaviour() {
    }

    public static EntityHandleMutationBehaviour getInstance() {
        return INSTANCE;
    }

    public void applyHandle(Object updatedHandle, HandleMutationCallbacks callbacks) {
        callbacks.setSuperHandle(updatedHandle);
        callbacks.assignHandleField(updatedHandle);
        callbacks.afterHandleAssignment(updatedHandle);
    }

    public interface HandleMutationCallbacks {
        void setSuperHandle(Object updatedHandle);

        void assignHandleField(Object updatedHandle);

        void afterHandleAssignment(Object updatedHandle);
    }
}
