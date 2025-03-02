package com.namefix.item;

import com.namefix.ZapinatorsMod;
import net.minecraft.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;
import java.util.Map;

public class ModArmorMaterials {
    public static final ArmorMaterial METEORITE_ARMOR = new ArmorMaterial(17, (Map) Util.make(new EnumMap(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 3);
        enumMap.put(ArmorType.LEGGINGS, 5);
        enumMap.put(ArmorType.CHESTPLATE, 5);
        enumMap.put(ArmorType.HELMET, 3);
        enumMap.put(ArmorType.BODY, 4);
    }), 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, ItemTags.REPAIRS_IRON_ARMOR, ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID,"meteorite")));
}
