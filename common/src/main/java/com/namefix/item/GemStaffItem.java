package com.namefix.item;

import com.namefix.config.ZapinatorsConfig;
import com.namefix.enums.GemStaffType;

public class GemStaffItem extends AbstractOrbShooterItem {
	GemStaffType type;

	public GemStaffItem(Properties properties, float manaCost, float baseDamage, int itemCooldown, int color, GemStaffType gemStaffType) {
		super(properties, manaCost, baseDamage, itemCooldown, color);
		this.type = gemStaffType;
	}

	@Override
	public float getDamageModifier() {
		return ZapinatorsConfig.Server.gemStavesDamageMultiplier;
	}
}
