package com.namefix.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.namefix.ZapinatorsMod;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ManaRenderer {
	private static final ResourceLocation MANA_STAR_TOP = ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "textures/gui/mana/mana_top.png");
	private static final ResourceLocation MANA_STAR_MIDDLE = ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "textures/gui/mana/mana_mid.png");
	private static final ResourceLocation MANA_STAR_BOTTOM = ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "textures/gui/mana/mana_bot.png");
	private static final ResourceLocation MANA_STAR_SINGLE = ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "textures/gui/mana/mana_single.png");
	private static final ResourceLocation MANA_STAR_FULL = ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "textures/gui/mana/mana_full.png");

	public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
		Minecraft minecraft = Minecraft.getInstance();

		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		guiGraphics.blit(RenderType::guiTextured, MANA_STAR_SINGLE, 0, 0, 0.0f, 0.0f, 30, 32, 30, 32, 30, 32, -1);
		RenderSystem.disableBlend();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
	}
}
