package com.namefix.fabric.datagen;

import com.namefix.registry.BlockRegistry;
import com.namefix.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ZapinatorsModRecipeProvider extends FabricRecipeProvider {
    public ZapinatorsModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        return new RecipeProvider(provider, recipeOutput) {
            @Override
            public void buildRecipes() {
                this.nineBlockStorageRecipes(RecipeCategory.MISC, ItemRegistry.RAW_METEORITE.get(), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.RAW_METEORITE_BLOCK.getB().get());
                this.nineBlockStorageRecipes(RecipeCategory.MISC, ItemRegistry.METEORITE_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.METEORITE_BLOCK.getB().get());

                this.nineBlockStorageRecipes(RecipeCategory.MISC, ItemRegistry.METEORITE_NUGGET.get(), RecipeCategory.MISC, ItemRegistry.METEORITE_INGOT.get(), "meteorite_ingot_from_meteorite_nugget", null, "meteorite_nugget_from_meteorite_ingot", null);

                this.oreSmelting(List.of(ItemRegistry.RAW_METEORITE.get(), BlockRegistry.METEORITE_ORE.getB().get()), RecipeCategory.MISC, ItemRegistry.METEORITE_INGOT.get(), 0.7f, 200, "meteorite_ingot");
                this.oreBlasting(List.of(ItemRegistry.RAW_METEORITE.get(), BlockRegistry.METEORITE_ORE.getB().get()), RecipeCategory.MISC, ItemRegistry.METEORITE_INGOT.get(), 0.7f, 100, "meteorite_ingot");

            }
        };
    }

    @Override
    public String getName() {
        return "";
    }
}
