package com.namefix.utils;

import com.namefix.ZapinatorsMod;
import com.namefix.compat.OpacCompat;
import com.namefix.enums.ZapinatorType;
import com.namefix.item.AbstractManaItem;
import com.namefix.registry.ItemRegistry;
import dev.architectury.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Utils {
    public static void rotateMotionXZ(Entity projectile, float degrees) {
        Vec3 motion = projectile.getDeltaMovement();
        float angle = (float) Math.toRadians(degrees);

        double x = motion.x * Math.cos(angle) - motion.z * Math.sin(angle);
        double z = motion.x * Math.sin(angle) + motion.z * Math.cos(angle);

        projectile.setDeltaMovement(x, motion.y, z);
    }

    public static void rotateMotionY(Entity ent, float degrees) {
        Vec3 motion = ent.getDeltaMovement();
        float angle = (float) Math.toRadians(degrees);

        double y = motion.y * Math.cos(angle) - motion.z * Math.sin(angle);
        double z = motion.y * Math.sin(angle) + motion.z * Math.cos(angle);

        ent.setDeltaMovement(motion.x, y, z);
    }

    public static void mirrorVertical(Entity ent) {
        Vec3 motion = ent.getDeltaMovement();
        ent.setDeltaMovement(motion.x, -motion.y, motion.z);
    }

    public static void mirrorHorizontal(Entity ent) {
        Vec3 motion = ent.getDeltaMovement();
        ent.setDeltaMovement(-motion.x, motion.y, -motion.z);
    }

    public static void slowdownEntity(Entity ent, float percentage) {
        float reduction = 1.0f - (percentage / 100.0f);
        Vec3 motion = ent.getDeltaMovement();
        ent.setDeltaMovement(motion.x * reduction, motion.y * reduction, motion.z * reduction);
    }

    public static void applyKnockback(LivingEntity target, Entity projectile, double knockbackPower) {
        if(target instanceof Player pl && pl.isCreative()) return;
        target.knockback(knockbackPower, projectile.getX()-target.getX(), projectile.getZ()-target.getZ());
        target.hurtMarked = true;
    }

    public static void removeOneItem(Player player, Item item) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack slotStack = player.getInventory().getItem(i);
            if (!slotStack.isEmpty() && slotStack.getItem() == item) { // Match item
                slotStack.shrink(1);
                if (slotStack.isEmpty()) {
                    player.getInventory().setItem(i, ItemStack.EMPTY);
                }
                break;
            }
        }
    }

    public static boolean getPlayerArmorFullSet(Player player, Class<? extends ArmorItem> armorClass) {
        AtomicBoolean fullset = new AtomicBoolean(true);

        var playerArmor = player.getArmorSlots();
        playerArmor.forEach(piece -> {
            if (!armorClass.isInstance(piece.getItem())) fullset.set(false);
        });

        return fullset.get();
    }

    public static int countPlayerArmorSet(Player player, Class<? extends ArmorItem> armorClass) {
        AtomicInteger pieceCount = new AtomicInteger(0);

        var playerArmor = player.getArmorSlots();
        playerArmor.forEach(piece -> {
            if (armorClass.isInstance(piece.getItem())) pieceCount.incrementAndGet();
        });

        return pieceCount.get();
    }

    public static boolean doesPlayerHoldManaWeapon(Player player) {
        if(player.getMainHandItem().getItem() instanceof AbstractManaItem || player.getOffhandItem().getItem() instanceof AbstractManaItem) return true;
        if(player.getMainHandItem().getItem().equals(ItemRegistry.MANA_CRYSTAL.get()) || player.getOffhandItem().getItem().equals(ItemRegistry.MANA_CRYSTAL.get())) return true;
        return false;
    }

    public static Item getZapinatorFromEnum(ZapinatorType type) {
        return ItemRegistry.ITEMS.getRegistrar().get(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, type.toString().toLowerCase()+"_zapinator"));
    }

    public static Item getCoreFromEnum(ZapinatorType type) {
        return ItemRegistry.ITEMS.getRegistrar().get(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, type.toString().toLowerCase()+"_core"));
    }

    public static void spawnLightning(Vec3 pos, Level level) {
        Entity lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
        lightning.setPos(pos);
        level.addFreshEntity(lightning);
    }

    public static boolean entityFallenDespawnStarCheck(Entity ent) {
            if (ent.level().isDay() && !ent.level().isClientSide && ent.tickCount > 100) {
                BlockPos blockPos = BlockPos.containing(ent.getX(), ent.getY(), ent.getZ());
                if (ent.level().canSeeSky(blockPos)) {
                    return true;
                }
            }
            return false;
    }

    public static int getMoonPhase(Level world) {
        long dayTime = world.getDayTime();
        return (int)((dayTime / 24000L) % 8L);
    }

    public static double getMoonMultiplier(Level world) {
        int phase = getMoonPhase(world);
		return switch (phase) {
			case 0 -> 2.0;
			case 1, 7 -> 1.5;
			case 2, 6 -> 1.0;
			case 3, 5 -> 0.5;
			default -> 0.2;
		};
    }

    public static int getRandomBrightColor(RandomSource random) {
        int red = 128 + random.nextInt(128);
        int green = 128 + random.nextInt(128);
        int blue = 128 + random.nextInt(128);
        return (red << 16) | (green << 8) | blue;
    }

    public static boolean shouldProjectileForceRemove(Projectile entity) {
        if (entity.tickCount > 200) return true;
        if (!entity.level().hasChunk(entity.chunkPosition().x, entity.chunkPosition().z)) return true;

        if (!entity.level().isClientSide) {
            Entity owner = entity.getOwner();
            if (owner != null && owner.distanceToSqr(entity) > 96 * 96) return true;

            boolean nearPlayer = entity.level().players().stream()
                    .anyMatch(player -> player.distanceToSqr(entity) < 80 * 80);
            if (!nearPlayer) return true;

            if (entity.level() instanceof ServerLevel serverLevel) {
                return !serverLevel.isPositionEntityTicking(entity.blockPosition());
            }
        }

        return false;
    }

    // custom liquid check implementation because the default one decides not to work.
    public static boolean isEntityInLiquidCustom(Entity entity) {
        BlockPos pos = entity.blockPosition();
        BlockState blockState = entity.level().getBlockState(pos);

        return blockState.getBlock() instanceof LiquidBlock ||
                !blockState.getFluidState().isEmpty() ||
                blockState.is(Blocks.WATER) ||
                blockState.is(Blocks.LAVA);
    }

    public static boolean canEntityDamageEntity(Entity entity1, Entity entity2) {
        Team e1Team = entity1.getTeam();
        Team e2Team = entity2.getTeam();

        if(e1Team == null && e2Team == null) return true;

        if(e1Team != null && e1Team.equals(e2Team))
			return e1Team.isAllowFriendlyFire();

        // Open Parties and Claims Compatibility
        if(Platform.isModLoaded("openpartiesandclaims") && entity1 instanceof Player p1 && entity2 instanceof Player p2)
            return !OpacCompat.isPlayerTeammate(p1, p2);

        return true;
    }
}
