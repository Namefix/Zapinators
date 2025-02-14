package com.namefix.fabric.datagen;

import com.namefix.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ZapinatorsModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ZapinatorsModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR).add(ItemRegistry.METEORITE_HELMET.get());
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR).add(ItemRegistry.METEORITE_CHESTPLATE.get());
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR).add(ItemRegistry.METEORITE_LEGGINGS.get());
        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR).add(ItemRegistry.METEORITE_BOOTS.get());
    }
}
