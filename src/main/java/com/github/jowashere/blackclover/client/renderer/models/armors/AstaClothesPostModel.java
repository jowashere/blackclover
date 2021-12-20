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
public class AstaClothesPostModel<T extends LivingEntity> extends BipedModel<T>{

    private final ModelRenderer LeftLeg;
    private final ModelRenderer RightLeg;
    private final ModelRenderer RightArm;
    private final ModelRenderer LeftArm;
    private final ModelRenderer Head;
    private final ModelRenderer cube_r1;
    private final ModelRenderer Body;
    private final ModelRenderer bone5;
    private final ModelRenderer cube_r2;
    private final ModelRenderer RightBoot;
    private final ModelRenderer bone3;
    private final ModelRenderer LeftBoot;
    private final ModelRenderer bone4;

    EquipmentSlotType slotType;

    public AstaClothesPostModel(EquipmentSlotType slotType) {

        super(1);

        this.slotType = slotType;

        texWidth = 64;
        texHeight = 64;

        LeftLeg = new ModelRenderer(this);
        LeftLeg.setPos(1.9F, 12.0F, 0.0F);
        LeftLeg.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        RightLeg = new ModelRenderer(this);
        RightLeg.setPos(-1.9F, 12.0F, 0.0F);
        RightLeg.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        RightArm = new ModelRenderer(this);
        RightArm.setPos(-5.0F, 2.0F, 0.0F);
        RightArm.texOffs(48, 48).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        LeftArm = new ModelRenderer(this);
        LeftArm.setPos(5.0F, 2.0F, 0.0F);
        LeftArm.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        Head = new ModelRenderer(this);
        Head.setPos(0.0F, 0.0F, 0.0F);


        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(3.9F, -6.0F, -4.2F);
        Head.addChild(cube_r1);
        setRotationAngle(cube_r1, -0.1309F, 0.0F, 0.0F);
        cube_r1.texOffs(0, 19).addBox(-7.9F, -1.5F, 0.1F, 8.0F, 2.0F, 8.0F, 0.3F, false);

        Body = new ModelRenderer(this);
        Body.setPos(0.0F, 0.0F, 0.0F);
        Body.texOffs(36, 31).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

        bone5 = new ModelRenderer(this);
        bone5.setPos(0.0F, 10.0F, 0.0F);
        Body.addChild(bone5);
        setRotationAngle(bone5, 0.0F, 0.0F, 0.0436F);
        bone5.texOffs(23, 41).addBox(-1.0F, -1.0F, -2.35F, 1.0F, 3.0F, 3.0F, 0.0F, false);
        bone5.texOffs(0, 0).addBox(0.0F, -1.0F, -2.1F, 1.0F, 3.0F, 3.0F, 0.0F, false);
        bone5.texOffs(40, 0).addBox(1.0F, -1.0F, -2.35F, 1.0F, 3.0F, 3.0F, 0.0F, false);
        bone5.texOffs(16, 38).addBox(2.0F, -1.0F, -2.3F, 2.0F, 3.0F, 3.0F, 0.0F, false);
        bone5.texOffs(0, 31).addBox(-4.0F, -1.0F, -2.3F, 3.0F, 3.0F, 3.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(0.0F, 12.0F, -11.0F);
        bone5.addChild(cube_r2);
        setRotationAngle(cube_r2, -0.7854F, 0.0F, 0.0F);
        cube_r2.texOffs(0, 42).addBox(-0.0436F, -16.7064F, -2.2064F, 1.0F, 2.0F, 3.0F, 0.0F, false);
        cube_r2.texOffs(0, 42).addBox(0.0F, -16.0F, -1.5F, 1.0F, 2.0F, 3.0F, 0.0F, false);

        RightBoot = new ModelRenderer(this);
        RightBoot.setPos(-1.9F, 12.0F, 0.0F);
        RightBoot.texOffs(32, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        bone3 = new ModelRenderer(this);
        bone3.setPos(1.9F, 12.0F, 0.0F);
        RightBoot.addChild(bone3);
        bone3.texOffs(16, 58).addBox(-3.9F, -8.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.2F, false);

        LeftBoot = new ModelRenderer(this);
        LeftBoot.setPos(1.9F, 12.0F, 0.0F);
        LeftBoot.texOffs(32, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        bone4 = new ModelRenderer(this);
        bone4.setPos(-5.9F, 21.0F, 0.0F);
        LeftBoot.addChild(bone4);
        bone4.texOffs(16, 58).addBox(3.9F, -17.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.2F, false);
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
        this.Head.copyFrom(this.head);

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
