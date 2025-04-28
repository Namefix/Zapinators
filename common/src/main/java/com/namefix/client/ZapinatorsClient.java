package com.namefix.client;

import com.namefix.network.payload.InitialSyncPayload;
import com.namefix.network.payload.ManaStatusPayload;
import com.namefix.registry.AttributeRegistry;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;

public class ZapinatorsClient {
	public static float mana = 0f;
	public static int manaRegenCooldown = 0;

	public static void handleInitialSync(InitialSyncPayload payload, NetworkManager.PacketContext context) {
		mana = payload.mana();
		manaRegenCooldown = payload.manaRegenCooldown();
	}

	public static void handleManaStatus(ManaStatusPayload payload, NetworkManager.PacketContext context) {
		mana = payload.mana();
		manaRegenCooldown = payload.manaRegenCooldown();
	}

	public static void tick(ClientLevel clientLevel) {
		Minecraft mc = Minecraft.getInstance();
		if(mc.player == null || mc.player.isDeadOrDying()) return;

		float maxMana = (float) mc.player.getAttributeValue(AttributeRegistry.getHolder(AttributeRegistry.MAX_MANA));
		float manaRegen = (float) mc.player.getAttributeValue(AttributeRegistry.getHolder(AttributeRegistry.MANA_REGENERATION));

		if(manaRegenCooldown > 0)
			manaRegenCooldown--;
		else
			mana = (float) Mth.clamp(mana+manaRegen, 0.0, maxMana);
	}

	public static void decreaseMana(float amount, boolean cooldown) {
		Minecraft mc = Minecraft.getInstance();
		float maxMana = (float) mc.player.getAttributeValue(AttributeRegistry.getHolder(AttributeRegistry.MAX_MANA));
		mana = Mth.clamp(mana-amount, 0.0f, maxMana);
		if(cooldown) manaRegenCooldown = 60;
	}
}
