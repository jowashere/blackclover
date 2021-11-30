package com.github.jowashere.blackclover.client.renderer.layers.models;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
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

public class BlackModeModel<T extends LivingEntity> extends BipedModel<T>{
    private final ModelRenderer LeftArm3;
    private final ModelRenderer Body2;
    private final ModelRenderer Wings;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;
    private final ModelRenderer bone52;
    private final ModelRenderer cube_r5;
    private final ModelRenderer cube_r6;
    private final ModelRenderer bone53;
    private final ModelRenderer bone54;
    private final ModelRenderer bone55;
    private final ModelRenderer bone56;
    private final ModelRenderer bone33;
    private final ModelRenderer bone17;
    private final ModelRenderer bone18;
    private final ModelRenderer cube_r7;
    private final ModelRenderer bone19;
    private final ModelRenderer cube_r8;
    private final ModelRenderer Head2;
    private final ModelRenderer bone;
    private final ModelRenderer cube_r9;
    private final ModelRenderer cube_r10;
    private final ModelRenderer cube_r11;
    private final ModelRenderer cube_r12;

    public BlackModeModel() {
        super(RenderType::entityTranslucent, 1, 0.0F, 64, 64);
        texWidth = 128;
        texHeight = 128;

        LeftArm3 = new ModelRenderer(this);
        LeftArm3.setPos(-4.0F, 2.0F, 0.0F);
        LeftArm3.texOffs(32, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.02F, false);
        LeftArm3.texOffs(0, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.27F, false);

        Body2 = new ModelRenderer(this);
        Body2.setPos(-1.0F, 0.0F, 0.0F);
        Body2.texOffs(24, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.02F, 0.2f, 0.4f);
        Body2.texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.27F, false);

