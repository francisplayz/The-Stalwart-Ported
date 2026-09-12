package com.francis.the_stalwart.entity.models;

import com.francis.the_stalwart.entity.EntitySwolager; import net.minecraft.client.model.HierarchicalModel; import net.minecraft.client.model.HeadedModel; import net.minecraft.client.model.geom.ModelPart; import net.minecraft.client.model.geom.ModelLayerLocation; import net.minecraft.client.model.geom.PartPose; import net.minecraft.client.model.geom.builders.CubeDeformation; import net.minecraft.client.model.geom.builders.CubeListBuilder; import net.minecraft.client.model.geom.builders.LayerDefinition; import net.minecraft.client.model.geom.builders.MeshDefinition; import net.minecraft.client.model.geom.builders.PartDefinition; import net.minecraft.client.model.AnimationUtils; import net.minecraft.util.Mth; import net.minecraftforge.api.distmarker.Dist; import net.minecraftforge.api.distmarker.OnlyIn; import com.mojang.blaze3d.vertex.PoseStack; import com.mojang.blaze3d.vertex.VertexConsumer;

@OnlyIn(Dist.CLIENT)
public class SwolagerModel<T extends EntitySwolager> extends HierarchicalModel<T> implements HeadedModel {
    private final ModelPart base;
    private final ModelPart Body;
    private final ModelPart LeftArm;
    private final ModelPart RightArm;
    private final ModelPart Head;
    private final ModelPart LeftLeg;
    private final ModelPart RightLeg;

    public SwolagerModel(ModelPart root) {
        this.base = root.getChild("base");
        this.Body = this.base.getChild("Body");
        this.LeftArm = this.Body.getChild("LeftArm");
        this.RightArm = this.Body.getChild("RightArm");
        this.Head = this.Body.getChild("Head");
        this.LeftLeg = this.base.getChild("LeftLeg");
        this.RightLeg = this.base.getChild("RightLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.25F));
        PartDefinition Body = base.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -32.0F, -9.25F, 28.0F, 24.0F, 18.0F, new CubeDeformation(0.0F)).texOffs(0, 42).addBox(-10.0F, -8.0F, -5.25F, 20.0F, 12.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));
        Body.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(0, 65).addBox(-0.75F, -6.75F, -6.0F, 10.0F, 44.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(44, 65).addBox(9.25F, 1.25F, -6.0F, 5.0F, 23.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(14.75F, -28.25F, -0.25F));
        Body.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(0, 65).mirror().addBox(-9.25F, -6.75F, -6.0F, 10.0F, 44.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(44, 65).mirror().addBox(-14.25F, 1.25F, -6.0F, 5.0F, 23.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-14.75F, -28.25F, -0.25F));
        Body.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(92, 0).addBox(-4.0F, -10.0F, -3.5F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(116, 0).addBox(-1.0F, -3.0F, -5.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -32.0F, -4.75F));
        base.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(78, 42).addBox(-3.75F, -3.25F, -6.0F, 11.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(78, 66).addBox(-3.75F, 8.75F, -5.0F, 8.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(4.75F, 7.25F, 0.25F));
        base.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(78, 42).mirror().addBox(-7.25F, -3.25F, -6.0F, 11.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(78, 66).mirror().addBox(-4.25F, 8.75F, -5.0F, 8.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.75F, 7.25F, 0.25F));
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public ModelPart root() {
        return this.base;
    }

    public void setupAnim(T swolager, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.Head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.Head.xRot = headPitch * ((float)Math.PI / 180F);
        this.RightLeg.xRot = Mth.cos(limbSwing * 0.65F) * 1.2F * limbSwingAmount;
        this.LeftLeg.xRot = Mth.cos(limbSwing * 0.65F) * -1.2F * limbSwingAmount;
        this.Body.xRot = 0.0F;
        this.Body.yRot = 0.0F;
        this.base.zRot = Mth.cos(limbSwing * 0.45F) * 0.3F * limbSwingAmount;
        this.RightArm.xRot = Mth.cos(limbSwing * 0.65F + (float)Math.PI) * limbSwingAmount * 1.2F;
        this.RightArm.yRot = 0.0F;
        this.LeftArm.xRot = Mth.cos(limbSwing * 0.65F) * limbSwingAmount * 1.2F;
        if (this.attackTime > 0.0F) {
            this.Body.xRot = 0.5F * Mth.sin(this.attackTime * (float)Math.PI);
            this.Body.yRot = -0.6F * Mth.sin(this.attackTime * (float)Math.PI);
            this.RightArm.xRot = -1.7F * Mth.sin(this.attackTime * (float)Math.PI);
            this.RightArm.zRot = 0.6F * Mth.sin(this.attackTime * (float)Math.PI);
            this.RightArm.yRot = -0.3F * Mth.sin(this.attackTime * (float)Math.PI);
        }

        this.RightArm.zRot = 0.0F;
        this.LeftArm.zRot = 0.0F;
        this.RightLeg.yRot = 0.0F;
        this.LeftLeg.yRot = 0.0F;
        AnimationUtils.bobArms(this.RightArm, this.LeftArm, ageInTicks);
    }

    public ModelPart getHead() {
        return this.Head;
    }

    public ModelPart getBase() {
        return this.base;
    }

    public ModelPart getBody() {
        return this.Body;
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int i2) {
        this.base.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }
}