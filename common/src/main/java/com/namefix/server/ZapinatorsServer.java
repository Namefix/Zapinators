package com.namefix.server;

import com.namefix.data.PlayerData;
import com.namefix.data.StateSaver;
import com.namefix.network.payload.InitialSyncPayload;
import com.namefix.network.payload.ManaStatusPayload;
import com.namefix.registry.AttributeRegistry;
import dev.architectury.networking.NetworkManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;

public class ZapinatorsServer {
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