        Wings = new ModelRenderer(this);
        Wings.setPos(-1.0F, 1.0F, 2.7F);
        Body2.addChild(Wings);
        setRotationAngle(Wings, 0.0F, 0.3927F, 0.0873F);


        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(-38.6383F, -24.7128F, -1.0F);
        Wings.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0F, 0.6545F);
        cube_r1.texOffs(111, 52).addBox(-0.9063F, -2.0647F, 0.0F, 14.0F, 10.0F, 0.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(-20.0F, 0.0F, -1.0F);
        Wings.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.0F, 0.6545F);
        cube_r2.texOffs(109, 28).addBox(-16.7373F, -10.3244F, 0.0F, 16.0F, 11.0F, 0.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(-4.0F, 6.0F, -1.0F);
        Wings.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 0.0F, 0.5236F);
        cube_r3.texOffs(96, 2).addBox(-16.8191F, -5.4264F, 0.0F, 16.0F, 6.0F, 0.0F, 0.0F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(-1.0F, -4.0F, 0.0F);
        Wings.addChild(cube_r4);
        setRotationAngle(cube_r4, -0.1309F, 0.1309F, 0.7854F);
        cube_r4.texOffs(24, 0).addBox(1.6053F, 3.0071F, -0.8908F, 2.0F, 2.0F, 1.0F, 0.0F, false);

        bone52 = new ModelRenderer(this);
        bone52.setPos(-2.0F, 0.0F, -1.0F);
        Wings.addChild(bone52);
        setRotationAngle(bone52, 0.0F, 0.0F, -0.5236F);


        cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(-4.0F, -4.0F, -1.0F);
        bone52.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.0F, 0.0F, -0.48F);
        cube_r5.texOffs(0, 48).addBox(-0.2523F, -10.3764F, 0.0F, 2.0F, 11.0F, 2.0F, 0.0F, false);

        cube_r6 = new ModelRenderer(this);
        cube_r6.setPos(1.0F, 0.0F, -1.0F);
        bone52.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.0F, 0.0F, -0.6109F);
        cube_r6.texOffs(0, 0).addBox(-2.0774F, -5.9597F, 0.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

        bone53 = new ModelRenderer(this);
        bone53.setPos(-8.1244F, -13.9282F, 0.0F);
        bone52.addChild(bone53);
        setRotationAngle(bone53, 0.0F, 0.0F, -0.3054F);


        bone54 = new ModelRenderer(this);
        bone54.setPos(0.8731F, -5.8629F, -5.0F);
        bone53.addChild(bone54);
        setRotationAngle(bone54, 0.0F, 0.0F, 1.6144F);


        bone55 = new ModelRenderer(this);
        bone55.setPos(1.1244F, -7.0718F, -1.0F);
        bone53.addChild(bone55);
        setRotationAngle(bone55, 0.0F, 0.0F, 1.0472F);


        bone56 = new ModelRenderer(this);
        bone56.setPos(1.1244F, -7.0718F, -1.0F);
        bone53.addChild(bone56);
        setRotationAngle(bone56, 0.0F, 0.0F, -0.8727F);


        bone33 = new ModelRenderer(this);
        bone33.setPos(-36.0F, -10.0F, -5.0F);
        Wings.addChild(bone33);
        setRotationAngle(bone33, 0.0F, 0.0F, 0.7418F);


        bone17 = new ModelRenderer(this);
        bone17.setPos(18.0F, -13.0F, 3.0F);
        bone33.addChild(bone17);
        setRotationAngle(bone17, -1.5708F, 0.0F, 0.4363F);


        bone18 = new ModelRenderer(this);
        bone18.setPos(-4.0F, -11.0F, -2.0F);
        bone17.addChild(bone18);
        setRotationAngle(bone18, 0.0F, -0.0873F, 0.0F);
        bone18.texOffs(48, 29).addBox(1.6619F, 10.0F, 1.8866F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r7 = new ModelRenderer(this);
        cube_r7.setPos(0.8821F, 0.0F, 1.9974F);
        bone18.addChild(cube_r7);
        setRotationAngle(cube_r7, 0.0F, -0.9599F, 0.0F);
        cube_r7.texOffs(32, 48).addBox(-0.8244F, 10.0F, -1.1287F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        bone19 = new ModelRenderer(this);
        bone19.setPos(-3.0F, -11.0F, -5.0F);
        bone17.addChild(bone19);
        setRotationAngle(bone19, 0.0F, 0.6981F, 0.0F);


        cube_r8 = new ModelRenderer(this);
        cube_r8.setPos(-2.0F, 0.0F, 0.0F);
        bone19.addChild(cube_r8);
        setRotationAngle(cube_r8, 0.0F, 0.9599F, 0.0F);
        cube_r8.texOffs(48, 27).addBox(-3.0111F, 10.0F, 0.6136F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        Head2 = new ModelRenderer(this);
        Head2.setPos(-1.0F, 0.0F, 0.0F);
        Head2.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.02F, false);

        bone = new ModelRenderer(this);
        bone.setPos(-10.0F, 8.0F, -2.0F);
        Head2.addChild(bone);
        setRotationAngle(bone, 0.2618F, 0.0F, -1.0036F);
        bone.texOffs(34, 8).addBox(14.4627F, -2.8147F, 0.2183F, 3.0F, 2.0F, 3.0F, 0.0F, false);

        cube_r9 = new ModelRenderer(this);
        cube_r9.setPos(15.0F, -2.0F, 3.0F);
        bone.addChild(cube_r9);
        setRotationAngle(cube_r9, 0.1309F, 0.0F, 0.0F);
        cube_r9.texOffs(35, 7).addBox(-0.5373F, -2.2792F, -2.6772F, 3.0F, 2.0F, 3.0F, -0.2F, false);

        cube_r10 = new ModelRenderer(this);
        cube_r10.setPos(15.05F, -3.0995F, 2.9456F);
        bone.addChild(cube_r10);
        setRotationAngle(cube_r10, 0.3054F, 0.0F, 0.0F);
        cube_r10.texOffs(36, 7).addBox(-0.5873F, -2.7113F, -2.5469F, 3.0F, 2.0F, 3.0F, -0.25F, false);

        cube_r11 = new ModelRenderer(this);
        cube_r11.setPos(15.1F, -5.0923F, 2.3418F);
        bone.addChild(cube_r11);
        setRotationAngle(cube_r11, 0.5672F, 0.0F, 0.0F);
        cube_r11.texOffs(34, 7).addBox(-0.6373F, -1.9698F, -2.3782F, 3.0F, 2.0F, 3.0F, -0.35F, false);

        cube_r12 = new ModelRenderer(this);
        cube_r12.setPos(16.0F, -6.0F, 0.0F);
        bone.addChild(cube_r12);
        setRotationAngle(cube_r12, 0.6109F, 0.0F, 0.0F);
        cube_r12.texOffs(36, 8).addBox(-1.0373F, -2.3421F, -0.3197F, 2.0F, 3.0F, 2.0F, -0.1F, false);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.Head2, this.LeftArm3, this.Body2).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.crouching = entityIn.isCrouching();

        AbstractClientPlayerEntity clientPlayer = (AbstractClientPlayerEntity) entityIn;

        BipedModel.ArmPose mainHandPos = armPose(clientPlayer, Hand.MAIN_HAND);
        BipedModel.ArmPose offHandPos = armPose(clientPlayer, Hand.OFF_HAND);

        this.swimAmount = clientPlayer.getSwimAmount(ageInTicks);

        if (clientPlayer.getMainArm() == HandSide.RIGHT) {
            this.rightArmPose = mainHandPos;
            this.leftArmPose = offHandPos;
        } else {
            this.rightArmPose = offHandPos;
            this.leftArmPose = mainHandPos;
        }

        this.Head2.copyFrom(this.head);
        this.LeftArm3.copyFrom(this.rightArm);
        this.Body2.copyFrom(this.body);
    }

    private static BipedModel.ArmPose armPose(AbstractClientPlayerEntity playerEntity, Hand hand) {
        ItemStack itemstack = playerEntity.getItemInHand(hand);
        if (itemstack.isEmpty()) {
            return BipedModel.ArmPose.EMPTY;
        } else {
            if (playerEntity.getUsedItemHand() == hand && playerEntity.getUseItemRemainingTicks() > 0) {
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

                if (useaction == UseAction.CROSSBOW && hand == playerEntity.getUsedItemHand()) {
                    return BipedModel.ArmPose.CROSSBOW_CHARGE;
                }
            } else if (!playerEntity.swinging && itemstack.getItem() == Items.CROSSBOW && CrossbowItem.isCharged(itemstack)) {
                return BipedModel.ArmPose.CROSSBOW_HOLD;
            }

            return BipedModel.ArmPose.ITEM;
        }
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
