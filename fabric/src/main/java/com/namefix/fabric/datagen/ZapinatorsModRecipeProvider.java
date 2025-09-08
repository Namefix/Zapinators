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
                this.nineBlockStorageRecipes(RecipeCategory.MISC, ItemRegistry.FALLEN_STAR.get(), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.STAR_BLOCK.getB().get());

                this.nineBlockStorageRecipes(RecipeCategory.MISC, ItemRegistry.METEORITE_NUGGET.get(), RecipeCategory.MISC, ItemRegistry.METEORITE_INGOT.get(), "meteorite_ingot_from_meteorite_nugget", null, "meteorite_nugget_from_meteorite_ingot", null);

                this.oreSmelting(List.of(ItemRegistry.RAW_METEORITE.get(), BlockRegistry.METEORITE_ORE.getB().get()), RecipeCategory.MISC, ItemRegistry.METEORITE_INGOT.get(), 0.7f, 200, "meteorite_ingot");
                this.oreBlasting(List.of(ItemRegistry.RAW_METEORITE.get(), BlockRegistry.METEORITE_ORE.getB().get()), RecipeCategory.MISC, ItemRegistry.METEORITE_INGOT.get(), 0.7f, 100, "meteorite_ingot");

                // space gun
                this.shaped(RecipeCategory.COMBAT, ItemRegistry.SPACE_GUN.get())
                        .pattern("M  ")
                        .pattern("MMM")
                        .pattern("M  ")
                        .define('M', ItemRegistry.METEORITE_INGOT.get())
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.METEORITE_INGOT.get()), this.has(ItemRegistry.METEORITE_INGOT.get()))
                        .save(recipeOutput);

                // energy cell
                this.shaped(RecipeCategory.MISC, ItemRegistry.ENERGY_CELL.get(), 16)
                        .pattern("MRG")
                        .define('M', ItemRegistry.METEORITE_NUGGET.get())
                        .define('R', Items.REDSTONE)
                        .define('G', Items.GLOWSTONE_DUST)
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.METEORITE_INGOT.get()), this.has(ItemRegistry.METEORITE_INGOT.get()))
                        .save(recipeOutput);

                // zapinator reset smithing template
                this.shaped(RecipeCategory.MISC, ItemRegistry.ZAPINATOR_RESET_SMITHING_TEMPLATE.get(), 2)
                        .pattern("ITI")
                        .pattern("NIN")
                        .pattern("INI")
                        .define('I', ItemRegistry.METEORITE_INGOT.get())
                        .define('N', ItemRegistry.METEORITE_NUGGET.get())
                        .define('T', ItemRegistry.ZAPINATOR_RESET_SMITHING_TEMPLATE.get())
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.ZAPINATOR_RESET_SMITHING_TEMPLATE.get()), this.has(ItemRegistry.ZAPINATOR_RESET_SMITHING_TEMPLATE.get()))
                        .save(recipeOutput);

                // meteorite armor
                this.shaped(RecipeCategory.COMBAT, ItemRegistry.METEORITE_HELMET.get())
                        .pattern("MMM")
                        .pattern("M M")
                        .define('M', ItemRegistry.METEORITE_INGOT.get())
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.METEORITE_INGOT.get()), this.has(ItemRegistry.METEORITE_INGOT.get()))
                        .save(recipeOutput);

                this.shaped(RecipeCategory.COMBAT, ItemRegistry.METEORITE_CHESTPLATE.get())
                        .pattern("M M")
                        .pattern("MMM")
                        .pattern("MMM")
                        .define('M', ItemRegistry.METEORITE_INGOT.get())
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.METEORITE_INGOT.get()), this.has(ItemRegistry.METEORITE_INGOT.get()))
                        .save(recipeOutput);

                this.shaped(RecipeCategory.COMBAT, ItemRegistry.METEORITE_LEGGINGS.get())
                        .pattern("MMM")
                        .pattern("M M")
                        .pattern("M M")
                        .define('M', ItemRegistry.METEORITE_INGOT.get())
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.METEORITE_INGOT.get()), this.has(ItemRegistry.METEORITE_INGOT.get()))
                        .save(recipeOutput);

                this.shaped(RecipeCategory.COMBAT, ItemRegistry.METEORITE_BOOTS.get())
                        .pattern("M M")
                        .pattern("M M")
                        .define('M', ItemRegistry.METEORITE_INGOT.get())
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.METEORITE_INGOT.get()), this.has(ItemRegistry.METEORITE_INGOT.get()))
                        .save(recipeOutput);

                // bee armor

                this.shaped(RecipeCategory.COMBAT, ItemRegistry.BEE_HELMET.get())
                        .pattern("HHH")
                        .pattern("H H")
                        .define('H', Items.HONEYCOMB)
                        .unlockedBy(RecipeProvider.getHasName(Items.HONEYCOMB), this.has(Items.HONEYCOMB))
                        .save(recipeOutput);

                this.shaped(RecipeCategory.COMBAT, ItemRegistry.BEE_CHESTPLATE.get())
                        .pattern("H H")
                        .pattern("HHH")
                        .pattern("HHH")
                        .define('H', Items.HONEYCOMB)
                        .unlockedBy(RecipeProvider.getHasName(Items.HONEYCOMB), this.has(Items.HONEYCOMB))
                        .save(recipeOutput);

                this.shaped(RecipeCategory.COMBAT, ItemRegistry.BEE_LEGGINGS.get())
                        .pattern("HHH")
                        .pattern("H H")
                        .pattern("H H")
                        .define('H', Items.HONEYCOMB)
                        .unlockedBy(RecipeProvider.getHasName(Items.HONEYCOMB), this.has(Items.HONEYCOMB))
                        .save(recipeOutput);

                this.shaped(RecipeCategory.COMBAT, ItemRegistry.BEE_BOOTS.get())
                        .pattern("H H")
                        .pattern("H H")
                        .define('H', Items.HONEYCOMB)
                        .unlockedBy(RecipeProvider.getHasName(Items.HONEYCOMB), this.has(Items.HONEYCOMB))
                        .save(recipeOutput);

                // mana crystal
                this.shapeless(RecipeCategory.MISC, ItemRegistry.MANA_CRYSTAL.get())
                        .requires(ItemRegistry.FALLEN_STAR.get(), 5)
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.FALLEN_STAR.get()), this.has(ItemRegistry.FALLEN_STAR.get()))
                        .save(recipeOutput);

                // bee gun
                this.shaped(RecipeCategory.COMBAT, ItemRegistry.BEE_GUN.get())
                        .pattern("H  ")
                        .pattern("HHH")
                        .pattern("H  ")
                        .define('H', Items.HONEYCOMB)
                        .unlockedBy(RecipeProvider.getHasName(Items.HONEYCOMB), this.has(Items.HONEYCOMB))
                        .save(recipeOutput);

                // zapinator cores
                for(ZapinatorType zap1 : ZapinatorType.values()) {
                    if(zap1.equals(ZapinatorType.NONE)) continue;
                    for (ZapinatorType zap2 : ZapinatorType.values()) {
                        if(zap2.equals(ZapinatorType.NONE) || zap1.equals(zap2)) continue;
                        zapinatorReset(Utils.getZapinatorFromEnum(zap1), Utils.getCoreFromEnum(zap2), Utils.getZapinatorFromEnum(zap2), recipeOutput, this);
                    }
                }

                // runes
                this.shapeless(RecipeCategory.MISC, ItemRegistry.ECHO_RUNE.get())
                        .requires(ItemRegistry.RUNE.get())
                        .requires(Items.ECHO_SHARD, 8)
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.RUNE.get()), this.has(ItemRegistry.RUNE.get()))
                        .unlockedBy(RecipeProvider.getHasName(Items.ECHO_SHARD), this.has(Items.ECHO_SHARD))
                        .save(recipeOutput);

                this.shapeless(RecipeCategory.MISC, ItemRegistry.DIAMOND_RUNE.get())
                        .requires(ItemRegistry.RUNE.get())
                        .requires(Items.DIAMOND, 8)
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.RUNE.get()), this.has(ItemRegistry.RUNE.get()))
                        .unlockedBy(RecipeProvider.getHasName(Items.DIAMOND), this.has(Items.DIAMOND))
                        .save(recipeOutput);

                this.shapeless(RecipeCategory.MISC, ItemRegistry.EMERALD_RUNE.get())
                        .requires(ItemRegistry.RUNE.get())
                        .requires(Items.EMERALD, 8)
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.RUNE.get()), this.has(ItemRegistry.RUNE.get()))
                        .unlockedBy(RecipeProvider.getHasName(Items.EMERALD), this.has(Items.EMERALD))
                        .save(recipeOutput);

                this.shapeless(RecipeCategory.MISC, ItemRegistry.AMETHYST_RUNE.get())
                        .requires(ItemRegistry.RUNE.get())
                        .requires(Items.AMETHYST_SHARD, 8)
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.RUNE.get()), this.has(ItemRegistry.RUNE.get()))
                        .unlockedBy(RecipeProvider.getHasName(Items.AMETHYST_SHARD), this.has(Items.AMETHYST_SHARD))
                        .save(recipeOutput);

                this.shapeless(RecipeCategory.MISC, ItemRegistry.LAPIS_RUNE.get())
                        .requires(ItemRegistry.RUNE.get())
                        .requires(Items.LAPIS_LAZULI, 8)
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.RUNE.get()), this.has(ItemRegistry.RUNE.get()))
                        .unlockedBy(RecipeProvider.getHasName(Items.LAPIS_LAZULI), this.has(Items.LAPIS_LAZULI))
                        .save(recipeOutput);

                this.shapeless(RecipeCategory.MISC, ItemRegistry.REDSTONE_RUNE.get())
                        .requires(ItemRegistry.RUNE.get())
                        .requires(Items.REDSTONE, 8)
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.RUNE.get()), this.has(ItemRegistry.RUNE.get()))
                        .unlockedBy(RecipeProvider.getHasName(Items.REDSTONE), this.has(Items.REDSTONE))
                        .save(recipeOutput);

                // gem staffs
                this.shaped(RecipeCategory.COMBAT, ItemRegistry.ECHO_STAFF.get())
                        .pattern("  R")
                        .pattern(" S ")
                        .pattern("S  ")
                        .define('R', ItemRegistry.ECHO_RUNE.get())
                        .define('S', Items.STICK)
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.RUNE.get()), this.has(ItemRegistry.RUNE.get()))
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.ECHO_RUNE.get()), this.has(ItemRegistry.ECHO_RUNE.get()))
                        .save(recipeOutput);

                this.shaped(RecipeCategory.COMBAT, ItemRegistry.DIAMOND_STAFF.get())
                        .pattern("  R")
                        .pattern(" S ")
                        .pattern("S  ")
                        .define('R', ItemRegistry.DIAMOND_RUNE.get())
                        .define('S', Items.STICK)
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.RUNE.get()), this.has(ItemRegistry.RUNE.get()))
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.DIAMOND_RUNE.get()), this.has(ItemRegistry.DIAMOND_RUNE.get()))
                        .save(recipeOutput);

                this.shaped(RecipeCategory.COMBAT, ItemRegistry.EMERALD_STAFF.get())
                        .pattern("  R")
                        .pattern(" S ")
                        .pattern("S  ")
                        .define('R', ItemRegistry.EMERALD_RUNE.get())
                        .define('S', Items.STICK)
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.RUNE.get()), this.has(ItemRegistry.RUNE.get()))
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.EMERALD_RUNE.get()), this.has(ItemRegistry.EMERALD_RUNE.get()))
                        .save(recipeOutput);

                this.shaped(RecipeCategory.COMBAT, ItemRegistry.AMETHYST_STAFF.get())
                        .pattern("  R")
                        .pattern(" S ")
                        .pattern("S  ")
                        .define('R', ItemRegistry.AMETHYST_RUNE.get())
                        .define('S', Items.STICK)
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.RUNE.get()), this.has(ItemRegistry.RUNE.get()))
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.AMETHYST_RUNE.get()), this.has(ItemRegistry.AMETHYST_RUNE.get()))
                        .save(recipeOutput);

                this.shaped(RecipeCategory.COMBAT, ItemRegistry.LAPIS_STAFF.get())
                        .pattern("  R")
                        .pattern(" S ")
                        .pattern("S  ")
                        .define('R', ItemRegistry.LAPIS_RUNE.get())
                        .define('S', Items.STICK)
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.RUNE.get()), this.has(ItemRegistry.RUNE.get()))
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.LAPIS_RUNE.get()), this.has(ItemRegistry.LAPIS_RUNE.get()))
                        .save(recipeOutput);

                this.shaped(RecipeCategory.COMBAT, ItemRegistry.REDSTONE_STAFF.get())
                        .pattern("  R")
                        .pattern(" S ")
                        .pattern("S  ")
                        .define('R', ItemRegistry.REDSTONE_RUNE.get())
                        .define('S', Items.STICK)
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.RUNE.get()), this.has(ItemRegistry.RUNE.get()))
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.REDSTONE_RUNE.get()), this.has(ItemRegistry.REDSTONE_RUNE.get()))
                        .save(recipeOutput);

                this.shaped(RecipeCategory.COMBAT, ItemRegistry.WAND_OF_SPARKING.get())
                        .pattern(" R")
                        .pattern("S ")
                        .define('R', ItemRegistry.RUNE.get())
                        .define('S', Items.STICK)
                        .unlockedBy(RecipeProvider.getHasName(ItemRegistry.RUNE.get()), this.has(ItemRegistry.RUNE.get()))
                        .save(recipeOutput);
            }
        };
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
