package org.bukkit.craftbukkit.block;

import com.legacyminecraft.poseidon.compat.bukkit.TileEntityBlockStateUpdateBehaviour;
import net.minecraft.server.TileEntitySign;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.CraftWorld;

public class CraftSign extends CraftBlockState implements Sign {
    private static final TileEntityBlockStateUpdateBehaviour TILE_ENTITY_BLOCK_STATE_UPDATE_BEHAVIOUR =
            TileEntityBlockStateUpdateBehaviour.getInstance();

    private final TileEntitySign sign;

    public CraftSign(final Block block) {
        super(block);

        CraftWorld world = (CraftWorld) block.getWorld();
        sign = (TileEntitySign) world.getTileEntityAt(getX(), getY(), getZ());
    }

    public String[] getLines() {
        return sign.lines;
    }

    public String getLine(int index) throws IndexOutOfBoundsException {
        return sign.lines[index];
    }

    public void setLine(int index, String line) throws IndexOutOfBoundsException {
        sign.lines[index] = line;
    }

    @Override
    public boolean update(boolean force) {
        return TILE_ENTITY_BLOCK_STATE_UPDATE_BEHAVIOUR.finalizeUpdate(super.update(force), sign);
    }
}
