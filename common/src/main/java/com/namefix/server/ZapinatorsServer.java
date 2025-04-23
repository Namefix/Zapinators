package com.namefix.server;

import com.namefix.data.PlayerData;
import com.namefix.data.StateSaver;
import com.namefix.network.payload.InitialSyncPayload;
import dev.architectury.networking.NetworkManager;
import net.minecraft.server.level.ServerPlayer;

public class ZapinatorsServer {
	public static void sendInitialSync(ServerPlayer player) {
		PlayerData data = StateSaver.getPlayerState(player);
		NetworkManager.sendToPlayer(player, new InitialSyncPayload(data.mana, data.maxMana));
	}
}
