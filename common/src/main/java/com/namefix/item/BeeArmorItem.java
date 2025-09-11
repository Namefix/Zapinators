package com.namefix.item;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;


import java.util.List;

public class BeeArmorItem extends ArmorItem {
	public static float percentDamageIncrease = 0.10f;

	public BeeArmorItem(ArmorMaterial armorMaterial, Type armorType, Properties properties) {
		super(Holder.direct(armorMaterial), armorType, properties);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(Component.empty());
		list.add(Component.translatable("item.zapinators.description.armor_full_set_bonus").withStyle(ChatFormatting.GRAY));
		list.add(Component.translatable("item.zapinators.bee_armor.description").withStyle(ChatFormatting.BLUE));
		list.add(Component.empty());
		list.add(Component.translatable("item.zapinators.description.armor_extra_bonus").withStyle(ChatFormatting.GRAY));
		list.add(Component.translatable("item.zapinators.bee_armor.extra_bonus", (int)(percentDamageIncrease*100)).withStyle(ChatFormatting.BLUE));
		if(!itemStack.getEnchantments().isEmpty()) list.add(Component.empty());

		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
	}
}
