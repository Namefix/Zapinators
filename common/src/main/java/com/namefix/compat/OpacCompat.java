package com.namefix.compat;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class OpacCompat {
	public static boolean isPlayerTeammate(Player player1, Player player2) {
		UUID team1 = getPlayerTeam(player1.getUUID(), player1.getServer());
		UUID team2 = getPlayerTeam(player2.getUUID(), player2.getServer());
		if(team1 == null || team2 == null) return false;
		return team1.equals(team2);
	}

	@ExpectPlatform
	public static UUID getPlayerTeam(UUID uuid, MinecraftServer server) {
		throw new AssertionError();
	}
}
