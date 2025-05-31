package com.namefix.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class CoreItem extends Item {
	public CoreItem(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
		String fullName = this.getName(itemStack).getString();
		String firstWord = fullName.split(" ")[0];
		list.add(Component.translatable("item.zapinators.core.description", firstWord).withStyle(ChatFormatting.GRAY));

		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
	}
}
