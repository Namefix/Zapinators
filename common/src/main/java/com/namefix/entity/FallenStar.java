package com.namefix.entity;

import com.namefix.config.ZapinatorsConfig;
import com.namefix.registry.ItemRegistry;
import com.namefix.registry.SoundRegistry;
import com.namefix.utils.Utils;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class FallenStar extends Entity {
	public FallenStar(EntityType<? extends FallenStar> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public void tick() {
		super.tick();
		if(!this.level().isClientSide && !this.level().isNight()) {
			this.discard();
			return;
		}

		if(tickCount > 500) {
			drop();
			return;
		}

		if(this.tickCount%20 == 0) {
			this.level().playSound(
				null,
				this.getX(),
				this.getY(),
				this.getZ(),
				SoundRegistry.FALLEN_STAR_FALL.get(),
				SoundSource.WEATHER
			);
		}

		Vec3 motion = getDeltaMovement();
		this.move(MoverType.SELF, motion);

		if(ZapinatorsConfig.Server.fallenStarEntityDamage > 0.0f && !this.level().isClientSide()) {
			List<Entity> collidingEntities = this.level().getEntities(this, this.getBoundingBox().inflate(0.1));
			for (Entity entity : collidingEntities) {
				if (entity != this && !(entity instanceof Player)) {
					entity.hurt(damageSources().magic(), ZapinatorsConfig.Server.fallenStarEntityDamage);
					drop();
					return;
				}
			}
		}

		if (this.onGround() || this.horizontalCollision || this.verticalCollision) {
			drop();
		}

		this.level().addParticle(new DustParticleOptions(Vec3.fromRGB24(15656731).toVector3f(), 1.069f), this.getX(), this.getY()+0.2, this.getZ(), 0.0D, 0.0D, 0.0D);
	}

	public void drop() {
		this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundRegistry.FALLEN_STAR_HIT.get(), SoundSource.BLOCKS);
		ItemStack dropStack = new ItemStack(ItemRegistry.FALLEN_STAR.get());
		ItemEntity drop = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), dropStack);
		this.level().addFreshEntity(drop);

		if (!this.level().isClientSide()) {
			int particleCount = 20;
			for (int i = 0; i < particleCount; i++) {
				double theta = this.random.nextDouble() * 2 * Math.PI;
				double phi = this.random.nextDouble() * (Math.PI / 2);
				double speed = 0.3 + this.random.nextDouble() * 0.2;

				double dx = speed * Math.cos(theta) * Math.sin(phi);
				double dy = speed * Math.cos(phi);
				double dz = speed * Math.sin(theta) * Math.sin(phi);

				((ServerLevel)this.level()).sendParticles(new DustParticleOptions(Vec3.fromRGB24(15656731).toVector3f(), 1.0f), this.getX(), this.getY(), this.getZ(), 1, dx, dy, dz, 0.0);
			}
		}

		this.discard();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compoundTag) {

	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compoundTag) {

	}
}
