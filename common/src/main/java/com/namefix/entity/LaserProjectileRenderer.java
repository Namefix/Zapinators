package com.namefix.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.namefix.ZapinatorsMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class LaserProjectileRenderer<T extends LaserProjectile, S extends LaserProjectileRenderState> extends EntityRenderer<T,S> {
    public LaserProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(S entityRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        int color = entityRenderState.color;
        float r = (color >> 16 & 255) / 255.0f;
        float g = (color >> 8 & 255) / 255.0f;
        float b = (color & 255) / 255.0f;

        poseStack.pushPose();
        Vec3 vel = entityRenderState.velocity;
        if (vel.lengthSqr() > 0) {
            float yaw = (float) (Math.toDegrees(Math.atan2(vel.z, vel.x)) - 90.0);
            float pitch = (float) Math.toDegrees(Math.atan2(vel.y, Math.sqrt(vel.x * vel.x + vel.z * vel.z)));

            //poseStack.translate(0.0, entityRenderState.size.z/ 2.0, 0.0);
            poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
            poseStack.mulPose(Axis.XP.rotationDegrees(-pitch));
        }
        renderLaser(entityRenderState, poseStack, multiBufferSource, r, g, b, 1.0f);
        poseStack.popPose();
        super.render(entityRenderState, poseStack, multiBufferSource, i);
    }

    private void renderLaser(S state, PoseStack poseStack, MultiBufferSource bufferSource, float r, float g, float b, float alpha) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "textures/entity/laser_projectile.png")));

        float width = state.size.x;
        float height = state.size.y;
        float length = state.size.z;

        int light = 15728880;
        int blockLight = light & 0xFFFF;
        int skyLight = (light >> 16) & 0xFFFF;

        poseStack.pushPose();
        poseStack.scale(1.0f, 1.0f, 1.0f);

        PoseStack.Pose entry = poseStack.last();
        Matrix4f matrix = poseStack.last().pose();

        // FRONT FACE
        renderQuad(vertexConsumer, matrix, entry,
                -width / 2, 0.0f, 0.0f,  // Bottom-left
                width / 2, height, 0.0f,  // Top-right
                r, g, b, alpha, blockLight, skyLight);

        // BACK FACE
        renderQuad(vertexConsumer, matrix, entry,
                width / 2, 0.0f, length,  // Bottom-right
                -width / 2, height, length, // Top-left
                r, g, b, alpha, blockLight, skyLight);

        // TOP FACE
        renderQuad(vertexConsumer, matrix, entry,
                -width / 2, height, 0.0f,  // Front-left
                width / 2, height, length, // Back-right
                r, g, b, alpha, blockLight, skyLight);

        // BOTTOM FACE
        renderQuad(vertexConsumer, matrix, entry,
                -width / 2, 0.0f, 0.0f,  // Front-left
                width / 2, 0.0f, length, // Back-right
                r, g, b, alpha, blockLight, skyLight);

        // LEFT FACE
        renderQuad(vertexConsumer, matrix, entry,
                -width / 2, 0.0f, 0.0f,    // Front-bottom
                -width / 2, height, length, // Back-top
                r, g, b, alpha, blockLight, skyLight);

        // RIGHT FACE
        renderQuad(vertexConsumer, matrix, entry,
                width / 2, 0.0f, 0.0f,    // Front-bottom
                width / 2, height, length, // Back-top
                r, g, b, alpha, blockLight, skyLight);

        poseStack.popPose();
    }

    private void renderQuad(VertexConsumer vertexConsumer, Matrix4f matrix, PoseStack.Pose entry,
                            float x1, float y1, float z1,
                            float x2, float y2, float z2,
                            float r, float g, float b, float alpha,
                            int blockLight, int skyLight) {
        // Front Face (Positive Z)
        vertexConsumer.addVertex(matrix, x1, y1, z1).setColor(r, g, b, alpha).setUv(0.0f, 0.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 0.0f, 0.0f, 1.0f);
        vertexConsumer.addVertex(matrix, x2, y1, z1).setColor(r, g, b, alpha).setUv(1.0f, 0.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 0.0f, 0.0f, 1.0f);
        vertexConsumer.addVertex(matrix, x2, y2, z1).setColor(r, g, b, alpha).setUv(1.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 0.0f, 0.0f, 1.0f);
        vertexConsumer.addVertex(matrix, x1, y2, z1).setColor(r, g, b, alpha).setUv(0.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 0.0f, 0.0f, 1.0f);

        // Back Face (Negative Z)
        vertexConsumer.addVertex(matrix, x2, y1, z2).setColor(r, g, b, alpha).setUv(0.0f, 0.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 0.0f, 0.0f, -1.0f);
        vertexConsumer.addVertex(matrix, x1, y1, z2).setColor(r, g, b, alpha).setUv(1.0f, 0.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 0.0f, 0.0f, -1.0f);
        vertexConsumer.addVertex(matrix, x1, y2, z2).setColor(r, g, b, alpha).setUv(1.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 0.0f, 0.0f, -1.0f);
        vertexConsumer.addVertex(matrix, x2, y2, z2).setColor(r, g, b, alpha).setUv(0.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 0.0f, 0.0f, -1.0f);

        // Left Face (Negative X)
        vertexConsumer.addVertex(matrix, x1, y1, z1).setColor(r, g, b, alpha).setUv(0.0f, 0.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, -1.0f, 0.0f, 0.0f);
        vertexConsumer.addVertex(matrix, x1, y2, z1).setColor(r, g, b, alpha).setUv(1.0f, 0.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, -1.0f, 0.0f, 0.0f);
        vertexConsumer.addVertex(matrix, x1, y2, z2).setColor(r, g, b, alpha).setUv(1.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, -1.0f, 0.0f, 0.0f);
        vertexConsumer.addVertex(matrix, x1, y1, z2).setColor(r, g, b, alpha).setUv(0.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, -1.0f, 0.0f, 0.0f);

        // Right Face (Positive X)
        vertexConsumer.addVertex(matrix, x2, y1, z1).setColor(r, g, b, alpha).setUv(0.0f, 0.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 1.0f, 0.0f, 0.0f);
        vertexConsumer.addVertex(matrix, x2, y2, z1).setColor(r, g, b, alpha).setUv(1.0f, 0.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 1.0f, 0.0f, 0.0f);
        vertexConsumer.addVertex(matrix, x2, y2, z2).setColor(r, g, b, alpha).setUv(1.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 1.0f, 0.0f, 0.0f);
        vertexConsumer.addVertex(matrix, x2, y1, z2).setColor(r, g, b, alpha).setUv(0.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 1.0f, 0.0f, 0.0f);

        // Top Face
        vertexConsumer.addVertex(matrix, x1, y2, z1).setColor(r, g, b, alpha).setUv(0.0f, 0.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 0.0f, 1.0f, 0.0f);
        vertexConsumer.addVertex(matrix, x2, y2, z1).setColor(r, g, b, alpha).setUv(1.0f, 0.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 0.0f, 1.0f, 0.0f);
        vertexConsumer.addVertex(matrix, x2, y2, z2).setColor(r, g, b, alpha).setUv(1.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 0.0f, 1.0f, 0.0f);
        vertexConsumer.addVertex(matrix, x1, y2, z2).setColor(r, g, b, alpha).setUv(0.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 0.0f, 1.0f, 0.0f);

        // Bottom Face
        vertexConsumer.addVertex(matrix, x1, y1, z1).setColor(r, g, b, alpha).setUv(0.0f, 0.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 0.0f, -1.0f, 0.0f);
        vertexConsumer.addVertex(matrix, x2, y1, z1).setColor(r, g, b, alpha).setUv(1.0f, 0.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 0.0f, -1.0f, 0.0f);
        vertexConsumer.addVertex(matrix, x2, y1, z2).setColor(r, g, b, alpha).setUv(1.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 0.0f, -1.0f, 0.0f);
        vertexConsumer.addVertex(matrix, x1, y1, z2).setColor(r, g, b, alpha).setUv(0.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(blockLight, skyLight).setNormal(entry, 0.0f, -1.0f, 0.0f);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull S createRenderState() {
        return (S) new LaserProjectileRenderState();
    }

    @Override
    public void extractRenderState(T entity, S entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        entityRenderState.color = entity.getColor();
        entityRenderState.velocity = entity.getDeltaMovement();
        entityRenderState.size = entity.getSize();
    }
}
