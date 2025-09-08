package com.namefix.item;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

public class BeeArmorItem extends ArmorItem {
	public BeeArmorItem(ArmorMaterial armorMaterial, Type armorType, Properties properties) {
		super(Holder.direct(armorMaterial), armorType, properties);
	}
}
