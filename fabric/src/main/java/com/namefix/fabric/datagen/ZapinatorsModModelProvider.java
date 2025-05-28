package com.namefix.fabric.datagen;

import com.namefix.ZapinatorsMod;
import com.namefix.item.ModArmorMaterials;
import com.namefix.registry.BlockRegistry;
import com.namefix.registry.ItemRegistry;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.model.Model;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class ZapinatorsModModelProvider extends FabricModelProvider {
    public ZapinatorsModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createTrivialCube(BlockRegistry.METEORITE_ORE.getA().get());
        blockModelGenerators.createTrivialCube(BlockRegistry.RAW_METEORITE_BLOCK.getA().get());
        blockModelGenerators.createTrivialCube(BlockRegistry.METEORITE_BLOCK.getA().get());
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        ModelTemplate gunTemplate = new ModelTemplate(Optional.of(ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "item/gun")), Optional.empty(), TextureSlot.LAYER0);;

        itemModelGenerators.generateFlatItem(ItemRegistry.SPACE_GUN.get(), gunTemplate);
        itemModelGenerators.generateFlatItem(ItemRegistry.LASER_RIFLE.get(), gunTemplate);
        itemModelGenerators.generateFlatItem(ItemRegistry.ZAPINATOR.get(), gunTemplate);
        itemModelGenerators.generateFlatItem(ItemRegistry.GRAY_ZAPINATOR.get(), gunTemplate);
        itemModelGenerators.generateFlatItem(ItemRegistry.ORANGE_ZAPINATOR.get(), gunTemplate);
        itemModelGenerators.generateFlatItem(ItemRegistry.RED_ZAPINATOR.get(), gunTemplate);
        itemModelGenerators.generateFlatItem(ItemRegistry.GREEN_ZAPINATOR.get(), gunTemplate);
        itemModelGenerators.generateFlatItem(ItemRegistry.BLUE_ZAPINATOR.get(), gunTemplate);
        itemModelGenerators.generateFlatItem(ItemRegistry.PURPLE_ZAPINATOR.get(), gunTemplate);
        itemModelGenerators.generateFlatItem(ItemRegistry.BLACK_ZAPINATOR.get(), gunTemplate);
        itemModelGenerators.generateFlatItem(ItemRegistry.YELLOW_ZAPINATOR.get(), gunTemplate);
        itemModelGenerators.generateFlatItem(ItemRegistry.WHITE_ZAPINATOR.get(), gunTemplate);

        itemModelGenerators.generateFlatItem(ItemRegistry.GRAY_CORE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.ORANGE_CORE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.RED_CORE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.GREEN_CORE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.BLUE_CORE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.PURPLE_CORE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.BLACK_CORE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.YELLOW_CORE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.WHITE_CORE.get(), ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ItemRegistry.BEE_GUN.get(), gunTemplate);
        itemModelGenerators.generateFlatItem(ItemRegistry.WAND_OF_SPARKING.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModelGenerators.generateFlatItem(ItemRegistry.ECHO_STAFF.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.DIAMOND_STAFF.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.EMERALD_STAFF.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.AMETHYST_STAFF.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.LAPIS_STAFF.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.REDSTONE_STAFF.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModelGenerators.generateFlatItem(ItemRegistry.RUNE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.ECHO_RUNE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.DIAMOND_RUNE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.EMERALD_RUNE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.AMETHYST_RUNE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.LAPIS_RUNE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.REDSTONE_RUNE.get(), ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ItemRegistry.ENERGY_CELL.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.FALLEN_STAR.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.MANA_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ItemRegistry.RAW_METEORITE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.METEORITE_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.METEORITE_NUGGET.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ItemRegistry.ZAPINATOR_RESET_SMITHING_TEMPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateTrimmableItem(ItemRegistry.METEORITE_HELMET.get(), ModArmorMaterials.METEORITE_ARMOR.assetId(), "helmet", false);
        itemModelGenerators.generateTrimmableItem(ItemRegistry.METEORITE_CHESTPLATE.get(), ModArmorMaterials.METEORITE_ARMOR.assetId(), "chestplate", false);
        itemModelGenerators.generateTrimmableItem(ItemRegistry.METEORITE_LEGGINGS.get(), ModArmorMaterials.METEORITE_ARMOR.assetId(), "leggings", false);
        itemModelGenerators.generateTrimmableItem(ItemRegistry.METEORITE_BOOTS.get(), ModArmorMaterials.METEORITE_ARMOR.assetId(), "boots", false);
    }
}
