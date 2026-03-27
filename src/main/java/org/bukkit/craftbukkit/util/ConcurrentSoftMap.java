package org.bukkit.craftbukkit.util;

import com.legacyminecraft.compat.bukkit.SoftMapStrongReferenceQueueBehaviour;
import com.legacyminecraft.compat.bukkit.SoftMapReferenceCleanupBehaviour;
import com.legacyminecraft.compat.bukkit.SoftMapMapAccessBehaviour;
import com.legacyminecraft.compat.bukkit.SoftMapPutAllBehaviour;
import com.legacyminecraft.compat.bukkit.SoftMapPutIfAbsentBehaviour;
import com.legacyminecraft.compat.bukkit.SoftMapUnsupportedOperationBehaviour;
import com.legacyminecraft.compat.bukkit.SoftMapValueAccessBehaviour;
import com.google.common.collect.MapMaker;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creates a map that uses soft reference.  This indicates to the garbage collector
 * that they can be removed if necessary
 *
 * A minimum number of strong references can be set.  These most recent N objects added
 * to the map will not be removed by the garbage collector.
 *
 * Objects will never be removed if they are referenced strongly from somewhere else

 * Note: While data corruption won't happen, the garbage collector is potentially async
 *       This could lead to the return values from containsKey() and similar methods being
 *       out of date by the time they are used.  The class could return null when the object
 *       is retrieved by a .get() call directly after a .containsKey() call returned true
 *
 * @deprecated Use {@link MapMaker} to create a concurrent soft-reference map, this class is inefficient and will be removed
 * @author raphfrk
 */

@Deprecated
public class ConcurrentSoftMap<K, V> {

    private final ConcurrentHashMap<K, SoftMapReference<K, V>> map = new ConcurrentHashMap<K, SoftMapReference<K, V>>();
    private final ReferenceQueue<SoftMapReference> queue = new ReferenceQueue<SoftMapReference>();
    private final LinkedList<V> strongReferenceQueue = new LinkedList<V>();
    private final int strongReferenceSize;
    private final SoftMapStrongReferenceQueueBehaviour softMapStrongReferenceQueueBehaviour =
            SoftMapStrongReferenceQueueBehaviour.getInstance();
    private final SoftMapReferenceCleanupBehaviour softMapReferenceCleanupBehaviour =
            SoftMapReferenceCleanupBehaviour.getInstance();
    private final SoftMapMapAccessBehaviour softMapMapAccessBehaviour =
            SoftMapMapAccessBehaviour.getInstance();
    private final SoftMapPutAllBehaviour softMapPutAllBehaviour =
            SoftMapPutAllBehaviour.getInstance();
    private final SoftMapPutIfAbsentBehaviour softMapPutIfAbsentBehaviour =
            SoftMapPutIfAbsentBehaviour.getInstance();
    private final SoftMapUnsupportedOperationBehaviour softMapUnsupportedOperationBehaviour =
            SoftMapUnsupportedOperationBehaviour.getInstance();
    private final SoftMapValueAccessBehaviour softMapValueAccessBehaviour =
            SoftMapValueAccessBehaviour.getInstance();

    public ConcurrentSoftMap() {
        this(20);
    }

    public ConcurrentSoftMap(int size) {
        strongReferenceSize = size;
    }

    // When a soft reference is deleted by the garbage collector, it is set to reference null
    // and added to the queue
    //
    // However, these null references still exist in the ConcurrentHashMap as keys.  This method removes these keys.
    //
    // It is called whenever there is a method call of the map.

    private void emptyQueue() {
        softMapReferenceCleanupBehaviour.drain(new SoftMapReferenceCleanupBehaviour.CleanupCallbacks() {
            @Override
            public Object pollReference() {
                return queue.poll();
            }

            @Override
            public Object extractKey(Object reference) {
                return ((SoftMapReference) reference).key;
            }

            @Override
            public void removeByKey(Object key) {
                map.remove(key);
            }
        });
    }

    public void clear() {
        synchronized (strongReferenceQueue) {
            softMapMapAccessBehaviour.clearStrongReferences(strongReferenceQueue);
        }
        softMapMapAccessBehaviour.clearMap(map);
        emptyQueue();
    }

    // Shouldn't support this, since the garbage collection is async

    public boolean containsKey(K key) {
        emptyQueue();
        return softMapMapAccessBehaviour.containsKey(map, key);
    }

    // Shouldn't support this, since the garbage collection is async

    public boolean containsValue(V value) {
        emptyQueue();
        return softMapMapAccessBehaviour.containsValue(map, value);
    }

    // Shouldn't support this since it would create strong references to all the entries

    public Set entrySet() {
        emptyQueue();
        throw softMapUnsupportedOperationBehaviour.unsupported(
                "SoftMap does not support this operation, since it creates potentially stong references"
        );
    }

    // Doesn't support these either

    public boolean equals(Object o) {
        emptyQueue();
        throw softMapUnsupportedOperationBehaviour.unsupported("SoftMap doesn't support equals checks");
    }

    // This operation returns null if the entry is not in the map

