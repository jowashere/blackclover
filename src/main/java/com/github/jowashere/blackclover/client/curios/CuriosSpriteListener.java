package com.github.jowashere.blackclover.client.curios;

import com.github.jowashere.blackclover.Main;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CuriosSpriteListener extends ReloadListener<Void> {

  private final Set<ResourceLocation> registeredSprites = new HashSet<>();

  @Override
  @Nonnull
  protected Void prepare(@Nonnull IResourceManager resourceManagerIn,
                         @Nonnull IProfiler profilerIn) {
    Collection<ResourceLocation> resources =
        resourceManagerIn.listResources("textures/slot",
            (fileName) -> ResourceLocation.isValidResourceLocation(fileName) &&
                fileName.endsWith(".png"));

    Set<ResourceLocation> result = new HashSet<>();

    for (ResourceLocation resource : resources) {
      String prefix = "textures/slot/";
      String namespace = resource.getNamespace();
      String path = resource.getPath();

      if (namespace.equals(Main.MODID) && path.startsWith(prefix)) {
        result.add(new ResourceLocation(namespace,
            path.substring("textures/".length(), path.length() - ".png".length())));
      }
    }
    registeredSprites.clear();
    registeredSprites.addAll(result);
    return null;
  }

  @Override
  protected void apply(@Nonnull Void objectIn,
                       @Nonnull IResourceManager resourceManagerIn, @Nonnull IProfiler profilerIn) {
    // NO-OP
  }

  public Set<ResourceLocation> getSprites() {
    return registeredSprites;
  }
}
