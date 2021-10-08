package com.github.jowashere.blackclover.api.internal;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class BCMMode {

    private final String mode;
    private IBCMPlugin plugin;
    private boolean flight;
    private List<Effect> effects = new ArrayList<>();
    private Effect attackingEffect;
    private AgeableModel model;
    private ResourceLocation modelTexture;
    private ResourceLocation guiTexture;
    private final int u;
    private final int v;

    public BCMMode(IBCMPlugin pluginIn, String mode, int u, int v) {
        this.plugin = pluginIn;
        this.mode = mode;
        this.u = u;
        this.v = v;

        this.guiTexture = new ResourceLocation(Main.MODID, "textures/gui/spells.png");
    }

    public String getName() {
        return this.mode;
    }

    public IBCMPlugin getCorrelatedPlugin() {
        return plugin;
    }

    public BCMMode setPlugin(IBCMPlugin pluginIn) {
        this.plugin = pluginIn;
        return this;
    }

    public int getU() {
        return u;
    }

    public int getV() {
        return v;
    }

    public BCMMode setResourceLocationForGUI(ResourceLocation resourceLocation) {
        this.guiTexture = resourceLocation;
        return this;
    }

    public BCMMode setAllowsPlayerFlight() {
        this.flight = true;
        return this;
    }

    public BCMMode addPlayerEffect(Effect effect) {
        this.effects.add(effect);
        return this;
    }

    public BCMMode setAttackingEffect(Effect effect) {
        this.attackingEffect = effect;
        return this;
    }

    public BCMMode setModelOnRender(AgeableModel model, ResourceLocation texture) {
        this.model = model;
        this.modelTexture = texture;
        return this;
    }

    public boolean allowsPlayerFlight() {
        return this.flight;
    }

    public List<Effect> getPlayerEffects() {
        return this.effects;
    }

    public Effect getAttackingEffect() {
        return this.attackingEffect;
    }

    public AgeableModel getModelOnRender() {
        return this.model;
    }

    public ResourceLocation getModelResourceLocationOnRender() {
        return this.modelTexture;
    }

    public ResourceLocation getResourceLocationForGUI() {
        return this.guiTexture;
    }

}
