package com.github.jowashere.blackclover.api.curios.event;

import com.github.jowashere.blackclover.api.curios.SlotContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event;

/**
 * CurioEquipEvent is fired when a curio item is about to be equipped and allows an event listener
 * to specify whether it should or not. <br>
 * This event is fired when ever the { Icurio canEquip(String, LivingEntity)}
 * is checked. <br>
 * <br>
 * This event has a {@link HasResult result}:
 * <li>{@link Result#ALLOW} means the curio item can be equipped.</li>
 * <li>{@link Result#DEFAULT} means the item tags and {@link top.theillusivec4.curios.api.type.capability.ICurio#canEquip(String, LivingEntity)}
 * determines the result.</li>
 * <li>{@link Result#DENY} means the curio item cannot be equipped.</li><br>
 * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS}.
 */
@Event.HasResult
public class CurioEquipEvent extends LivingEvent {

  private final SlotContext slotContext;
  private final ItemStack stack;

  public CurioEquipEvent(ItemStack stack, SlotContext slotContext) {
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
