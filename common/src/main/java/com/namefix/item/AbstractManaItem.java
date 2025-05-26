package com.namefix.item;

import com.namefix.client.ZapinatorsClient;
import com.namefix.data.PlayerData;
import com.namefix.server.ZapinatorsServer;
import com.namefix.utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class AbstractManaItem extends Item {
	boolean meteoriteArmorSavesMana = false;
	float manaCost = 1.0f;

	public AbstractManaItem(Properties properties) {
		super(properties);
	}

	public boolean canPlayerUseManaItem(Level level, Player player) {
		boolean meteoriteSetBonus = Utils.getPlayerMeteoriteSetBonus(player);
		if(!meteoriteArmorSavesMana) meteoriteSetBonus = false;

		if(!player.isCreative() && !meteoriteSetBonus && manaCost > 0.0f) {
			if (level.isClientSide()) {
				return !(ZapinatorsClient.mana < manaCost);
			} else {
				PlayerData data = ZapinatorsServer.getPlayerData((ServerPlayer) player);
				return !(data.mana < manaCost);
			}
		}
		return true;
	}

	public void useManaItem(Level level, Player player, boolean meteoriteArmorSavesMana) {
		boolean meteoriteSetBonus = Utils.getPlayerMeteoriteSetBonus(player);
		if(!player.isCreative() && manaCost > 0.0f && (!meteoriteSetBonus || !meteoriteArmorSavesMana)) {
			if(level.isClientSide()) {
				ZapinatorsClient.decreaseMana(manaCost, true);
			} else {
				ZapinatorsServer.decreaseMana((ServerPlayer) player, manaCost, true);
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
		if (manaCost > 0.0f) {
			String manaFormatted = (manaCost == (int) manaCost)
					? String.valueOf((int) manaCost)
					: String.format("%.1f", manaCost);
			list.add(Component.translatable("item.zapinators.description.mana_usage", manaFormatted).withStyle(ChatFormatting.BLUE));
		}

		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
	}
}
