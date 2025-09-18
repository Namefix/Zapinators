package com.namefix.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.namefix.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FallenStarRenderer<T extends Entity, S extends FallenStarRenderState> extends EntityRenderer<T, S> {
	private final ItemStack starStack;
	private long lastRenderTime = 0;

	public FallenStarRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.starStack = new ItemStack(ItemRegistry.FALLEN_STAR.get());
	}

	@Override
	public void render(S entityRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
		Minecraft mc = Minecraft.getInstance();
		if(mc.isPaused()) {
			if(lastRenderTime == 0) lastRenderTime = System.currentTimeMillis();
		}
		else lastRenderTime = 0;
		long ms = lastRenderTime == 0 ? System.currentTimeMillis() : lastRenderTime;
		float rotation = (ms % 1000L) / 1000.0f * 360.0f;

		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(-90.f));
		poseStack.translate(0, 0.1f, 0);
		poseStack.mulPose(Axis.XP.rotationDegrees(entityRenderState.entityXRot));
		poseStack.mulPose(Axis.YP.rotationDegrees(-entityRenderState.entityYRot));

		poseStack.translate(0, 0.1f, 0);
		poseStack.mulPose(Axis.ZP.rotationDegrees(-rotation));
		poseStack.translate(0, -0.1f, 0);

		mc.getItemRenderer().renderStatic(
				starStack,
				ItemDisplayContext.GROUND,
				15728880,
				OverlayTexture.NO_OVERLAY,
				poseStack,
				multiBufferSource,
				mc.level,
				0
		);

		poseStack.popPose();
	}

	@Override
	@SuppressWarnings("unchecked")
	public @NotNull S createRenderState() {
		return (S) new FallenStarRenderState();
	}

	@Override
	public void extractRenderState(T entity, S entityRenderState, float f) {
		super.extractRenderState(entity, entityRenderState, f);
		entityRenderState.entityXRot = entity.getXRot();
		entityRenderState.entityYRot = entity.getYRot();
	}
}
