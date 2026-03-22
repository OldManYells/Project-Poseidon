package net.minecraft.server;

import com.legacyminecraft.poseidon.block.WebStateBehaviour;

import java.util.Random;

public class BlockWeb extends Block {
    private final WebStateBehaviour webStateService = WebStateBehaviour.getInstance();

    public BlockWeb(int i, int j) {
        super(i, j, Material.WEB);
    }

    public void a(World world, int i, int j, int k, Entity entity) {
        webStateService.applyEntanglement(entity);
    }

    public boolean a() {
        return false;
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return null;
    }

    public boolean b() {
        return false;
    }

    public int a(int i, Random random) {
        return webStateService.resolveDropItemId(Item.STRING.id);
    }
}
