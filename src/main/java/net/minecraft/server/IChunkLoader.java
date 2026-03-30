package net.minecraft.server;

import org.bukkit.craftbukkit.server.Chunk;
import org.bukkit.craftbukkit.world.World;

import java.io.IOException;

public interface IChunkLoader {

    Chunk a(World world, int i, int j) throws IOException;

    void a(World world, Chunk chunk);

    void b(World world, Chunk chunk);

    void a();

    void b();
}
