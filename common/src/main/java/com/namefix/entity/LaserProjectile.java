package com.namefix.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.joml.Vector3f;

public class LaserProjectile extends AbstractHurtingProjectile {
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(LaserProjectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Vector3f> SIZE = SynchedEntityData.defineId(LaserProjectile.class, EntityDataSerializers.VECTOR3);

    private float baseDamage = 1.0f;
    private boolean blockPiercing = false;
    private boolean entityPiercing = false;
    private int piercedEntities = 0;
    private int maxPiercing = 3;

    public LaserProjectile(EntityType<? extends LaserProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public void setColor(int color) {
        this.entityData.set(COLOR, color);
    }

    public int getColor() {
        return this.entityData.get(COLOR);
    }

    public void setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
    }
    public float getBaseDamage() {
        return this.baseDamage;
    }
    public void setSize(Vector3f size) {
        this.entityData.set(SIZE, size);
    }
    public Vector3f getSize() {
        return this.entityData.get(SIZE);
    }
    public void setBlockPiercing(boolean status) {
        blockPiercing = status;
        this.noPhysics = status;
    }
    public boolean canPierceBlocks() {
        return blockPiercing;
    }
    public void setEntityPiercing(boolean status) {
        entityPiercing = status;
    }
    public boolean canPierceEntities() {
        return entityPiercing;
    }
    public void setMaxPiercing(int maxPiercing) {
        this.maxPiercing = maxPiercing;
    }
    public int getMaxPiercing() {
        return maxPiercing;
    }

    @Override
    public void tick() {
        super.tick();

        if(this.tickCount > 200) {
            this.discard();
            return;
        }

        if (this.isOnFire()) {
            this.extinguishFire();
        }

        Vector3f size = this.getSize();
        this.setBoundingBox(new AABB(
                this.getX() - size.x, this.getY(), this.getZ(),
                this.getX() + size.x, this.getY() + size.y, this.getZ() + size.z
        ));

        if (!this.level().isClientSide) {
            BlockHitResult blockHitResult = this.level().clip(new ClipContext(
                    this.position(),
                    this.position().add(this.getDeltaMovement()),
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    this
            ));
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                this.onHitBlock(blockHitResult);
            }

            EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(
                    this.level(),
                    this,
                    this.position(),
                    this.position().add(this.getDeltaMovement()),
                    this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(0.5),
                    entity -> !entity.isSpectator() && entity.isAlive(),
                    0.0f
            );
            if (entityHitResult != null) {
                this.onHitEntity(entityHitResult);
            }
        }

        Vec3 motion = this.getDeltaMovement();
        this.setPos(this.getX() + motion.x, this.getY() + motion.y, this.getZ() + motion.z);
    }

    protected void onHitBlock(BlockHitResult hitResult) {
        if(level().isClientSide) return;
        if(!canPierceBlocks()) {
            this.setDeltaMovement(Vec3.ZERO);
            this.setPos(hitResult.getBlockPos().getX(), hitResult.getBlockPos().getY(), hitResult.getBlockPos().getZ());
            this.discard();
        }
    }

    protected void onHitEntity(EntityHitResult hitResult) {
        if(level().isClientSide) return;
        Entity target = hitResult.getEntity();
        target.hurtServer((ServerLevel) target.level(), damageSources().magic(), baseDamage);
        target.invulnerableTime = 0;
        if(canPierceEntities()) {
            piercedEntities++;
        } else {
            this.discard();
        }
        if(piercedEntities >= maxPiercing)
            this.discard();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(COLOR, 0xFFFFFF);
        builder.define(SIZE, new Vector3f(0.15f, 0.15f, 1.6f));
        builder.build();
    }
}
