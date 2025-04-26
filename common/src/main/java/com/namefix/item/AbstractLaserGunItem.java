package com.namefix.item;

import com.namefix.client.ZapinatorsClient;
import com.namefix.data.PlayerData;
import com.namefix.entity.LaserProjectile;
import com.namefix.enums.ZapinatorType;
import com.namefix.registry.EntityRegistry;
import com.namefix.registry.ItemRegistry;
import com.namefix.server.ZapinatorsServer;
import com.namefix.utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;

public abstract class AbstractLaserGunItem extends Item {
    protected int color = 0xFFFFFF;
    protected float baseDamage = 1.0f;
    protected float projectileSpeed = 0.5f;
    protected int itemCooldown = 20;
    protected float laserLength = 1.6f;
    protected float laserWidth = 0.15f;
    protected float laserHeight = 0.15f;
    protected boolean blockPiercing = false;
    protected boolean entityPiercing = true;
    protected int maxPiercing = 3;
    protected float manaCost = 1f;
    protected boolean meteoriteArmorSavesMana = false;
    protected ZapinatorType zapinatorType = ZapinatorType.NONE;
    protected SoundEvent shootSound;

    public AbstractLaserGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        boolean meteoriteSetBonus = Utils.getPlayerMeteoriteSetBonus(player);
        if(!meteoriteArmorSavesMana) meteoriteSetBonus = false;

        if(!player.isCreative() && !meteoriteSetBonus && manaCost > 0.0f) {
            if (level.isClientSide()) {
                if (ZapinatorsClient.mana < manaCost) return InteractionResultHolder.fail(player.getItemInHand(interactionHand));
            } else {
                PlayerData data = ZapinatorsServer.getPlayerData((ServerPlayer) player);
                if(data.mana < manaCost) return InteractionResultHolder.fail(player.getItemInHand(interactionHand));
            }
        }

        if(!level.isClientSide) {
            LaserProjectile projectile = new LaserProjectile(EntityRegistry.LASER_PROJECTILE.get(), level);

            Vec3 lookVec = player.getLookAngle();
            Vec3 offsetVec = interactionHand == InteractionHand.MAIN_HAND ? lookVec.cross(new Vec3(0, 1, 0)).normalize() : lookVec.cross(new Vec3(0, -1, 0)).normalize();
            double offsetAmount = 0.2;
            projectile.setPos(
                    player.getX() + offsetVec.x * offsetAmount,
                    player.getEyeY() - 0.1 + offsetVec.y * offsetAmount,
                    player.getZ() + offsetVec.z * offsetAmount
            );

            double trajectoryCorrection = 0.07;
            Vec3 adjustedLookVec = lookVec.add(offsetVec.scale(-trajectoryCorrection * offsetAmount));
            projectile.setDeltaMovement(
                    adjustedLookVec.x * projectileSpeed,
                    adjustedLookVec.y * projectileSpeed,
                    adjustedLookVec.z * projectileSpeed
            );

            projectile.setOwner(player);
            projectile.setXRot(player.getXRot());
            projectile.setYRot(player.getYRot());
            projectile.setColor(color);
            projectile.setBaseDamage(baseDamage);
            projectile.setSize(new Vector3f(laserWidth, laserHeight, laserLength));
            projectile.setBlockPiercing(blockPiercing);
            projectile.setEntityPiercing(entityPiercing);
            projectile.setMaxPiercing(maxPiercing);
            projectile.setZapinatorType(zapinatorType);
            level.addFreshEntity(projectile);
        }

        if(!player.isCreative() && manaCost > 0.0f && !meteoriteSetBonus) {
            if(level.isClientSide()) {
                ZapinatorsClient.decreaseMana(manaCost, true);
            } else {
                ZapinatorsServer.decreaseMana((ServerPlayer) player, manaCost, true);
            }
        }

        player.getCooldowns().addCooldown(this, itemCooldown);
        if(shootSound != null) player.level().playSound(null, player.getX(), player.getY(), player.getZ(), shootSound, SoundSource.PLAYERS);
        return InteractionResultHolder.consume(player.getItemInHand(interactionHand));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.NONE;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        if (manaCost > 0.0f) {
            String manaFormatted = (manaCost == (int) manaCost)
                ? String.valueOf((int) manaCost)
                : String.format("%.1f", manaCost);
            list.add(Component.translatable("item.zapinators.description.mana_usage", manaFormatted).withStyle(ChatFormatting.BLUE));
        }

        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }
}
