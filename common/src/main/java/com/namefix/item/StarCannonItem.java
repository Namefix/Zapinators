package com.namefix.item;

import com.namefix.entity.StarProjectile;
import com.namefix.registry.EntityRegistry;
import com.namefix.registry.ItemRegistry;
import com.namefix.registry.SoundRegistry;
import com.namefix.utils.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class StarCannonItem extends Item {
	private float starSpeed = 1.5f;

	public StarCannonItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		if(!player.isCreative() && !player.getInventory().contains(ItemRegistry.FALLEN_STAR.get().getDefaultInstance()))
			return InteractionResultHolder.fail(player.getItemInHand(interactionHand));

		if(!level.isClientSide) {
			Vec3 lookVec = player.getLookAngle();
			Vec3 offsetVec = interactionHand == InteractionHand.MAIN_HAND ? lookVec.cross(new Vec3(0, 1, 0)).normalize() : lookVec.cross(new Vec3(0, -1, 0)).normalize();
			double offsetAmount = 0.2;
			StarProjectile star = new StarProjectile(EntityRegistry.STAR_PROJECTILE.get(), level);
			star.setPos(
					player.getX() + offsetVec.x * offsetAmount,
					player.getEyeY() - 0.1 + offsetVec.y * offsetAmount,
					player.getZ() + offsetVec.z * offsetAmount
			);

			double trajectoryCorrection = 0.07;
			Vec3 adjustedLookVec = lookVec.add(offsetVec.scale(-trajectoryCorrection * offsetAmount));

			star.setDeltaMovement(
					adjustedLookVec.x * this.starSpeed,
					adjustedLookVec.y * this.starSpeed,
					adjustedLookVec.z * this.starSpeed
			);
			star.setOwner(player);
			star.shotFromCreative = player.isCreative();
			star.setXRot(player.getXRot());
			star.setYRot(player.getYRot());
			level.addFreshEntity(star);

			if(!player.isCreative())
				Utils.removeOneItem(player, ItemRegistry.FALLEN_STAR.get());
		}

		player.getCooldowns().addCooldown(this, 5);
		player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegistry.STAR_CANNON_SHOOT.getOrNull(), SoundSource.PLAYERS);
		return InteractionResultHolder.consume(player.getItemInHand(interactionHand));
	}

	public float getDamageModifier() {
		return 1.0f;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.NONE;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(Component.translatable("item.zapinators.star_cannon.description").withStyle(net.minecraft.ChatFormatting.GRAY));

		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
	}
}
