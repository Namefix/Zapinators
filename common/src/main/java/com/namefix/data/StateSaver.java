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
		CompoundTag playersTag = new CompoundTag();
		players.forEach((uuid, playerData) -> {
			CompoundTag playerTag = new CompoundTag();

			playerTag.putFloat("mana", playerData.mana);
			playerTag.putFloat("maxMana", playerData.maxMana);
			playerTag.putInt("manaRegenCooldown", playerData.manaRegenCooldown);

			playersTag.put(uuid.toString(), playerTag);
		});
		compoundTag.put("players", playersTag);
		return compoundTag;
	}

	public static StateSaver createFromNbt(CompoundTag compoundTag, HolderLookup.Provider provider) {
		StateSaver state = new StateSaver();
		CompoundTag playersTag = compoundTag.getCompound("players");
		playersTag.getAllKeys().forEach(key -> {
			PlayerData playerData = new PlayerData();

			playerData.mana = playersTag.getFloat(key);
			playerData.maxMana = playersTag.getFloat(key);
			playerData.manaRegenCooldown = playersTag.getInt(key);

			UUID uuid = UUID.fromString(key);
			state.players.put(uuid, playerData);
		});
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

		StateSaver state = world.getDataStorage().computeIfAbsent(type, ZapinatorsMod.MOD_ID);

		state.setDirty();
		return state;
	}

	public static PlayerData getPlayerState(LivingEntity player) {
		StateSaver serverState = getServerState(player.level().getServer());

		PlayerData playerState = serverState.players.computeIfAbsent(player.getUUID(), uuid -> new PlayerData());

		return playerState;
	}
}
