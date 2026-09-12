package com.francis.the_stalwart.entity.layer;

import com.francis.the_stalwart.entity.EntitySwolager;
import com.francis.the_stalwart.entity.models.SwolagerModel;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Direction;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.entity.WalkAnimationState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SwolagerHeadLayer<T extends EntitySwolager, M extends SwolagerModel<T>> extends RenderLayer<T, M> {
    private final float scaleX;
    private final float scaleY;
    private final float scaleZ;
    private final Map<SkullBlock.Type, SkullModelBase> skullModels;
    private final ItemInHandRenderer itemInHandRenderer;

    public SwolagerHeadLayer(RenderLayerParent<T, M> renderer, EntityModelSet modelSet, ItemInHandRenderer itemInHandRenderer) {
        this(renderer, modelSet, 1.0F, 1.0F, 1.0F, itemInHandRenderer);
    }

    public SwolagerHeadLayer(RenderLayerParent<T, M> renderer, EntityModelSet modelSet, float scaleX, float scaleY, float scaleZ, ItemInHandRenderer itemInHandRenderer) {
        super(renderer);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
        this.skullModels = SkullBlockRenderer.createSkullRenderers(modelSet);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T swolager, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemstack = swolager.getItemBySlot(EquipmentSlot.HEAD);
        if (!itemstack.isEmpty()) {
            Item item = itemstack.getItem();
            poseStack.pushPose();
            poseStack.scale(this.scaleX, this.scaleY, this.scaleZ);
            if (swolager.isBaby()) {
                poseStack.translate(0.0F, 0.03125F, 0.0F);
                poseStack.scale(0.7F, 0.7F, 0.7F);
                poseStack.translate(0.0F, 1.0F, 0.0F);
            }

            ((SwolagerModel)this.getParentModel()).getBase().translateAndRotate(poseStack);
            ((SwolagerModel)this.getParentModel()).getBody().translateAndRotate(poseStack);
            ((SwolagerModel)this.getParentModel()).getHead().translateAndRotate(poseStack);
            if (item instanceof BlockItem && ((BlockItem)item).getBlock() instanceof AbstractSkullBlock) {
                float f2 = 1.1875F;
                poseStack.scale(f2, -f2, -f2);
                GameProfile resolvableprofile = null;
                if (itemstack.hasTag() && itemstack.getTag().contains("SkullOwner")) {
                    resolvableprofile = NbtUtils.readGameProfile(itemstack.getTag().getCompound("SkullOwner"));
                }
                poseStack.translate((double)-0.5F, (double)0.0F, (double)-0.5F);
                SkullBlock.Type skullblock$type = ((AbstractSkullBlock)((BlockItem)item).getBlock()).getType();
                SkullModelBase skullmodelbase = (SkullModelBase)this.skullModels.get(skullblock$type);
                RenderType rendertype = SkullBlockRenderer.getRenderType(skullblock$type, resolvableprofile);
                Entity var20 = swolager.getVehicle();
                WalkAnimationState walkanimationstate;
                if (var20 instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)var20;
                    walkanimationstate = livingentity.walkAnimation;
                } else {
                    walkanimationstate = swolager.walkAnimation;
                }

                float f3 = walkanimationstate.position(partialTicks);
                SkullBlockRenderer.renderSkull((Direction)null, 180.0F, f3, poseStack, buffer, packedLight, skullmodelbase, rendertype);
            } else {
                label35: {
                    if (item instanceof ArmorItem) {
                        ArmorItem armoritem = (ArmorItem)item;
                        if (armoritem.getEquipmentSlot() == EquipmentSlot.HEAD) {
                            break label35;
                        }
                    }

                    CustomHeadLayer.translateToHead(poseStack, false);
                    this.itemInHandRenderer.renderItem(swolager, itemstack, ItemDisplayContext.HEAD, false, poseStack, buffer, packedLight);
                }
            }

            poseStack.popPose();
        }

    }
}