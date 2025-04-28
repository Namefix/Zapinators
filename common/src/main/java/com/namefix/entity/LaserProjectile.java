package com.namefix.entity;

import com.namefix.config.ZapinatorsConfig;
import com.namefix.enums.ZapinatorType;
import com.namefix.utils.Utils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
    private float baseKnockback = 0.5f;
    private boolean blockPiercing = false;
    private boolean entityPiercing = false;
    private int piercedEntities = 0;
    private int maxPiercing = 3;

    private ZapinatorType zapinatorType = ZapinatorType.NONE;
    private boolean zapinatorSlowedDown = false;

    public LaserProjectile(EntityType<? extends LaserProjectile> entityType, Level level) {
        super(entityType, level);
    }

    private void updateHitbox() {
        Vec3 direction = this.getDeltaMovement().normalize();
        var data = getEntityData().get(SIZE);

        double endX = direction.x * data.z;
        double endY = direction.y * data.z;
        double endZ = direction.z * data.z;

        double minX = Math.min(0, endX) - (data.x / 2);
        double minY = Math.min(0, endY) - (data.y / 2);
        double minZ = Math.min(0, endZ) - (data.x / 2);
        double maxX = Math.max(0, endX) + (data.x / 2);
        double maxY = Math.max(0, endY) + (data.y / 2);
        double maxZ = Math.max(0, endZ) + (data.x / 2);

        this.setBoundingBox(new AABB(
                this.getX() + minX,
                this.getY() + minY,
                this.getZ() + minZ,
                this.getX() + maxX,
                this.getY() + maxY,
                this.getZ() + maxZ
        ));
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
                    level(),
                    this,
                    position(),
                    position().add(getDeltaMovement()),
                    getBoundingBox().expandTowards(getDeltaMovement()).inflate(0.2),
                    entity -> !entity.isSpectator() && entity.isAlive()
            );
            if (entityHitResult != null) {
                this.onHitEntity(entityHitResult);
            }
        }

        Vec3 motion = this.getDeltaMovement();
        this.setPos(this.getX() + motion.x, this.getY() + motion.y, this.getZ() + motion.z);
        updateHitbox();
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
        if(this.getOwner() != null && target.getUUID().equals(this.getOwner().getUUID())) return;
        if(!zapinatorType.equals(ZapinatorType.NONE) && target.invulnerableTime == 0) {
            handleZapinatorChances(target);
        }
        else { // non zapinator
            target.hurtServer((ServerLevel) target.level(), damageSources().playerAttack((Player) this.getOwner()), baseDamage);
            target.invulnerableTime = 0;
        }

        if(canPierceEntities()) {
            piercedEntities++;
        } else {
            this.discard();
        }
        if(maxPiercing != -1 && piercedEntities >= maxPiercing)
            this.discard();
    }

    protected void handleZapinatorChances(Entity target) {
        if(this.level().isClientSide) return;

        RandomSource random = this.random;
        float damage = this.baseDamage;
        boolean disableBlockUp = random.nextFloat() < 0.05f; // 5%
        boolean disableBlockDown = random.nextFloat() < 0.05f; // 5%
        boolean disableBlock = random.nextFloat() < 0.5f; // 50%
        boolean backwards = random.nextFloat() < 0.67f; // 67%
        boolean shiftRight = random.nextFloat() < 0.145f; // 14.5%
        boolean shiftBottom = random.nextFloat() < 0.145f; // 14.5%
        boolean multiplyKnockback = random.nextFloat() < 0.05f; // 5%
        boolean multiplyDamage = random.nextFloat() < 0.02f; // 2%
        boolean teleportUp = random.nextFloat() < 0.145f; // 14.5%
        boolean teleportDown = random.nextFloat() < 0.145f; // 14.5%
        boolean mirrorVertical = random.nextFloat() < 0.07f; // 7%
        boolean mirrorHorizontal = random.nextFloat() < 0.07f; // 7%
        boolean slowdown = random.nextFloat() < 0.10f; // 10%

        if(disableBlockUp) {
            this.blockPiercing = true;
            this.move(null, new Vec3(0, 8, 0));
        }
        if(disableBlockDown) {
            this.blockPiercing = true;
            this.move(null, new Vec3(0, -8, 0));
        }
        if(disableBlock) {
            this.blockPiercing = true;
        }
        if(backwards) {
            Utils.mirrorHorizontal(this);
            Utils.mirrorVertical(this);
            Vec3 motion = this.getDeltaMovement();
            int moveAmount = random.nextInt(1, 20);
            this.move(null, new Vec3(motion.x*moveAmount, motion.y*moveAmount, motion.z*moveAmount));
        }
        if(shiftRight) {
            Utils.rotateMotionXZ(this, 30);
        }
        if(shiftBottom) {
            Utils.rotateMotionY(this, 30);
        }
        if(teleportUp) {
            this.move(null, new Vec3(0, random.nextInt(1,5), 0));
        }
        if(teleportDown) {
            this.move(null, new Vec3(0, -(random.nextInt(1,5)), 0));
        }
        if(mirrorVertical) {
            Utils.mirrorVertical(this);
        }
        if(mirrorHorizontal) {
            Utils.mirrorHorizontal(this);
        }

        if(!zapinatorSlowedDown) {
            Utils.slowdownEntity(this, 40);
            this.baseDamage *= 0.9f;
            this.baseKnockback *= 0.9f;
            zapinatorSlowedDown = true;
        }

        if(multiplyKnockback) {
            baseKnockback *= 10;
        }
        if(multiplyDamage) {
            damage *= 10;
        }
        if(slowdown) {
            float slowAmount = (random.nextFloat() *10)-0.05f;
            Utils.slowdownEntity(this, slowAmount);
        }

        // custom zapinators
        boolean customChance = random.nextFloat() < 0.5f;
        if(customChance && target instanceof LivingEntity livingTarget) {
            switch (zapinatorType) {
                case RED: {
                    livingTarget.setRemainingFireTicks(livingTarget.getRemainingFireTicks() + random.nextInt(5, 201));
                    break;
                }
                case GREEN: {
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.POISON, random.nextInt(20, 201), 1));
                    break;
                }
                case BLUE: {
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, random.nextInt(20, 201), 1));
                    break;
                }
                case PURPLE: {
                    Utils.applyKnockback(livingTarget, this, baseKnockback*10);
                    break;
                }
                case YELLOW: {
                    Utils.spawnLightning(livingTarget.position(), livingTarget.level());
                    break;
                }
                case WHITE: {
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.LEVITATION, random.nextInt(20, 70)));
                    break;
                }
            }
        }

        target.hurtServer((ServerLevel) target.level(), damageSources().playerAttack((Player) this.getOwner()), damage* ZapinatorsConfig.Server.zapinatorDamageMultiplier);
        if(target instanceof LivingEntity livingTarget) Utils.applyKnockback(livingTarget, this, baseKnockback);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(COLOR, 0xFFFFFF);
        builder.define(SIZE, new Vector3f(0.15f, 0.15f, 1.6f));
        builder.build();
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
    public ZapinatorType getZapinatorType() {
        return zapinatorType;
    }
    public void setZapinatorType(ZapinatorType zapinatorType) {
        this.zapinatorType = zapinatorType;
    }
}
