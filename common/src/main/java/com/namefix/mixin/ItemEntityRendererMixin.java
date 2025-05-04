package com.namefix.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.namefix.registry.ItemRegistry;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {
	@ModifyVariable(
			method = "render(Lnet/minecraft/world/entity/item/ItemEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
			at = @At("HEAD"),
			argsOnly = true)
	private int zapinators$modifyRender(int value, @Local(argsOnly = true) ItemEntity itemEntity) {
		if(itemEntity.getItem().getItem().equals(ItemRegistry.FALLEN_STAR.get())) {
			return 15728880;
		}
		return value;
	}
}
