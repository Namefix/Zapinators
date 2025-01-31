package com.namefix.registry;

import com.namefix.ZapinatorsMod;
import com.namefix.enums.ZapinatorType;
import com.namefix.item.ColorZapinatorItem;
import com.namefix.item.LaserRifleItem;
import com.namefix.item.SpaceGunItem;
import com.namefix.item.ZapinatorItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ZapinatorsMod.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> SPACE_GUN = registerItem("space_gun", properties -> new SpaceGunItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> LASER_RIFLE = registerItem("laser_rifle", properties -> new LaserRifleItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> ZAPINATOR = registerItem("zapinator", properties -> new ZapinatorItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB)));
    public static final RegistrySupplier<Item> GRAY_ZAPINATOR = registerItem("gray_zapinator", properties -> new ColorZapinatorItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB), ZapinatorType.GRAY, 2.0f));
    public static final RegistrySupplier<Item> ORANGE_ZAPINATOR = registerItem("orange_zapinator", properties -> new ColorZapinatorItem(properties.stacksTo(1).arch$tab(TabRegistry.ZAPINATORS_TAB), ZapinatorType.ORANGE, 5.0f));

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
