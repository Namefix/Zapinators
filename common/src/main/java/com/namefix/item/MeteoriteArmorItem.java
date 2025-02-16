package com.namefix.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.List;

public class MeteoriteArmorItem extends ArmorItem {
    private int savePercent;

    public MeteoriteArmorItem(ArmorMaterial armorMaterial, ArmorType armorType, Properties properties, int savePercent) {
        super(armorMaterial, armorType, properties);
        this.savePercent = savePercent;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.zapinators.meteorite_armor.save_percent_tooltip", savePercent).withStyle(ChatFormatting.BLUE));

        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }

    public int getSavePercent() {
        return savePercent;
    }
}
