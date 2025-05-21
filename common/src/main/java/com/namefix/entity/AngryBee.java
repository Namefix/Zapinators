package com.namefix.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;

public class AngryBee extends AbstractHurtingProjectile {
	protected AngryBee(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
		super(entityType, level);
	}
}
