package com.legacyminecraft.poseidon.entity;


public final class FireballStateBehaviour {
    private static final FireballStateBehaviour INSTANCE = new FireballStateBehaviour();

    private FireballStateBehaviour() {
    }

    public static FireballStateBehaviour getInstance() {
        return INSTANCE;
    }

    public DirectionVector computeDirectionFromNoisyVector(double noisyX, double noisyY, double noisyZ) {
        double magnitude = (double) com.legacyminecraft.compat.bukkit.MathHelper.a(noisyX * noisyX + noisyY * noisyY + noisyZ * noisyZ);
        return new DirectionVector(noisyX / magnitude * 0.1D, noisyY / magnitude * 0.1D, noisyZ / magnitude * 0.1D);
    }

    public void writeTileAndGroundState(NBTTagCompound nbt, int tileX, int tileY, int tileZ, int inTileId, int shake, boolean inGround) {
        nbt.a("xTile", (short) tileX);
        nbt.a("yTile", (short) tileY);
        nbt.a("zTile", (short) tileZ);
        nbt.a("inTile", (byte) inTileId);
        nbt.a("shake", (byte) shake);
        nbt.a("inGround", (byte) (inGround ? 1 : 0));
    }

    public LoadedTileState readTileAndGroundState(NBTTagCompound nbt) {
        int tileX = nbt.d("xTile");
        int tileY = nbt.d("yTile");
        int tileZ = nbt.d("zTile");
        int inTileId = nbt.c("inTile") & 255;
        int shake = nbt.c("shake") & 255;
        boolean inGround = nbt.c("inGround") == 1;
        return new LoadedTileState(tileX, tileY, tileZ, inTileId, shake, inGround);
    }

    public boolean onDamagedByEntity(EntityFireball fireball, Entity attacker) {
        if (attacker == null) {
            return false;
        }

        Vec3D attackVector = attacker.Z();
        if (attackVector != null) {
            fireball.motX = attackVector.a;
            fireball.motY = attackVector.b;
            fireball.motZ = attackVector.c;
            fireball.c = fireball.motX * 0.1D;
            fireball.d = fireball.motY * 0.1D;
            fireball.e = fireball.motZ * 0.1D;
        }

        return true;
    }

    public void writeTileAndGroundState(Object nbt, int tileX, int tileY, int tileZ, int inTileId, int shake, boolean inGround) {
        if (nbt == null) {
            return;
        }
        invokeWrite(nbt, "xTile", Short.valueOf((short) tileX));
        invokeWrite(nbt, "yTile", Short.valueOf((short) tileY));
        invokeWrite(nbt, "zTile", Short.valueOf((short) tileZ));
        invokeWrite(nbt, "inTile", Byte.valueOf((byte) inTileId));
        invokeWrite(nbt, "shake", Byte.valueOf((byte) shake));
        invokeWrite(nbt, "inGround", Byte.valueOf((byte) (inGround ? 1 : 0)));
    }

    public LoadedTileState readTileAndGroundState(Object nbt) {
        int tileX = invokeReadInt(nbt, "xTile");
        int tileY = invokeReadInt(nbt, "yTile");
        int tileZ = invokeReadInt(nbt, "zTile");
        int inTileId = invokeReadByte(nbt, "inTile") & 255;
        int shake = invokeReadByte(nbt, "shake") & 255;
        boolean inGround = invokeReadByte(nbt, "inGround") == 1;
        return new LoadedTileState(tileX, tileY, tileZ, inTileId, shake, inGround);
    }

    public boolean onDamagedByEntity(Object fireball, Object attacker) {
        if (fireball == null || attacker == null) {
            return false;
        }
        try {
            Object attackVector = attacker.getClass().getMethod("Z").invoke(attacker);
            if (attackVector != null) {
                double x = ((Number) attackVector.getClass().getField("a").get(attackVector)).doubleValue();
                double y = ((Number) attackVector.getClass().getField("b").get(attackVector)).doubleValue();
                double z = ((Number) attackVector.getClass().getField("c").get(attackVector)).doubleValue();
                fireball.getClass().getField("motX").setDouble(fireball, x);
                fireball.getClass().getField("motY").setDouble(fireball, y);
                fireball.getClass().getField("motZ").setDouble(fireball, z);
                fireball.getClass().getField("c").setDouble(fireball, x * 0.1D);
                fireball.getClass().getField("d").setDouble(fireball, y * 0.1D);
                fireball.getClass().getField("e").setDouble(fireball, z * 0.1D);
            }
            return true;
        } catch (ReflectiveOperationException ignored) {
            return false;
        }
    }

    private void invokeWrite(Object nbt, String key, Object value) {
        try {
            java.lang.reflect.Method[] methods = nbt.getClass().getMethods();
            for (int i = 0; i < methods.length; ++i) {
                java.lang.reflect.Method method = methods[i];
                if ("a".equals(method.getName()) && method.getParameterTypes().length == 2
                        && method.getParameterTypes()[0] == String.class) {
                    method.invoke(nbt, key, value);
                    return;
                }
            }
        } catch (ReflectiveOperationException ignored) {
        }
    }

    private int invokeReadInt(Object nbt, String key) {
        try {
            Object value = nbt.getClass().getMethod("d", String.class).invoke(nbt, key);
            return value instanceof Number ? ((Number) value).intValue() : 0;
        } catch (ReflectiveOperationException ignored) {
            return 0;
        }
    }

    private byte invokeReadByte(Object nbt, String key) {
        try {
            Object value = nbt.getClass().getMethod("c", String.class).invoke(nbt, key);
            return value instanceof Number ? ((Number) value).byteValue() : (byte) 0;
        } catch (ReflectiveOperationException ignored) {
            return (byte) 0;
        }
    }

    public static final class DirectionVector {
        public final double x;
        public final double y;
        public final double z;

        public DirectionVector(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static final class LoadedTileState {
        public final int tileX;
        public final int tileY;
        public final int tileZ;
        public final int inTileId;
        public final int shake;
        public final boolean inGround;

        public LoadedTileState(int tileX, int tileY, int tileZ, int inTileId, int shake, boolean inGround) {
            this.tileX = tileX;
            this.tileY = tileY;
            this.tileZ = tileZ;
            this.inTileId = inTileId;
            this.shake = shake;
            this.inGround = inGround;
        }
    }
}
