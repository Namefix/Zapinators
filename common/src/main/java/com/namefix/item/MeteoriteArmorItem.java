package com.namefix.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class MeteoriteArmorItem extends ArmorItem {
    public MeteoriteArmorItem(ArmorMaterial armorMaterial, Type armorType, Properties properties) {
        super(Holder.direct(armorMaterial), armorType, properties);
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
