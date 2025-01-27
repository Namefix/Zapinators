package com.namefix.registry;

import com.namefix.ZapinatorsMod;
import com.namefix.entity.LaserProjectile;
import com.namefix.entity.LaserProjectileRenderer;
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
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ZapinatorsMod.MOD_ID,"laser_projectile")))
    );

    public static void register() {
        ENTITY_TYPES.register();

        EntityRendererRegistry.register(LASER_PROJECTILE, LaserProjectileRenderer::new);
    }
}
