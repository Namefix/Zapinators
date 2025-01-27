package com.namefix.item;

import com.namefix.registry.SoundRegistry;

public class LaserRifleItem extends AbstractLaserGunItem {
    public LaserRifleItem(Properties properties) {
        super(properties);
        this.color = 0x7771B7;
        this.baseDamage = 2.0f;
        this.projectileSpeed = 1.5f;
        this.itemCooldown = 2;
        this.laserLength = 3.0f;
        this.shootSound = SoundRegistry.LASER_RIFLE_SHOOT.getOrNull();
    }
}
