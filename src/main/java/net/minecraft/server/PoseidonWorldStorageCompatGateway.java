package net.minecraft.server;

import com.legacyminecraft.poseidon.world.WorldStorageCompatGateway;

import java.io.InputStream;
import java.io.OutputStream;

final class PoseidonWorldStorageCompatGateway implements WorldStorageCompatGateway {
    @Override
    public Object readCompressed(InputStream inputStream) {
        return CompressedStreamTools.a(inputStream);
    }

    @Override
    public void writeCompressed(Object rootTag, OutputStream outputStream) {
        CompressedStreamTools.a((NBTTagCompound) rootTag, outputStream);
    }

    @Override
    public Object extractDataTag(Object rootTag) {
        return ((NBTTagCompound) rootTag).k("Data");
    }

    @Override
    public Object createWorldData(Object dataTag) {
        return new WorldData((NBTTagCompound) dataTag);
    }

    @Override
    public Object createRootTag() {
        return new NBTTagCompound();
    }

    @Override
    public void setDataTag(Object rootTag, Object dataTag) {
        ((NBTTagCompound) rootTag).a("Data", (NBTBase) dataTag);
    }
}
