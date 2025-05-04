package com.namefix.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.namefix.interfaces.ItemEntityRenderStateExtension;
import com.namefix.registry.ItemRegistry;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.state.ItemEntityRenderState;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {
	@Inject(
			method = "extractRenderState(Lnet/minecraft/world/entity/item/ItemEntity;Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;F)V",
			at = @At("HEAD")
	)
	private void zapinators$extractItemRenderState(ItemEntity itemEntity, ItemEntityRenderState itemEntityRenderState, float f, CallbackInfo ci) {
		var stateExt = (ItemEntityRenderStateExtension) itemEntityRenderState;
		stateExt.zapinators$setItem(itemEntity.getItem().getItem());
	}

	@ModifyVariable(
			method = "render(Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
			at = @At("HEAD"),
			argsOnly = true)
	private int zapinators$modifyRender(int value, @Local(argsOnly = true) ItemEntityRenderState itemEntityRenderState) {
		var stateExt = (ItemEntityRenderStateExtension) itemEntityRenderState;
		if(stateExt.zapinators$getItem() == null) return value;
		if(stateExt.zapinators$getItem().equals(ItemRegistry.FALLEN_STAR.get())) {
			return 15728880;
		}
		return value;
	}
}
