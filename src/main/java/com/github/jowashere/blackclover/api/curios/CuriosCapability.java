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

package com.github.jowashere.blackclover.api.curios;

import com.github.jowashere.blackclover.api.curios.type.capability.ICurio;
import com.github.jowashere.blackclover.api.curios.type.capability.ICuriosItemHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CuriosCapability {

  @CapabilityInject(ICuriosItemHandler.class)
  public static final Capability<ICuriosItemHandler> INVENTORY;

  @CapabilityInject(ICurio.class)
  public static final Capability<ICurio> ITEM;

  public static final ResourceLocation ID_INVENTORY = new ResourceLocation(CuriosApi.MODID,
      "inventory");
  public static final ResourceLocation ID_ITEM = new ResourceLocation(CuriosApi.MODID, "item");

  static {
    INVENTORY = null;
    ITEM = null;
  }
}
