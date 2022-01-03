package com.github.jowashere.blackclover.client.curios;

import com.github.jowashere.blackclover.api.curios.CuriosApi;
import com.github.jowashere.blackclover.api.curios.SlotTypePreset;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;


public class CuriosClientMod {

  private static final CuriosSpriteListener SPRITE_LISTENER = new CuriosSpriteListener();

  @SuppressWarnings("ConstantConditions")
  public static void init() {
    Minecraft mc = Minecraft.getInstance();

    if (mc != null) {
      IResourceManager manager = mc.getResourceManager();

      if (manager instanceof IReloadableResourceManager) {
        IReloadableResourceManager reloader = (IReloadableResourceManager) manager;
        reloader.registerReloadListener(SPRITE_LISTENER);
      }
    }
  }

  public static void stitch(final TextureStitchEvent.Pre evt) {

    if (evt.getMap().location() == PlayerContainer.BLOCK_ATLAS) {

      for (SlotTypePreset preset : SlotTypePreset.values()) {
        evt.addSprite(
            new ResourceLocation(CuriosApi.MODID, "curios/empty_" + preset.getIdentifier() + "_slot"));
      }
      evt.addSprite(new ResourceLocation(CuriosApi.MODID, "curios/empty_cosmetic_slot"));
      evt.addSprite(new ResourceLocation(CuriosApi.MODID, "curios/empty_curio_slot"));

      for (ResourceLocation sprite : SPRITE_LISTENER.getSprites()) {
        evt.addSprite(sprite);
      }
    }
  }
}
