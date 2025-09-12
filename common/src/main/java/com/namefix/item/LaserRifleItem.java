package com.namefix.item;

import com.namefix.config.ZapinatorsConfig;
import com.namefix.registry.SoundRegistry;

public class LaserRifleItem extends AbstractLaserGunItem {
    public LaserRifleItem(Properties properties) {
        super(properties);
        this.color = 0x7771B7;
        this.baseDamage = 3.0f;
        this.projectileSpeed = 1.2f;
        this.itemCooldown = 4;
        this.laserLength = 2.5f;
        this.manaCost = 6f;
        this.shootSound = SoundRegistry.LASER_RIFLE_SHOOT.getOrNull();
    }

    @Override
    public float getDamageModifier() {
        return ZapinatorsConfig.Server.laserRifleDamageMultiplier;
    }
}
