package com.namefix.data;

import com.namefix.ZapinatorsMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.UUID;

public class StateSaver extends SavedData {

	public HashMap<UUID, PlayerData> players = new HashMap<>();

	@Override
	public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
		return compoundTag;
	}

	public static StateSaver createFromNbt(CompoundTag compoundTag, HolderLookup.Provider provider) {
		StateSaver state = new StateSaver();
		return state;
	}

	public static StateSaver createNew() {
		StateSaver state = new StateSaver();
		return state;
	}

	private static final Factory<StateSaver> type = new Factory<>(
			StateSaver::createNew,
			StateSaver::createFromNbt,
			null
	);

	public static StateSaver getServerState(MinecraftServer server) {
		ServerLevel world = server.getLevel(Level.OVERWORLD);
		assert world != null;

		StateSaver state = world.getDataStorage().get(type, ZapinatorsMod.MOD_ID);

		state.setDirty();
		return state;
	}

	public static PlayerData getPlayerState(LivingEntity player) {
		StateSaver serverState = getServerState(player.level().getServer());

		PlayerData playerState = serverState.players.computeIfAbsent(player.getUUID(), uuid -> new PlayerData());

		return playerState;
	}
}
