package com.github.jowashere.blackclover.client.renderer.layers.models;

// Made with Blockbench 3.9.3
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class ModelGrimoire<T extends LivingEntity> extends BipedModel<T> {
    private final ModelRenderer Body;
    private final ModelRenderer Book;
    private final ModelRenderer BackPages_r1;
    private final ModelRenderer FrontCover_r1;

    public ModelGrimoire() {
        super(1F);
        texWidth = 64;
        texHeight = 32;

        Body = new ModelRenderer(this);
        Body.setPos(0.0F, 0.0F, 0.0F);
        Body.texOffs(40, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

        Book = new ModelRenderer(this);
        Book.setPos(5.474F, 9.1415F, -12.0F);
        setRotationAngle(Book, 2.3998F, 0.0F, 0.0F);
        Book.texOffs(42, 22).addBox(-0.4958F, 0.0664F, -4.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
        Book.texOffs(20, 0).addBox(-0.4958F, -0.2336F, -3.95F, 1.0F, 1.0F, 8.0F, 0.25F, -0.25F, 0.25F);

        BackPages_r1 = new ModelRenderer(this);
        BackPages_r1.setPos(0.4313F, -0.3082F, -7.0F);
        Book.addChild(BackPages_r1);
        setRotationAngle(BackPages_r1, 0.0F, 0.0F, -0.3927F);
        BackPages_r1.texOffs(6, 22).addBox(-7.0F, 0.0F, 3.0F, 6.0F, 1.0F, 8.0F, 0.0F, false);
        BackPages_r1.texOffs(0, 0).addBox(-7.25F, -0.25F, 3.05F, 6.0F, 1.0F, 8.0F, 0.25F, -0.25F, 0.25F);

        FrontCover_r1 = new ModelRenderer(this);
        FrontCover_r1.setPos(-0.423F, -0.3082F, -7.0F);
        Book.addChild(FrontCover_r1);
        setRotationAngle(FrontCover_r1, 0.0F, 0.0F, 0.3927F);
        FrontCover_r1.texOffs(0, 9).addBox(1.225F, -0.25F, 3.05F, 6.0F, 1.0F, 8.0F, 0.25F, -0.25F, 0.25F);
        FrontCover_r1.texOffs(6, 22).addBox(1.0F, 0.0F, 3.0F, 6.0F, 1.0F, 8.0F, 0.0F, false);
        Body.addChild(Book);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below

        this.Body.copyFrom(this.body);

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Book.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
