/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Curios, a mod made for Minecraft.
 *
 * Curios is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Curios is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Curios.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.jowashere.blackclover.client.curios;

import static net.minecraft.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

import com.github.jowashere.blackclover.api.curios.CuriosApi;
import com.github.jowashere.blackclover.api.curios.SlotContext;
import com.github.jowashere.blackclover.api.curios.type.capability.ICurio;
import com.github.jowashere.blackclover.common.curios.CuriosHelper;
import com.github.jowashere.blackclover.common.curios.network.client.CPacketOpenCurios;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;


import java.util.*;


public class ClientEventHandler {

  private static final UUID ATTACK_DAMAGE_MODIFIER = UUID
      .fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
  private static final UUID ATTACK_SPEED_MODIFIER = UUID
      .fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

  @SubscribeEvent
  public void onKeyInput(TickEvent.ClientTickEvent evt) {

    if (evt.phase != TickEvent.Phase.END) {
      return;
    }

    Minecraft mc = Minecraft.getInstance();

    if (KeyRegistry.openCurios.consumeClick() && mc.isWindowActive()) {
      NetworkLoader.INSTANCE.send(PacketDistributor.SERVER.noArg(), new CPacketOpenCurios());
    }
  }

  @SubscribeEvent
  public void onTooltip(ItemTooltipEvent evt) {
    ItemStack stack = evt.getItemStack();
    PlayerEntity player = evt.getPlayer();

    if (!stack.isEmpty()) {
      List<ITextComponent> tooltip = evt.getToolTip();
      CompoundNBT tag = stack.getTag();
      int i = 0;

      if (tag != null && tag.contains("HideFlags", 99)) {
        i = tag.getInt("HideFlags");
      }

      Set<String> curioTags = CuriosApi.getCuriosHelper().getCurioTags(stack.getItem());
      List<String> slots = new ArrayList<>(curioTags);

      if (!slots.isEmpty()) {
        List<ITextComponent> tagTooltips = new ArrayList<>();
        IFormattableTextComponent slotsTooltip = new TranslationTextComponent("curios.slot")
            .append(": ").withStyle(TextFormatting.GOLD);

        for (int j = 0; j < slots.size(); j++) {
          String key = "curios.identifier." + slots.get(j);
          IFormattableTextComponent type = new TranslationTextComponent(key);

          if (j < slots.size() - 1) {
            type = type.append(", ");
          }

          type = type.withStyle(TextFormatting.YELLOW);
          slotsTooltip.append(type);
        }

        tagTooltips.add(slotsTooltip);

        LazyOptional<ICurio> optionalCurio = CuriosApi.getCuriosHelper().getCurio(stack);
        optionalCurio.ifPresent(curio -> {
          List<ITextComponent> curioTagsTooltip = curio.getTagsTooltip(tagTooltips);

          if (!curioTagsTooltip.isEmpty()) {
            tooltip.addAll(1, curio.getTagsTooltip(tagTooltips));
          }

        });

        if (!optionalCurio.isPresent()) {
          tooltip.addAll(1, tagTooltips);
        }

        for (String identifier : slots) {
          Multimap<Attribute, AttributeModifier> multimap = CuriosApi.getCuriosHelper()
              .getAttributeModifiers(new SlotContext(identifier, player), UUID.randomUUID(), stack);
          boolean addAttributeTooltips = optionalCurio
              .map(curio -> curio.showAttributesTooltip(identifier)).orElse(true);

          if (addAttributeTooltips && !multimap.isEmpty() && (i & 2) == 0) {
            tooltip.add(StringTextComponent.EMPTY);
            tooltip.add(new TranslationTextComponent("curios.modifiers." + identifier)
                .withStyle(TextFormatting.GOLD));

            for (Map.Entry<Attribute, AttributeModifier> entry : multimap.entries()) {
              AttributeModifier attributemodifier = entry.getValue();
              double amount = attributemodifier.getAmount();
              boolean flag = false;

              if (player != null) {

                if (attributemodifier.getId() == ATTACK_DAMAGE_MODIFIER) {
                  ModifiableAttributeInstance att = player.getAttribute(Attributes.ATTACK_DAMAGE);

                  if (att != null) {
                    amount = amount + att.getBaseValue();
                  }
                  amount = amount + EnchantmentHelper
                      .getDamageBonus(stack, CreatureAttribute.UNDEFINED);
                  flag = true;
                } else if (attributemodifier.getId() == ATTACK_SPEED_MODIFIER) {
                  ModifiableAttributeInstance att = player.getAttribute(Attributes.ATTACK_SPEED);

                  if (att != null) {
                    amount += att.getBaseValue();
                  }
                  flag = true;
                }

                double d1;

                if (attributemodifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE
                    && attributemodifier.getOperation()
                    != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                  d1 = amount;
                } else {
                  d1 = amount * 100.0D;
                }

                if (entry.getKey() instanceof CuriosHelper.SlotAttributeWrapper) {
                  CuriosHelper.SlotAttributeWrapper wrapper =
                      (CuriosHelper.SlotAttributeWrapper) entry.getKey();

                  if (amount > 0.0D) {
                    tooltip.add((new TranslationTextComponent(
                        "curios.modifiers.slots.plus." + attributemodifier.getOperation().toValue(),
                            ATTRIBUTE_MODIFIER_FORMAT, new TranslationTextComponent(
                        "curios.identifier." + wrapper.identifier))).withStyle(
                        TextFormatting.BLUE));
                  } else {
                    d1 = d1 * -1.0D;
                    tooltip.add((new TranslationTextComponent(
                        "curios.modifiers.slots.take." + attributemodifier.getOperation().toValue(),
                            ATTRIBUTE_MODIFIER_FORMAT.format(d1), new TranslationTextComponent(
                        "curios.identifier." + wrapper.identifier))).withStyle(
                        TextFormatting.RED));
                  }
                } else if (flag) {
                  tooltip.add(
                      (new StringTextComponent(" ")).append(new TranslationTextComponent(
                              "attribute.modifier.equals." + attributemodifier.getOperation().toValue(),
                              ATTRIBUTE_MODIFIER_FORMAT.format(d1),
                              new TranslationTextComponent(entry.getKey().getDescriptionId())))
                          .withStyle(TextFormatting.DARK_GREEN));
                } else if (amount > 0.0D) {
                  tooltip.add((new TranslationTextComponent(
                      "attribute.modifier.plus." + attributemodifier.getOperation().toValue(),
                          ATTRIBUTE_MODIFIER_FORMAT.format(d1),
                      new TranslationTextComponent(entry.getKey().getDescriptionId())))
                      .withStyle(TextFormatting.BLUE));
                } else if (amount < 0.0D) {
                  d1 = d1 * -1.0D;
                  tooltip.add((new TranslationTextComponent(
                      "attribute.modifier.take." + attributemodifier.getOperation().toValue(),
                          ATTRIBUTE_MODIFIER_FORMAT.format(d1),
                      new TranslationTextComponent(entry.getKey().getDescriptionId())))
                      .withStyle(TextFormatting.RED));
                }
              }
            }
          }
        }
      }
    }
  }
}
