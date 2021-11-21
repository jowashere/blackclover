package com.github.jowashere.blackclover.networking;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.networking.packets.*;
import com.github.jowashere.blackclover.networking.packets.mana.*;
import com.github.jowashere.blackclover.networking.packets.modes.PacketModeSync;
import com.github.jowashere.blackclover.networking.packets.server.SPacketSpellNBTSync;
import com.github.jowashere.blackclover.networking.packets.server.SSyncManaPacket;
import com.github.jowashere.blackclover.networking.packets.settings.PacketKeybindSet;
import com.github.jowashere.blackclover.networking.packets.settings.PacketSetGrimoireTexture;
import com.github.jowashere.blackclover.networking.packets.spells.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkLoader {

    public static SimpleChannel INSTANCE;
    private static int id = 1;

    public static int nextID() {
        return id++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Main.MODID, "blackclover"), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(nextID(), PacketManaSync.class, PacketManaSync::encode, PacketManaSync::decode, PacketManaSync::handle);
        INSTANCE.registerMessage(nextID(), PacketRegenManaSync.class, PacketRegenManaSync::encode, PacketRegenManaSync::decode, PacketRegenManaSync::handle);
        INSTANCE.registerMessage(nextID(), PacketManaAddition.class, PacketManaAddition::encode, PacketManaAddition::decode, PacketManaAddition::handle);
        INSTANCE.registerMessage(nextID(), PacketMaxManaSync.class, PacketMaxManaSync::encode, PacketMaxManaSync::decode, PacketMaxManaSync::handle);
        INSTANCE.registerMessage(nextID(), PacketColourManaSync.class, PacketColourManaSync::encode, PacketColourManaSync::decode, PacketColourManaSync::handle);
        INSTANCE.registerMessage(nextID(), PacketManaBoolean.class, PacketManaBoolean::encode, PacketManaBoolean::decode, PacketManaBoolean::handle);
        INSTANCE.registerMessage(nextID(), PacketKeybindSet.class, PacketKeybindSet::encode, PacketKeybindSet::decode, PacketKeybindSet::handle);
        INSTANCE.registerMessage(nextID(), PacketSetSpellBoolean.class, PacketSetSpellBoolean::encode, PacketSetSpellBoolean::decode, PacketSetSpellBoolean::handle);
        INSTANCE.registerMessage(nextID(), PacketSpellCaller.class, PacketSpellCaller::encode, PacketSpellCaller::decode, PacketSpellCaller::handle);
        INSTANCE.registerMessage(nextID(), PacketSpellNBTSync.class, PacketSpellNBTSync::encode, PacketSpellNBTSync::decode, PacketSpellNBTSync::handle);
        INSTANCE.registerMessage(nextID(), PacketIntSpellNBTSync.class, PacketIntSpellNBTSync::encode, PacketIntSpellNBTSync::decode, PacketIntSpellNBTSync::handle);
        INSTANCE.registerMessage(nextID(), SPacketSpellNBTSync.class, SPacketSpellNBTSync::encode, SPacketSpellNBTSync::decode, SPacketSpellNBTSync::handle);
        INSTANCE.registerMessage(nextID(), PacketSpellModeToggle.class, PacketSpellModeToggle::encode, PacketSpellModeToggle::decode, PacketSpellModeToggle::handle);
        INSTANCE.registerMessage(nextID(), PacketKeybindCD.class, PacketKeybindCD::encode, PacketKeybindCD::decode, PacketKeybindCD::handle);
        INSTANCE.registerMessage(nextID(), SSyncManaPacket.class, SSyncManaPacket::encode, SSyncManaPacket::decode, SSyncManaPacket::handle);

        INSTANCE.registerMessage(nextID(), PacketSetGrimoireTexture.class, PacketSetGrimoireTexture::encode, PacketSetGrimoireTexture::decode, PacketSetGrimoireTexture::handle);

        INSTANCE.registerMessage(nextID(), PacketSetGrimoire.class, PacketSetGrimoire::encode, PacketSetGrimoire::decode, PacketSetGrimoire::handle);
        INSTANCE.registerMessage(nextID(), PacketMagicLevel.class, PacketMagicLevel::encode, PacketMagicLevel::decode, PacketMagicLevel::handle);
        INSTANCE.registerMessage(nextID(), PacketMagicExpSync.class, PacketMagicExpSync::encode, PacketMagicExpSync::decode, PacketMagicExpSync::handle);
        INSTANCE.registerMessage(nextID(), PacketModeSync.class, PacketModeSync::encode, PacketModeSync::decode, PacketModeSync::handle);
        INSTANCE.registerMessage(nextID(), PacketHasModeSync.class, PacketHasModeSync::encode, PacketHasModeSync::decode, PacketHasModeSync::handle);

        INSTANCE.registerMessage(nextID(), PacketRaceSync.class, PacketRaceSync::encode, PacketRaceSync::decode, PacketRaceSync::handle);
        INSTANCE.registerMessage(nextID(), PacketAttributeSync.class, PacketAttributeSync::encode, PacketAttributeSync::decode, PacketAttributeSync::handle);

        INSTANCE.registerMessage(nextID(), PacketToggleInfusionBoolean.class, PacketToggleInfusionBoolean::encode, PacketToggleInfusionBoolean::decode, PacketToggleInfusionBoolean::handle);

    }
}
