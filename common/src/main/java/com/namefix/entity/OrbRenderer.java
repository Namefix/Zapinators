package com.namefix.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class OrbRenderer <T extends Orb> extends EntityRenderer<T> {
	private static final int SEGMENTS = 8;

	public OrbRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return null;
	}

	@Override
	public void render(T entity, float fa, float ga, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
		poseStack.pushPose();

		poseStack.scale(0.1F, 0.1F, 0.1F);
		poseStack.translate(0, 1.55, 0);

		VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.debugQuads());

		int color = entity.getColor();
		float r = (color >> 16 & 255) / 255.0f;
		float g = (color >> 8 & 255) / 255.0f;
		float b = (color & 255) / 255.0f;

		int light = 15728880;
		int blockLight = light & 0xFFFF;
		int skyLight = (light >> 16) & 0xFFFF;

		// drawing a sphere I guess
		for (int il = 0; il < SEGMENTS; il++) {
			float lat0 = (float) (Math.PI * (-0.5 + (double) (il) / SEGMENTS));
			float lat1 = (float) (Math.PI * (-0.5 + (double) (il + 1) / SEGMENTS));

			for (int j = 0; j < SEGMENTS; j++) {
				float lon0 = (float) (2 * Math.PI * (double) (j) / SEGMENTS);
				float lon1 = (float) (2 * Math.PI * (double) (j + 1) / SEGMENTS);

				float x1 = (float) (Math.cos(lat0) * Math.cos(lon0));
				float y1 = (float) Math.sin(lat0);
				float z1 = (float) (Math.cos(lat0) * Math.sin(lon0));

				float x2 = (float) (Math.cos(lat0) * Math.cos(lon1));
				float y2 = (float) Math.sin(lat0);
				float z2 = (float) (Math.cos(lat0) * Math.sin(lon1));

				float x3 = (float) (Math.cos(lat1) * Math.cos(lon0));
				float y3 = (float) Math.sin(lat1);
				float z3 = (float) (Math.cos(lat1) * Math.sin(lon0));

				float x4 = (float) (Math.cos(lat1) * Math.cos(lon1));
				float y4 = (float) Math.sin(lat1);
				float z4 = (float) (Math.cos(lat1) * Math.sin(lon1));

				vertexConsumer.addVertex(poseStack.last().pose(), x1, y1, z1)
						.setColor(r, g, b, 1.0f)
						.setUv(0, 0)
						.setOverlay(0)
						.setUv2(blockLight, skyLight)
						.setNormal(x1, y1, z1);

				vertexConsumer.addVertex(poseStack.last().pose(), x2, y2, z2)
						.setColor(r, g, b, 1.0f)
						.setUv(0, 0)
						.setOverlay(0)
						.setUv2(blockLight, skyLight)
						.setNormal(x2, y2, z2);

				vertexConsumer.addVertex(poseStack.last().pose(), x3, y3, z3)
						.setColor(r, g, b, 1.0f)
						.setUv(0, 0)
						.setOverlay(0)
						.setUv2(blockLight, skyLight)
						.setNormal(x3, y3, z3);

				vertexConsumer.addVertex(poseStack.last().pose(), x2, y2, z2)
						.setColor(r, g, b, 1.0f)
						.setUv(0, 0)
						.setOverlay(0)
						.setUv2(blockLight, skyLight)
						.setNormal(x2, y2, z2);

				vertexConsumer.addVertex(poseStack.last().pose(), x4, y4, z4)
						.setColor(r, g, b, 1.0f)
						.setUv(0, 0)
						.setOverlay(0)
						.setUv2(blockLight, skyLight)
						.setNormal(x4, y4, z4);

				vertexConsumer.addVertex(poseStack.last().pose(), x3, y3, z3)
						.setColor(r, g, b, 1.0f)
						.setUv(0, 0)
						.setOverlay(0)
						.setUv2(blockLight, skyLight)
						.setNormal(x3, y3, z3);
			}
		}

		poseStack.popPose();
	}
}
