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

		@ConfigEntry(
				id = "beesApplyKnockback",
				translation = "config.zapinators.beesApplyKnockback"
		)
		public static boolean beesApplyKnockback = false;

		@ConfigOption.Range(min=0f, max=100f)
		@ConfigEntry(
				id = "zapinatorDamageMultiplier",
				translation = "config.zapinators.zapinatorDamageMultiplier"
		)
		public static float zapinatorDamageMultiplier = 1.0f;

		@ConfigOption.Range(min=0f, max=100f)
		@ConfigEntry(
				id = "spaceGunDamageMultiplier",
				translation = "config.zapinators.spaceGunDamageMultiplier"
		)
		public static float spaceGunDamageMultiplier = 1.0f;

		@ConfigOption.Range(min=0f, max=100f)
		@ConfigEntry(
				id = "laserRifleDamageMultiplier",
				translation = "config.zapinators.laserRifleDamageMultiplier"
		)
		public static float laserRifleDamageMultiplier = 1.0f;

		@ConfigOption.Range(min=0f, max=100f)
		@ConfigEntry(
				id = "beeDamageMultiplier",
				translation = "config.zapinators.beeDamageMultiplier"
		)
		public static float beeDamageMultiplier = 1.0f;

		@ConfigOption.Range(min=0f, max=100f)
		@ConfigEntry(
				id = "sparkingDamageMultiplier",
				translation = "config.zapinators.sparkingDamageMultiplier"
		)
		public static float sparkingDamageMultiplier = 1.0f;

		@ConfigOption.Range(min=0f, max=100f)
		@ConfigEntry(
				id = "gemStavesDamageMultiplier",
				translation = "config.zapinators.gemStavesDamageMultiplier"
		)
		public static float gemStavesDamageMultiplier = 1.0f;
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

		@ConfigEntry(
				id = "manaRefillSound",
				translation = "config.zapinators.manaRefillSound"
		)
		public static boolean manaRefillSound = true;
	}
}
