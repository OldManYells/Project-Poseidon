package com.legacyminecraft.poseidon.entity;


public final class EntityListEntryBehaviour {
    private static final EntityListEntryBehaviour INSTANCE = new EntityListEntryBehaviour();

    private EntityListEntryBehaviour() {
    }

    public static EntityListEntryBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean equalsEntry(EntityListEntry self, Object other) {
        if (!(other instanceof EntityListEntry)) {
            return false;
        }

        EntityListEntry otherEntry = (EntityListEntry) other;
        Integer keyA = Integer.valueOf(self.poseidonGetSlot());
        Integer keyB = Integer.valueOf(otherEntry.poseidonGetSlot());

        if (keyA == keyB || keyA != null && keyA.equals(keyB)) {
            Object valueA = self.poseidonGetValue();
            Object valueB = otherEntry.poseidonGetValue();

            if (valueA == valueB || valueA != null && valueA.equals(valueB)) {
                return true;
            }
        }

        return false;
    }

    public int hashCode(EntityListEntry self) {
        return EntityList.poseidonHash(self.poseidonGetSlot());
    }

    public String stringify(EntityListEntry self) {
        return self.poseidonGetSlot() + "=" + self.poseidonGetValue();
    }
}
