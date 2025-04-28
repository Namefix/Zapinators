package com.namefix.network.payload;

import com.namefix.network.ZapinatorsNetwork;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ManaStatusPayload(float mana, int manaRegenCooldown) implements CustomPacketPayload {
	public static final StreamCodec<RegistryFriendlyByteBuf, ManaStatusPayload> CODEC = StreamCodec.composite(ByteBufCodecs.FLOAT, ManaStatusPayload::mana, ByteBufCodecs.INT, ManaStatusPayload::manaRegenCooldown, ManaStatusPayload::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
			return ZapinatorsNetwork.MANA_STATUS;
	}
}
