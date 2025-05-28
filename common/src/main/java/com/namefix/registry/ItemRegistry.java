package com.namefix.registry;

import com.namefix.ZapinatorsMod;
import com.namefix.enums.GemStaffType;
import com.namefix.enums.ZapinatorType;
import com.namefix.item.*;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.equipment.ArmorType;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ZapinatorsMod.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> SPACE_GUN = registerItem("space_gun", properties -> new SpaceGunItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> LASER_RIFLE = registerItem("laser_rifle", properties -> new LaserRifleItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> ZAPINATOR = registerItem("zapinator", properties -> new ZapinatorItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> GRAY_ZAPINATOR = registerItem("gray_zapinator", properties -> new ColorZapinatorItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB), ZapinatorType.GRAY, 2.5f, 5.0f, true));
    public static final RegistrySupplier<Item> ORANGE_ZAPINATOR = registerItem("orange_zapinator", properties -> new ColorZapinatorItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB), ZapinatorType.ORANGE, 5.0f, 5.0f, true));
    public static final RegistrySupplier<Item> RED_ZAPINATOR = registerItem("red_zapinator", properties -> new ColorZapinatorItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB), ZapinatorType.RED, 3.0f, 6.0f, false));
    public static final RegistrySupplier<Item> GREEN_ZAPINATOR = registerItem("green_zapinator", properties -> new ColorZapinatorItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB), ZapinatorType.GREEN, 3.5f, 6.0f, false));
    public static final RegistrySupplier<Item> BLUE_ZAPINATOR = registerItem("blue_zapinator", properties -> new ColorZapinatorItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB), ZapinatorType.BLUE, 4.0f, 5.0f, false));
    public static final RegistrySupplier<Item> PURPLE_ZAPINATOR = registerItem("purple_zapinator", properties -> new ColorZapinatorItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB), ZapinatorType.PURPLE, 4.5f, 5.0f, false));
    public static final RegistrySupplier<Item> BLACK_ZAPINATOR = registerItem("black_zapinator", properties -> new ColorZapinatorItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB).rarity(Rarity.EPIC), ZapinatorType.BLACK, 69420f, 0.0f, false));
    public static final RegistrySupplier<Item> YELLOW_ZAPINATOR = registerItem("yellow_zapinator", properties -> new ColorZapinatorItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB), ZapinatorType.YELLOW, 3.0f, 6.0f, false));
    public static final RegistrySupplier<Item> WHITE_ZAPINATOR = registerItem("white_zapinator", properties -> new ColorZapinatorItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB), ZapinatorType.WHITE, 3.5f, 4.0f, false));

    public static final RegistrySupplier<Item> ENDER_ZAPINATOR = registerItem("ender_zapinator", properties -> new EnderZapinatorItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB).rarity(Rarity.RARE)));

    public static final RegistrySupplier<Item> GRAY_CORE = registerItem("gray_core", properties -> new CoreItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> ORANGE_CORE = registerItem("orange_core", properties -> new CoreItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> RED_CORE = registerItem("red_core", properties -> new CoreItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> GREEN_CORE = registerItem("green_core", properties -> new CoreItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> BLUE_CORE = registerItem("blue_core", properties -> new CoreItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> PURPLE_CORE = registerItem("purple_core", properties -> new CoreItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> BLACK_CORE = registerItem("black_core", properties -> new CoreItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> YELLOW_CORE = registerItem("yellow_core", properties -> new CoreItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> WHITE_CORE = registerItem("white_core", properties -> new CoreItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB)));

    public static final RegistrySupplier<Item> BEE_GUN = registerItem("bee_gun", properties -> new BeeGunItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> WAND_OF_SPARKING = registerItem("wand_of_sparking", properties -> new WandOfSparkingItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB), 4f, 2f, 12, 0xff591c));

    public static final RegistrySupplier<Item> ECHO_STAFF = registerItem("echo_staff", properties -> new GemStaffItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB).rarity(Rarity.RARE), 10f, 10f, 5, 0x0b4075, GemStaffType.ECHO));
    public static final RegistrySupplier<Item> DIAMOND_STAFF = registerItem("diamond_staff", properties -> new GemStaffItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB).rarity(Rarity.UNCOMMON), 6f, 6f, 5, 0xd1e3ff, GemStaffType.DIAMOND));
    public static final RegistrySupplier<Item> EMERALD_STAFF = registerItem("emerald_staff", properties -> new GemStaffItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB).rarity(Rarity.UNCOMMON), 6f, 5f, 7, 0x94ff99, GemStaffType.EMERALD));
    public static final RegistrySupplier<Item> AMETHYST_STAFF = registerItem("amethyst_staff", properties -> new GemStaffItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB).rarity(Rarity.UNCOMMON), 6f, 4f, 7, 0xf678ff, GemStaffType.AMETHYST));
    public static final RegistrySupplier<Item> LAPIS_STAFF = registerItem("lapis_staff", properties -> new GemStaffItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB).rarity(Rarity.UNCOMMON), 5f, 3f, 10, 0x5993ff, GemStaffType.LAPIS));
    public static final RegistrySupplier<Item> REDSTONE_STAFF = registerItem("redstone_staff", properties -> new GemStaffItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB).rarity(Rarity.UNCOMMON), 4f, 1f, 10, 0xff615e, GemStaffType.REDSTONE));

    public static final RegistrySupplier<Item> RUNE = registerItem("rune", properties -> new RuneItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB).rarity(Rarity.UNCOMMON), null));
    public static final RegistrySupplier<Item> ECHO_RUNE = registerItem("echo_rune", properties -> new RuneItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB).rarity(Rarity.RARE), GemStaffType.ECHO));
    public static final RegistrySupplier<Item> DIAMOND_RUNE = registerItem("diamond_rune", properties -> new RuneItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB).rarity(Rarity.UNCOMMON), GemStaffType.DIAMOND));
    public static final RegistrySupplier<Item> EMERALD_RUNE = registerItem("emerald_rune", properties -> new RuneItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB).rarity(Rarity.UNCOMMON), GemStaffType.EMERALD));
    public static final RegistrySupplier<Item> AMETHYST_RUNE = registerItem("amethyst_rune", properties -> new RuneItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB).rarity(Rarity.UNCOMMON), GemStaffType.AMETHYST));
    public static final RegistrySupplier<Item> LAPIS_RUNE = registerItem("lapis_rune", properties -> new RuneItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB).rarity(Rarity.UNCOMMON), GemStaffType.LAPIS));
    public static final RegistrySupplier<Item> REDSTONE_RUNE = registerItem("redstone_rune", properties -> new RuneItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB).rarity(Rarity.UNCOMMON), GemStaffType.REDSTONE));

    public static final RegistrySupplier<Item> RAW_METEORITE = registerItem("raw_meteorite", properties -> new Item(properties.arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> METEORITE_INGOT = registerItem("meteorite_ingot", properties -> new Item(properties.arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> METEORITE_NUGGET = registerItem("meteorite_nugget", properties -> new Item(properties.arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> ZAPINATOR_RESET_SMITHING_TEMPLATE = registerItem("zapinator_reset_smithing_template", properties -> new SmithingTemplateItem(
            Component.translatable("item.zapinators.smithing_template.zapinator_reset.applies_to").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.zapinators.smithing_template.zapinator_reset.ingredients").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.zapinators.smithing_template.zapinator_reset.base_slot_description"),
            Component.translatable("item.zapinators.smithing_template.zapinator_reset.additions_slot_description"),
            null,
            null,
            properties.arch$tab(TabRegistry.ZAPINATORS_TAB).rarity(Rarity.UNCOMMON)
    ));
    public static final RegistrySupplier<Item> ENERGY_CELL = registerItem("energy_cell", properties -> new Item(properties.arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> FALLEN_STAR = registerItem("fallen_star", properties -> new FallenStarItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> MANA_CRYSTAL = registerItem("mana_crystal", properties -> new ManaCrystalItem(properties.arch$tab(TabRegistry.ZAPINATORS_TAB)));

    public static final RegistrySupplier<Item> METEORITE_HELMET = registerItem("meteorite_helmet", properties -> new MeteoriteArmorItem(ModArmorMaterials.METEORITE_ARMOR, ArmorType.HELMET, properties.arch$tab(TabRegistry.ZAPINATORS_TAB), 25));
    public static final RegistrySupplier<Item> METEORITE_CHESTPLATE = registerItem("meteorite_chestplate", properties -> new MeteoriteArmorItem(ModArmorMaterials.METEORITE_ARMOR, ArmorType.CHESTPLATE, properties.arch$tab(TabRegistry.ZAPINATORS_TAB), 25));
    public static final RegistrySupplier<Item> METEORITE_LEGGINGS = registerItem("meteorite_leggings", properties -> new MeteoriteArmorItem(ModArmorMaterials.METEORITE_ARMOR, ArmorType.LEGGINGS, properties.arch$tab(TabRegistry.ZAPINATORS_TAB), 25));
    public static final RegistrySupplier<Item> METEORITE_BOOTS = registerItem("meteorite_boots", properties -> new MeteoriteArmorItem(ModArmorMaterials.METEORITE_ARMOR, ArmorType.BOOTS, properties.arch$tab(TabRegistry.ZAPINATORS_TAB), 25));

    public static void register() {
        ITEMS.register();
    }

    @FunctionalInterface
    public interface ItemFactory<T extends Item> {
        T create(Item.Properties settings);
    }
    public static RegistrySupplier<Item> registerItem(String name) {
        return registerItem(name, Item::new);
    }
    public static <T extends Item> RegistrySupplier<T> registerItem(String name, ItemFactory<T> factory) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, name));
        return ITEMS.register(name, () -> factory.create(new Item.Properties().setId(key)));
    }
}
