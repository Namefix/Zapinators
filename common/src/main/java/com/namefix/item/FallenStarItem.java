package com.namefix.item;

import com.namefix.registry.SoundRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FallenStarItem extends Item {
	public FallenStarItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
		player.playSound(SoundRegistry.FALLEN_STAR_USE.get());
		player.getCooldowns().addCooldown(player.getItemInHand(interactionHand), 20);
		return InteractionResult.SUCCESS;
	}
}
