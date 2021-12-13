package com.github.jowashere.blackclover.init.spells.light;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractToggleSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ItemInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class LightSword extends AbstractToggleSpell {

    public static final LightSword INSTANCE = new LightSword(null);

    public LightSword(IBCMPlugin plugin) {
        super(plugin, "light_sword", AttributeInit.LIGHT);

        this.setSkillSpell(true);
        this.setManaCost(0.2F);
        this.setCooldown(30);
        this.setUnlockLevel(1);
        this.setUV(80, 32);

        this.onStartEvent = this::onStart;

    }

    public void onStart(LivingEntity caster, float manaIn) {
        BCMHelper.GiveItem(caster, new ItemStack(ItemInit.LIGHT_SWORD.get()));
    }
}
