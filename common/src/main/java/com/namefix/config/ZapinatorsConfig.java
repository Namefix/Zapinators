package com.namefix.config;

import com.namefix.ZapinatorsMod;
import com.teamresourceful.resourcefulconfig.api.annotations.Config;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigOption;

@Config(value = ZapinatorsMod.MOD_ID)
@ConfigInfo.Provider(ZapinatorsConfigInfoProvider.class)
public class ZapinatorsConfig {

	@ConfigOption.Slider
	@ConfigOption.Range(min=0f, max=10f)
	@ConfigEntry(
			id = "zapinatorDamageMultiplier",
			translation = "config.zapinators.zapinatorDamageMultiplier"
	)
	public static float zapinatorDamageMultiplier = 1.0f;
}
