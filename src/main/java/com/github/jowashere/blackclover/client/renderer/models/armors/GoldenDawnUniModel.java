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
public class GoldenDawnUniModel<T extends LivingEntity> extends BipedModel<T>{

    private final ModelRenderer LeftLeg;
    private final ModelRenderer bone3;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer LeftArm;
    private final ModelRenderer Body;
    private final ModelRenderer RightArm;
    private final ModelRenderer RightLeg;
    private final ModelRenderer bone2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;

    EquipmentSlotType slotType;

    public GoldenDawnUniModel(EquipmentSlotType slotType) {
        super(1);

        this.slotType = slotType;

        texWidth = 128;
        texHeight = 128;

        LeftLeg = new ModelRenderer(this);
        LeftLeg.setPos(1.9F, 12.0F, 0.0F);
        LeftLeg.texOffs(0, 44).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        bone3 = new ModelRenderer(this);
        bone3.setPos(-1.9F, 12.0F, 0.0F);
        LeftLeg.addChild(bone3);
        bone3.texOffs(0, 18).addBox(-0.2F, -5.0F, 1.1F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bone3.texOffs(16, 51).addBox(3.0F, -5.0F, -2.1F, 1.0F, 1.0F, 4.0F, 0.0F, false);
        bone3.texOffs(0, 16).addBox(3.0F, -5.0F, 1.1F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bone3.texOffs(49, 9).addBox(-0.2F, -5.0F, -2.1F, 1.0F, 1.0F, 4.0F, 0.0F, false);
        bone3.texOffs(20, 28).addBox(1.0F, -5.0F, 1.1F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        bone3.texOffs(20, 28).addBox(0.0F, -5.0F, 1.1F, 2.0F, 1.0F, 1.0F, 0.0F, true);
        bone3.texOffs(0, 0).addBox(1.4F, -4.8073F, -2.1F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(3.0F, -5.0F, -1.8F);
        bone3.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0F, -0.2182F);
        cube_r1.texOffs(0, 2).addBox(-1.0F, 0.0F, -0.3F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(0.8F, -5.0F, -1.8F);
        bone3.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.0F, 0.2182F);
        cube_r2.texOffs(0, 28).addBox(0.0F, 0.0F, -0.3F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        LeftArm = new ModelRenderer(this);
        LeftArm.setPos(5.0F, 2.0F, 0.0F);
        LeftArm.texOffs(24, 39).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        Body = new ModelRenderer(this);
        Body.setPos(0.0F, 0.0F, 0.0F);
        Body.texOffs(0, 28).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
        Body.texOffs(49, 6).addBox(-4.0F, 9.0F, 1.2F, 8.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(40, 0).addBox(-4.0F, 9.0F, -2.2F, 8.0F, 2.0F, 4.0F, 0.0F, false);

        RightArm = new ModelRenderer(this);
        RightArm.setPos(-5.0F, 2.0F, 0.0F);
        RightArm.texOffs(40, 18).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        RightLeg = new ModelRenderer(this);
        RightLeg.setPos(-1.9F, 12.0F, 0.0F);
        RightLeg.texOffs(40, 39).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setPos(1.9F, 12.0F, 0.0F);
        RightLeg.addChild(bone2);
        bone2.texOffs(0, 18).addBox(-0.8F, -5.0F, 1.1F, 1.0F, 1.0F, 1.0F, 0.0F, true);
        bone2.texOffs(16, 51).addBox(-4.0F, -5.0F, -2.1F, 1.0F, 1.0F, 4.0F, 0.0F, true);
        bone2.texOffs(0, 16).addBox(-4.0F, -5.0F, 1.1F, 1.0F, 1.0F, 1.0F, 0.0F, true);
        bone2.texOffs(49, 9).addBox(-0.8F, -5.0F, -2.1F, 1.0F, 1.0F, 4.0F, 0.0F, true);
        bone2.texOffs(20, 28).addBox(-3.0F, -5.0F, 1.1F, 2.0F, 1.0F, 1.0F, 0.0F, true);
        bone2.texOffs(20, 28).addBox(-2.0F, -5.0F, 1.1F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        bone2.texOffs(0, 0).addBox(-2.4F, -4.8073F, -2.1F, 1.0F, 1.0F, 1.0F, 0.0F, true);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(-3.0F, -5.0F, -1.8F);
        bone2.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 0.0F, 0.2182F);
        cube_r3.texOffs(0, 2).addBox(0.0F, 0.0F, -0.3F, 1.0F, 1.0F, 1.0F, 0.0F, true);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(-0.8F, -5.0F, -1.8F);
        bone2.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 0.0F, -0.2182F);
        cube_r4.texOffs(0, 28).addBox(-1.0F, 0.0F, -0.3F, 1.0F, 1.0F, 1.0F, 0.0F, true);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){

        this.Body.copyFrom(this.body);
        this.RightArm.copyFrom(this.rightArm);
        this.LeftArm.copyFrom(this.leftArm);
        this.RightLeg.copyFrom(this.rightLeg);
        this.LeftLeg.copyFrom(this.leftLeg);

        if(this.slotType.equals(EquipmentSlotType.LEGS) || this.slotType.equals(EquipmentSlotType.FEET)) {
            this.LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
            this.RightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        if(this.slotType.equals(EquipmentSlotType.CHEST)){
            this.LeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
            this.RightArm.render(matrixStack, buffer, packedLight, packedOverlay);
            this.Body.render(matrixStack, buffer, packedLight, packedOverlay);
        }
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

}
