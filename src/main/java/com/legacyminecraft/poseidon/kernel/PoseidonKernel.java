package com.legacyminecraft.poseidon.kernel;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Lightweight service registry used while migrating logic out of legacy packages.
 */
public final class PoseidonKernel {
    private static final PoseidonKernel INSTANCE = new PoseidonKernel();

    private final AtomicBoolean bootstrapped = new AtomicBoolean(false);
    private final Map<Class<?>, Object> services = new ConcurrentHashMap<Class<?>, Object>();

    private PoseidonKernel() {
    }

    public static PoseidonKernel getInstance() {
        return INSTANCE;
    }

    public void markBootstrapped() {
        bootstrapped.set(true);
    }

    public boolean isBootstrapped() {
        return bootstrapped.get();
    }

    public <T> void registerService(Class<T> type, T service) {
        if (type == null) {
            throw new IllegalArgumentException("Service type cannot be null");
        }
        if (service == null) {
            throw new IllegalArgumentException("Service instance cannot be null");
        }
        services.put(type, service);
    }

    public <T> Optional<T> findService(Class<T> type) {
        Object value = services.get(type);
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(type.cast(value));
    }

    public <T> T getRequiredService(Class<T> type) {
        return findService(type).orElseThrow(() -> new IllegalStateException("Service not registered: " + type.getName()));
    }

    public void clear() {
        services.clear();
        bootstrapped.set(false);
    }
}
