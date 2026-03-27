/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bukkit.craftbukkit.util;

import com.legacyminecraft.compat.bukkit.LongHashKeyBehaviour;

/**
 *
 * @author Nathan
 */
public abstract class LongHash {
    private static final LongHashKeyBehaviour LONG_HASH_KEY_BEHAVIOUR = LongHashKeyBehaviour.getInstance();

    static long toLong(int msw, int lsw) {
        return LONG_HASH_KEY_BEHAVIOUR.toLong(msw, lsw);
    }

    static int msw(long l) {
        return LONG_HASH_KEY_BEHAVIOUR.mostSignificantWord(l);
    }

    static int lsw(long l) {
        return LONG_HASH_KEY_BEHAVIOUR.leastSignificantWord(l);
    }

    public boolean containsKey(int msw, int lsw) {
        return containsKey(toLong(msw, lsw));
    }

    public void remove(int msw, int lsw) {
        remove(toLong(msw, lsw));
    }

    public abstract boolean containsKey(long key);

    public abstract void remove(long key);
}
