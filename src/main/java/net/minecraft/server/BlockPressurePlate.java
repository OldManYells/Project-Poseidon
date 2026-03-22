package net.minecraft.server;

import com.legacyminecraft.poseidon.block.PressurePlateStateBehaviour;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityInteractEvent;

import java.util.List;
import java.util.Random;

// CraftBukkit start
// CraftBukkit end

public class BlockPressurePlate extends Block {

    private EnumMobType a;
    private final PressurePlateStateBehaviour pressurePlateStateService = PressurePlateStateBehaviour.getInstance();

    protected BlockPressurePlate(int i, int j, EnumMobType enummobtype, Material material) {
        super(i, j, material);
        this.a = enummobtype;
        this.a(true);
        float f = 0.0625F;

        this.a(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
    }

    public int c() {
        return 20;
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return null;
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return pressurePlateStateService.hasSupportBelow(this.supportQuery(world), i, j, k);
    }

    public void c(World world, int i, int j, int k) {}

    public void doPhysics(World world, int i, int j, int k, int l) {
        if (pressurePlateStateService.shouldDropWithoutSupport(this.supportQuery(world), i, j, k)) {
            this.g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
        }
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (!world.isStatic) {
            if (pressurePlateStateService.shouldReevaluateFromScheduledTick(world.getData(i, j, k))) {
                this.g(world, i, j, k);
            }
        }
    }

    public void a(World world, int i, int j, int k, Entity entity) {
        if (!world.isStatic) {
            if (pressurePlateStateService.shouldReevaluateFromEntityTouch(world.getData(i, j, k))) {
                this.g(world, i, j, k);
            }
        }
    }

    private void g(World world, int i, int j, int k) {
        boolean flag = pressurePlateStateService.isPowered(world.getData(i, j, k));
        List list = pressurePlateStateService.collectEntities(
                world,
                this.a,
                pressurePlateStateService.createDetectionBox(i, j, k, 0.125F)
        );
        boolean flag1 = pressurePlateStateService.hasTriggeringEntities(list);

        // CraftBukkit start - Interact Pressure Plate
        org.bukkit.World bworld = world.getWorld();
        org.bukkit.plugin.PluginManager manager = world.getServer().getPluginManager();

        if (flag != flag1) {
            if (flag1) {
                for (Object object: list) {
                    if (object != null) {
                        org.bukkit.event.Cancellable cancellable;

                        if (object instanceof EntityHuman) {
                            cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman) object, org.bukkit.event.block.Action.PHYSICAL, i, j, k, -1, null);
                        } else if (object instanceof Entity) {
                            cancellable = new EntityInteractEvent(((Entity) object).getBukkitEntity(), bworld.getBlockAt(i, j, k));
                            manager.callEvent((EntityInteractEvent) cancellable);
                        } else {
                            continue;
                        }
                        if (cancellable.isCancelled()) {
                            return;
                        }
                    }
                }
            }

            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bworld.getBlockAt(i, j, k), flag ? 1 : 0, flag1 ? 1 : 0);
            manager.callEvent(eventRedstone);

            flag1 = eventRedstone.getNewCurrent() > 0;
        }
        // CraftBukkit end

        if (flag1 && !flag) {
            world.setData(i, j, k, pressurePlateStateService.toLegacyData(true));
            world.applyPhysics(i, j, k, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            world.b(i, j, k, i, j, k);
            world.makeSound((double) i + 0.5D, (double) j + 0.1D, (double) k + 0.5D, "random.click", 0.3F, 0.6F);
        }

        if (!flag1 && flag) {
            world.setData(i, j, k, pressurePlateStateService.toLegacyData(false));
            world.applyPhysics(i, j, k, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            world.b(i, j, k, i, j, k);
            world.makeSound((double) i + 0.5D, (double) j + 0.1D, (double) k + 0.5D, "random.click", 0.3F, 0.5F);
        }

        if (flag1) {
            world.c(i, j, k, this.id, this.c());
        }
    }

    public void remove(World world, int i, int j, int k) {
        int l = world.getData(i, j, k);

        if (l > 0) {
            world.applyPhysics(i, j, k, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
        }

        super.remove(world, i, j, k);
    }

    public void a(IBlockAccess iblockaccess, int i, int j, int k) {
        PressurePlateStateBehaviour.Bounds bounds =
                pressurePlateStateService.resolveVisualBounds(iblockaccess.getData(i, j, k));
        this.a(
                bounds.getMinX(),
                bounds.getMinY(),
                bounds.getMinZ(),
                bounds.getMaxX(),
                bounds.getMaxY(),
                bounds.getMaxZ()
        );
    }

    public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return pressurePlateStateService.isPowered(iblockaccess.getData(i, j, k));
    }

    public boolean d(World world, int i, int j, int k, int l) {
        return pressurePlateStateService.isPoweringSide(world.getData(i, j, k), l);
    }

    public boolean isPowerSource() {
        return true;
    }

    public int e() {
        return 1;
    }

    private PressurePlateStateBehaviour.SupportQuery supportQuery(final World world) {
        return new PressurePlateStateBehaviour.SupportQuery() {
            public boolean isBlockSolid(int x, int y, int z) {
                return world.e(x, y, z);
            }
        };
    }
}
