package com.namefix.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.namefix.ZapinatorsMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AngryBeeRenderer <T extends AngryBee, S extends AngryBeeRenderState> extends EntityRenderer<T,S> {
	private static final ResourceLocation ANGRY_BEE_TEXTURE = ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "textures/entity/angry_bee.png");
	private static final int FRAME_COUNT = 4;
	private static final float FRAME_HEIGHT = 1.0f / FRAME_COUNT;

	public AngryBeeRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(S entityRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
		int frame = (int) ((entityRenderState.ageInTicks) % FRAME_COUNT);
		float v0 = frame * FRAME_HEIGHT;
		float v1 = v0 + FRAME_HEIGHT;

		poseStack.pushPose();
		poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		poseStack.scale(0.25f, 0.25f, 0.25f);
		poseStack.translate(0, 1, 0);

		VertexConsumer vc = multiBufferSource.getBuffer(RenderType.entityCutout(ANGRY_BEE_TEXTURE));

		float r = 1.0f, g = 1.0f, b = 1.0f, a = 1.0f;
		int overlay = OverlayTexture.NO_OVERLAY;
		float nx = 0, ny = 0, nz = 1; // facing out
		int blockLight = i & 0xFFFF;
		int skyLight = (i >> 16) & 0xFFFF;

		vc.addVertex(poseStack.last().pose(), -0.5f, -0.5f, 0)
				.setColor(r, g, b, a)
				.setUv(0, v1)
				.setUv2(blockLight, skyLight)
				.setOverlay(overlay)
				.setNormal(nx, ny, nz);
		vc.addVertex(poseStack.last().pose(), 0.5f, -0.5f, 0)
				.setColor(r, g, b, a)
				.setUv(1, v1)
				.setUv2(blockLight, skyLight)
				.setOverlay(overlay)
				.setNormal(nx, ny, nz);
		vc.addVertex(poseStack.last().pose(), 0.5f, 0.5f, 0)
				.setColor(r, g, b, a)
				.setUv(1, v0)
				.setUv2(blockLight, skyLight)
				.setOverlay(overlay)
				.setNormal(nx, ny, nz);
		vc.addVertex(poseStack.last().pose(), -0.5f, 0.5f, 0)
				.setColor(r, g, b, a)
				.setUv(0, v0)
				.setUv2(blockLight, skyLight)
				.setOverlay(overlay)
				.setNormal(nx, ny, nz);

		poseStack.popPose();
		super.render(entityRenderState, poseStack, multiBufferSource, i);
	}

	@Override
	@SuppressWarnings("unchecked")
	public @NotNull S createRenderState() {
		return (S) new AngryBeeRenderState();
	}
}
