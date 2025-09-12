package com.namefix.item;

import com.namefix.config.ZapinatorsConfig;
import com.namefix.registry.SoundRegistry;

public class SpaceGunItem extends AbstractLaserGunItem {
    public SpaceGunItem(Properties properties) {
        super(properties);
        this.color = 0x00FF00;
        this.baseDamage = 2.0f;
        this.itemCooldown = 6;
        this.manaCost = 4f;
        this.meteoriteArmorSavesMana = true;
        this.shootSound = SoundRegistry.SPACE_GUN_SHOOT.getOrNull();
    }

    @Override
    public float getDamageModifier() {
        return ZapinatorsConfig.Server.spaceGunDamageMultiplier;
    }
}
