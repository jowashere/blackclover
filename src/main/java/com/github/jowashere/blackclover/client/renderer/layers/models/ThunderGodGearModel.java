package com.github.jowashere.blackclover.client.renderer.layers.models;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ThunderGodGearModel<T extends LivingEntity> extends BipedModel<T> {

    private final ModelRenderer LeftLeg;
    private final ModelRenderer bone4;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;
    private final ModelRenderer cube_r5;
    private final ModelRenderer cube_r6;
    private final ModelRenderer RightLeg;
    private final ModelRenderer bone5;
    private final ModelRenderer cube_r7;
    private final ModelRenderer cube_r8;
    private final ModelRenderer cube_r9;
    private final ModelRenderer cube_r10;
    private final ModelRenderer cube_r11;
    private final ModelRenderer cube_r12;
    private final ModelRenderer bone2;
    private final ModelRenderer cube_r13;
    private final ModelRenderer cube_r14;
    private final ModelRenderer cube_r15;
    private final ModelRenderer RightArm;
    private final ModelRenderer bone3;
    private final ModelRenderer cube_r16;
    private final ModelRenderer cube_r17;
    private final ModelRenderer cube_r18;
    private final ModelRenderer LeftArm;

    public boolean showGloves;
    public boolean showBoots;

    public ThunderGodGearModel() {


        super(RenderType::entitySolid, 1, 0.0F, 64, 64);
        texWidth = 16;
        texHeight = 16;

        LeftLeg = new ModelRenderer(this);
        LeftLeg.setPos(1.9F, 12.0F, 0.0F);

        bone4 = new ModelRenderer(this);
        bone4.setPos(0.1F, 24.0F, 6.0F);
        LeftLeg.addChild(bone4);
        bone4.texOffs(2, 4).addBox(-2.1F, -19.8F, -8.0F, 4.0F, 8.0F, 4.0F, 0.2F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(0.0F, -20.6322F, -3.9935F);
        bone4.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0873F, 0.0F, 0.0F);
        cube_r1.texOffs(2, 4).addBox(-2.1F, -0.2F, 0.2F, 4.0F, 2.0F, 1.0F, 0.0F, true);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(0.0F, -20.6322F, -8.0065F);
        bone4.addChild(cube_r2);
        setRotationAngle(cube_r2, -0.0873F, 0.0F, 0.0F);
        cube_r2.texOffs(2, 4).addBox(-2.1F, -0.2F, -1.2F, 4.0F, 2.0F, 1.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(0.0F, -19.0828F, -4.8F);
        bone4.addChild(cube_r3);
        setRotationAngle(cube_r3, -1.0472F, 0.0F, 0.0F);
        cube_r3.texOffs(2, 4).addBox(-2.1F, -1.8F, 0.2F, 4.0F, 2.0F, 1.0F, 0.0F, true);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(0.0F, -19.0828F, -7.2F);
        bone4.addChild(cube_r4);
        setRotationAngle(cube_r4, 1.0472F, 0.0F, 0.0F);
        cube_r4.texOffs(2, 4).addBox(-2.1F, -1.8F, -1.2F, 4.0F, 2.0F, 1.0F, 0.0F, false);

        cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(-1.2F, -20.0828F, -6.0F);
        bone4.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.0F, 0.0F, -0.7854F);


        cube_r6 = new ModelRenderer(this);
        cube_r6.setPos(1.2F, -20.0828F, -6.0F);
        bone4.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.0F, 0.0F, 0.7854F);
        cube_r6.texOffs(11, 13).addBox(0.2F, -1.8F, -2.0F, 1.0F, 2.0F, 4.0F, 0.0F, false);

        RightLeg = new ModelRenderer(this);
        RightLeg.setPos(-1.9F, 12.0F, 0.0F);

        bone5 = new ModelRenderer(this);
        bone5.setPos(-0.1F, 24.0F, 6.0F);
        RightLeg.addChild(bone5);
        bone5.texOffs(2, 0).addBox(-1.9F, -19.8F, -8.0F, 4.0F, 8.0F, 4.0F, 0.2F, true);

        cube_r7 = new ModelRenderer(this);
        cube_r7.setPos(0.0F, -20.6322F, -3.9935F);
        bone5.addChild(cube_r7);
        setRotationAngle(cube_r7, 0.0873F, 0.0F, 0.0F);
        cube_r7.texOffs(2, 0).addBox(-1.9F, -0.2F, 0.2F, 4.0F, 2.0F, 1.0F, 0.0F, false);

        cube_r8 = new ModelRenderer(this);
        cube_r8.setPos(0.0F, -20.6322F, -8.0065F);
        bone5.addChild(cube_r8);
        setRotationAngle(cube_r8, -0.0873F, 0.0F, 0.0F);
        cube_r8.texOffs(2, 0).addBox(-1.9F, -0.2F, -1.2F, 4.0F, 2.0F, 1.0F, 0.0F, true);

        cube_r9 = new ModelRenderer(this);
        cube_r9.setPos(0.0F, -19.0828F, -4.8F);
        bone5.addChild(cube_r9);
        setRotationAngle(cube_r9, -1.0472F, 0.0F, 0.0F);
        cube_r9.texOffs(2, 0).addBox(-1.9F, -1.8F, 0.2F, 4.0F, 2.0F, 1.0F, 0.0F, false);

        cube_r10 = new ModelRenderer(this);
        cube_r10.setPos(0.0F, -19.0828F, -7.2F);
        bone5.addChild(cube_r10);
        setRotationAngle(cube_r10, 1.0472F, 0.0F, 0.0F);
        cube_r10.texOffs(2, 0).addBox(-1.9F, -1.8F, -1.2F, 4.0F, 2.0F, 1.0F, 0.0F, true);

        cube_r11 = new ModelRenderer(this);
        cube_r11.setPos(1.2F, -20.0828F, -6.0F);
        bone5.addChild(cube_r11);
        setRotationAngle(cube_r11, 0.0F, 0.0F, 0.7854F);


        cube_r12 = new ModelRenderer(this);
        cube_r12.setPos(-1.2F, -20.0828F, -6.0F);
        bone5.addChild(cube_r12);
        setRotationAngle(cube_r12, 0.0F, 0.0F, -0.7854F);
        cube_r12.texOffs(11, 13).addBox(-1.2F, -1.8F, -2.0F, 1.0F, 2.0F, 4.0F, 0.0F, true);

        RightArm = new ModelRenderer(this);
        RightArm.setPos(-5.0F, 2.0F, 0.0F);

        bone3 = new ModelRenderer(this);
        bone3.setPos(5.0F, 22.0F, 0.0F);
        RightArm.addChild(bone3);
        bone3.texOffs(1, 0).addBox(-8.0F, -20.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.2F, true);

        cube_r13 = new ModelRenderer(this);
        cube_r13.setPos(-7.2F, -20.0828F, 0.0F);
        bone3.addChild(cube_r13);
        setRotationAngle(cube_r13, 0.0F, 0.0F, -0.7854F);
        cube_r13.texOffs(0, 4).addBox(-1.2F, -1.8F, -2.0F, 1.0F, 2.0F, 4.0F, 0.0F, true);

        cube_r14 = new ModelRenderer(this);
        cube_r14.setPos(-6.0F, -20.0828F, -1.2F);
        bone3.addChild(cube_r14);
        setRotationAngle(cube_r14, 0.7854F, 0.0F, 0.0F);
        cube_r14.texOffs(0, 0).addBox(-2.0F, -1.8F, -1.2F, 4.0F, 2.0F, 1.0F, 0.0F, true);

        cube_r15 = new ModelRenderer(this);
        cube_r15.setPos(-6.0F, -20.0828F, 1.2F);
        bone3.addChild(cube_r15);
        setRotationAngle(cube_r15, -0.7854F, 0.0F, 0.0F);
        cube_r15.texOffs(1, 0).addBox(-2.0F, -1.8F, 0.2F, 4.0F, 2.0F, 1.0F, 0.0F, true);

        LeftArm = new ModelRenderer(this);
        LeftArm.setPos(5.0F, 2.0F, 0.0F);

        bone2 = new ModelRenderer(this);
        bone2.setPos(-5.0F, 22.0F, 0.0F);
        LeftArm.addChild(bone2);
        bone2.texOffs(1, 3).addBox(4.0F, -20.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.2F, false);

        cube_r16 = new ModelRenderer(this);
        cube_r16.setPos(7.2F, -20.0828F, 0.0F);
        bone2.addChild(cube_r16);
        setRotationAngle(cube_r16, 0.0F, 0.0F, 0.7854F);
        cube_r16.texOffs(0, 4).addBox(0.2F, -1.8F, -2.0F, 1.0F, 2.0F, 4.0F, 0.0F, false);

        cube_r17 = new ModelRenderer(this);
        cube_r17.setPos(6.0F, -20.0828F, -1.2F);
        bone2.addChild(cube_r17);
        setRotationAngle(cube_r17, 0.7854F, 0.0F, 0.0F);
        cube_r17.texOffs(0, 4).addBox(-2.0F, -1.8F, -1.2F, 4.0F, 2.0F, 1.0F, 0.0F, false);

        cube_r18 = new ModelRenderer(this);
        cube_r18.setPos(6.0F, -20.0828F, 1.2F);
        bone2.addChild(cube_r18);
        setRotationAngle(cube_r18, -0.7854F, 0.0F, 0.0F);
        cube_r18.texOffs(1, 4).addBox(-2.0F, -1.8F, 0.2F, 4.0F, 2.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        if(showGloves){
            ImmutableList.of(this.RightArm, this.LeftArm).forEach((modelRenderer) -> {
                modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
        }
        if(showBoots){
            ImmutableList.of(this.LeftLeg, this.RightLeg).forEach((modelRenderer) -> {
                modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
        }

    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.crouching = entityIn.isCrouching();

        BipedModel.ArmPose mainHandPos = armPose(entityIn, Hand.MAIN_HAND);
        BipedModel.ArmPose offHandPos = armPose(entityIn, Hand.OFF_HAND);

        this.swimAmount = entityIn.getSwimAmount(ageInTicks);

        if (entityIn.getMainArm() == HandSide.RIGHT) {
            this.rightArmPose = mainHandPos;
            this.leftArmPose = offHandPos;
        } else {
            this.rightArmPose = offHandPos;
            this.leftArmPose = mainHandPos;
        }

        this.RightArm.copyFrom(this.rightArm);
        this.LeftArm.copyFrom(this.leftArm);
        this.RightLeg.copyFrom(this.rightLeg);
        this.LeftLeg.copyFrom(this.leftLeg);
    }

    private static BipedModel.ArmPose armPose(LivingEntity livingEntity, Hand hand) {
        ItemStack itemstack = livingEntity.getItemInHand(hand);
        if (itemstack.isEmpty()) {
            return BipedModel.ArmPose.EMPTY;
        } else {
            if (livingEntity.getUsedItemHand() == hand && livingEntity.getUseItemRemainingTicks() > 0) {
                UseAction useaction = itemstack.getUseAnimation();
                if (useaction == UseAction.BLOCK) {
                    return BipedModel.ArmPose.BLOCK;
                }

                if (useaction == UseAction.BOW) {
                    return BipedModel.ArmPose.BOW_AND_ARROW;
                }

                if (useaction == UseAction.SPEAR) {
                    return BipedModel.ArmPose.THROW_SPEAR;
                }

                if (useaction == UseAction.CROSSBOW && hand == livingEntity.getUsedItemHand()) {
                    return BipedModel.ArmPose.CROSSBOW_CHARGE;
                }
            } else if (!livingEntity.swinging && itemstack.getItem() == Items.CROSSBOW && CrossbowItem.isCharged(itemstack)) {
                return BipedModel.ArmPose.CROSSBOW_HOLD;
            }

            return BipedModel.ArmPose.ITEM;
        }
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
