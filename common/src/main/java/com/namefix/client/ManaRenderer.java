package com.namefix.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.namefix.ZapinatorsMod;
import com.namefix.config.ZapinatorsConfig;
import com.namefix.registry.AttributeRegistry;
import com.namefix.utils.Utils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ManaRenderer {
	private static final ResourceLocation MANA_STAR_TOP = ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "textures/gui/mana/mana_top.png");
	private static final ResourceLocation MANA_STAR_MIDDLE = ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "textures/gui/mana/mana_mid.png");
	private static final ResourceLocation MANA_STAR_BOTTOM = ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "textures/gui/mana/mana_bot.png");
	private static final ResourceLocation MANA_STAR_SINGLE = ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "textures/gui/mana/mana_single.png");
	private static final ResourceLocation MANA_STAR_FULL = ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "textures/gui/mana/mana_full.png");

	public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
		Minecraft mc = Minecraft.getInstance();
		if(
				mc.options.hideGui ||
				mc.player.isCreative() ||
				mc.player.isSpectator() ||
				ZapinatorsConfig.Client.manaHudStyle.equals(ZapinatorsConfig.ManaHudStyle.DISABLED)
		) return;

		double maxMana = mc.player.getAttributeValue(AttributeRegistry.getHolder(AttributeRegistry.MAX_MANA));
		if(maxMana == 0) return;

		if(ZapinatorsConfig.Client.manaHudHideAuto && !Utils.doesPlayerHoldManaWeapon(mc.player) && ZapinatorsClient.mana == maxMana) return;

		boolean isCustom = ZapinatorsConfig.Client.manaHudStyle.equals(ZapinatorsConfig.ManaHudStyle.CUSTOM);
		int baseManaX = guiGraphics.guiWidth() - 24;
		int baseManaY = 10;
		float manaSize = 0.5f;

		if(isCustom) {
			baseManaX = ZapinatorsConfig.Client.manaHudX;
			baseManaY = ZapinatorsConfig.Client.manaHudY;
			manaSize = ZapinatorsConfig.Client.manaHudScale;
		}

		if(!mc.player.getActiveEffects().isEmpty() && !isCustom)
			baseManaY += 48;

		int manaX = Math.round(baseManaX/manaSize);
		int manaY = Math.round(baseManaY/manaSize);

		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		guiGraphics.pose().pushPose();
		guiGraphics.pose().scale(manaSize, manaSize, manaSize);

		if(maxMana <= 20.0) {
			guiGraphics.blit(MANA_STAR_SINGLE, manaX, manaY, 0.0f, 0.0f, 30, 32, 30, 32);
		} else {
			int starAmount = Mth.ceil(maxMana / 20);
			int midStarAmount = Math.max(starAmount - 2, 0);

			guiGraphics.blit(MANA_STAR_TOP, manaX, manaY, 0.0f, 0.0f, 30, 26, 30, 26);
			
			for (int i = 0; i < midStarAmount; i++) {
				guiGraphics.blit(MANA_STAR_MIDDLE, manaX, manaY + 26 + (i * 22), 0.0f, 0.0f, 30, 22, 30, 22);
			}
			
			guiGraphics.blit(MANA_STAR_BOTTOM, manaX, manaY + 26 + (midStarAmount * 22), 0.0f, 0.0f, 30, 28, 30, 28);
		}

		if(ZapinatorsClient.mana > 0) {
			int fullStars = Mth.floor(ZapinatorsClient.mana / 20);
			int manaRemainder = (int)(ZapinatorsClient.mana % 20);

			for (int i = 0; i < fullStars; i++) {
				guiGraphics.blit(MANA_STAR_FULL, manaX + 4, manaY + 4 + (i * 22), 0.0f, 0.0f, 22, 24, 22, 24);
			}

			if (manaRemainder > 0) {
				float fillFraction = manaRemainder / 20f;
				int fullStarWidth = 22;
				int fullStarHeight = 24;

				guiGraphics.pose().pushPose();
				guiGraphics.pose().translate(manaX + 4 + fullStarWidth / 2.0f, manaY + 4 + (fullStars * 22) + fullStarHeight / 2.0f, 0);
				guiGraphics.pose().scale(fillFraction, fillFraction, 1);
				guiGraphics.blit(MANA_STAR_FULL, -fullStarWidth / 2, -fullStarHeight / 2, 0.0f, 0.0f, fullStarWidth, fullStarHeight, fullStarWidth, fullStarHeight);
				guiGraphics.pose().popPose();
			}
		}

		guiGraphics.pose().popPose();
		RenderSystem.disableBlend();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
	}
}
