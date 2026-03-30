package org.bukkit.craftbukkit.entity;

import net.minecraft.server.*;
import org.bukkit.craftbukkit.item.Item;
import org.bukkit.craftbukkit.item.ItemStack;
import org.bukkit.craftbukkit.world.World;
import org.bukkit.event.painting.PaintingBreakByEntityEvent;
import org.bukkit.event.painting.PaintingBreakByWorldEvent;

import java.util.ArrayList;
import java.util.List;

// CraftBukkit start
// CraftBukkit end

public class EntityPainting extends Entity {

    private int checkCounter;
    public int hangingDirection;
    public int hangingX;
    public int hangingY;
    public int hangingZ;
    public EnumArt art;

    public EntityPainting(World world) {
        super(world);
        this.checkCounter = 0;
        this.hangingDirection = 0;
        this.height = 0.0F;
        this.b(0.5F, 0.5F);
    }

    public EntityPainting(World world, int i, int j, int k, int l) {
        this(world);
        this.hangingX = i;
        this.hangingY = j;
        this.hangingZ = k;
        ArrayList arraylist = new ArrayList();
        EnumArt[] aenumart = EnumArt.values();
        int i1 = aenumart.length;

        for (int j1 = 0; j1 < i1; ++j1) {
            EnumArt enumart = aenumart[j1];

            this.art = enumart;
            this.setDirection(l);
            if (this.isPositionValid()) {
                arraylist.add(enumart);
            }
        }

        if (arraylist.size() > 0) {
            this.art = (EnumArt) arraylist.get(this.random.nextInt(arraylist.size()));
        }

        this.setDirection(l);
    }

    protected void b() {}

    public void setDirection(int i) {
        this.hangingDirection = i;
        this.lastYaw = this.yaw = (float) (i * 90);
        float f = (float) this.art.B;
        float f1 = (float) this.art.C;
        float f2 = (float) this.art.B;

        if (i != 0 && i != 2) {
            f = 0.5F;
        } else {
            f2 = 0.5F;
        }

        f /= 32.0F;
        f1 /= 32.0F;
        f2 /= 32.0F;
        float f3 = (float) this.hangingX + 0.5F;
        float f4 = (float) this.hangingY + 0.5F;
        float f5 = (float) this.hangingZ + 0.5F;
        float f6 = 0.5625F;

        if (i == 0) {
            f5 -= f6;
        }

        if (i == 1) {
            f3 -= f6;
        }

        if (i == 2) {
            f5 += f6;
        }

        if (i == 3) {
            f3 += f6;
        }

        if (i == 0) {
            f3 -= this.getArtOffset(this.art.B);
        }

        if (i == 1) {
            f5 += this.getArtOffset(this.art.B);
        }

        if (i == 2) {
            f3 += this.getArtOffset(this.art.B);
        }

        if (i == 3) {
            f5 -= this.getArtOffset(this.art.B);
        }

        f4 += this.getArtOffset(this.art.C);
        this.setPosition((double) f3, (double) f4, (double) f5);
        float f7 = -0.00625F;

        this.boundingBox.c((double) (f3 - f - f7), (double) (f4 - f1 - f7), (double) (f5 - f2 - f7), (double) (f3 + f + f7), (double) (f4 + f1 + f7), (double) (f5 + f2 + f7));
    }

    private float getArtOffset(int i) {
        return i == 32 ? 0.5F : (i == 64 ? 0.5F : 0.0F);
    }

