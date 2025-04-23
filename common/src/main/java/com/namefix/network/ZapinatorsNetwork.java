package com.namefix.network;

import com.namefix.ZapinatorsMod;
import com.namefix.client.ZapinatorsClient;
import com.namefix.network.payload.InitialSyncPayload;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class ZapinatorsNetwork {
	public static final CustomPacketPayload.Type<InitialSyncPayload> INITIAL_SYNC = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "initial_sync"));

	public static void initialize() {
		NetworkManager.registerReceiver(NetworkManager.Side.S2C, INITIAL_SYNC, InitialSyncPayload.CODEC, ZapinatorsClient::handleInitialSync);
	}

	public static void initializeClient() {

	}
}
