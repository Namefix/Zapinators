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
import org.joml.Matrix4f;

public class LaserProjectileRenderer<T extends LaserProjectile> extends EntityRenderer<T> {
    public LaserProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return null;
    }

    @Override
    public void render(T entity, float f, float gf, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        int color = entity.getColor();
        float r = (color >> 16 & 255) / 255.0f;
        float g = (color >> 8 & 255) / 255.0f;
        float b = (color & 255) / 255.0f;

        poseStack.pushPose();
        Vec3 vel = entity.getDeltaMovement();
        if (vel.lengthSqr() > 0) {
            float yaw = (float) (Math.toDegrees(Math.atan2(vel.z, vel.x)) - 90.0);
            float pitch = (float) Math.toDegrees(Math.atan2(vel.y, Math.sqrt(vel.x * vel.x + vel.z * vel.z)));

            poseStack.translate(-entity.getSize().x / 2, entity.getSize().y / 2, 0);

            poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
            poseStack.mulPose(Axis.XP.rotationDegrees(-pitch));
        }
        renderLaser(entity, poseStack, multiBufferSource, r, g, b, 0.5f);
        poseStack.popPose();
        super.render(entity, f, g, poseStack, multiBufferSource, i);
    }

    private void renderLaser(T entity, PoseStack poseStack, MultiBufferSource bufferSource, float r, float g, float b, float alpha) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "textures/entity/laser_projectile.png")));

        float width = entity.getSize().x;
        float height = entity.getSize().y;
        float length = entity.getSize().z;

        int light = 15728880;
        int blockLight = light & 0xFFFF;
        int skyLight = (light >> 16) & 0xFFFF;

        poseStack.pushPose();
        poseStack.translate(-width/2, -height/2, -length/2);

        PoseStack.Pose entry = poseStack.last();
        Matrix4f matrix = poseStack.last().pose();
        // uv1 - front left, uv2 - front right, uv3 - back-right, uv4 - back-left
        // TO-DO: Toggleable laser rendering options, fancy and fast
        // LASER CORE

        float coreThickness = 0.04f;
        // front
        renderQuad(vertexConsumer, matrix, entry,
                coreThickness, coreThickness, length/4,          // x1, y1, z1 (bottom-left)
                width-coreThickness, coreThickness, length/4,      // x2, y2, z2 (bottom-right)
                width-coreThickness, height-coreThickness, length/4, // x3, y3, z3 (top-right)
                coreThickness, height-coreThickness, length/4,     // x4, y4, z4 (top-left)
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                r, g, b, 1.0f, blockLight, skyLight,
                0.0f, 0.0f, -1.0f
        );
        // back
        renderQuad(vertexConsumer, matrix, entry,
                width-coreThickness, coreThickness, length-(length/4),      // bottom-right
                coreThickness, coreThickness, length-(length/4),          // bottom-left
                coreThickness, height-coreThickness, length-(length/4),     // top-left
                width-coreThickness, height-coreThickness, length-(length/4), // top-right
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                r, g, b, 1.0f, blockLight, skyLight,
                0.0f, 0.0f, 1.0f
        );
        // top
        renderQuad(vertexConsumer, matrix, entry,
                coreThickness, height-coreThickness, length/4,          // front-left
                width-coreThickness, height-coreThickness, length/4,      // front-right
                width-coreThickness, height-coreThickness, length-(length/4), // back-right
                coreThickness, height-coreThickness, length-(length/4),     // back-left
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                r, g, b, 1.0f, blockLight, skyLight,
                0.0f, 1.0f, 0.0f
        );
        // bottom
        renderQuad(vertexConsumer, matrix, entry,
                coreThickness, coreThickness, length/4,          // front-left
                width-coreThickness, coreThickness, length/4,      // front-right
                width-coreThickness, coreThickness, length-(length/4), // back-right
                coreThickness, coreThickness, length-(length/4),     // back-left
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                r, g, b, 1.0f, blockLight, skyLight,
                0.0f, -1.0f, 0.0f
        );
        // left
        renderQuad(vertexConsumer, matrix, entry,
                coreThickness, coreThickness, length/4,          // Bottom-front
                coreThickness, coreThickness, length-(length/4),     // Bottom-back
                coreThickness, height-coreThickness, length-(length/4),// Top-back
                coreThickness, height-coreThickness, length/4,     // Top-front
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
                r, g, b, alpha, blockLight, skyLight,
                -1.0f, 0.0f, 0.0f
        );
        // right
        renderQuad(vertexConsumer, matrix, entry,
                width-coreThickness, coreThickness, length-(length/4),     // Bottom-back
                width-coreThickness, coreThickness, length/4,          // Bottom-front
                width-coreThickness, height-coreThickness, length/4,     // Top-front
                width-coreThickness, height-coreThickness, length-(length/4),// Top-back
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                r, g, b, 1.0f, blockLight, skyLight,
                1.0f, 0.0f, 0.0f
        );

        // LASER OUTLINE
        // Front face (z=0)
        renderQuad(vertexConsumer, matrix, entry,
                0, 0, 0,          // x1, y1, z1 (bottom-left)
                width, 0, 0,      // x2, y2, z2 (bottom-right)
                width, height, 0, // x3, y3, z3 (top-right)
                0, height, 0,     // x4, y4, z4 (top-left)
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                r, g, b, alpha, blockLight, skyLight,
                0.0f, 0.0f, -1.0f
        );

        // Back face (z=length)
        renderQuad(vertexConsumer, matrix, entry,
                width, 0, length,      // bottom-right
                0, 0, length,          // bottom-left
                0, height, length,     // top-left
                width, height, length, // top-right
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                r, g, b, alpha, blockLight, skyLight,
                0.0f, 0.0f, 1.0f
        );

        // Top face (y=height)
        renderQuad(vertexConsumer, matrix, entry,
                0, height, 0,          // front-left
                width, height, 0,      // front-right
                width, height, length, // back-right
                0, height, length,     // back-left
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                r, g, b, alpha, blockLight, skyLight,
                0.0f, 1.0f, 0.0f
        );

        // Bottom face (y=0)
        renderQuad(vertexConsumer, matrix, entry,
                0, 0, 0,          // front-left
                width, 0, 0,      // front-right
                width, 0, length, // back-right
                0, 0, length,     // back-left
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                r, g, b, alpha, blockLight, skyLight,
                0.0f, -1.0f, 0.0f
        );

        // Left face (x=0)
        renderQuad(vertexConsumer, matrix, entry,
                0, 0, 0,          // Bottom-front
                0, 0, length,     // Bottom-back
                0, height, length,// Top-back
                0, height, 0,     // Top-front
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
                r, g, b, alpha, blockLight, skyLight,
                -1.0f, 0.0f, 0.0f
        );

        // Right face (x=width)
        renderQuad(vertexConsumer, matrix, entry,
                width, 0, length,     // Bottom-back
                width, 0, 0,          // Bottom-front
                width, height, 0,     // Top-front
                width, height, length,// Top-back
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                r, g, b, alpha, blockLight, skyLight,
                1.0f, 0.0f, 0.0f
        );

        poseStack.popPose();
    }

    private void renderQuad(VertexConsumer vertexConsumer, Matrix4f matrix, PoseStack.Pose entry,
                            float x1, float y1, float z1,
                            float x2, float y2, float z2,
                            float x3, float y3, float z3,
                            float x4, float y4, float z4,
                            float u1, float v1,
                            float u2, float v2,
                            float u3, float v3,
                            float u4, float v4,
                            float r, float g, float b, float alpha,
                            int blockLight, int skyLight,
                            float nx, float ny, float nz) {
        vertexConsumer.addVertex(matrix, x1, y1, z1)
                .setColor(r, g, b, alpha)
                .setUv(u1, v1)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setUv2(blockLight, skyLight)
                .setNormal(entry, nx, ny, nz);
        vertexConsumer.addVertex(matrix, x2, y2, z2)
                .setColor(r, g, b, alpha)
                .setUv(u2, v2)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setUv2(blockLight, skyLight)
                .setNormal(entry, nx, ny, nz);
        vertexConsumer.addVertex(matrix, x3, y3, z3)
                .setColor(r, g, b, alpha)
                .setUv(u3, v3)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setUv2(blockLight, skyLight)
                .setNormal(entry, nx, ny, nz);
        vertexConsumer.addVertex(matrix, x4, y4, z4)
                .setColor(r, g, b, alpha)
                .setUv(u4, v4)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setUv2(blockLight, skyLight)
                .setNormal(entry, nx, ny, nz);
    }
}
