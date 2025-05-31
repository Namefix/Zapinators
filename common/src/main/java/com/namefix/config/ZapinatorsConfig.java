package com.namefix.config;

import com.namefix.ZapinatorsMod;
import com.teamresourceful.resourcefulconfig.api.annotations.*;

@Config(value = ZapinatorsMod.MOD_ID, categories = {ZapinatorsConfig.Server.class, ZapinatorsConfig.Client.class})
@ConfigInfo.Provider(ZapinatorsConfigInfoProvider.class)
public class ZapinatorsConfig {
	public enum ManaHudStyle {
		DISABLED,
		STAR1,
		CUSTOM
	}

	public enum LaserRenderStyle {
		FANCY,
		FAST
	}

	@Category(value = "Server")
	public static class Server {
		@ConfigOption.Range(min=0f, max=10f)
		@ConfigEntry(
				id = "zapinatorDamageMultiplier",
				translation = "config.zapinators.zapinatorDamageMultiplier"
		)
		public static float zapinatorDamageMultiplier = 1.0f;

		@Comment(value = "fallenStarEntityDamageDesc", translation = "config.zapinators.fallenStarEntityDamage.description")
		@ConfigEntry(
				id = "fallenStarEntityDamage",
				translation = "config.zapinators.fallenStarEntityDamage"
		)
		public static float fallenStarEntityDamage = 0.0f;

		@Comment(value = "fallenStarMoonPhaseDesc", translation = "config.zapinators.fallenStarMoonPhase.description")
		@ConfigEntry(
				id = "fallenStarMoonPhase",
				translation = "config.zapinators.fallenStarMoonPhase"
		)
		public static boolean fallenStarMoonPhase = true;
	}

	@Category(value = "Client")
	public static class Client {
		@ConfigEntry(
				id = "manaHudStyle",
				translation = "config.zapinators.manaHudStyle"
		)
		public static ManaHudStyle manaHudStyle = ManaHudStyle.STAR1;

		@Comment(value = "manaHudHideAutoDesc", translation = "config.zapinators.manaHudHideAuto.description")
		@ConfigEntry(
				id = "manaHudHideAuto",
				translation = "config.zapinators.manaHudHideAuto"
		)
		public static boolean manaHudHideAuto = true;

		@ConfigEntry(
				id = "customManaHudX",
				translation = "config.zapinators.customManaHudX"
		)
		public static int manaHudX = 0;

		@ConfigEntry(
				id = "customManaHudY",
				translation = "config.zapinators.customManaHudY"
		)
		public static int manaHudY = 0;

		@ConfigEntry(
				id = "customManaHudScale",
				translation = "config.zapinators.customManaHudScale"
		)
		public static float manaHudScale = 0.5f;

		@ConfigEntry(
				id = "laserRenderStyle",
				translation = "config.zapinators.laserRenderStyle"
		)
		public static LaserRenderStyle laserRenderStyle = LaserRenderStyle.FANCY;
	}
}
