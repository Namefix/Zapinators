package com.namefix.entity;

import com.namefix.item.BeeGunItem;
import com.namefix.utils.Utils;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AngryBee extends AbstractHurtingProjectile {
	public enum BeeSource {
		BEE_GUN,
		BEE_ARMOR
	}

	public double beeSpeed = BeeGunItem.BEE_SPEED;
	public BeeSource beeSource = BeeSource.BEE_GUN;
	int blockBounces = 3;
	int enemyHits = 3;
	public float baseDamage = 0.5f;

	double homingRadius = 8;

	public AngryBee(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public void tick() {
		if(!this.level().isClientSide && (this.tickCount > 200 || this.isInLiquid())) {
			((ServerLevel)this.level()).sendParticles(new DustParticleOptions(Vec3.fromRGB24(0xbfbfbf).toVector3f(), 1.0f), this.getX(), this.getY(), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0);
			this.discard();
			return;
		}

		double nearestDistance = Double.MAX_VALUE;
		Entity nearestEntity = null;

		if(this.beeSource.equals(BeeSource.BEE_ARMOR) || this.tickCount > 5) {
			for (Entity entity : level().getEntities(this, getBoundingBox().inflate(homingRadius),
					e -> e.isAlive() &&
							!(e instanceof AngryBee) &&
							e != this.getOwner() &&
							e instanceof Mob mob &&
							(mob.isAggressive() || e instanceof Monster)
			)) {
				double dist = this.distanceToSqr(entity);
				if (dist < nearestDistance) {
					nearestDistance = dist;
					nearestEntity = entity;
				}
			}

			if (nearestEntity != null) {
				double targetCenterY = nearestEntity.getY() + nearestEntity.getBbHeight() * 0.5;
				Vec3 targetCenter = new Vec3(
						nearestEntity.getX(),
						targetCenterY,
						nearestEntity.getZ()
				);
				Vec3 toTarget = targetCenter.subtract(this.position()).normalize();
				Vec3 currentVelocity = this.getDeltaMovement();
				double speed = currentVelocity.length();

				if (speed > 0.0001) {
					double turnRate = 0.4;
					Vec3 wantedVelocity = toTarget.scale(speed);
					Vec3 newVelocity = currentVelocity.scale(1.0 - turnRate).add(wantedVelocity.scale(turnRate));
					this.setDeltaMovement(newVelocity.normalize().scale(beeSpeed));
				}
			}
		}

		if (!this.level().isClientSide) {
			BlockHitResult blockHitResult = this.level().clip(new ClipContext(
					this.position(),
					this.position().add(this.getDeltaMovement()),
					ClipContext.Block.COLLIDER,
					ClipContext.Fluid.NONE,
					this
			));
			if (blockHitResult.getType() == HitResult.Type.BLOCK) {
				this.onHitBlock(blockHitResult);
			}

			EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(
					level(),
					this,
					position(),
					position().add(getDeltaMovement()),
					getBoundingBox().expandTowards(getDeltaMovement()).inflate(0.2),
					entity -> !entity.isSpectator() && entity.isAlive() && !(entity instanceof AngryBee)
			);
			if (entityHitResult != null) {
				this.onHitEntity(entityHitResult);
			} else {
				for (Entity entity : level().getEntities(this, getBoundingBox().inflate(0.1),
						e -> !e.isSpectator() &&
						e.isAlive() &&
						!(e instanceof AngryBee) &&
						e != this.getOwner() &&
						((e instanceof Mob mob && mob.isAggressive()) || (e instanceof Monster))
				)) {
					this.onHitEntity(new EntityHitResult(entity));
					break;
				}
			}
		}

		Vec3 motion = this.getDeltaMovement();
		this.move(MoverType.SELF, motion);
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		if(!level().isClientSide) {
			if (blockBounces <= 0) {
				this.discard();
				return;
			}
		}

		Direction dir = blockHitResult.getDirection();
		Vec3 normal = new Vec3(dir.getStepX(), dir.getStepY(), dir.getStepZ());
		Vec3 velocity = this.getDeltaMovement();
		Vec3 reflected = velocity.subtract(normal.scale(2 * velocity.dot(normal)));
		Vec3 newVelocity = reflected.normalize().scale(beeSpeed);
		this.setDeltaMovement(newVelocity);

		blockBounces--;
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		if(level().isClientSide) return;
		Entity target = entityHitResult.getEntity();
		if(target.invulnerableTime > 0) return;
		if(this.getOwner() != null) {
			if(target.getUUID().equals(this.getOwner().getUUID()) || !Utils.canEntityDamageEntity(Objects.requireNonNull(this.getOwner()), target)) return;
			target.hurt(damageSources().playerAttack((Player) this.getOwner()), baseDamage);
		} else {
			if(target instanceof LivingEntity)
				target.hurt(damageSources().sting((LivingEntity) target), baseDamage);
			else
				target.hurt(damageSources().generic(), baseDamage);
		}

		target.invulnerableTime = 2;
		enemyHits--;
		if(enemyHits <= 0) this.discard();
	}

	@Override
	protected @Nullable ParticleOptions getTrailParticle() {
		return null;
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}
}
