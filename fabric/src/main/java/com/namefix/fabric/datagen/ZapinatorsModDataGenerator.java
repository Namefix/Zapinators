package com.namefix.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ZapinatorsModDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ZapinatorsModModelProvider::new);
        pack.addProvider(ZapinatorsModRecipeProvider::new);
        pack.addProvider(ZapinatorsModBlockTagProvider::new);
        pack.addProvider(ZapinatorsModItemTagProvider::new);
        pack.addProvider(ZapinatorsModBlockLootTableProvider::new);
    }
}
