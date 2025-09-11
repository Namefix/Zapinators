package com.namefix.item;

import com.namefix.ZapinatorsMod;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
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
    }), 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "repairs_meteorite_armor")), ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID,"meteorite")));

    public static final ArmorMaterial BEE_ARMOR = new ArmorMaterial(15, (Map) Util.make(new EnumMap(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 2);
        enumMap.put(ArmorType.LEGGINGS, 4);
        enumMap.put(ArmorType.CHESTPLATE, 5);
        enumMap.put(ArmorType.HELMET, 3);
        enumMap.put(ArmorType.BODY, 3);
    }), 8, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "repairs_bee_armor")), ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "bee")));
}
