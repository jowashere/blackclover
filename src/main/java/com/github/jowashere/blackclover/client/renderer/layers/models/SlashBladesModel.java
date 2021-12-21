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

public class SlashBladesModel<T extends LivingEntity> extends BipedModel<T> {

    private final ModelRenderer LeftArm;
    private final ModelRenderer bone7;
    private final ModelRenderer bone2;
    private final ModelRenderer cube_r1;
    private final ModelRenderer bone9;
    private final ModelRenderer cube_r2;
    private final ModelRenderer bone10;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;
    private final ModelRenderer cube_r5;
    private final ModelRenderer bone11;
    private final ModelRenderer cube_r6;
    private final ModelRenderer cube_r7;
    private final ModelRenderer cube_r8;
    private final ModelRenderer RightArm;
    private final ModelRenderer bone3;
    private final ModelRenderer bone4;
    private final ModelRenderer cube_r9;
    private final ModelRenderer bone5;
    private final ModelRenderer cube_r10;
    private final ModelRenderer bone6;
    private final ModelRenderer cube_r11;
    private final ModelRenderer cube_r12;
    private final ModelRenderer cube_r13;
    private final ModelRenderer bone12;
    private final ModelRenderer cube_r14;
    private final ModelRenderer cube_r15;
    private final ModelRenderer cube_r16;

