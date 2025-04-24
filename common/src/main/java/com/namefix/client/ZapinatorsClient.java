package com.namefix.client;

import com.namefix.network.payload.InitialSyncPayload;
import dev.architectury.networking.NetworkManager;

public class ZapinatorsClient {
	public static float mana = 0f;
	public static float maxMana = 20f;
	public static int manaRegenCooldown = 0;

	public static void handleInitialSync(InitialSyncPayload payload, NetworkManager.PacketContext context) {
		mana = payload.mana();
		maxMana = payload.maxMana();
		manaRegenCooldown = payload.manaRegenCooldown();
	}
}
