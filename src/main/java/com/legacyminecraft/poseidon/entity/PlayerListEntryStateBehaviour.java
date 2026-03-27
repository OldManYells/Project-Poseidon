package com.legacyminecraft.poseidon.entity;


/**
 * Canonical behaviour for player-list entry equality and string formatting.
 */
public final class PlayerListEntryStateBehaviour {
    private static final PlayerListEntryStateBehaviour INSTANCE = new PlayerListEntryStateBehaviour();

    private PlayerListEntryStateBehaviour() {
    }

    public static PlayerListEntryStateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean equalsEntry(Object self, Object other) {
        if (other == null || !self.getClass().isInstance(other)) {
            return false;
        }

        Long selfKey = Long.valueOf(((Number) invoke(self, "a")).longValue());
        Long otherKey = Long.valueOf(((Number) invoke(other, "a")).longValue());

        if (selfKey != otherKey && (selfKey == null || !selfKey.equals(otherKey))) {
            return false;
        }

        Object selfValue = invoke(self, "b");
        Object otherValue = invoke(other, "b");
        return selfValue == otherValue || (selfValue != null && selfValue.equals(otherValue));
    }

    public String toEntryString(Object entry) {
        return invoke(entry, "a") + "=" + invoke(entry, "b");
    }

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
