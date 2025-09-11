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
    public MeteoriteArmorItem(ArmorMaterial armorMaterial, ArmorType armorType, Properties properties) {
        super(armorMaterial, armorType, properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.empty());
        list.add(Component.translatable("item.zapinators.description.armor_full_set_bonus").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("item.zapinators.meteorite_armor.description").withStyle(ChatFormatting.BLUE));
        if(!itemStack.getEnchantments().isEmpty()) list.add(Component.empty());

        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }
}