    public void m_() {
        if (this.checkCounter++ == 100 && !this.world.isStatic) {
            this.checkCounter = 0;
            if (!this.isPositionValid()) {
                // CraftBukkit start
                PaintingBreakByWorldEvent event = new PaintingBreakByWorldEvent((org.bukkit.entity.Painting) this.getBukkitEntity());
                this.world.getServer().getPluginManager().callEvent(event);

                if (event.isCancelled()) {
                    return;
                }
                // CraftBukkit end

                this.die();
                this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(org.bukkit.craftbukkit.item.Item.PAINTING)));
            }
        }
    }

    public boolean isPositionValid() {
        if (this.world.getEntities(this, this.boundingBox).size() > 0) {
            return false;
        } else {
            int i = this.art.B / 16;
            int j = this.art.C / 16;
            int k = this.hangingX;
            int l = this.hangingY;
            int i1 = this.hangingZ;

            if (this.hangingDirection == 0) {
                k = MathHelper.floor(this.locX - (double) ((float) this.art.B / 32.0F));
            }

            if (this.hangingDirection == 1) {
                i1 = MathHelper.floor(this.locZ - (double) ((float) this.art.B / 32.0F));
            }

            if (this.hangingDirection == 2) {
                k = MathHelper.floor(this.locX - (double) ((float) this.art.B / 32.0F));
            }

            if (this.hangingDirection == 3) {
                i1 = MathHelper.floor(this.locZ - (double) ((float) this.art.B / 32.0F));
            }

            l = MathHelper.floor(this.locY - (double) ((float) this.art.C / 32.0F));

            int j1;

            for (int k1 = 0; k1 < i; ++k1) {
                for (j1 = 0; j1 < j; ++j1) {
                    Material material;

                    if (this.hangingDirection != 0 && this.hangingDirection != 2) {
                        material = this.world.getMaterial(this.hangingX, l + j1, i1 + k1);
                    } else {
                        material = this.world.getMaterial(k + k1, l + j1, this.hangingZ);
                    }

                    if (!material.isBuildable()) {
                        return false;
                    }
                }
            }

            List list = this.world.b((Entity) this, this.boundingBox);

            for (j1 = 0; j1 < list.size(); ++j1) {
                if (list.get(j1) instanceof EntityPainting) {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean l_() {
        return true;
    }

    public boolean damageEntity(Entity entity, int i) {
        if (!this.dead && !this.world.isStatic) {
            // CraftBukkit start
            PaintingBreakByEntityEvent event = new PaintingBreakByEntityEvent((org.bukkit.entity.Painting) this.getBukkitEntity(), entity == null ? null : entity.getBukkitEntity());
            this.world.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return true;
            }
            // CraftBukkit end

            this.die();
            this.af();
            this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(org.bukkit.craftbukkit.item.Item.PAINTING)));
        }

        return true;
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("Dir", (byte) this.hangingDirection);
        nbttagcompound.setString("Motive", this.art.A);
        nbttagcompound.a("TileX", this.hangingX);
        nbttagcompound.a("TileY", this.hangingY);
        nbttagcompound.a("TileZ", this.hangingZ);
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.hangingDirection = nbttagcompound.c("Dir");
        this.hangingX = nbttagcompound.e("TileX");
        this.hangingY = nbttagcompound.e("TileY");
        this.hangingZ = nbttagcompound.e("TileZ");
        String s = nbttagcompound.getString("Motive");
        EnumArt[] aenumart = EnumArt.values();
        int i = aenumart.length;

        for (int j = 0; j < i; ++j) {
            EnumArt enumart = aenumart[j];

            if (enumart.A.equals(s)) {
                this.art = enumart;
            }
        }

        if (this.art == null) {
            this.art = EnumArt.KEBAB;
        }

        this.setDirection(this.hangingDirection);
    }

    public void a(double d0, double d1, double d2) {
        if (!this.world.isStatic && d0 * d0 + d1 * d1 + d2 * d2 > 0.0D) {
            this.die();
            this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(org.bukkit.craftbukkit.item.Item.PAINTING)));
        }
    }

    public void b(double d0, double d1, double d2) {
        if (!this.world.isStatic && d0 * d0 + d1 * d1 + d2 * d2 > 0.0D) {
            this.die();
            this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Item.PAINTING)));
        }
    }
}
