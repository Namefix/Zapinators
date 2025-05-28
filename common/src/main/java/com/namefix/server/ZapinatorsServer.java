package com.namefix.server;

import com.namefix.config.ZapinatorsConfig;
import com.namefix.data.PlayerData;
import com.namefix.data.StateSaver;
import com.namefix.entity.FallenStar;
import com.namefix.network.payload.InitialSyncPayload;
import com.namefix.network.payload.ManaStatusPayload;
import com.namefix.registry.AttributeRegistry;
import com.namefix.registry.EntityRegistry;
import com.namefix.utils.Utils;
import dev.architectury.networking.NetworkManager;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class ZapinatorsServer {
	static int baseFallingStarDropRate = 8000;

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

		int dropRate = ZapinatorsConfig.Server.fallenStarMoonPhase ? (int) (baseFallingStarDropRate / Utils.getMoonMultiplier(serverLevel)) : baseFallingStarDropRate;
		if(overworld.isNight() && overworld.random.nextIntBetweenInclusive(0, dropRate) == 0) {
			Player randomPlayer = overworld.getRandomPlayer();
			if(randomPlayer == null) return;
			spawnFallenStar(randomPlayer);
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
