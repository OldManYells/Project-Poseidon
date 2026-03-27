package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behaviour for CraftBukkit projectile shooter conversion and assignment flow.
 */
public final class ProjectileShooterAssignmentBehaviour {
    private static final ProjectileShooterAssignmentBehaviour INSTANCE = new ProjectileShooterAssignmentBehaviour();

    private ProjectileShooterAssignmentBehaviour() {
    }

    public static ProjectileShooterAssignmentBehaviour getInstance() {
        return INSTANCE;
    }

    public Object toBukkitShooter(Object shooterEntity, ShooterProjection projection) {
        return projection.toBukkitShooter(shooterEntity);
    }

    public void assignShooter(Object shooter, ShooterConversion conversion, ShooterAssignment assignment) {
        Object shooterEntity = conversion.toNmsShooter(shooter);
        if (shooterEntity != null) {
            assignment.assign(shooterEntity);
        }
    }

    public interface ShooterProjection {
        Object toBukkitShooter(Object shooterEntity);
    }

    public interface ShooterConversion {
        Object toNmsShooter(Object shooter);
    }

    public interface ShooterAssignment {
        void assign(Object shooterEntity);
    }
}
