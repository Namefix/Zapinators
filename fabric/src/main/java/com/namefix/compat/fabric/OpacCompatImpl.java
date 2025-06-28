package com.namefix.compat.fabric;

import net.minecraft.server.MinecraftServer;
import xaero.pac.common.server.api.OpenPACServerAPI;

import java.util.UUID;

public class OpacCompatImpl {
	public static UUID getPlayerTeam(UUID uuid, MinecraftServer server) {
		var team = OpenPACServerAPI.get(server).getPartyManager().getPartyByMember(uuid);
		if(team == null) return null;
		return team.getId();
	}
}
