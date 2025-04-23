package com.namefix.network.payload;

import com.namefix.network.ZapinatorsNetwork;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record InitialSyncPayload(float mana, float maxMana) implements CustomPacketPayload {
	public static final StreamCodec<RegistryFriendlyByteBuf, InitialSyncPayload> CODEC = StreamCodec.composite(ByteBufCodecs.FLOAT, InitialSyncPayload::mana, ByteBufCodecs.FLOAT, InitialSyncPayload::maxMana, InitialSyncPayload::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ZapinatorsNetwork.INITIAL_SYNC;
	}
}
