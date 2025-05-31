package com.namefix.item;

import com.namefix.entity.AngryBee;
import com.namefix.registry.EntityRegistry;
import com.namefix.registry.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BeeGunItem extends AbstractManaItem {
	public static float BEE_SPEED = 0.3f;
	protected int itemCooldown = 5;
	protected SoundEvent shootSound = SoundRegistry.BEE_GUN_SHOOT.getOrNull();

	public BeeGunItem(Properties properties) {
		super(properties);
		manaCost = 12f;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		if(!canPlayerUseManaItem(level, player)) return InteractionResultHolder.fail(player.getItemInHand(interactionHand));

		if(!level.isClientSide) {
			int initialBeeAmount = level.getRandom().nextInt(1, 3);
			if(level.getRandom().nextFloat() < 0.16f) initialBeeAmount++;

			for (int i=0;i<initialBeeAmount;i++) {
				double offsetX = (level.getRandom().nextDouble() - 0.5) * 0.4;
				double offsetY = (level.getRandom().nextDouble() - 0.5) * 0.4;
				double offsetZ = (level.getRandom().nextDouble() - 0.5) * 0.4;

				AngryBee bee = new AngryBee(EntityRegistry.ANGRY_BEE.get(), level);
				bee.beeSpeed = BEE_SPEED;
				bee.setPos(
					player.getX() + offsetX,
					player.getEyeY() - 0.1 + offsetY,
					player.getZ() + offsetZ
				);
				bee.setDeltaMovement(player.getLookAngle().scale(BEE_SPEED));
				bee.setOwner(player);
				bee.setXRot(player.getXRot());
				bee.setYRot(player.getYRot());
				level.addFreshEntity(bee);
			}
		}

		useManaItem(level, player, meteoriteArmorSavesMana);

		player.getCooldowns().addCooldown(this, itemCooldown);
		if(shootSound != null) player.level().playSound(null, player.getX(), player.getY(), player.getZ(), shootSound, SoundSource.PLAYERS);
		return InteractionResultHolder.consume(player.getItemInHand(interactionHand));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.NONE;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(Component.translatable("item.zapinators.bee_gun.description").withStyle(ChatFormatting.GRAY));
		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
	}
}
