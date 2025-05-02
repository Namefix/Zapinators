package com.namefix.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.namefix.ZapinatorsMod;
import com.namefix.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FallenStarRenderer<T extends FallenStar> extends EntityRenderer<T> {
	private final ItemStack starStack;

	public FallenStarRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.starStack = new ItemStack(ItemRegistry.FALLEN_STAR.get());
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "textures/item/fallen_star.png");
	}

	@Override
	public void render(T entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
		Minecraft mc = Minecraft.getInstance();
		long ms = System.currentTimeMillis();
		float rotation = (ms % 1000L) / 1000.0f * 360.0f;

		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(-90.f));
		poseStack.translate(0, 0.1f, 0);
		poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
		poseStack.mulPose(Axis.YP.rotationDegrees(-entity.getYRot()));

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
}
