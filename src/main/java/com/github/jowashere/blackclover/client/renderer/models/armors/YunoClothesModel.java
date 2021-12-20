package com.github.jowashere.blackclover.client.renderer.models.armors;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class YunoClothesModel<T extends LivingEntity> extends BipedModel<T>{

    private final ModelRenderer LeftLeg;
    private final ModelRenderer RightLeg;
    private final ModelRenderer RightArm;
    private final ModelRenderer bone7;
    private final ModelRenderer bone6;
    private final ModelRenderer LeftArm;
    private final ModelRenderer bone2;
    private final ModelRenderer Body;
    private final ModelRenderer Head;
    private final ModelRenderer RightBoot;
    private final ModelRenderer LeftBoot;

    private EquipmentSlotType slotType;

    public YunoClothesModel(EquipmentSlotType slotType) {

        super(1);

        texWidth = 64;
        texHeight = 64;

        this.slotType = slotType;

        LeftLeg = new ModelRenderer(this);
        LeftLeg.setPos(1.9F, 12.0F, 0.0F);
        LeftLeg.texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        RightLeg = new ModelRenderer(this);
        RightLeg.setPos(-1.9F, 12.0F, 0.0F);
        RightLeg.texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        RightArm = new ModelRenderer(this);
        RightArm.setPos(-5.0F, 2.0F, 0.0F);
        RightArm.texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        bone7 = new ModelRenderer(this);
        bone7.setPos(5.0F, 28.0F, 0.0F);
        RightArm.addChild(bone7);
        bone7.texOffs(0, 5).addBox(-8.0F, -22.5F, -2.0F, 4.0F, 2.0F, 4.0F, 0.15F, false);
        bone6 = new ModelRenderer(this);
        bone6.setPos(5.0F, 22.0F, 0.0F);
        RightArm.addChild(bone6);
        bone6.texOffs(0, 5).addBox(-8.0F, -21.5F, -2.0F, 4.0F, 2.0F, 4.0F, 0.15F, true);
        bone6.texOffs(4, 1).addBox(-8.3F, -22.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.2F, true);
        LeftArm = new ModelRenderer(this);
        LeftArm.setPos(5.0F, 2.0F, 0.0F);
        LeftArm.texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        bone2 = new ModelRenderer(this);
        bone2.setPos(-5.0F, 22.0F, 0.0F);
        LeftArm.addChild(bone2);
        bone2.texOffs(0, 5).addBox(4.0F, -21.5F, -2.0F, 4.0F, 2.0F, 4.0F, 0.15F, false);
        bone2.texOffs(4, 1).addBox(7.3F, -22.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        Body = new ModelRenderer(this);
        Body.setPos(0.0F, 0.0F, 0.0F);
        Body.texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
        Head = new ModelRenderer(this);
        Head.setPos(0.0F, 0.0F, 0.0F);
        Body.addChild(Head);
        RightBoot = new ModelRenderer(this);
        RightBoot.setPos(-1.9F, 12.0F, 0.0F);
        RightBoot.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        RightBoot.texOffs(0, 48).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.15F, false);
        LeftBoot = new ModelRenderer(this);
        LeftBoot.setPos(1.9F, 12.0F, 0.0F);
        LeftBoot.texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        LeftBoot.texOffs(0, 56).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.15F, false);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
                       float alpha) {

        this.Body.copyFrom(this.body);
        this.RightArm.copyFrom(this.rightArm);
        this.LeftArm.copyFrom(this.leftArm);
        this.RightLeg.copyFrom(this.rightLeg);
        this.LeftLeg.copyFrom(this.leftLeg);
        this.RightBoot.copyFrom(this.rightLeg);
        this.LeftBoot.copyFrom(this.leftLeg);

        if(this.slotType.equals(EquipmentSlotType.HEAD))
            this.Head.render(matrixStack, buffer, packedLight, packedOverlay);

        if(this.slotType.equals(EquipmentSlotType.CHEST)){
            this.LeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
            this.RightArm.render(matrixStack, buffer, packedLight, packedOverlay);
            this.Body.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        if(this.slotType.equals(EquipmentSlotType.LEGS)) {
            this.LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
            this.RightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        if(this.slotType.equals(EquipmentSlotType.FEET)) {
            this.LeftBoot.render(matrixStack, buffer, packedLight, packedOverlay);
            this.RightBoot.render(matrixStack, buffer, packedLight, packedOverlay);
        }
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

}
