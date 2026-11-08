package com.platypushasnohat.shifted_lens.events;

import com.platypushasnohat.shifted_lens.ShiftedLens;
import com.platypushasnohat.shifted_lens.mixin_utils.CamelExtension;
import com.platypushasnohat.shifted_lens.registry.SLItems;
import com.platypushasnohat.shifted_lens.registry.tags.SLBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;

public class ModEvents {

    public static void init() {
        registerVanillaSpawnPlacements();
        registerBiomeModifications();
        modifyAttributes();
        registerCreativeModeTab();
    }

    private static void registerVanillaSpawnPlacements() {
        SpawnPlacements.DATA_BY_TYPE.remove(EntityType.CAMEL);
        SpawnPlacements.register(EntityType.CAMEL, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.WORLD_SURFACE_WG, CamelExtension::checkCamelSpawnRules);
    }

    private static void registerBiomeModifications() {
        BiomeModifications.addSpawn(BiomeSelectors.tag(SLBiomeTags.HAS_CAMELS), EntityType.CAMEL.getCategory(), EntityType.CAMEL, 1, 1, 2);

        BiomeModifications.create(ShiftedLens.modPrefix("remove_squid_spawns"))
                .add(ModificationPhase.REMOVALS, BiomeSelectors.tag(BiomeTags.IS_RIVER),
                        context -> context.getSpawnSettings().removeSpawnsOfEntityType(EntityType.SQUID));
    }

    private static void modifyAttributes() {
        add(EntityType.COD, Attributes.MAX_HEALTH, 4.0D);
        add(EntityType.COD, Attributes.MOVEMENT_SPEED, 0.8D);

        add(EntityType.ELDER_GUARDIAN, Attributes.MOVEMENT_SPEED, 0.8D);

        add(EntityType.GHAST, Attributes.MAX_HEALTH, 20.0D);
        add(EntityType.GHAST, Attributes.FLYING_SPEED, 0.11D);
        add(EntityType.GHAST, Attributes.FOLLOW_RANGE, 96.0D);

        add(EntityType.GUARDIAN, Attributes.MOVEMENT_SPEED, 1.0D);

        add(EntityType.PUFFERFISH, Attributes.MOVEMENT_SPEED, 0.5D);

        add(EntityType.RABBIT, Attributes.ATTACK_DAMAGE, 1.0D);

        add(EntityType.SALMON, Attributes.MAX_HEALTH, 6.0D);
        add(EntityType.SALMON, Attributes.MOVEMENT_SPEED, 0.8D);

        add(EntityType.SQUID, Attributes.MOVEMENT_SPEED, 0.64D);
        add(EntityType.GLOW_SQUID, Attributes.MOVEMENT_SPEED, 0.64D);

        add(EntityType.TROPICAL_FISH, Attributes.MOVEMENT_SPEED, 0.9D);
    }

    private static void add(EntityType<? extends LivingEntity> type, Holder<Attribute> attribute, double value) {
        AttributeSupplier current = DefaultAttributes.getSupplier(type);
        AttributeSupplier.Builder builder = AttributeSupplier.builder();
        BuiltInRegistries.ATTRIBUTE.holders().forEach(holder -> {
            if (current.hasAttribute(holder)) {
                builder.add(holder, current.getBaseValue(holder));
            }
        });
        builder.add(attribute, value);
        FabricDefaultAttributeRegistry.register(type, builder);
    }

    private static void registerCreativeModeTab() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> entries.addAfter(Items.PUFFERFISH_BUCKET, SLItems.SQUID_BUCKET));
    }
}
