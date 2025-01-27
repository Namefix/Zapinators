package com.namefix.item;

import com.namefix.entity.LaserProjectile;
import com.namefix.registry.EntityRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public abstract class AbstractLaserGunItem extends Item {
    protected int color = 0xFFFFFF;
    protected float baseDamage = 1.0f;
    protected float projectileSpeed = 1.0f;
    protected int itemCooldown = 20;
    protected float laserLength = 1.6f;
    protected float laserWidth = 0.15f;
    protected float laserHeight = 0.15f;
    protected boolean blockPiercing = false;
    protected boolean entityPiercing = true;
    protected SoundEvent shootSound;

    public AbstractLaserGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        if(!level.isClientSide) {
            LaserProjectile projectile = new LaserProjectile(EntityRegistry.LASER_PROJECTILE.get(), level);
            projectile.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
            Vec3 lookVec = player.getLookAngle();
            projectile.setDeltaMovement(
                    lookVec.x * projectileSpeed,
                    lookVec.y * projectileSpeed,
                    lookVec.z * projectileSpeed
            );
            projectile.setXRot(player.getXRot());
            projectile.setYRot(player.getYRot());
            projectile.setColor(color);
            projectile.setBaseDamage(baseDamage);
            projectile.setSize(new Vector3f(laserWidth, laserHeight, laserLength));
            projectile.setBlockPiercing(blockPiercing);
            projectile.setEntityPiercing(entityPiercing);
            level.addFreshEntity(projectile);
        }
        player.getCooldowns().addCooldown(this.arch$registryName(), itemCooldown);
        if(shootSound != null) player.level().playSound(null, player.getX(), player.getY(), player.getZ(), shootSound, SoundSource.PLAYERS);
        return InteractionResult.CONSUME;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.NONE;
    }
}
