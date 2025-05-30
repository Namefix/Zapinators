package com.namefix.registry;

import com.namefix.ZapinatorsMod;
import com.namefix.entity.*;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ZapinatorsMod.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<LaserProjectile>> LASER_PROJECTILE = ENTITY_TYPES.register("laser_projectile", () ->
            EntityType.Builder.of(LaserProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .updateInterval(1)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID,"laser_projectile")))
    );

    public static final RegistrySupplier<EntityType<FallenStar>> FALLEN_STAR = ENTITY_TYPES.register("fallen_star", () ->
            EntityType.Builder.of(FallenStar::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(64)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID,"fallen_star")))
    );

    public static final RegistrySupplier<EntityType<AngryBee>> ANGRY_BEE = ENTITY_TYPES.register("angry_bee", () ->
            EntityType.Builder.of(AngryBee::new, MobCategory.MISC)
                    .sized(0.2f, 0.2f)
                    .updateInterval(10)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "angry_bee")))
            );

    public static final RegistrySupplier<EntityType<Orb>> ORB = ENTITY_TYPES.register("orb", () ->
            EntityType.Builder.of(Orb::new, MobCategory.MISC)
                    .sized(0.4f, 0.4f)
                    .updateInterval(1)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID, "orb")))
            );

    public static void register() {
        ENTITY_TYPES.register();
    }

    public static void registerRenderers() {
        EntityRendererRegistry.register(LASER_PROJECTILE, LaserProjectileRenderer::new);
        EntityRendererRegistry.register(FALLEN_STAR, FallenStarRenderer::new);
        EntityRendererRegistry.register(ANGRY_BEE, AngryBeeRenderer::new);
        EntityRendererRegistry.register(ORB, OrbRenderer::new);
    }
}
