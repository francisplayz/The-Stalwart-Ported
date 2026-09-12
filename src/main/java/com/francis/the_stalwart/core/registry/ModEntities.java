package com.francis.the_stalwart.core.registry;

import com.francis.the_stalwart.TheStalwart;
import com.francis.the_stalwart.core.ModModelLayers;
import com.francis.the_stalwart.entity.EntitySwolager;
import com.francis.the_stalwart.entity.models.SwolagerModel;
import com.francis.the_stalwart.entity.renderer.SwolagerRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = TheStalwart.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TheStalwart.MODID);

    public static final RegistryObject<EntityType<EntitySwolager>> SWOLAGER = registerEntity("swolager", createBuilder(EntitySwolager::new, MobCategory.MONSTER).sized(1.6F, 3.8F));

    public static <T extends Entity> EntityType.Builder<T> createBuilder(EntityType.EntityFactory<T> factory, MobCategory category)
    {
        return EntityType.Builder.<T>of(factory, category);
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SWOLAGER.get(), SwolagerRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.swolager, SwolagerModel::createBodyLayer);
    }

    @OnlyIn(Dist.CLIENT)
    public static void RegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.swolager, SwolagerModel::createBodyLayer);
    }

    @OnlyIn(Dist.CLIENT)
    public static void ClientSetup() {
        EntityRenderers.register(SWOLAGER.get(), SwolagerRenderer::new);
    }

    public static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder)
    {
        return ENTITY_TYPES.register(name, () -> builder.build(ResourceLocation.fromNamespaceAndPath(TheStalwart.MODID, name).toString()));
    }
}