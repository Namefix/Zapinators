package com.namefix.item;

import com.namefix.enums.ZapinatorType;
import com.namefix.registry.SoundRegistry;

public class ColorZapinatorItem extends AbstractLaserGunItem {

    public ColorZapinatorItem(Properties properties, ZapinatorType zapinatorType, float baseDamage) {
        super(properties);
        this.zapinatorType = zapinatorType;
        this.color = 0x5BBCEA;
        this.baseDamage = baseDamage;
        this.itemCooldown = 20;
        this.laserLength = 3.5f;
        this.maxPiercing = 8;
        this.shootSound = SoundRegistry.COLOR_ZAPINATOR_SHOOT.getOrNull();
    }
}
