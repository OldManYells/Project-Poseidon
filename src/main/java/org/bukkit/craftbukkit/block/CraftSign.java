package org.bukkit.craftbukkit.block;

import com.legacyminecraft.compat.bukkit.TileEntityBlockStateUpdateBehaviour;
import com.legacyminecraft.compat.bukkit.TileEntityLookupBehaviour;
import com.legacyminecraft.compat.bukkit.SignLineAccessBehaviour;
import net.minecraft.server.TileEntitySign;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class CraftSign extends CraftBlockState implements Sign {
    private static final TileEntityBlockStateUpdateBehaviour TILE_ENTITY_BLOCK_STATE_UPDATE_BEHAVIOUR =
            TileEntityBlockStateUpdateBehaviour.getInstance();
    private static final TileEntityLookupBehaviour TILE_ENTITY_LOOKUP_BEHAVIOUR =
            TileEntityLookupBehaviour.getInstance();
    private static final SignLineAccessBehaviour SIGN_LINE_ACCESS_BEHAVIOUR =
            SignLineAccessBehaviour.getInstance();

    private final TileEntitySign sign;

    public CraftSign(final Block block) {
        super(block);
        sign = TILE_ENTITY_LOOKUP_BEHAVIOUR.resolveSign(block, getX(), getY(), getZ());
    }

    public String[] getLines() {
        return SIGN_LINE_ACCESS_BEHAVIOUR.getLines(sign);
    }

    public String getLine(int index) throws IndexOutOfBoundsException {
        return SIGN_LINE_ACCESS_BEHAVIOUR.getLine(sign, index);
    }

    public void setLine(int index, String line) throws IndexOutOfBoundsException {
        SIGN_LINE_ACCESS_BEHAVIOUR.setLine(sign, index, line);
    }

    @Override
    public boolean update(boolean force) {
        return TILE_ENTITY_BLOCK_STATE_UPDATE_BEHAVIOUR.finalizeUpdate(super.update(force), sign);
    }
}
