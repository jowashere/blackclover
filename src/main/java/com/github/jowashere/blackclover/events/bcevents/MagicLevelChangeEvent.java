package com.github.jowashere.blackclover.events.bcevents;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class MagicLevelChangeEvent extends PlayerEvent {

    private final PlayerEntity player;
    private final int oldLevel;
    private final int newLevel;

    public MagicLevelChangeEvent(PlayerEntity player, int oldLevel, int newLevel)
    {
        super(player);
        this.player = player;
        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
    }

    public PlayerEntity getPlayer()
    {
        return player;
    }

    public int getNewLevel(){
        return newLevel;
    }

    public int getOldLevel(){
        return oldLevel;
    }


}
