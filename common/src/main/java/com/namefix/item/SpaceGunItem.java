package com.namefix.item;

import com.namefix.registry.SoundRegistry;

public class SpaceGunItem extends AbstractLaserGunItem {
    public SpaceGunItem(Properties properties) {
        super(properties);
        this.color = 0x00FF00;
        this.baseDamage = 2.0f;
        this.itemCooldown = 6;
        this.shootSound = SoundRegistry.SPACE_GUN_SHOOT.getOrNull();
    }
}
