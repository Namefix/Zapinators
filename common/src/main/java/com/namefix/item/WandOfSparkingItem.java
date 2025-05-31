package com.namefix.item;

import com.namefix.registry.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class WandOfSparkingItem extends AbstractOrbShooterItem {
	public WandOfSparkingItem(Properties properties, float manaCost, float baseDamage, int itemCooldown, int color) {
		super(properties, manaCost, baseDamage, itemCooldown, color);
		this.gravity = true;
		this.shootSound = SoundRegistry.WAND_OF_SPARKING_SHOOT.getOrNull();
		this.orbDespawnTick = 40;
		this.piercingAmount = 1;
		this.fireChance = true;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(Component.translatable("item.zapinators.wand_of_sparking.description").withStyle(ChatFormatting.GRAY));
		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
	}
}
