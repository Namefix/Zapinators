package com.namefix.utils;

import com.namefix.item.MeteoriteArmorItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.concurrent.atomic.AtomicInteger;

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

    public static void removeOneItem(Player player, Item item) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack slotStack = player.getInventory().getItem(i);
            if (!slotStack.isEmpty() && slotStack.getItem() == item) { // Match item
                slotStack.shrink(1);
                if (slotStack.isEmpty()) {
                    player.getInventory().setItem(i, ItemStack.EMPTY);
                }
                break;
            }
        }
    }

    public static int getPlayerAmmoSaveChance(Player player) {
        AtomicInteger total = new AtomicInteger();

        var playerArmor = player.getArmorSlots();
        playerArmor.forEach((piece) -> {
            if(piece.getItem() instanceof MeteoriteArmorItem armor) {
                total.addAndGet(armor.getSavePercent());
            }
        });

        return total.get();
    }
}
