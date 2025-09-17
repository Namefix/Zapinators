package com.namefix.server;

import com.namefix.config.ZapinatorsConfig;
import com.namefix.data.PlayerData;
import com.namefix.data.StateSaver;
import com.namefix.entity.AngryBee;
import com.namefix.entity.FallenStar;
import com.namefix.item.BeeArmorItem;
import com.namefix.item.BeeGunItem;
import com.namefix.network.payload.InitialSyncPayload;
import com.namefix.network.payload.ManaStatusPayload;
import com.namefix.registry.AttributeRegistry;
import com.namefix.registry.EntityRegistry;
import com.namefix.utils.Utils;
import dev.architectury.event.EventResult;
import dev.architectury.networking.NetworkManager;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class ZapinatorsServer {
	static int baseFallingStarDropRate = 10000;

	public static void onPlayerRespawn(ServerPlayer player, boolean b, Entity.RemovalReason removalReason) {
		if(removalReason == Entity.RemovalReason.KILLED) {
			PlayerData data = StateSaver.getPlayerState(player);
			float maxMana = (float) player.getAttributeValue(AttributeRegistry.getHolder(AttributeRegistry.MAX_MANA));
			data.mana = maxMana;
			NetworkManager.sendToPlayer(player, new ManaStatusPayload(data.mana, data.manaRegenCooldown));
		}
	}

	public static void sendInitialSync(ServerPlayer player) {
		PlayerData data = StateSaver.getPlayerState(player);
		NetworkManager.sendToPlayer(player, new InitialSyncPayload(data.mana, data.manaRegenCooldown));
	}

	public static void tick(ServerLevel serverLevel) {
		serverLevel.players().forEach(player -> {
			if(player.isDeadOrDying()) return;

			PlayerData data = getPlayerData(player);
			float maxMana = (float) player.getAttributeValue(AttributeRegistry.getHolder(AttributeRegistry.MAX_MANA));
			float manaRegen = (float) player.getAttributeValue(AttributeRegistry.getHolder(AttributeRegistry.MANA_REGENERATION));

			if(data.manaRegenCooldown > 0)
				data.manaRegenCooldown--;
			else
				data.mana = (float) Mth.clamp(data.mana+manaRegen, 0.0, maxMana);
		});

		ServerLevel overworld = serverLevel.getServer().getLevel(ServerLevel.OVERWORLD);
		if(overworld == null) return;

		float dropMultiplier = 1.0f / ZapinatorsConfig.Server.fallenStarDropRateMultiplier;
		int moonDropMultiplier = ZapinatorsConfig.Server.fallenStarMoonPhase ? (int) (baseFallingStarDropRate / Utils.getMoonMultiplier(serverLevel)) : baseFallingStarDropRate;
		int dropRate = (int) (moonDropMultiplier * dropMultiplier);

		if(dropMultiplier > .0f && overworld.isNight()) {
			for(ServerPlayer player : overworld.players()) {
				if(overworld.random.nextIntBetweenInclusive(0, dropRate) == 0)
					spawnFallenStar(player);
			}
		}
	}

	public static void spawnFallenStar(Player player) {
		RandomSource random = player.level().random;
		double maxDistance = 80.0;
		double offsetX = (random.nextDouble() * 2 - 1) * maxDistance;
		double offsetZ = (random.nextDouble() * 2 - 1) * maxDistance;

		double spawnX = player.getX() + offsetX;
		double spawnZ = player.getZ() + offsetZ;

		boolean safe = player.position().y+100 > player.level().getHeight(Heightmap.Types.MOTION_BLOCKING, (int) spawnX, (int) spawnZ);
		if(!safe) return;
		int spawnY = (int) player.position().y+100;

		FallenStar star = new FallenStar(EntityRegistry.FALLEN_STAR.getOrNull(), player.level());
		star.setPos(spawnX, spawnY, spawnZ);

		// towards player calculation
		double targetOffsetX = (random.nextDouble() * 2 - 1) * 15;
		double targetOffsetZ = (random.nextDouble() * 2 - 1) * 15;
		double targetX = player.getX() + targetOffsetX;
		double targetZ = player.getZ() + targetOffsetZ;
		double targetY = player.getY();

		double dx = targetX - spawnX;
		double dy = targetY - spawnY;
		double dz = targetZ - spawnZ;

		if (dy > 0) {
			dy = -Math.abs(dy);
		}

		double length = Math.sqrt(dx * dx + dy * dy + dz * dz);
		double speed = 0.5;
		if (length != 0) {
			dx = dx / length * speed;
			dy = dy / length * speed;
			dz = dz / length * speed;
		}

		star.setDeltaMovement(dx, dy, dz);
		star.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(targetX, targetY, targetZ));
		player.level().addFreshEntity(star);
	}

	public static EventResult onPlayerDamage(LivingEntity livingEntity, DamageSource damageSource, float v) {
		if(!(livingEntity instanceof ServerPlayer player)) return EventResult.pass();

		if(v <= 0 || player.isCreative() || player.invulnerableTime > 0 || player.isInvulnerableTo((ServerLevel) player.level(), damageSource)) return EventResult.pass();

		if(Utils.getPlayerArmorFullSet(player, BeeArmorItem.class)) {
			Level level = player.level();
			int initialBeeAmount = level.getRandom().nextInt(1, 3);

			for (int i = 0; i < initialBeeAmount; i++) {
				double speed = BeeGunItem.BEE_SPEED;
				double offsetX = (level.getRandom().nextDouble() - 0.5) * 0.4;
				double offsetY = (level.getRandom().nextDouble() - 0.5) * 0.4;
				double offsetZ = (level.getRandom().nextDouble() - 0.5) * 0.4;

				AngryBee bee = new AngryBee(EntityRegistry.ANGRY_BEE.get(), level);
				bee.beeSpeed = speed;
				bee.beeSource = AngryBee.BeeSource.BEE_ARMOR;
				bee.baseDamage *= (1+0.05f*Utils.countPlayerArmorSet(player, BeeArmorItem.class))*ZapinatorsConfig.Server.beeDamageMultiplier;
				bee.setPos(
						player.getX() + offsetX,
						player.getEyeY() - 0.2 + offsetY,
						player.getZ() + offsetZ
				);

				double dx = (level.getRandom().nextDouble() - 0.5) * 2 * speed;
				double dy = (level.getRandom().nextDouble() - 0.5) * 2 * speed;
				double dz = (level.getRandom().nextDouble() - 0.5) * 2 * speed;
				bee.setDeltaMovement(dx, dy, dz);
				bee.setOwner(player);
				bee.setXRot(level.getRandom().nextFloat() * 360.0f);
				bee.setYRot(level.getRandom().nextFloat() * 360.0f);
				level.addFreshEntity(bee);
			}
		}

		return EventResult.pass();
	}

	public static PlayerData getPlayerData(ServerPlayer player) {
		return StateSaver.getPlayerState(player);
	}

	public static void decreaseMana(ServerPlayer player, float amount, boolean cooldown) {
		PlayerData data = getPlayerData(player);
		float maxMana = (float) player.getAttributeValue(AttributeRegistry.getHolder(AttributeRegistry.MAX_MANA));
		data.mana = Mth.clamp(data.mana-amount, 0.0f, maxMana);
		if(cooldown) data.manaRegenCooldown = 60;

		NetworkManager.sendToPlayer(player, new ManaStatusPayload(data.mana, data.manaRegenCooldown));
	}
}
