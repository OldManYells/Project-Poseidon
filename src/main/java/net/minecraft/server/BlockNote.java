package net.minecraft.server;

import com.legacyminecraft.poseidon.block.NoteBlockStateBehaviour;

public class BlockNote extends BlockContainer {
    private final NoteBlockStateBehaviour noteBlockStateService = NoteBlockStateBehaviour.getInstance();

    public BlockNote(int i) {
        super(i, 74, Material.WOOD);
    }

    public int a(int i) {
        return this.textureId;
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (noteBlockStateService.shouldHandleNeighborPowerUpdate(l, l > 0 && Block.byId[l].isPowerSource())) {
            boolean flag = world.isBlockPowered(i, j, k);
            TileEntityNote tileentitynote = (TileEntityNote) world.getTileEntity(i, j, k);

            if (noteBlockStateService.hasPowerStateChanged(tileentitynote.b, flag)) {
                if (noteBlockStateService.shouldPlayOnPowerChange(flag)) {
                    tileentitynote.play(world, i, j, k);
                }

                tileentitynote.b = flag;
            }
        }
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        if (noteBlockStateService.shouldIgnoreClientInteraction(world.isStatic)) {
            return true;
        } else {
            TileEntityNote tileentitynote = (TileEntityNote) world.getTileEntity(i, j, k);

            tileentitynote.a();
            tileentitynote.play(world, i, j, k);
            return true;
        }
    }

    public void b(World world, int i, int j, int k, EntityHuman entityhuman) {
        if (!world.isStatic) {
            TileEntityNote tileentitynote = (TileEntityNote) world.getTileEntity(i, j, k);

            tileentitynote.play(world, i, j, k);
        }
    }

    protected TileEntity a_() {
        return new TileEntityNote();
    }

    public void a(World world, int i, int j, int k, int l, int i1) {
        float f = noteBlockStateService.resolvePitchFromNoteValue(i1);
        String s = noteBlockStateService.resolveInstrumentName(l);

        world.makeSound(
                noteBlockStateService.resolveCenteredCoordinate(i),
                noteBlockStateService.resolveCenteredCoordinate(j),
                noteBlockStateService.resolveCenteredCoordinate(k),
                noteBlockStateService.resolveSoundEffectName(s),
                3.0F,
                f
        );
        world.a(
                "note",
                noteBlockStateService.resolveCenteredCoordinate(i),
                noteBlockStateService.resolveNoteParticleY(j),
                noteBlockStateService.resolveCenteredCoordinate(k),
                noteBlockStateService.resolveNoteParticleData(i1),
                0.0D,
                0.0D
        );
    }
}
