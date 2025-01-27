package com.namefix.item;

import com.namefix.registry.SoundRegistry;

public class ZapinatorItem extends AbstractLaserGunItem {
    public ZapinatorItem(Properties properties) {
        super(properties);
        this.color = 0x00FF00;
        this.baseDamage = 2.5f;
        this.itemCooldown = 3;
        this.laserLength = 1.0f;
        this.shootSound = SoundRegistry.ZAPINATOR_SHOOT.getOrNull();
    }
}