    public SlashBladesModel() {
        super(RenderType::entityTranslucent, 1, 0.0F, 64, 64);
        texWidth = 16;
        texHeight = 16;

        LeftArm = new ModelRenderer(this);
        LeftArm.setPos(5.0F, 2.0F, 0.0F);

        bone7 = new ModelRenderer(this);
        bone7.setPos(24.0F, -5.0F, 1.0F);
        LeftArm.addChild(bone7);
        setRotationAngle(bone7, 0.0F, 0.0F, 0.2182F);


        bone2 = new ModelRenderer(this);
        bone2.setPos(0.7627F, -2.0835F, 0.0F);
        bone7.addChild(bone2);
        setRotationAngle(bone2, 0.0F, 0.0F, -0.9599F);
        bone2.texOffs(2, 6).addBox(-25.6784F, -7.5411F, -1.0F, 4.0F, 3.0F, 1.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(-2.7024F, -1.0509F, 0.0F);
        bone2.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0F, 0.3491F);
        cube_r1.texOffs(2, 6).addBox(-24.1522F, 0.8198F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        bone9 = new ModelRenderer(this);
        bone9.setPos(1.2976F, 2.9491F, 0.0F);
        bone2.addChild(bone9);
        setRotationAngle(bone9, 0.0F, 0.0F, -0.4363F);
        bone9.texOffs(2, 6).addBox(-17.6578F, -19.4985F, -1.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(0.0F, 0.0F, 0.0F);
        bone9.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.0F, -0.2618F);
        cube_r2.texOffs(2, 6).addBox(-12.786F, -21.5065F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);

        bone10 = new ModelRenderer(this);
        bone10.setPos(3.5747F, -1.3488F, 0.0F);
        bone9.addChild(bone10);
        setRotationAngle(bone10, 0.0F, 0.0F, -0.2618F);


        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(0.5566F, -0.6224F, 0.0F);
        bone10.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 0.0F, -0.0436F);
        cube_r3.texOffs(2, 6).addBox(-12.3107F, -22.021F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(3.0526F, -1.2243F, 0.0F);
        bone10.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 0.0F, -0.48F);
        cube_r4.texOffs(0, 2).addBox(-2.0543F, -24.526F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);

        cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(0.3401F, 0.4014F, 0.0F);
        bone10.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.0F, 0.0F, -0.3491F);
        cube_r5.texOffs(2, 6).addBox(-5.0013F, -24.6429F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        bone11 = new ModelRenderer(this);
        bone11.setPos(-2.7339F, -0.9004F, 0.0F);
        bone9.addChild(bone11);
        setRotationAngle(bone11, 0.0F, 0.0F, -0.3054F);


        cube_r6 = new ModelRenderer(this);
        cube_r6.setPos(-30.0962F, -0.2835F, 0.0F);
        bone11.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.0F, 0.0F, 0.0436F);
        cube_r6.texOffs(3, 6).addBox(14.7105F, -23.7378F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r7 = new ModelRenderer(this);
        cube_r7.setPos(-32.5922F, -0.8855F, 0.0F);
        bone11.addChild(cube_r7);
        setRotationAngle(cube_r7, 0.0F, 0.0F, 0.48F);
        cube_r7.texOffs(3, 6).addBox(5.2226F, -28.364F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r8 = new ModelRenderer(this);
        cube_r8.setPos(-29.8798F, 0.7402F, 0.0F);
        bone11.addChild(cube_r8);
        setRotationAngle(cube_r8, 0.0F, 0.0F, 0.3491F);
        cube_r8.texOffs(3, 6).addBox(6.6349F, -27.904F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        RightArm = new ModelRenderer(this);
        RightArm.setPos(-5.0F, 2.0F, 0.0F);

        bone3 = new ModelRenderer(this);
        bone3.setPos(-24.0F, -5.0F, 1.0F);
        RightArm.addChild(bone3);
        setRotationAngle(bone3, 0.0F, 0.0F, -0.2182F);


        bone4 = new ModelRenderer(this);
        bone4.setPos(-0.7627F, -2.0835F, 0.0F);
        bone3.addChild(bone4);
        setRotationAngle(bone4, 0.0F, 0.0F, 0.9599F);
        bone4.texOffs(3, 6).addBox(21.6784F, -7.5411F, -1.0F, 4.0F, 3.0F, 1.0F, 0.0F, false);

        cube_r9 = new ModelRenderer(this);
        cube_r9.setPos(2.7024F, -1.0509F, 0.0F);
        bone4.addChild(cube_r9);
        setRotationAngle(cube_r9, 0.0F, 0.0F, -0.3491F);
        cube_r9.texOffs(3, 6).addBox(21.1522F, 0.8198F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        bone5 = new ModelRenderer(this);
        bone5.setPos(-1.2976F, 2.9491F, 0.0F);
        bone4.addChild(bone5);
        setRotationAngle(bone5, 0.0F, 0.0F, 0.4363F);
        bone5.texOffs(3, 6).addBox(13.6578F, -19.4985F, -1.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);

        cube_r10 = new ModelRenderer(this);
        cube_r10.setPos(0.0F, 0.0F, 0.0F);
        bone5.addChild(cube_r10);
        setRotationAngle(cube_r10, 0.0F, 0.0F, 0.2618F);
        cube_r10.texOffs(3, 6).addBox(8.786F, -21.5065F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);

        bone6 = new ModelRenderer(this);
        bone6.setPos(-3.5747F, -1.3488F, 0.0F);
        bone5.addChild(bone6);
        setRotationAngle(bone6, 0.0F, 0.0F, 0.2618F);


        cube_r11 = new ModelRenderer(this);
        cube_r11.setPos(-0.5566F, -0.6224F, 0.0F);
        bone6.addChild(cube_r11);
        setRotationAngle(cube_r11, 0.0F, 0.0F, 0.0436F);
        cube_r11.texOffs(3, 6).addBox(9.3107F, -22.021F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r12 = new ModelRenderer(this);
        cube_r12.setPos(-3.0526F, -1.2243F, 0.0F);
        bone6.addChild(cube_r12);
        setRotationAngle(cube_r12, 0.0F, 0.0F, 0.48F);
        cube_r12.texOffs(0, 2).addBox(1.0543F, -24.526F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r13 = new ModelRenderer(this);
        cube_r13.setPos(-0.3401F, 0.4014F, 0.0F);
        bone6.addChild(cube_r13);
        setRotationAngle(cube_r13, 0.0F, 0.0F, 0.3491F);
        cube_r13.texOffs(3, 6).addBox(2.0013F, -24.6429F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        bone12 = new ModelRenderer(this);
        bone12.setPos(2.7339F, -0.9004F, 0.0F);
        bone5.addChild(bone12);
        setRotationAngle(bone12, 0.0F, 0.0F, 0.3054F);


        cube_r14 = new ModelRenderer(this);
        cube_r14.setPos(30.0962F, -0.2835F, 0.0F);
        bone12.addChild(cube_r14);
        setRotationAngle(cube_r14, 0.0F, 0.0F, -0.0436F);
        cube_r14.texOffs(4, 6).addBox(-17.7105F, -23.7378F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r15 = new ModelRenderer(this);
        cube_r15.setPos(32.5922F, -0.8855F, 0.0F);
        bone12.addChild(cube_r15);
        setRotationAngle(cube_r15, 0.0F, 0.0F, -0.48F);
        cube_r15.texOffs(4, 6).addBox(-6.2226F, -28.364F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r16 = new ModelRenderer(this);
        cube_r16.setPos(29.8798F, 0.7402F, 0.0F);
        bone12.addChild(cube_r16);
        setRotationAngle(cube_r16, 0.0F, 0.0F, -0.3491F);
        cube_r16.texOffs(3, 7).addBox(-9.6349F, -27.904F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
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
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.RightArm, this.LeftArm).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
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
