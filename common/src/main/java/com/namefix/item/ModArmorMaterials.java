package com.namefix.item;

import com.namefix.ZapinatorsMod;
import com.namefix.registry.ItemRegistry;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ModArmorMaterials {
    public static final ArmorMaterial METEORITE_ARMOR = new ArmorMaterial((Map) Util.make(new EnumMap(Type.class), (enumMap) -> {
        enumMap.put(Type.BOOTS, 3);
        enumMap.put(Type.LEGGINGS, 5);
        enumMap.put(Type.CHESTPLATE, 5);
        enumMap.put(Type.HELMET, 3);
        enumMap.put(Type.BODY, 4);
    }), 9, SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(ItemRegistry.METEORITE_INGOT.get()), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "meteorite"))), 0.0F, 0.0F);

    public static final ArmorMaterial BEE_ARMOR = new ArmorMaterial((Map) Util.make(new EnumMap(Type.class), (enumMap) -> {
        enumMap.put(Type.BOOTS, 3);
        enumMap.put(Type.LEGGINGS, 5);
        enumMap.put(Type.CHESTPLATE, 5);
        enumMap.put(Type.HELMET, 2);
        enumMap.put(Type.BODY, 3);
    }), 8, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.HONEYCOMB), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "bee"))), 0.0F, 0.0F);
}