    public V get(K key) {
        emptyQueue();
        return fastGet(key);
    }

    private V fastGet(K key) {
        return (V) softMapValueAccessBehaviour.getValue(key, new SoftMapValueAccessBehaviour.GetCallbacks() {
            @Override
            public Object getReference(Object lookupKey) {
                return map.get(lookupKey);
            }

            @Override
            public Object dereference(Object reference) {
                return ((SoftMapReference<K, V>) reference).get();
            }

            @Override
            public void promote(Object value) {
                synchronized (strongReferenceQueue) {
                    softMapStrongReferenceQueueBehaviour.promote(strongReferenceQueue, (V) value, strongReferenceSize);
                }
            }
        });
    }

    // Doesn't support this either

    public int hashCode() {
        emptyQueue();
        throw softMapUnsupportedOperationBehaviour.unsupported("SoftMap doesn't support hashCode");
    }

    // This is another risky method, since again, garbage collection is async

    public boolean isEmpty() {
        emptyQueue();
        return softMapMapAccessBehaviour.isEmpty(map);
    }

    // Return all the keys, again could go out of date

    public Set keySet() {
        emptyQueue();
        return softMapMapAccessBehaviour.keySet(map);
    }

    // Adds the mapping to the map

    public V put(K key, V value) {
        emptyQueue();
        V old = fastGet(key);
        fastPut(key, value);
        return old;
    }

    private void fastPut(K key, V value) {
        softMapValueAccessBehaviour.putAndPromote(
                key,
                new SoftMapReference<K, V>(key, value, queue),
                value,
                new SoftMapValueAccessBehaviour.PutCallbacks() {
                    @Override
                    public void put(Object putKey, Object reference) {
                        softMapMapAccessBehaviour.put(map, putKey, reference);
                    }

                    @Override
                    public void promote(Object promotedValue) {
                        synchronized (strongReferenceQueue) {
                            softMapStrongReferenceQueueBehaviour.promote(
                                    strongReferenceQueue,
                                    (V) promotedValue,
                                    strongReferenceSize
                            );
                        }
                    }
                }
        );
    }

    public V putIfAbsent(K key, V value) {
        emptyQueue();
        return fastPutIfAbsent(key, value);
    }

    private V fastPutIfAbsent(K key, V value) {
        final SoftMapReference<K, V> newValue = new SoftMapReference<K, V>(key, value, queue);
        V ret = (V) softMapPutIfAbsentBehaviour.putIfAbsent(key, newValue, new SoftMapPutIfAbsentBehaviour.PutIfAbsentCallbacks() {
            @Override
            public boolean containsKey(Object lookupKey) {
                return softMapMapAccessBehaviour.containsKey(map, lookupKey);
            }

            @Override
            public Object get(Object lookupKey) {
                return map.get(lookupKey);
            }

            @Override
            public Object dereference(Object reference) {
                return ((SoftMapReference<K, V>) reference).get();
            }

            @Override
            public Object putIfAbsent(Object lookupKey, Object reference) {
                return softMapMapAccessBehaviour.putIfAbsent(map, lookupKey, reference);
            }

            @Override
            public boolean replace(Object lookupKey, Object oldReference, Object newReference) {
                return softMapMapAccessBehaviour.replace(map, lookupKey, oldReference, newReference);
            }
        });

        if (ret == null) {
            synchronized (strongReferenceQueue) {
                softMapStrongReferenceQueueBehaviour.promote(strongReferenceQueue, value, strongReferenceSize);
            }
        }

        return ret;
    }

    // Adds the mappings to the map

    public void putAll(Map other) {
        emptyQueue();
        softMapPutAllBehaviour.putAll(other, new SoftMapPutAllBehaviour.PutCallbacks() {
            @Override
            public void put(Object key, Object value) {
                fastPut((K) key, (V) value);
            }
        });
    }

    // Remove object

    public V remove(K key) {
        emptyQueue();
        return (V) softMapValueAccessBehaviour.removeAndDereference(key, new SoftMapValueAccessBehaviour.RemoveCallbacks() {
            @Override
            public Object remove(Object removeKey) {
                return softMapMapAccessBehaviour.remove(map, removeKey);
            }

            @Override
            public Object dereference(Object reference) {
                return ((SoftMapReference<K, V>) reference).get();
            }
        });
    }

    // Returns size, could go out of date

    public int size() {
        emptyQueue();
        return softMapMapAccessBehaviour.size(map);
    }

    // Shouldn't support this since it would create strong references to all the entries

    public Collection values() {
        emptyQueue();
        throw softMapUnsupportedOperationBehaviour.unsupported(
                "SoftMap does not support this operation, since it creates potentially stong references"
        );
    }

    private static class SoftMapReference<K, V> extends SoftReference<V> {
        K key;

        SoftMapReference(K key, V value, ReferenceQueue queue) {
            super(value, queue);
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (!(o instanceof SoftMapReference)) {
                return false;
            }
            SoftMapReference other = (SoftMapReference) o;
            return other.get() == get();
        }
    }
}
