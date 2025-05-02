package com.namefix.entity;

import com.namefix.config.ZapinatorsConfig;
import com.namefix.registry.ItemRegistry;
import com.namefix.registry.SoundRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class FallenStar extends Entity {
	public FallenStar(EntityType<? extends FallenStar> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public void tick() {
		super.tick();
		if(!this.level().isNight()) {
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
					entity.hurtServer((ServerLevel) this.level(), damageSources().magic(), ZapinatorsConfig.Server.fallenStarEntityDamage);
					drop();
					return;
				}
			}
		}

		if (this.onGround() || this.horizontalCollision) {
			drop();
		}
	}

	public void drop() {
		this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundRegistry.FALLEN_STAR_HIT.get(), SoundSource.BLOCKS);
		ItemStack dropStack = new ItemStack(ItemRegistry.FALLEN_STAR.get());
		ItemEntity drop = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), dropStack);
		this.level().addFreshEntity(drop);
		this.discard();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float f) {
		return false;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compoundTag) {

	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compoundTag) {

	}
}
