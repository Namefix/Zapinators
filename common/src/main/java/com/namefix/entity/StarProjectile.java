package com.namefix.entity;

import com.namefix.registry.ItemRegistry;
import com.namefix.registry.SoundRegistry;
import com.namefix.utils.Utils;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class StarProjectile extends AbstractHurtingProjectile {
	public StarProjectile(EntityType<? extends StarProjectile> entityType, Level level) {
		super(entityType, level);
	}

	private float baseDamage = 15.0f;
	private boolean hitEntity = false;
	public boolean shotFromCreative = false;

	@Override
	public void tick() {
		if (Utils.shouldProjectileForceRemove(this)) {
			this.discard();
			return;
		}
		if(this.horizontalCollision || this.verticalCollision) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundRegistry.FALLEN_STAR_HIT.get(), SoundSource.BLOCKS);
			if(!hitEntity && !shotFromCreative && !this.level().isClientSide) {
				ItemStack dropStack = new ItemStack(ItemRegistry.FALLEN_STAR.get());
				ItemEntity drop = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), dropStack);
				this.level().addFreshEntity(drop);
			}
			this.discard();
			return;
		}

		this.level().addParticle(new DustParticleOptions(Utils.getRandomBrightColor(this.random), 1.069f), this.getX(), this.getY()+0.2, this.getZ(), 0.0D, 0.0D, 0.0D);

		Vec3 motion = this.getDeltaMovement();
		this.move(MoverType.SELF, motion);

		if(!this.level().isClientSide) {
			List<Entity> collidingEntities = this.level().getEntities(this, this.getBoundingBox().inflate(0.1), e -> e != this && e != this.getOwner() && e.isAlive());
			for (Entity entity : collidingEntities) {
				if (
						entity instanceof LivingEntity living &&
						!living.isInvulnerableTo((ServerLevel) this.level(), damageSources().mobAttack(living))
				) {
					if(!entity.isOnFire() && entity.invulnerableTime > 0) return;
					float damage = entity instanceof Player ? baseDamage/4 : baseDamage;
					if(this.getOwner() != null) {
						if(!Utils.canEntityDamageEntity(Objects.requireNonNull(this.getOwner()), entity)) continue;
						entity.hurtServer((ServerLevel) this.level(), damageSources().mobAttack((LivingEntity) this.getOwner()), damage);
					} else {
						entity.hurtServer((ServerLevel) this.level(), damageSources().magic(), damage);
					}

					entity.invulnerableTime = 2;
					hitEntity = true;
				}
			}
		}
	}

	@Override
	public boolean shouldBeSaved() {
		return false;
	}

	@Override
	public void checkDespawn() {
		if (Utils.shouldProjectileForceRemove(this)) {
			this.discard();
			return;
		}
		super.checkDespawn();
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	@Override
	protected @Nullable ParticleOptions getTrailParticle() {
		return null;
	}

	public float getBaseDamage() {
		return baseDamage;
	}

	public void setBaseDamage(float baseDamage) {
		this.baseDamage = baseDamage;
	}
}
