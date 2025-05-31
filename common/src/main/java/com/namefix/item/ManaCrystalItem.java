package com.namefix.item;

import com.namefix.registry.AttributeRegistry;
import com.namefix.registry.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ManaCrystalItem extends Item {
	public ManaCrystalItem(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		AttributeInstance maxMana = player.getAttribute(AttributeRegistry.getHolder(AttributeRegistry.MAX_MANA));
		player.playSound(SoundRegistry.MANA_CRYSTAL_USE.get());
		player.getCooldowns().addCooldown(player.getItemInHand(interactionHand).getItem(), 20);
		if(maxMana == null || maxMana.getValue() >= 200) return InteractionResultHolder.fail(player.getItemInHand(interactionHand));
		maxMana.setBaseValue(maxMana.getValue()+20);

		if (!player.isCreative()) {
			ItemStack heldStack = player.getItemInHand(interactionHand);
			heldStack.shrink(1);
		}

		return InteractionResultHolder.success(player.getItemInHand(interactionHand));
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(Component.translatable("item.zapinators.mana_crystal_description").withStyle(ChatFormatting.BLUE));
		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
	}
}
