package com.github.jowashere.blackclover.common.spells;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.api.internal.AbstractToggleSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketIntSpellNBTSync;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSpellNBTSync;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

public class SpellCaller {

    public static void SpellCaller(LivingEntity caster, String spellName)
    {
        if(!caster.level.isClientSide){

            if (spellName != null) {

                if( caster instanceof PlayerEntity) {
                    PlayerEntity playerIn = (PlayerEntity) caster;
                    IPlayerHandler playercap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                    for (AbstractSpell spell : BCMRegistry.SPELLS.getValues()) {
                        if (("spell." + spell.getCorrelatedPlugin().getPluginId() + "." + spell.getName()).equalsIgnoreCase(spellName)) {
                            if (spell instanceof AbstractToggleSpell) {
                                String nbtName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName();

                                if (!spell.isSkillSpell() && !playercap.returnHasGrimoire()) {
                                    playerIn.displayClientMessage(new TranslationTextComponent("spell." + Main.MODID + ".message.nogrimoire"), true);
                                    return;
                                }

                                if (!playerIn.getPersistentData().getBoolean(nbtName)) {
                                    if (spell.getExtraCheck() != null) {
                                        if (!spell.getExtraCheck().check(playerIn)) {
                                            if (spell.getCheckFailMsg() != null) {
                                                playerIn.displayClientMessage(new StringTextComponent(spell.getCheckFailMsg()), true);
                                            }
                                            return;
                                        }
                                    }
                                    if (((AbstractToggleSpell) spell).getToggleTimer() > -1) {
                                        playerIn.getPersistentData().putInt(nbtName + "_timer", ((AbstractToggleSpell) spell).getToggleTimer());
                                        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) playerIn), new PacketIntSpellNBTSync(playerIn.getId(), "black_mode_fatigue", 900));
                                    }

                                    float manaCost = spell.getManaCost() + ((float) Math.sqrt(playercap.getMagicLevel()) * (spell.getManaCost() / 5));

                                    ((AbstractToggleSpell) spell).throwStartEvent(playerIn, manaCost);
                                    playerIn.getPersistentData().putBoolean(nbtName, true);
                                    NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketSpellNBTSync(playerIn.getId(), nbtName, true));

                                    if (!playercap.returnToggleSpellMessage())
                                        //playerIn.displayClientMessage(new StringTextComponent(new TranslationTextComponent("spell." + spell.getCorrelatedPlugin().getPluginId() + "." + spell.getName()).getString() + "!"), false);
                                        return;
                                } else {
                                    ((AbstractToggleSpell) spell).throwCancelEvent(playerIn);
                                    playerIn.getPersistentData().putBoolean(nbtName, false);
                                    NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketSpellNBTSync(playerIn.getId(), nbtName, false));

                                }
                            } else {
                                spell.act(playerIn);
                            }
                        }
                    }
                } else if (caster instanceof BCEntity) {
                    BCEntity bcEntity = (BCEntity) caster;

                    for (AbstractSpell spell : BCMRegistry.SPELLS.getValues()) {
                        if (("spell." + spell.getCorrelatedPlugin().getPluginId() + "." + spell.getName()).equalsIgnoreCase(spellName)) {
                            if (spell instanceof AbstractToggleSpell) {
                                String nbtName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName();
                                if (!bcEntity.getPersistentData().getBoolean(nbtName)) {

                                    float manaCost = spell.getManaCost() + ((float) Math.sqrt(BCMHelper.getMagicLevel(bcEntity)) * (spell.getManaCost() / 5));

                                    ((AbstractToggleSpell) spell).throwStartEvent(bcEntity, manaCost);
                                    bcEntity.getPersistentData().putBoolean(nbtName, true);
                                    NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketSpellNBTSync(bcEntity.getId(), nbtName, true));

                                } else {
                                    ((AbstractToggleSpell) spell).throwCancelEvent(bcEntity);
                                    bcEntity.getPersistentData().putBoolean(nbtName, false);
                                    NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketSpellNBTSync(bcEntity.getId(), nbtName, false));

                                }
                            } else {
                                spell.act(bcEntity);
                            }
                        }
                    }
                }
            }
        }
    }


}
