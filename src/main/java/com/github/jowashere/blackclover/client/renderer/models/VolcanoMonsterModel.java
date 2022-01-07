package com.github.jowashere.blackclover.client.renderer.models;

import com.github.jowashere.blackclover.entities.mobs.hostile.VolcanoMonsterEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class VolcanoMonsterModel<T extends VolcanoMonsterEntity> extends EntityModel<T> {

    private final ModelRenderer bone14;
    private final ModelRenderer bone13;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer bone12;
    private final ModelRenderer cube_r4;
    private final ModelRenderer cube_r5;
    private final ModelRenderer cube_r6;
    private final ModelRenderer bone11;
    private final ModelRenderer cube_r7;
    private final ModelRenderer cube_r8;
    private final ModelRenderer cube_r9;
    private final ModelRenderer bone10;
    private final ModelRenderer cube_r10;
    private final ModelRenderer cube_r11;
    private final ModelRenderer cube_r12;
    private final ModelRenderer bone9;
    private final ModelRenderer bone8;
    private final ModelRenderer cube_r13;
    private final ModelRenderer cube_r14;
    private final ModelRenderer cube_r15;
    private final ModelRenderer cube_r16;
    private final ModelRenderer cube_r17;
    private final ModelRenderer cube_r18;
    private final ModelRenderer cube_r19;
    private final ModelRenderer cube_r20;
    private final ModelRenderer Head;
    private final ModelRenderer cube_r21;
    private final ModelRenderer bone4;
    private final ModelRenderer cube_r22;
    private final ModelRenderer cube_r23;
    private final ModelRenderer cube_r24;
    private final ModelRenderer bone3;
    private final ModelRenderer cube_r25;
    private final ModelRenderer cube_r26;
    private final ModelRenderer bone2;
    private final ModelRenderer cube_r27;
    private final ModelRenderer cube_r28;
    private final ModelRenderer cube_r29;
    private final ModelRenderer bone5;
    private final ModelRenderer cube_r30;
    private final ModelRenderer cube_r31;
    private final ModelRenderer cube_r32;
    private final ModelRenderer cube_r33;
    private final ModelRenderer bone6;
    private final ModelRenderer cube_r34;
    private final ModelRenderer cube_r35;
    private final ModelRenderer cube_r36;
    private final ModelRenderer cube_r37;
    private final ModelRenderer bone52;
    private final ModelRenderer cube_r38;
    private final ModelRenderer cube_r39;
    private final ModelRenderer cube_r40;
    private final ModelRenderer cube_r41;
    private final ModelRenderer bone7;
    private final ModelRenderer cube_r42;
    private final ModelRenderer cube_r43;
    private final ModelRenderer cube_r44;
    private final ModelRenderer cube_r45;
    private final ModelRenderer bone16;
    private final ModelRenderer bone15;
    public VolcanoMonsterModel()
    {
        texWidth = 480;
        texHeight = 480;

        bone14 = new ModelRenderer(this);
        bone14.setPos(0.1667F, 2.0F, 0.0F);
        bone14.texOffs(0, 0).addBox(-36.3333F, 16.0F, -19.0F, 66.0F, 6.0F, 60.0F, 0.0F, false);
        bone14.texOffs(253, 0).addBox(-26.3333F, 6.0F, -25.0F, 58.0F, 10.0F, 52.0F, 0.0F, false);
        bone14.texOffs(0, 67).addBox(-29.3333F, -2.0F, -18.0F, 48.0F, 8.0F, 48.0F, 0.0F, false);
        bone14.texOffs(0, 124).addBox(-15.3333F, -14.0F, -13.0F, 32.0F, 8.0F, 28.0F, 0.0F, false);
        bone14.texOffs(322, 67).addBox(-20.3333F, -6.0F, -18.0F, 34.0F, 4.0F, 30.0F, 0.0F, false);
        bone14.texOffs(226, 124).addBox(-13.3333F, -22.0F, -15.0F, 26.0F, 8.0F, 26.0F, 0.0F, false);

        bone13 = new ModelRenderer(this);
        bone13.setPos(-0.1667F, 11.0F, 0.0F);
        bone14.addChild(bone13);


        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(8.8333F, -26.0F, 13.0F);
        bone13.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.5585F, -0.7854F, 0.0F);
        cube_r1.texOffs(118, 237).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 10.0F, 12.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(-7.1667F, -14.0F, 13.0F);
        bone13.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.7854F, -0.7854F, -0.7854F);
        cube_r2.texOffs(167, 237).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 10.0F, 12.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(8.8333F, 2.0F, 23.0F);
        bone13.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, -0.7854F, -0.7854F);
        cube_r3.texOffs(216, 237).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 10.0F, 12.0F, 0.0F, false);

        bone12 = new ModelRenderer(this);
        bone12.setPos(39.8333F, -35.0F, -8.0F);
        bone14.addChild(bone12);


        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(-13.1667F, 48.0F, 9.0F);
        bone12.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, -0.7854F, -0.5585F);
        cube_r4.texOffs(265, 237).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 10.0F, 12.0F, 0.0F, false);

        cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(-17.1667F, 36.0F, -7.0F);
        bone12.addChild(cube_r5);
        setRotationAngle(cube_r5, -0.7854F, -0.7854F, -0.7854F);
        cube_r5.texOffs(0, 266).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 10.0F, 12.0F, 0.0F, false);

        cube_r6 = new ModelRenderer(this);
        cube_r6.setPos(-23.1667F, 20.0F, 9.0F);
        bone12.addChild(cube_r6);
        setRotationAngle(cube_r6, -0.7854F, -0.7854F, 0.0F);
        cube_r6.texOffs(49, 266).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 10.0F, 12.0F, 0.0F, false);

        bone11 = new ModelRenderer(this);
        bone11.setPos(-2.1667F, 11.0F, -2.0F);
        bone14.addChild(bone11);


        cube_r7 = new ModelRenderer(this);
        cube_r7.setPos(-13.1667F, -26.0F, 9.0F);
        bone11.addChild(cube_r7);
        setRotationAngle(cube_r7, 0.0F, -0.7854F, 0.5585F);
        cube_r7.texOffs(98, 266).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 10.0F, 12.0F, 0.0F, false);

        cube_r8 = new ModelRenderer(this);
        cube_r8.setPos(-13.1667F, -14.0F, -7.0F);
        bone11.addChild(cube_r8);
        setRotationAngle(cube_r8, 0.7854F, -0.7854F, 0.7854F);
        cube_r8.texOffs(147, 266).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 10.0F, 12.0F, 0.0F, false);

        cube_r9 = new ModelRenderer(this);
        cube_r9.setPos(-23.1667F, 2.0F, 9.0F);
        bone11.addChild(cube_r9);
        setRotationAngle(cube_r9, 0.7854F, -0.7854F, 0.0F);
        cube_r9.texOffs(196, 266).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 10.0F, 12.0F, 0.0F, false);

        bone10 = new ModelRenderer(this);
        bone10.setPos(-0.1667F, 11.0F, 0.0F);
        bone14.addChild(bone10);


        cube_r10 = new ModelRenderer(this);
        cube_r10.setPos(-9.1667F, -26.0F, -13.0F);
        bone10.addChild(cube_r10);
        setRotationAngle(cube_r10, -0.5585F, -0.7854F, 0.0F);
        cube_r10.texOffs(245, 266).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 10.0F, 12.0F, 0.0F, false);

        cube_r11 = new ModelRenderer(this);
        cube_r11.setPos(6.8333F, -14.0F, -13.0F);
        bone10.addChild(cube_r11);
        setRotationAngle(cube_r11, -0.7854F, -0.7854F, 0.7854F);
        cube_r11.texOffs(0, 289).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 10.0F, 12.0F, 0.0F, false);

        cube_r12 = new ModelRenderer(this);
        cube_r12.setPos(-9.1667F, 2.0F, -23.0F);
        bone10.addChild(cube_r12);
        setRotationAngle(cube_r12, 0.0F, -0.7854F, 0.7854F);
        cube_r12.texOffs(294, 266).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 10.0F, 12.0F, 0.0F, false);

        bone9 = new ModelRenderer(this);
        bone9.setPos(-0.1667F, 7.0F, 0.0F);
        bone14.addChild(bone9);


        bone8 = new ModelRenderer(this);
        bone8.setPos(0.0F, 0.0F, 0.0F);
        bone9.addChild(bone8);
        bone8.texOffs(193, 67).addBox(-16.1667F, -54.5F, -16.0F, 32.0F, 18.0F, 32.0F, 0.0F, false);
        bone8.texOffs(121, 124).addBox(-13.1667F, -60.5F, -14.0F, 26.0F, 12.0F, 26.0F, 0.0F, false);

        cube_r13 = new ModelRenderer(this);
        cube_r13.setPos(-13.1667F, -61.0F, 0.0F);
        bone8.addChild(cube_r13);
        setRotationAngle(cube_r13, 0.0F, 0.0F, 0.2618F);
        cube_r13.texOffs(0, 200).addBox(-1.0F, 0.0F, -14.0F, 2.0F, 8.0F, 28.0F, 0.0F, false);

        cube_r14 = new ModelRenderer(this);
        cube_r14.setPos(-0.1667F, -61.0F, 13.0F);
        bone8.addChild(cube_r14);
        setRotationAngle(cube_r14, 0.2618F, 0.0F, 0.0F);
        cube_r14.texOffs(49, 289).addBox(-14.0F, 0.0F, -1.0F, 28.0F, 8.0F, 2.0F, 0.0F, false);

        cube_r15 = new ModelRenderer(this);
        cube_r15.setPos(12.8333F, -61.0F, 0.0F);
        bone8.addChild(cube_r15);
        setRotationAngle(cube_r15, 0.0F, 0.0F, -0.2618F);
        cube_r15.texOffs(260, 163).addBox(-1.0F, 0.0F, -14.0F, 2.0F, 8.0F, 28.0F, 0.0F, false);

        cube_r16 = new ModelRenderer(this);
        cube_r16.setPos(-0.1667F, -61.0F, -13.0F);
        bone8.addChild(cube_r16);
        setRotationAngle(cube_r16, -0.2618F, 0.0F, 0.0F);
        cube_r16.texOffs(49, 300).addBox(-14.0F, 0.0F, -1.0F, 28.0F, 8.0F, 2.0F, 0.0F, false);

        cube_r17 = new ModelRenderer(this);
        cube_r17.setPos(-13.1667F, -29.0F, 0.0F);
        bone8.addChild(cube_r17);
        setRotationAngle(cube_r17, 0.0F, 0.0F, -0.2182F);
        cube_r17.texOffs(122, 200).addBox(-1.0F, -8.0F, -14.0F, 2.0F, 8.0F, 28.0F, 0.0F, false);

        cube_r18 = new ModelRenderer(this);
        cube_r18.setPos(-0.1667F, -29.0F, 13.0F);
        bone8.addChild(cube_r18);
        setRotationAngle(cube_r18, -0.2618F, 0.0F, 0.0F);
        cube_r18.texOffs(110, 289).addBox(-14.0F, -8.0F, -1.0F, 28.0F, 8.0F, 2.0F, 0.0F, false);

        cube_r19 = new ModelRenderer(this);
        cube_r19.setPos(-0.1667F, -29.0F, -13.0F);
        bone8.addChild(cube_r19);
        setRotationAngle(cube_r19, 0.2618F, 0.0F, 0.0F);
        cube_r19.texOffs(110, 300).addBox(-14.0F, -8.0F, -1.0F, 28.0F, 8.0F, 2.0F, 0.0F, false);

        cube_r20 = new ModelRenderer(this);
        cube_r20.setPos(12.8333F, -29.0F, 0.0F);
        bone8.addChild(cube_r20);
        setRotationAngle(cube_r20, 0.0F, 0.0F, 0.2182F);
        cube_r20.texOffs(61, 200).addBox(-1.0F, -8.0F, -14.0F, 2.0F, 8.0F, 28.0F, 0.0F, false);

        Head = new ModelRenderer(this);
        Head.setPos(0.0F, 1.5F, -2.0F);
        bone9.addChild(Head);
        Head.texOffs(183, 200).addBox(-8.1667F, -80.5F, -8.0F, 16.0F, 10.0F, 16.0F, 0.0F, false);
        Head.texOffs(61, 237).addBox(-8.1667F, -68.5F, -4.0F, 16.0F, 6.0F, 12.0F, 0.0F, false);

        cube_r21 = new ModelRenderer(this);
        cube_r21.setPos(-0.1667F, -69.0F, -8.5F);
        Head.addChild(cube_r21);
        setRotationAngle(cube_r21, -0.1309F, 0.0F, 0.0F);
        cube_r21.texOffs(302, 312).addBox(-7.0F, -2.0F, 0.5F, 14.0F, 10.0F, 6.0F, 0.0F, false);

        bone4 = new ModelRenderer(this);
        bone4.setPos(-7.9369F, -82.0017F, 2.0F);
        Head.addChild(bone4);
        setRotationAngle(bone4, 0.0F, 0.0F, -0.1309F);


        cube_r22 = new ModelRenderer(this);
        cube_r22.setPos(-3.6846F, 18.8894F, 0.0F);
        bone4.addChild(cube_r22);
        setRotationAngle(cube_r22, 0.0F, 0.0F, -0.3054F);
        cube_r22.texOffs(0, 329).addBox(-3.5F, -1.5F, -6.0F, 4.0F, 4.0F, 12.0F, 0.0F, false);

        cube_r23 = new ModelRenderer(this);
        cube_r23.setPos(-0.9814F, 11.9619F, 0.0F);
        bone4.addChild(cube_r23);
        setRotationAngle(cube_r23, 0.0F, 0.0F, 0.7854F);
        cube_r23.texOffs(33, 329).addBox(1.8047F, 2.5634F, -6.0F, 4.0F, 4.0F, 12.0F, 0.0F, false);

        cube_r24 = new ModelRenderer(this);
        cube_r24.setPos(-0.2298F, 9.5017F, -1.0F);
        bone4.addChild(cube_r24);
        setRotationAngle(cube_r24, 0.0F, 0.0F, 0.3491F);
        cube_r24.texOffs(202, 329).addBox(0.5F, 0.5F, -5.0F, 2.0F, 6.0F, 12.0F, -0.1F, false);

        bone3 = new ModelRenderer(this);
        bone3.setPos(0.0F, -82.0017F, 7.9369F);
        Head.addChild(bone3);
        setRotationAngle(bone3, -0.2182F, 0.0F, 0.0F);


        cube_r25 = new ModelRenderer(this);
        cube_r25.setPos(1.8333F, 18.8894F, 3.5179F);
        bone3.addChild(cube_r25);
        setRotationAngle(cube_r25, -0.3054F, 0.0F, 0.0F);
        cube_r25.texOffs(66, 329).addBox(-10.0F, -1.5F, -0.5F, 16.0F, 4.0F, 4.0F, 0.0F, false);

        cube_r26 = new ModelRenderer(this);
        cube_r26.setPos(1.8333F, 11.9619F, 0.8147F);
        bone3.addChild(cube_r26);
        setRotationAngle(cube_r26, 0.7854F, 0.0F, 0.0F);
        cube_r26.texOffs(66, 338).addBox(-10.0F, 2.5634F, -5.8047F, 16.0F, 4.0F, 4.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setPos(7.9369F, -82.0017F, 2.0F);
        Head.addChild(bone2);
        setRotationAngle(bone2, 0.0F, 0.0F, 0.1309F);


        cube_r27 = new ModelRenderer(this);
        cube_r27.setPos(3.3513F, 18.8894F, 0.0F);
        bone2.addChild(cube_r27);
        setRotationAngle(cube_r27, 0.0F, 0.0F, 0.3054F);
        cube_r27.texOffs(107, 329).addBox(-0.5F, -1.5F, -6.0F, 4.0F, 4.0F, 12.0F, 0.0F, false);

        cube_r28 = new ModelRenderer(this);
        cube_r28.setPos(0.648F, 11.9619F, 0.0F);
        bone2.addChild(cube_r28);
        setRotationAngle(cube_r28, 0.0F, 0.0F, -0.7854F);
        cube_r28.texOffs(140, 329).addBox(-5.8047F, 2.5634F, -6.0F, 4.0F, 4.0F, 12.0F, 0.0F, false);

        cube_r29 = new ModelRenderer(this);
        cube_r29.setPos(-0.1036F, 9.5017F, -1.0F);
        bone2.addChild(cube_r29);
        setRotationAngle(cube_r29, 0.0F, 0.0F, -0.3491F);
        cube_r29.texOffs(173, 329).addBox(-2.5F, 0.5F, -5.0F, 2.0F, 6.0F, 12.0F, -0.1F, false);

        bone5 = new ModelRenderer(this);
        bone5.setPos(0.0F, 0.0F, 2.0F);
        Head.addChild(bone5);


        cube_r30 = new ModelRenderer(this);
        cube_r30.setPos(-7.1667F, -76.5F, -2.0F);
        bone5.addChild(cube_r30);
        setRotationAngle(cube_r30, 0.0F, 0.0F, 0.5672F);
        cube_r30.texOffs(231, 338).addBox(-3.5F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

        cube_r31 = new ModelRenderer(this);
        cube_r31.setPos(-9.1667F, -77.5F, -2.0F);
        bone5.addChild(cube_r31);
        setRotationAngle(cube_r31, 0.0F, 0.0F, 1.0036F);
        cube_r31.texOffs(52, 348).addBox(-3.35F, -1.85F, -1.85F, 3.0F, 3.0F, 3.0F, -0.15F, false);

        cube_r32 = new ModelRenderer(this);
        cube_r32.setPos(-10.6667F, -81.0F, -2.0F);
        bone5.addChild(cube_r32);
        setRotationAngle(cube_r32, 0.0F, 0.0F, -0.0873F);
        cube_r32.texOffs(39, 348).addBox(-1.75F, -1.75F, -1.75F, 3.0F, 3.0F, 3.0F, -0.25F, false);

        cube_r33 = new ModelRenderer(this);
        cube_r33.setPos(-10.6667F, -83.0F, -2.0F);
        bone5.addChild(cube_r33);
        setRotationAngle(cube_r33, 0.0F, 0.0F, 0.2618F);
        cube_r33.texOffs(26, 348).addBox(-1.65F, -1.65F, -1.65F, 3.0F, 3.0F, 3.0F, -0.35F, false);

        bone6 = new ModelRenderer(this);
        bone6.setPos(0.0F, 0.0F, 2.0F);
        Head.addChild(bone6);


        cube_r34 = new ModelRenderer(this);
        cube_r34.setPos(6.8333F, -76.5F, -2.0F);
        bone6.addChild(cube_r34);
        setRotationAngle(cube_r34, 0.0F, 0.0F, -0.5672F);
        cube_r34.texOffs(231, 329).addBox(-0.5F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

        cube_r35 = new ModelRenderer(this);
        cube_r35.setPos(8.8333F, -77.5F, -2.0F);
        bone6.addChild(cube_r35);
        setRotationAngle(cube_r35, 0.0F, 0.0F, -1.0036F);
        cube_r35.texOffs(248, 329).addBox(-0.35F, -1.85F, -1.85F, 3.0F, 3.0F, 3.0F, -0.15F, false);

        cube_r36 = new ModelRenderer(this);
        cube_r36.setPos(10.3333F, -81.0F, -2.0F);
        bone6.addChild(cube_r36);
        setRotationAngle(cube_r36, 0.0F, 0.0F, 0.0873F);
        cube_r36.texOffs(0, 348).addBox(-1.75F, -1.75F, -1.75F, 3.0F, 3.0F, 3.0F, -0.25F, false);

        cube_r37 = new ModelRenderer(this);
        cube_r37.setPos(10.3333F, -83.0F, -2.0F);
        bone6.addChild(cube_r37);
        setRotationAngle(cube_r37, 0.0F, 0.0F, -0.2618F);
        cube_r37.texOffs(13, 348).addBox(-1.65F, -1.65F, -1.65F, 3.0F, 3.0F, 3.0F, -0.35F, false);

        bone52 = new ModelRenderer(this);
        bone52.setPos(5.7997F, -86.2792F, -6.9207F);
        Head.addChild(bone52);
        setRotationAngle(bone52, 0.0F, 0.0436F, -0.0436F);


        cube_r38 = new ModelRenderer(this);
        cube_r38.setPos(0.0337F, 13.6327F, -0.8116F);
        bone52.addChild(cube_r38);
        setRotationAngle(cube_r38, -0.9599F, 0.0F, -0.0436F);
        cube_r38.texOffs(91, 348).addBox(-3.9591F, -1.5848F, -0.4678F, 4.0F, 2.0F, 2.0F, 0.0F, false);

        cube_r39 = new ModelRenderer(this);
        cube_r39.setPos(0.0682F, 13.4861F, -0.0058F);
        bone52.addChild(cube_r39);
        setRotationAngle(cube_r39, -0.8727F, 0.0F, -1.7017F);
        cube_r39.texOffs(135, 348).addBox(0.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        cube_r40 = new ModelRenderer(this);
        cube_r40.setPos(-0.2966F, 12.0543F, -0.7891F);
        bone52.addChild(cube_r40);
        setRotationAngle(cube_r40, -0.3054F, 0.0873F, -0.0436F);
        cube_r40.texOffs(104, 348).addBox(-3.9335F, -1.9511F, -0.6802F, 4.0F, 2.0F, 2.0F, 0.0F, false);

        cube_r41 = new ModelRenderer(this);
        cube_r41.setPos(-3.9852F, 13.8633F, -0.0216F);
        bone52.addChild(cube_r41);
        setRotationAngle(cube_r41, -1.0472F, 0.1309F, -0.48F);
        cube_r41.texOffs(144, 348).addBox(-1.6744F, -0.8923F, -0.8167F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        bone7 = new ModelRenderer(this);
        bone7.setPos(-5.7997F, -86.2792F, -6.9207F);
        Head.addChild(bone7);
        setRotationAngle(bone7, 0.0F, -0.0436F, 0.0436F);


        cube_r42 = new ModelRenderer(this);
        cube_r42.setPos(-0.367F, 13.6327F, -0.8116F);
        bone7.addChild(cube_r42);
        setRotationAngle(cube_r42, -0.9599F, 0.0F, 0.0436F);
        cube_r42.texOffs(65, 348).addBox(-0.0409F, -1.5848F, -0.4678F, 4.0F, 2.0F, 2.0F, 0.0F, false);

        cube_r43 = new ModelRenderer(this);
        cube_r43.setPos(-0.4015F, 13.4861F, -0.0058F);
        bone7.addChild(cube_r43);
        setRotationAngle(cube_r43, -0.8727F, 0.0F, 1.7017F);
        cube_r43.texOffs(117, 348).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        cube_r44 = new ModelRenderer(this);
        cube_r44.setPos(-0.0367F, 12.0543F, -0.7891F);
        bone7.addChild(cube_r44);
        setRotationAngle(cube_r44, -0.3054F, -0.0873F, 0.0436F);
        cube_r44.texOffs(78, 348).addBox(-0.0665F, -1.9511F, -0.6802F, 4.0F, 2.0F, 2.0F, 0.0F, false);

        cube_r45 = new ModelRenderer(this);
        cube_r45.setPos(3.6518F, 13.8633F, -0.0216F);
        bone7.addChild(cube_r45);
        setRotationAngle(cube_r45, -1.0472F, -0.1309F, 0.48F);
        cube_r45.texOffs(126, 348).addBox(-0.3256F, -0.8923F, -0.8167F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        bone16 = new ModelRenderer(this);
        bone16.setPos(-15.1667F, -62.7143F, 1.0F);
        bone14.addChild(bone16);
        setRotationAngle(bone16, 0.0F, 0.0873F, 0.2182F);
        bone16.texOffs(69, 163).addBox(-12.1667F, 6.7143F, -9.0F, 16.0F, 16.0F, 18.0F, 0.0F, false);
        bone16.texOffs(159, 312).addBox(-10.1667F, 44.7143F, -7.0F, 12.0F, 2.0F, 14.0F, 0.0F, false);
        bone16.texOffs(257, 312).addBox(-9.1667F, 46.7143F, -6.0F, 10.0F, 2.0F, 12.0F, 0.0F, false);
        bone16.texOffs(106, 312).addBox(-10.1667F, 48.7143F, -7.0F, 12.0F, 2.0F, 14.0F, 0.0F, false);
        bone16.texOffs(53, 312).addBox(-10.1667F, 22.7143F, -7.0F, 12.0F, 2.0F, 14.0F, 0.0F, false);
        bone16.texOffs(199, 163).addBox(-11.1667F, 24.7143F, -8.0F, 14.0F, 20.0F, 16.0F, 0.0F, false);
        bone16.texOffs(0, 237).addBox(-11.1667F, 50.7143F, -8.0F, 14.0F, 12.0F, 16.0F, 0.0F, false);

        bone15 = new ModelRenderer(this);
        bone15.setPos(14.8333F, -62.7143F, 1.0F);
        bone14.addChild(bone15);
        setRotationAngle(bone15, 0.0F, -0.0873F, -0.2182F);
        bone15.texOffs(0, 163).addBox(-3.8333F, 6.7143F, -9.0F, 16.0F, 16.0F, 18.0F, 0.0F, false);
        bone15.texOffs(0, 312).addBox(-1.8333F, 44.7143F, -7.0F, 12.0F, 2.0F, 14.0F, 0.0F, false);
        bone15.texOffs(212, 312).addBox(-0.8333F, 46.7143F, -6.0F, 10.0F, 2.0F, 12.0F, 0.0F, false);
        bone15.texOffs(224, 289).addBox(-1.8333F, 48.7143F, -7.0F, 12.0F, 2.0F, 14.0F, 0.0F, false);
        bone15.texOffs(171, 289).addBox(-1.8333F, 22.7143F, -7.0F, 12.0F, 2.0F, 14.0F, 0.0F, false);
        bone15.texOffs(138, 163).addBox(-2.8333F, 24.7143F, -8.0F, 14.0F, 20.0F, 16.0F, 0.0F, false);
        bone15.texOffs(248, 200).addBox(-2.8333F, 50.7143F, -8.0F, 14.0F, 12.0F, 16.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

    }


    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        bone14.render(matrixStack, buffer, packedLight, packedOverlay);
    }
    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}