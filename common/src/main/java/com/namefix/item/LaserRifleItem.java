package com.namefix.item;

import com.namefix.registry.SoundRegistry;

public class LaserRifleItem extends AbstractLaserGunItem {
    public LaserRifleItem(Properties properties) {
        super(properties);
        this.color = 0x7771B7;
        this.baseDamage = 1.5f;
        this.projectileSpeed = 1.0f;
        this.itemCooldown = 4;
        this.laserLength = 2.5f;
        this.manaCost = 10f;
        this.shootSound = SoundRegistry.LASER_RIFLE_SHOOT.getOrNull();
    }
}
