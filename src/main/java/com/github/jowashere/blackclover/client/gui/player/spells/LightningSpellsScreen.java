package com.github.jowashere.blackclover.client.gui.player.spells;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.client.gui.widgets.spells.GuiButtonSpell;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

@OnlyIn(Dist.CLIENT)
public class LightningSpellsScreen extends AbstractSpellScreen{

    private ArrayList<BCMSpell> spells = new ArrayList<>();

    public LightningSpellsScreen() {
        super(new TranslationTextComponent("gui." + Main.MODID + ".title.lightningspells"));
        /*for (BCMSpell spells : BCMRegistry.SPELLS.getValues()) {
            if (spells.getType() == BCMSpell.Type.WIND_MAGIC) {
                this.spells.add(spells);
            }
        }*/
    }

    @Override
    public void registerSpells(IPlayerHandler playerCapability) {

        for (BCMSpell spells : BCMRegistry.SPELLS.getValues()) {
            if (spells.getType() == BCMSpell.Type.LIGHTNING_MAGIC) {
                if(playerCapability.hasSpellBoolean(spells)){
                    if(!this.spells.contains(spells)){
                        this.spells.add(spells);
                    }
                }
            }
        }

        int x = -90;
        int y = -90;
        for (BCMSpell spell : this.spells) {
            addButton(new GuiButtonSpell(this.guiLeft + x, this.guiTop + y, spell.getU(), spell.getV(), spell.getCorrelatedPlugin().getPluginId() + "." + spell.getName(), false, spell.getResourceLocationForGUI()));
            if (x == 90) {
                x = -90;
                y += 20;
            }
            else {
                x += 20;
            }
        }
    }

    @Override
    public void setSpellsBooleans(IPlayerHandler playerCapability) {
        for (BCMSpell spell : this.spells) {
            for (Widget button : this.buttons) {
                if (button instanceof GuiButtonSpell) {
                    GuiButtonSpell buttonSpell = (GuiButtonSpell) button;
                    if (buttonSpell.getSpellName().equalsIgnoreCase(spell.getName())) {
                        spell.update(buttonSpell, spell, playerCapability);
                    }
                }
            }
        }
    }

}
