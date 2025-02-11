package com.namefix.fabric.datagen;

import com.namefix.registry.BlockRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ZapinatorsModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ZapinatorsModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(BlockRegistry.METEORITE_ORE.getA().get())
                .add(BlockRegistry.RAW_METEORITE_BLOCK.getA().get())
                .add(BlockRegistry.METEORITE_BLOCK.getA().get());

        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BlockRegistry.METEORITE_ORE.getA().get())
                .add(BlockRegistry.RAW_METEORITE_BLOCK.getA().get())
                .add(BlockRegistry.METEORITE_BLOCK.getA().get());
    }
}
