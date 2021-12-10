package com.github.jowashere.blackclover.common.spells;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketIntSpellNBTSync;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSpellNBTSync;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

public class SpellCaller {

    public static void SpellCaller(PlayerEntity playerIn, String spellName, int spellKey)
    {
        //System.out.println("spell called");
        if(!playerIn.level.isClientSide){
            if (spellName != null) {
                IPlayerHandler playercap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                for (BCMSpell spell : BCMRegistry.SPELLS.getValues()) {
                    if (("spell." + spell.getCorrelatedPlugin().getPluginId() + "." + spell.getName()).equalsIgnoreCase(spellName)) {
                        if (spell.isToggle()) {
                            String nbtName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName();

                            if(!spell.isSkillSpell() && !playercap.returnHasGrimoire()){
                                playerIn.displayClientMessage(new TranslationTextComponent("spell." + Main.MODID + ".message.nogrimoire"), true);
                                return;
                            }

                            if (!playerIn.getPersistentData().getBoolean(nbtName)) {
                                if (spell.getExtraCheck() != null) {
                                    if (!spell.getExtraCheck().check(playerIn)) {
                                        if(spell.getCheckFailMsg() != null){
                                            playerIn.displayClientMessage(new StringTextComponent(spell.getCheckFailMsg()), true);
                                        }
                                        return;
                                    }
                                }
                                if(spell.getToggleTimer() > -1){
                                    playerIn.getPersistentData().putInt(nbtName + "_timer", spell.getToggleTimer());
                                    NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(()-> (ServerPlayerEntity) playerIn), new PacketIntSpellNBTSync(playerIn.getId(), "black_mode_fatigue", 900));
                                }

                                float manaCost = spell.getManaCost() + ((float) Math.sqrt(playercap.ReturnMagicLevel()) * (spell.getManaCost() / 5) );

                                spell.throwStartEvent(playerIn, manaCost);
                                playerIn.getPersistentData().putBoolean(nbtName, true);
                                NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketSpellNBTSync(playerIn.getId(), nbtName, true));

                                if (!playercap.returnToggleSpellMessage())
                                    //playerIn.displayClientMessage(new StringTextComponent(new TranslationTextComponent("spell." + spell.getCorrelatedPlugin().getPluginId() + "." + spell.getName()).getString() + "!"), false);
                                    return;
                            } else {
                                spell.throwCancelEvent(playerIn, spellKey);
                                playerIn.getPersistentData().putBoolean(nbtName, false);
                                NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketSpellNBTSync(playerIn.getId(), nbtName, false));

                            }
                        }
                        else {
                            int modifier0 = Math.max(0, playercap.ReturnMagicLevel() / 5);
                            int modifier1 = Math.max(0, playercap.ReturnMagicLevel() / 5) - 1;

                            spell.act(playerIn, modifier0, modifier1, spellKey);
                        }
                    }
                }
            }
        }
    }


}
