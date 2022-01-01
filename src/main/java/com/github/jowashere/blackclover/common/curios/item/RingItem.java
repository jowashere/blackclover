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

package com.github.jowashere.blackclover.common.curios.item;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.curios.SlotContext;
import com.github.jowashere.blackclover.api.curios.type.capability.ICurio;
import com.github.jowashere.blackclover.common.curios.capability.CurioItemCapability;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import java.util.UUID;

public class RingItem extends Item {

  public RingItem() {
    super(new Properties().tab(ItemGroup.TAB_TOOLS).stacksTo(1));
    this.setRegistryName(Main.MODID, "ring");
  }

  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused) {
    return CurioItemCapability.createProvider(new ICurio() {

      @Override
      public void curioTick(String identifier, int index, LivingEntity livingEntity) {

        if (!livingEntity.getEntity().level.isClientSide && livingEntity.tickCount % 19 == 0) {
          livingEntity.addEffect(new EffectInstance(Effects.DIG_SPEED, 20, 0, true, true));
        }
      }

      @Override
      public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext,
                                                                          UUID uuid) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.MOVEMENT_SPEED,
            new AttributeModifier(uuid, Main.MODID + ":speed_bonus", 0.1,
                AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.ARMOR,
            new AttributeModifier(uuid, Main.MODID + ":armor_bonus", 2,
                AttributeModifier.Operation.ADDITION));
        return atts;
      }

      @Nonnull
      @Override
      public DropRule getDropRule(LivingEntity livingEntity) {
        return DropRule.ALWAYS_KEEP;
      }

      @Nonnull
      @Override
      public SoundInfo getEquipSound(SlotContext slotContext) {
        return new SoundInfo(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 1.0f);
      }

      @Override
      public boolean canEquipFromUse(SlotContext slot) {
        return true;
      }
    });
  }

  @Override
  public boolean isFoil(@Nonnull ItemStack stack) {
    return true;
  }
}
