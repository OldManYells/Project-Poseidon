package com.legacyminecraft.poseidon.nbt;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Canonical registry and codec flow for legacy NBT tag type resolution.
 *
 * @deprecated Use {@link NbtTypeRegistry}. Kept as a binary-compatible forwarding shim.
 */
@Deprecated
public final class NbtTypeRegistryService {
    private static final NbtTypeRegistryService INSTANCE = new NbtTypeRegistryService();
    private final NbtTypeRegistry registry = NbtTypeRegistry.getInstance();

    private NbtTypeRegistryService() {
    }

    public static NbtTypeRegistryService getInstance() {
        return INSTANCE;
    }

    public NBTBase readNamedTag(DataInput input) throws IOException {
        return registry.readNamedTag(input);
    }

    public void writeNamedTag(NBTBase tag, DataOutput output) throws IOException {
        registry.writeNamedTag(tag, output);
    }

    public NBTBase create(byte typeId) {
        return registry.create(typeId);
    }

    public String typeName(byte typeId) {
        return registry.typeName(typeId);
    }
}
