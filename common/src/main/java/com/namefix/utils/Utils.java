package com.namefix.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class Utils {
    public static void rotateMotionXZ(Entity projectile, float degrees) {
        Vec3 motion = projectile.getDeltaMovement();
        float angle = (float) Math.toRadians(degrees);

        double x = motion.x * Math.cos(angle) - motion.z * Math.sin(angle);
        double z = motion.x * Math.sin(angle) + motion.z * Math.cos(angle);

        projectile.setDeltaMovement(x, motion.y, z);
    }

    public static void rotateMotionY(Entity ent, float degrees) {
        Vec3 motion = ent.getDeltaMovement();
        float angle = (float) Math.toRadians(degrees);

        double y = motion.y * Math.cos(angle) - motion.z * Math.sin(angle);
        double z = motion.y * Math.sin(angle) + motion.z * Math.cos(angle);

        ent.setDeltaMovement(motion.x, y, z);
    }

    public static void mirrorVertical(Entity ent) {
        Vec3 motion = ent.getDeltaMovement();
        ent.setDeltaMovement(motion.x, -motion.y, motion.z);
    }

    public static void mirrorHorizontal(Entity ent) {
        Vec3 motion = ent.getDeltaMovement();
        ent.setDeltaMovement(-motion.x, motion.y, -motion.z);
    }

    public static void slowdownEntity(Entity ent, float percentage) {
        float reduction = 1.0f - (percentage / 100.0f);
        Vec3 motion = ent.getDeltaMovement();
        ent.setDeltaMovement(motion.x * reduction, motion.y * reduction, motion.z * reduction);
    }

    public static void applyKnockback(LivingEntity target, Entity projectile, double knockbackPower) {
        target.knockback(knockbackPower, projectile.getX()-target.getX(), projectile.getZ()-target.getZ());
        target.hurtMarked = true;
    }
}
