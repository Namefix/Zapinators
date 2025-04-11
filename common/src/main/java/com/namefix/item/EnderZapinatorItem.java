package com.namefix.item;

import com.namefix.registry.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class EnderZapinatorItem extends AbstractLaserGunItem {

	public EnderZapinatorItem(Properties properties) {
		super(properties);
		this.color = 0xffdf87;
		this.baseDamage = 6.0f;
		this.itemCooldown = 20;
		this.laserLength = 2.5f;
		this.maxPiercing = 12;
		this.projectileSpeed = 1.0f;
		this.shootSound = SoundRegistry.COLOR_ZAPINATOR_SHOOT.getOrNull();
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(Component.translatable("item.zapinators.ender_zapinator.description").withStyle(ChatFormatting.DARK_PURPLE));
		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
	}
}
