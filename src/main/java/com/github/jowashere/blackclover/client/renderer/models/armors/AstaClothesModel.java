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
public class AstaClothesModel<T extends LivingEntity> extends BipedModel<T>{

    private final ModelRenderer LeftLeg;
    private final ModelRenderer RightLeg;
    private final ModelRenderer RightArm;
    private final ModelRenderer bone3;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer LeftArm;
    private final ModelRenderer bone2;
    private final ModelRenderer cube_r4;
    private final ModelRenderer cube_r5;
    private final ModelRenderer cube_r6;
    private final ModelRenderer Body;
    private final ModelRenderer bone5;
    private final ModelRenderer cube_r7;
    private final ModelRenderer Head;
    private final ModelRenderer cube_r8;
    private final ModelRenderer LeftBoot;
    private final ModelRenderer RightBoot;

    private EquipmentSlotType slotType;

    public AstaClothesModel(EquipmentSlotType slotType) {
        super(1);

        this.slotType = slotType;

        texWidth = 64;
        texHeight= 64;

        LeftLeg = new ModelRenderer(this);
        LeftLeg.setPos(1.9F, 12.0F, 0.0F);
        LeftLeg.texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        RightLeg = new ModelRenderer(this);
        RightLeg.setPos(-1.9F, 12.0F, 0.0F);
        RightLeg.texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        RightArm = new ModelRenderer(this);
        RightArm.setPos(-5.0F, 2.0F, 0.0F);
        RightArm.texOffs(32, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        bone3 = new ModelRenderer(this);
        bone3.setPos(5.0F, 22.0F, 0.0F);
        RightArm.addChild(bone3);
        bone3.texOffs(0, 42).addBox(-8.0F, -17.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.2F, false);
        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(-8.0F, -16.0F, -2.0F);
        bone3.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0F, -0.5672F);
        cube_r1.texOffs(0, 16).addBox(-0.2F, -2.0F, -0.2F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        cube_r1.texOffs(0, 19).addBox(-0.2F, -2.0F, 2.8F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(-7.7661F, -17.6328F, 1.7888F);
        bone3.addChild(cube_r2);
        setRotationAngle(cube_r2, -0.3927F, 0.0F, -0.5672F);
        cube_r2.texOffs(4, 16).addBox(-1.2746F, -0.3132F, -1.2F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(-7.7661F, -17.6328F, -1.7888F);
        bone3.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.3927F, 0.0F, -0.5672F);
        cube_r3.texOffs(4, 19).addBox(-1.2746F, -0.3132F, -0.2F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        LeftArm = new ModelRenderer(this);
        LeftArm.setPos(5.0F, 2.0F, 0.0F);
        LeftArm.texOffs(24, 26).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        bone2 = new ModelRenderer(this);
        bone2.setPos(-5.0F, 22.0F, 0.0F);
        LeftArm.addChild(bone2);
        bone2.texOffs(16, 42).addBox(4.0F, -17.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.2F, false);
        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(8.0F, -16.0F, -2.0F);
        bone2.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 0.0F, 0.5672F);
        cube_r4.texOffs(0, 26).addBox(-0.8F, -2.0F, -0.2F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        cube_r4.texOffs(24, 0).addBox(-0.8F, -2.0F, 2.8F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(7.7661F, -17.6328F, 1.7888F);
        bone2.addChild(cube_r5);
        setRotationAngle(cube_r5, -0.3927F, 0.0F, 0.5672F);
        cube_r5.texOffs(24, 3).addBox(0.2746F, -0.3132F, -1.2F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        cube_r6 = new ModelRenderer(this);
        cube_r6.setPos(7.7661F, -17.6328F, -1.7888F);
        bone2.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.3927F, 0.0F, 0.5672F);
        cube_r6.texOffs(24, 16).addBox(0.2746F, -0.3132F, -0.2F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body = new ModelRenderer(this);
        Body.setPos(0.0F, 0.0F, 0.0F);
        Body.texOffs(0, 26).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
        bone5 = new ModelRenderer(this);
        bone5.setPos(0.0F, 24.0F, 0.0F);
        Body.addChild(bone5);
        bone5.texOffs(27, 5).addBox(-1.0F, -16.0F, -2.35F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        bone5.texOffs(20, 26).addBox(0.0F, -16.0F, -2.1F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        bone5.texOffs(24, 26).addBox(1.0F, -16.0F, -2.35F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        bone5.texOffs(0, 3).addBox(2.0F, -16.0F, -2.3F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        bone5.texOffs(0, 0).addBox(-4.0F, -16.0F, -2.3F, 3.0F, 2.0F, 1.0F, 0.0F, false);
        cube_r7 = new ModelRenderer(this);
        cube_r7.setPos(0.0F, -4.0F, -11.0F);
        bone5.addChild(cube_r7);
        setRotationAngle(cube_r7, -0.7854F, 0.0F, 0.0F);
        cube_r7.texOffs(0, 6).addBox(0.0F, -15.0F, -1.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Head = new ModelRenderer(this);
        Head.setPos(0.0F, 0.0F, 0.0F);
        Head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        cube_r8 = new ModelRenderer(this);
        cube_r8.setPos(4.0F, -6.0F, -4.0F);
        Head.addChild(cube_r8);
        setRotationAngle(cube_r8, -0.1309F, 0.0F, 0.0F);
        cube_r8.texOffs(0, 16).addBox(-8.0F, -1.3F, -0.1F, 8.0F, 2.0F, 8.0F, 0.35F, false);
        LeftBoot = new ModelRenderer(this);
        LeftBoot.setPos(1.9F, 12.0F, 0.0F);
        LeftBoot.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        LeftBoot.texOffs(32, 58).addBox(-2.0F, 7.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.3F, false);
        RightBoot = new ModelRenderer(this);
        RightBoot.setPos(-1.9F, 12.0F, 0.0F);
        RightBoot.texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        RightBoot.texOffs(32, 58).addBox(-2.0F, 7.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.3F, false);
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
