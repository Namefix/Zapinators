package com.namefix.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;

public class DescriptionItem extends Item {
	List<Component> components = new ArrayList<>();

	public DescriptionItem(Properties properties, Component component) {
		super(properties);
		components.add(component);
	}

	public DescriptionItem(Properties properties, List<Component> component) {
		super(properties);
		components.addAll(component);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
		list.addAll(components);
		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
	}
}
