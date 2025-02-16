package com.namefix.fabric.datagen;

import com.namefix.ZapinatorsMod;
import com.namefix.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class ZapinatorsModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ZapinatorsModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    public static TagKey<Item> COLOR_ZAPINATORS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "color_zapinators"));
    public static TagKey<Item> ZAPINATOR_CORES = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "zapinator_cores"));

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR).add(ItemRegistry.METEORITE_HELMET.get());
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR).add(ItemRegistry.METEORITE_CHESTPLATE.get());
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR).add(ItemRegistry.METEORITE_LEGGINGS.get());
        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR).add(ItemRegistry.METEORITE_BOOTS.get());

        getOrCreateTagBuilder(COLOR_ZAPINATORS)
                .add(ItemRegistry.GRAY_ZAPINATOR.get())
                .add(ItemRegistry.ORANGE_ZAPINATOR.get())
                .add(ItemRegistry.RED_ZAPINATOR.get())
                .add(ItemRegistry.GREEN_ZAPINATOR.get())
                .add(ItemRegistry.BLUE_ZAPINATOR.get())
                .add(ItemRegistry.PURPLE_ZAPINATOR.get())
                .add(ItemRegistry.BLACK_ZAPINATOR.get());

        getOrCreateTagBuilder(ZAPINATOR_CORES)
                .add(ItemRegistry.GRAY_CORE.get())
                .add(ItemRegistry.ORANGE_CORE.get())
                .add(ItemRegistry.RED_CORE.get())
                .add(ItemRegistry.GREEN_CORE.get())
                .add(ItemRegistry.BLUE_CORE.get())
                .add(ItemRegistry.PURPLE_CORE.get())
                .add(ItemRegistry.BLACK_CORE.get());
    }
}
