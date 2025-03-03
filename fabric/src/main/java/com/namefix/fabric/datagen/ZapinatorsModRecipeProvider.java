package com.namefix.fabric.datagen;

import com.namefix.ZapinatorsMod;
import com.namefix.enums.ZapinatorType;
import com.namefix.registry.BlockRegistry;
import com.namefix.registry.ItemRegistry;
import com.namefix.utils.Utils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.data.recipes.ShapedRecipeBuilder.shaped;

public class ZapinatorsModRecipeProvider extends FabricRecipeProvider {
    public ZapinatorsModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, ItemRegistry.RAW_METEORITE.get(), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.RAW_METEORITE_BLOCK.getB().get());
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, ItemRegistry.METEORITE_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.METEORITE_BLOCK.getB().get());

        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, ItemRegistry.METEORITE_NUGGET.get(), RecipeCategory.MISC, ItemRegistry.METEORITE_INGOT.get(), "meteorite_ingot_from_meteorite_nugget", null, "meteorite_nugget_from_meteorite_ingot", null);

        oreSmelting(recipeOutput, List.of(ItemRegistry.RAW_METEORITE.get(), BlockRegistry.METEORITE_ORE.getB().get()), RecipeCategory.MISC, ItemRegistry.METEORITE_INGOT.get(), 0.7f, 200, "meteorite_ingot");
        oreBlasting(recipeOutput, List.of(ItemRegistry.RAW_METEORITE.get(), BlockRegistry.METEORITE_ORE.getB().get()), RecipeCategory.MISC, ItemRegistry.METEORITE_INGOT.get(), 0.7f, 100, "meteorite_ingot");

        // space gun
        shaped(RecipeCategory.COMBAT, ItemRegistry.SPACE_GUN.get())
                .pattern("M  ")
                .pattern("MMM")
                .pattern("M  ")
                .define('M', ItemRegistry.METEORITE_INGOT.get())
                .unlockedBy(RecipeProvider.getHasName(ItemRegistry.METEORITE_INGOT.get()), this.has(ItemRegistry.METEORITE_INGOT.get()))
                .save(recipeOutput);

        // energy cell
        shaped(RecipeCategory.MISC, ItemRegistry.ENERGY_CELL.get(), 16)
                .pattern("MRG")
                .define('M', ItemRegistry.METEORITE_NUGGET.get())
                .define('R', Items.REDSTONE)
                .define('G', Items.GLOWSTONE_DUST)
                .unlockedBy(RecipeProvider.getHasName(ItemRegistry.METEORITE_INGOT.get()), this.has(ItemRegistry.METEORITE_INGOT.get()))
                .save(recipeOutput);

        // zapinator reset smithing template
        shaped(RecipeCategory.MISC, ItemRegistry.ZAPINATOR_RESET_SMITHING_TEMPLATE.get(), 2)
                .pattern("ITI")
                .pattern("NIN")
                .pattern("INI")
                .define('I', ItemRegistry.METEORITE_INGOT.get())
                .define('N', ItemRegistry.METEORITE_NUGGET.get())
                .define('T', ItemRegistry.ZAPINATOR_RESET_SMITHING_TEMPLATE.get())
                .unlockedBy(RecipeProvider.getHasName(ItemRegistry.ZAPINATOR_RESET_SMITHING_TEMPLATE.get()), this.has(ItemRegistry.ZAPINATOR_RESET_SMITHING_TEMPLATE.get()))
                .save(recipeOutput);

        // zapinator cores
        for(ZapinatorType zap1 : ZapinatorType.values()) {
            if(zap1.equals(ZapinatorType.NONE)) continue;
            for (ZapinatorType zap2 : ZapinatorType.values()) {
                if(zap2.equals(ZapinatorType.NONE) || zap1.equals(zap2)) continue;
                zapinatorReset(Utils.getZapinatorFromEnum(zap1), Utils.getCoreFromEnum(zap2), Utils.getZapinatorFromEnum(zap2), recipeOutput, this);
            }
        }
    }

    private void zapinatorReset(Item zapinator, Item core, Item result, RecipeOutput output, RecipeProvider provider) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ItemRegistry.ZAPINATOR_RESET_SMITHING_TEMPLATE.get()), Ingredient.of(zapinator), Ingredient.of(core), RecipeCategory.MISC, result).unlocks("has_zapinator_core", provider.has(ZapinatorsModItemTagProvider.ZAPINATOR_CORES)).save(output, itemName(zapinator)+"_to_"+itemName(result)+"_smithing");
    }

    private String itemName(Item item) {
        return item.toString().replace(ZapinatorsMod.MOD_ID+":", "");
    }

    @Override
    public String getName() {
        return ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "recipe_provider").toString();
    }
}
