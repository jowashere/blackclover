package com.github.jowashere.blackclover.events;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.init.ModAttributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class AttributeHandlers {

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent e)
    {
        if (!e.player.level.isClientSide)
            return;

        if(e.player.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()) == null)
            return;

        ModifiableAttributeInstance attributeInstance = e.player.getAttribute(ModAttributes.STEP_HEIGHT.get());
        e.player.maxUpStep = (float) attributeInstance.getValue();

    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent e)
    {
        if(e.getEntityLiving().getAttribute(ModAttributes.FALL_RESISTANCE.get()) == null)
            return;

        ModifiableAttributeInstance attributeInstance = e.getEntityLiving().getAttribute(ModAttributes.FALL_RESISTANCE.get());
        e.setDistance((float) (e.getDistance() - attributeInstance.getValue()));
    }

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent e)
    {
        if(e.getEntityLiving().getAttribute(ModAttributes.JUMP_HEIGHT.get()) == null)
            return;

        double value = e.getEntityLiving().getAttribute(ModAttributes.JUMP_HEIGHT.get()).getValue();
        e.getEntityLiving().push(0, 0.1F * (value - 1), 0);
        if (value <= 0)
            e.getEntityLiving().setDeltaMovement(0, e.getEntityLiving().getDeltaMovement().y, 0);
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent e)
    {
        if (e.getEntityLiving().level.isClientSide)
            return;

        if(e.getEntityLiving().getAttribute(ModAttributes.DAMAGE_REDUCTION.get()) == null)
            return;

        if(e.getEntityLiving().getAttribute(ModAttributes.SPECIAL_DAMAGE_REDUCTION.get()) == null)
            return;

        double reduction = Math.min(e.getEntityLiving().getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).getValue(), 0.99f);

        if(e.getSource().isBypassInvul())
            return;

        double specialReduction = Math.min(e.getEntityLiving().getAttribute(ModAttributes.SPECIAL_DAMAGE_REDUCTION.get()).getValue(), 0.99f);

        e.setAmount((float) (e.getAmount() * (1.5 - (specialReduction/3))));

        if(e.getSource().isBypassArmor())
            return;
        e.setAmount((float) (e.getAmount() * (1 - reduction)));
    }

    @SubscribeEvent
    public static void onHeal(LivingHealEvent event)
    {
        if(event.getEntityLiving().getAttribute(ModAttributes.REGEN_RATE.get()) == null)
            return;

        float value = (float) event.getEntityLiving().getAttribute(ModAttributes.REGEN_RATE.get()).getValue();
        if (value != 1)
            event.setAmount(event.getAmount() * value);
    }

}
