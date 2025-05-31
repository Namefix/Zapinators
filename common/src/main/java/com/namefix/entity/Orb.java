package com.namefix.entity;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Orb extends AbstractHurtingProjectile {
	private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(Orb.class, EntityDataSerializers.INT);
	float orbSpeed;
	float baseDamage;
	int despawnTicks = 200;
	boolean hasGravity = false;
	int piercingLeft = 0;
	boolean fireChance = false;

	public Orb(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public void tick() {
		if(despawnTicks <= 0 || this.horizontalCollision || this.verticalCollision) {
			this.discard();
			return;
		}

		Vec3 particlePos = this.position().add(this.getDeltaMovement());
		this.level().addParticle(new DustParticleOptions(this.entityData.get(COLOR), 1.51337f), particlePos.x, particlePos.y+(this.getBbHeight()/2), particlePos.z, 1.0D, 1.0D, 1.0D);

		Vec3 motion = this.getDeltaMovement();
		if (this.hasGravity) {
			motion = motion.add(0, -0.015, 0);
			this.setDeltaMovement(motion);
		}
		this.move(MoverType.SELF, motion);

		if(!this.level().isClientSide) {
			List<Entity> collidingEntities = this.level().getEntities(this, this.getBoundingBox().inflate(0.1), e -> e != this && e != this.getOwner() && e.isAlive());
			for (Entity entity : collidingEntities) {
				if (entity instanceof LivingEntity living && !living.isInvulnerableTo((ServerLevel) this.level(), damageSources().mobAttack(living))) {
					if(entity.invulnerableTime > 0) continue;
					entity.hurtServer((ServerLevel) this.level(), damageSources().mobAttack((LivingEntity) this.getOwner()), baseDamage);
					entity.invulnerableTime = 5;

					if(fireChance) {
						RandomSource rand = this.level().random;
						int roll1 = rand.nextFloat() < 0.83 ? rand.nextInt(1, 3) : 0;
						int roll2 = rand.nextFloat() < 0.17 ? rand.nextInt(1, 3) : 0;
						entity.setRemainingFireTicks((roll1+roll2)*20);
					}

					if(piercingLeft <= 0) this.discard();
					piercingLeft--;
					return;
				}
			}
		}

		despawnTicks--;
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	@Override
	protected @Nullable ParticleOptions getTrailParticle() {
		return null;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(COLOR, 0xFFFFFF);
		builder.build();
	}

	public void setColor(int color) {
		this.entityData.set(COLOR, color);
	}
	public int getColor() {
		return this.entityData.get(COLOR);
	}
	public void setOrbSpeed(float orbSpeed) {
		this.orbSpeed = orbSpeed;
	}
	public float getOrbSpeed() {
		return orbSpeed;
	}
	public void setBaseDamage(float baseDamage) {
		this.baseDamage = baseDamage;
	}
	public float getBaseDamage() {
		return baseDamage;
	}
	public void setHasGravity(boolean hasGravity) {
		this.hasGravity = hasGravity;
	}
	public boolean getHasGravity() {
		return hasGravity;
	}
	public void setDespawnTicks(int despawnTicks) {
		this.despawnTicks = despawnTicks;
	}
	public void setPiercingLeft(int piercingLeft) {
		this.piercingLeft = piercingLeft;
	}
	public void setFireChance(boolean fireChance) {
		this.fireChance = fireChance;
	}
}
