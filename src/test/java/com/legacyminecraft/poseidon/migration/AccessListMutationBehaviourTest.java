package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.AccessListMutationBehaviour;
import com.legacyminecraft.poseidon.auth.login.AccessListPersistence;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class AccessListMutationBehaviourTest {
    private final AccessListMutationBehaviour behaviour = AccessListMutationBehaviour.getInstance();
    private final AccessListPersistence persistence = AccessListPersistence.getInstance();

    @Test
    public void addAndPersistNormalizesAndInvokesHooks() {
        Set values = new HashSet();
        HookCounter hooks = new HookCounter();

        behaviour.addAndPersist(values, " Admin ", persistence, hooks);

        Assert.assertTrue(values.contains("admin"));
        Assert.assertEquals(1, hooks.persistCount);
        Assert.assertEquals(1, hooks.afterMutationCount);
    }

    @Test
    public void removeAndPersistNormalizesAndInvokesHooks() {
        Set values = new HashSet();
        values.add("admin");
        HookCounter hooks = new HookCounter();

        behaviour.removeAndPersist(values, " ADMIN ", persistence, hooks);

        Assert.assertFalse(values.contains("admin"));
        Assert.assertEquals(1, hooks.persistCount);
        Assert.assertEquals(1, hooks.afterMutationCount);
    }

    private static final class HookCounter implements AccessListMutationBehaviour.MutationHooks {
        private int persistCount;
        private int afterMutationCount;

        public void persist() {
            persistCount++;
        }

        public void afterMutation() {
            afterMutationCount++;
        }
    }
}
