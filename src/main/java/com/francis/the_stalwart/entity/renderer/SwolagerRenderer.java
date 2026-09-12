package com.francis.the_stalwart.entity.renderer;

import com.francis.the_stalwart.TheStalwart;
import com.francis.the_stalwart.core.ModModelLayers;
import com.francis.the_stalwart.entity.EntitySwolager;
import com.francis.the_stalwart.entity.layer.SwolagerHeadLayer;
import com.francis.the_stalwart.entity.models.SwolagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SwolagerRenderer<T extends AbstractIllager> extends MobRenderer<EntitySwolager, SwolagerModel<EntitySwolager>> {
    public SwolagerRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SwolagerModel<>(ctx.bakeLayer(ModModelLayers.swolager)), 0.9F);
        this.addLayer(new SwolagerHeadLayer(this, ctx.getModelSet(), ctx.getItemInHandRenderer()));
    }

    public ResourceLocation getTextureLocation(EntitySwolager entity) {
        return ResourceLocation.fromNamespaceAndPath(TheStalwart.MODID, "textures/entity/swolager/swolager.png");
    }
}