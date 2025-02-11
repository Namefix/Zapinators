package com.namefix.fabric.datagen;

import com.namefix.registry.BlockRegistry;
import com.namefix.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ZapinatorsModLootTableProvider extends FabricBlockLootTableProvider {
    protected ZapinatorsModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropSelf(BlockRegistry.METEORITE_BLOCK.getA().get());
        dropSelf(BlockRegistry.RAW_METEORITE_BLOCK.getA().get());

        add(BlockRegistry.METEORITE_ORE.getA().get(), createOreDrop(BlockRegistry.METEORITE_ORE.getA().get(), ItemRegistry.RAW_METEORITE.get()));
    }
}
