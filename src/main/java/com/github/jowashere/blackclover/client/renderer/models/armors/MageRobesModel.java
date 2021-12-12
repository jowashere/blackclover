package com.github.jowashere.blackclover.client.renderer.models.armors;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MageRobesModel<T extends LivingEntity> extends BipedModel<T>{

    private final ModelRenderer RightLeg;
    private final ModelRenderer cube_r1;
    private final ModelRenderer LeftLeg;
    private final ModelRenderer cube_r2;
    private final ModelRenderer LeftArm;
    private final ModelRenderer bone11;
    private final ModelRenderer cube_r3;
    private final ModelRenderer RightArm;
    private final ModelRenderer bone12;
    private final ModelRenderer cube_r4;
    private final ModelRenderer Body;
    private final ModelRenderer Head;
    private final ModelRenderer Hat;
    private final ModelRenderer bone3;
    private final ModelRenderer bone7;
    private final ModelRenderer bone6;
    private final ModelRenderer bone5;
    private final ModelRenderer bone4;
    private final ModelRenderer bone8;
    private final ModelRenderer bone9;
    private final ModelRenderer bone2;

    boolean showHead;
    boolean showChest;
    boolean showLegs;
    boolean showFeet;

    public MageRobesModel(boolean showHead, boolean showChest, boolean showLegs, boolean showFeet) {
        super(1);

        this.showHead = showHead;
        this.showChest = showChest;
        this.showLegs = showLegs;
        this.showFeet = showFeet;

        texWidth = 112;
        texHeight = 112;

        RightLeg = new ModelRenderer(this);
        RightLeg.setPos(-1.9F, 12.0F, 0.0F);
        RightLeg.texOffs(34, 54).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        RightLeg.texOffs(0, 91).addBox(-2.2F, -1.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.25F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(5.9F, 9.0F, 2.0F);
        RightLeg.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.3491F, 0.0F, 0.0F);
        cube_r1.texOffs(0, 71).addBox(-8.1F, -5.0F, 0.0F, 4.0F, 5.0F, 2.0F, 0.25F, false);

        LeftLeg = new ModelRenderer(this);
        LeftLeg.setPos(1.9F, 12.0F, 0.0F);
        LeftLeg.texOffs(51, 54).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        LeftLeg.texOffs(29, 91).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.25F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(2.1F, 9.0F, 2.0F);
        LeftLeg.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.3491F, 0.0F, 0.0F);
        cube_r2.texOffs(0, 71).addBox(-4.1F, -5.0F, 0.0F, 4.0F, 5.0F, 2.0F, 0.25F, false);

        LeftArm = new ModelRenderer(this);
        LeftArm.setPos(5.0F, 2.0F, 0.0F);
        LeftArm.texOffs(17, 54).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        LeftArm.texOffs(80, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.25F, false);

        bone11 = new ModelRenderer(this);
        bone11.setPos(-5.0F, 22.0F, 0.0F);
        LeftArm.addChild(bone11);
        bone11.texOffs(38, 71).addBox(4.0F, -15.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.11F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(8.0F, -13.75F, 2.0F);
        bone11.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.6109F, 0.0F, 0.0F);
        cube_r3.texOffs(68, 71).addBox(-4.0F, -3.0F, 0.0F, 4.0F, 3.0F, 2.0F, 0.1F, false);

        RightArm = new ModelRenderer(this);
        RightArm.setPos(-5.0F, 2.0F, 0.0F);
        RightArm.texOffs(0, 54).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        RightArm.texOffs(80, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.25F, false);

        bone12 = new ModelRenderer(this);
        bone12.setPos(5.0F, 22.0F, 0.0F);
        RightArm.addChild(bone12);
        bone12.texOffs(21, 71).addBox(-8.0F, -15.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.11F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(-8.0F, -13.75F, 2.0F);
        bone12.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.6109F, 0.0F, 0.0F);
        cube_r4.texOffs(55, 71).addBox(0.0F, -3.0F, 0.0F, 4.0F, 3.0F, 2.0F, 0.1F, false);

        Body = new ModelRenderer(this);
        Body.setPos(0.0F, 0.0F, 0.0F);
        Body.texOffs(0, 37).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
        Body.texOffs(61, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 7.0F, 4.0F, 0.25F, false);

        Head = new ModelRenderer(this);
        Head.setPos(0.0F, 0.0F, 0.0F);
        Head.texOffs(80, 96).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        Hat = new ModelRenderer(this);
        Hat.setPos(0.0F, -7.0F, 0.0F);
        Head.addChild(Hat);
        Hat.texOffs(0, 0).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 0.0F, 14.0F, 0.0F, false);

        bone3 = new ModelRenderer(this);
        bone3.setPos(0.0F, -0.5F, -4.0F);
        Hat.addChild(bone3);
        setRotationAngle(bone3, -0.0436F, 0.0F, -0.0436F);
        bone3.texOffs(33, 27).addBox(-4.0F, -1.5F, 0.0F, 8.0F, 1.0F, 8.0F, 0.1F, false);

        bone7 = new ModelRenderer(this);
        bone7.setPos(2.9535F, -0.7403F, 1.0521F);
        bone3.addChild(bone7);
        setRotationAngle(bone7, -0.1309F, 0.0F, -0.0873F);
        bone7.texOffs(0, 27).addBox(-6.9535F, -1.7578F, -1.0692F, 8.0F, 1.0F, 8.0F, 0.0F, false);

        bone6 = new ModelRenderer(this);
        bone6.setPos(-2.9687F, -1.8139F, -1.0085F);
        bone7.addChild(bone6);
        setRotationAngle(bone6, -0.1309F, 0.0F, -0.0436F);
        bone6.texOffs(66, 15).addBox(-3.9848F, -1.6939F, -0.0607F, 8.0F, 2.0F, 8.0F, -0.2F, false);

        bone5 = new ModelRenderer(this);
        bone5.setPos(-2.9735F, -2.0482F, 0.9542F);
        bone6.addChild(bone5);
        setRotationAngle(bone5, -0.2182F, 0.0F, 0.1745F);
        bone5.texOffs(33, 15).addBox(-1.0113F, -1.9345F, -0.9085F, 8.0F, 3.0F, 8.0F, -0.4F, false);

        bone4 = new ModelRenderer(this);
        bone4.setPos(0.1214F, -1.7688F, -0.1234F);
        bone5.addChild(bone4);
        setRotationAngle(bone4, -0.1745F, 0.0F, 0.1309F);
        bone4.texOffs(0, 15).addBox(-1.1327F, -2.1854F, -0.7943F, 8.0F, 3.0F, 8.0F, -0.6F, false);

        bone8 = new ModelRenderer(this);
        bone8.setPos(1.1008F, -1.3764F, 1.6409F);
        bone4.addChild(bone8);
        setRotationAngle(bone8, -0.3491F, -0.0873F, 0.3927F);
        bone8.texOffs(50, 37).addBox(-1.2334F, -3.5502F, -1.5387F, 6.0F, 4.0F, 6.0F, -0.6F, false);

        bone9 = new ModelRenderer(this);
        bone9.setPos(2.8914F, -3.2326F, 0.6651F);
        bone8.addChild(bone9);
        setRotationAngle(bone9, 0.0F, 0.0F, -0.2182F);
        bone9.texOffs(81, 71).addBox(-2.1248F, -1.3176F, -0.2038F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setPos(0.0F, 30.0F, 0.0F);
        Hat.addChild(bone2);
        bone2.texOffs(66, 27).addBox(-4.0F, -31.0F, -4.0F, 8.0F, 1.0F, 8.0F, 0.2F, false);
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
        this.Head.copyFrom(this.head);

        if(this.showHead)
            this.Head.render(matrixStack, buffer, packedLight, packedOverlay);

        if(this.showFeet) {
            this.LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
            this.RightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        if(this.showLegs){
            this.LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
            this.RightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        if(this.showChest){
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
