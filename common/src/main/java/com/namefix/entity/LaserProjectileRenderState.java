package com.namefix.entity;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class LaserProjectileRenderState extends EntityRenderState {
    public int color;
    public Vec3 velocity;
    public Vector3f size;

    public LaserProjectileRenderState() {}
}
