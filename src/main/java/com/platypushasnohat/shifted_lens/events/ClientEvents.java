package com.platypushasnohat.shifted_lens.events;

import com.platypushasnohat.shifted_lens.client.models.*;
import com.platypushasnohat.shifted_lens.client.renderer.*;
import com.platypushasnohat.shifted_lens.registry.SLModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.world.entity.EntityType;

@Environment(EnvType.CLIENT)
public final class ClientEvents {

    public static void init() {
        registerVanillaEntityRenderers();
        registerEntityLayerDefinitions();
    }

    public static void registerVanillaEntityRenderers() {
        EntityRendererRegistry.register(EntityType.COD, SLCodRenderer::new);
        EntityRendererRegistry.register(EntityType.ELDER_GUARDIAN, SLElderGuardianRenderer::new);
        EntityRendererRegistry.register(EntityType.GHAST, SLGhastRenderer::new);
        EntityRendererRegistry.register(EntityType.GLOW_SQUID, SLGlowSquidRenderer::new);
        EntityRendererRegistry.register(EntityType.GUARDIAN, SLGuardianRenderer::new);
        EntityRendererRegistry.register(EntityType.SALMON, SLSalmonRenderer::new);
        EntityRendererRegistry.register(EntityType.SQUID, SLSquidRenderer::new);
    }

    public static void registerEntityLayerDefinitions() {
        EntityModelLayerRegistry.registerModelLayer(SLModelLayers.COD, SLCodModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SLModelLayers.ELDER_GUARDIAN, SLElderGuardianModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SLModelLayers.GHAST, SLGhastModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SLModelLayers.GLOW_SQUID, SLSquidModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SLModelLayers.GUARDIAN, SLGuardianModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SLModelLayers.RABBIT, RabbitModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SLModelLayers.SALMON, SLSalmonModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SLModelLayers.SQUID, SLSquidModel::createBodyLayer);
    }
}
