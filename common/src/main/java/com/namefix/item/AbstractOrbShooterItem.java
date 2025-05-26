package com.namefix.item;

import com.namefix.entity.Orb;
import com.namefix.registry.EntityRegistry;
import com.namefix.registry.SoundRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractOrbShooterItem extends AbstractManaItem {
	float baseDamage;
	int itemCooldown;
	int color;
	float orbSpeed = 0.5f;
	boolean gravity = false;
	int orbDespawnTick = 200;
	SoundEvent shootSound;

	public AbstractOrbShooterItem(Properties properties, float manaCost, float baseDamage, int itemCooldown, int color) {
		super(properties);
		this.manaCost = manaCost;
		this.baseDamage = baseDamage;
		this.itemCooldown = itemCooldown;
		this.color = color;
		this.shootSound = getShootSound();
	}

	@Override
	public @NotNull InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
		if(!canPlayerUseManaItem(level, player)) return InteractionResult.FAIL;

		if(!level.isClientSide) {
			Vec3 lookVec = player.getLookAngle();
			Vec3 offsetVec = interactionHand == InteractionHand.MAIN_HAND ? lookVec.cross(new Vec3(0, 1, 0)).normalize() : lookVec.cross(new Vec3(0, -1, 0)).normalize();
			double offsetAmount = 0.2;
			Orb orb = new Orb(EntityRegistry.ORB.get(), level);
			orb.setPos(
					player.getX() + offsetVec.x * offsetAmount,
					player.getEyeY() - 0.1 + offsetVec.y * offsetAmount,
					player.getZ() + offsetVec.z * offsetAmount
			);

			double trajectoryCorrection = 0.07;
			Vec3 adjustedLookVec = lookVec.add(offsetVec.scale(-trajectoryCorrection * offsetAmount));

			orb.setDeltaMovement(
					adjustedLookVec.x * this.orbSpeed,
					adjustedLookVec.y * this.orbSpeed,
					adjustedLookVec.z * this.orbSpeed
			);
			orb.setOrbSpeed(this.orbSpeed);
			orb.setColor(this.color);
			orb.setOwner(player);
			orb.setXRot(player.getXRot());
			orb.setYRot(player.getYRot());
			orb.setHasGravity(this.gravity);
			orb.setBaseDamage(baseDamage);
			orb.setDespawnTicks(orbDespawnTick);
			level.addFreshEntity(orb);
		}

		useManaItem(level, player, meteoriteArmorSavesMana);
		player.getCooldowns().addCooldown(player.getItemInHand(interactionHand), itemCooldown);
		if(shootSound != null) player.level().playSound(null, player.getX(), player.getY(), player.getZ(), shootSound, SoundSource.PLAYERS);
		return InteractionResult.SUCCESS;
	}

	protected SoundEvent getShootSound() {
		return SoundRegistry.GEM_STAFF_SHOOT.getOrNull();
	}
}
