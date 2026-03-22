package com.legacyminecraft.poseidon.auth.login;

import java.util.Set;

/**
 * Canonical mutation flow for legacy access-control list updates.
 */
public final class AccessListMutationBehaviour {
    private static final AccessListMutationBehaviour INSTANCE = new AccessListMutationBehaviour();

    private AccessListMutationBehaviour() {
    }

    public static AccessListMutationBehaviour getInstance() {
        return INSTANCE;
    }

    public void addAndPersist(Set target,
                              String value,
                              AccessListPersistence accessListPersistence,
                              MutationHooks mutationHooks) {
        accessListPersistence.addNormalized(target, value);
        if (mutationHooks != null) {
            mutationHooks.persist();
            mutationHooks.afterMutation();
        }
    }

    public void removeAndPersist(Set target,
                                 String value,
                                 AccessListPersistence accessListPersistence,
                                 MutationHooks mutationHooks) {
        accessListPersistence.removeNormalized(target, value);
        if (mutationHooks != null) {
            mutationHooks.persist();
            mutationHooks.afterMutation();
        }
    }

    public interface MutationHooks {
        void persist();

        void afterMutation();
    }
}
