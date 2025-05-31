package com.namefix.item;

import com.namefix.enums.GemStaffType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RuneItem extends Item {
	@Nullable GemStaffType type;

	public RuneItem(Properties properties, @Nullable GemStaffType type) {
		super(properties);
		this.type = type;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
		if(type == null) {
			list.add(Component.translatable("item.zapinators.rune.description").withStyle(ChatFormatting.GRAY));
		} else {
			String fullName = this.getName().getString();
			String firstWord = fullName.split(" ")[0];
			list.add(Component.translatable("item.zapinators.gem_rune.description", firstWord).withStyle(ChatFormatting.GRAY));
		}
		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
	}
}
