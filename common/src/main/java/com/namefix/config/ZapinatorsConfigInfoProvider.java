package com.namefix.config;

import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigColor;
import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigColorValue;
import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigInfo;
import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigLink;
import com.teamresourceful.resourcefulconfig.api.types.options.TranslatableValue;

public class ZapinatorsConfigInfoProvider implements ResourcefulConfigInfo {
	@Override
	public TranslatableValue title() {
		return new TranslatableValue("Zapinators");
	}

	@Override
	public TranslatableValue description() {
		return new TranslatableValue("Zapinators", "config.zapinators.description");
	}

	@Override
	public String icon() {
		return "tree-deciduous";
	}

	@Override
	public ResourcefulConfigColor color() {
		return (ResourcefulConfigColorValue) () -> "#111111";
	}

	@Override
	public ResourcefulConfigLink[] links() {
		return new ResourcefulConfigLink[0];
	}

	@Override
	public boolean isHidden() {
		return false;
	}
}
