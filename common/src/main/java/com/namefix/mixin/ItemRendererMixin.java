package com.namefix.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.namefix.registry.ItemRegistry;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
	@ModifyVariable(
			method = "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V",
			at = @At("HEAD"),
			ordinal = 0,
			argsOnly = true
	)
	private int zapinators$modifyItemLightLevel(int original, @Local(argsOnly = true) ItemStack itemStack) {
		if(itemStack.is(ItemRegistry.FALLEN_STAR.get())) return 15728880;
		return original;
	}
}
