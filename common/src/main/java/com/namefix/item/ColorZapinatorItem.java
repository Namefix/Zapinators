package com.namefix.item;

import com.namefix.enums.ZapinatorType;
import com.namefix.registry.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ColorZapinatorItem extends AbstractLaserGunItem {

    public ColorZapinatorItem(Properties properties, ZapinatorType zapinatorType, float baseDamage, float manaCost) {
        super(properties);
        this.zapinatorType = zapinatorType;
        this.color = 0x5BBCEA;
        this.baseDamage = baseDamage;
        this.itemCooldown = 20;
        this.laserLength = 2.5f;
        this.maxPiercing = 8;
        this.manaCost = manaCost;
        this.shootSound = SoundRegistry.COLOR_ZAPINATOR_SHOOT.getOrNull();
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        switch(this.zapinatorType) {
            case GRAY, ORANGE:
                list.add(Component.translatable("item.zapinators.color_zapinator.description").withStyle(ChatFormatting.GRAY));
                break;
            case RED:
                list.add(Component.translatable("item.zapinators.red_zapinator.description").withStyle(ChatFormatting.GRAY));
                break;
            case GREEN:
                list.add(Component.translatable("item.zapinators.green_zapinator.description").withStyle(ChatFormatting.GRAY));
                break;
            case BLUE:
                list.add(Component.translatable("item.zapinators.blue_zapinator.description").withStyle(ChatFormatting.GRAY));
                break;
            case PURPLE:
                list.add(Component.translatable("item.zapinators.purple_zapinator.description").withStyle(ChatFormatting.GRAY));
                break;
            case BLACK:
                list.add(Component.literal("One shot, one kill").withStyle(ChatFormatting.DARK_GRAY));
                list.add(Component.translatable("item.zapinators.black_zapinator.description").withStyle(ChatFormatting.GOLD));
                break;
            case YELLOW:
                list.add(Component.translatable("item.zapinators.yellow_zapinator.description").withStyle(ChatFormatting.GRAY));
                break;
            case WHITE:
                list.add(Component.translatable("item.zapinators.white_zapinator.description").withStyle(ChatFormatting.GRAY));
                break;
        }
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }
}
