package com.namefix.item;

import com.namefix.registry.SoundRegistry;

public class WandOfSparkingItem extends AbstractOrbShooterItem {
	public WandOfSparkingItem(Properties properties, float manaCost, float baseDamage, int itemCooldown, int color) {
		super(properties, manaCost, baseDamage, itemCooldown, color);
		this.gravity = true;
		this.shootSound = SoundRegistry.WAND_OF_SPARKING_SHOOT.getOrNull();
		this.orbDespawnTick = 40;
		this.piercingAmount = 1;
		this.fireChance = true;
	}
}
