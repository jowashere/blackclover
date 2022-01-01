package com.github.jowashere.blackclover.api.curios.event;

import com.github.jowashere.blackclover.api.curios.SlotContext;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event;

/**
 * CurioUnequipEvent is fired when a curio item is about to be unequipped and allows an event
 * listener to specify whether it should or not. <br>
 * This event is fired when ever the {ICurio#canUnequip(String, LivingEntity)}
 * is checked. <br>
 * <br>
 * This event has a {@link HasResult result}:
 * <li>{@link Result#ALLOW} means the curio item can be unequipped.</li>
 * <li>{@link Result#DEFAULT} means {ICurio#canUnequip(String, LivingEntity)}
 * determines the result.</li>
 * <li>{@link Result#DENY} means the curio item cannot be unequipped.</li><br>
 * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS}.
 */
@Event.HasResult
public class CurioUnequipEvent extends LivingEvent {

  private final SlotContext slotContext;
  private final ItemStack stack;

  public CurioUnequipEvent(ItemStack stack, SlotContext slotContext) {
    super(slotContext.getWearer());
    this.slotContext = slotContext;
    this.stack = stack;
  }

  public SlotContext getSlotContext() {
    return slotContext;
  }

  public ItemStack getStack() {
    return stack;
  }
}
