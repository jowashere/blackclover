package com.github.jowashere.blackclover.networking;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.curios.CuriosApi;
import com.github.jowashere.blackclover.common.curios.network.client.*;
import com.github.jowashere.blackclover.common.curios.network.server.SPacketBreak;
import com.github.jowashere.blackclover.common.curios.network.server.SPacketGrabbedItem;
import com.github.jowashere.blackclover.common.curios.network.server.SPacketScroll;
import com.github.jowashere.blackclover.common.curios.network.server.SPacketSetIcons;
import com.github.jowashere.blackclover.common.curios.network.server.sync.*;
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
import net.minecraftforge.fml.network.PacketDistributor;
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
        INSTANCE.registerMessage(nextID(), SSyncManaPacket.class, SSyncManaPacket::encode, SSyncManaPacket::decode, SSyncManaPacket::handle);

        INSTANCE.registerMessage(nextID(), PacketSetGrimoireTexture.class, PacketSetGrimoireTexture::encode, PacketSetGrimoireTexture::decode, PacketSetGrimoireTexture::handle);
        INSTANCE.registerMessage(nextID(), PacketSwordSlotSet.class, PacketSwordSlotSet::encode, PacketSwordSlotSet::decode, PacketSwordSlotSet::handle);
        INSTANCE.registerMessage(nextID(), PacketGrimoireSword.class, PacketGrimoireSword::encode, PacketGrimoireSword::decode, PacketGrimoireSword::handle);

        INSTANCE.registerMessage(nextID(), PacketSetGrimoire.class, PacketSetGrimoire::encode, PacketSetGrimoire::decode, PacketSetGrimoire::handle);
        INSTANCE.registerMessage(nextID(), PacketMagicLevel.class, PacketMagicLevel::encode, PacketMagicLevel::decode, PacketMagicLevel::handle);
        INSTANCE.registerMessage(nextID(), PacketMagicExpSync.class, PacketMagicExpSync::encode, PacketMagicExpSync::decode, PacketMagicExpSync::handle);
        INSTANCE.registerMessage(nextID(), PacketModeSync.class, PacketModeSync::encode, PacketModeSync::decode, PacketModeSync::handle);
        INSTANCE.registerMessage(nextID(), PacketHasModeSync.class, PacketHasModeSync::encode, PacketHasModeSync::decode, PacketHasModeSync::handle);

        INSTANCE.registerMessage(nextID(), PacketRaceSync.class, PacketRaceSync::encode, PacketRaceSync::decode, PacketRaceSync::handle);
        INSTANCE.registerMessage(nextID(), PacketAttributeSync.class, PacketAttributeSync::encode, PacketAttributeSync::decode, PacketAttributeSync::handle);

        INSTANCE.registerMessage(nextID(), PacketToggleInfusionBoolean.class, PacketToggleInfusionBoolean::encode, PacketToggleInfusionBoolean::decode, PacketToggleInfusionBoolean::handle);


        //CURIOS

        // Client Packets
        INSTANCE.registerMessage(nextID(), CPacketOpenCurios.class, CPacketOpenCurios::encode, CPacketOpenCurios::decode,
                CPacketOpenCurios::handle);

        INSTANCE.registerMessage(nextID(), CPacketOpenVanilla.class, CPacketOpenVanilla::encode, CPacketOpenVanilla::decode,
                CPacketOpenVanilla::handle);
        INSTANCE.registerMessage(nextID(), CPacketScroll.class, CPacketScroll::encode, CPacketScroll::decode,
                CPacketScroll::handle);
        INSTANCE.registerMessage(nextID(), CPacketDestroy.class, CPacketDestroy::encode, CPacketDestroy::decode,
                CPacketDestroy::handle);
        INSTANCE.registerMessage(nextID(), CPacketToggleRender.class, CPacketToggleRender::encode, CPacketToggleRender::decode,
                CPacketToggleRender::handle);

        // Server Packets
        INSTANCE.registerMessage(nextID(), SPacketSyncStack.class, SPacketSyncStack::encode, SPacketSyncStack::decode,
                SPacketSyncStack::handle);
        INSTANCE.registerMessage(nextID(), SPacketScroll.class, SPacketScroll::encode, SPacketScroll::decode,
                SPacketScroll::handle);
        INSTANCE.registerMessage(nextID(), SPacketSyncOperation.class, SPacketSyncOperation::encode, SPacketSyncOperation::decode,
                SPacketSyncOperation::handle);
        INSTANCE.registerMessage(nextID(), SPacketSyncCurios.class, SPacketSyncCurios::encode, SPacketSyncCurios::decode,
                SPacketSyncCurios::handle);
        INSTANCE.registerMessage(nextID(), SPacketBreak.class, SPacketBreak::encode, SPacketBreak::decode, SPacketBreak::handle);
        INSTANCE.registerMessage(nextID(), SPacketGrabbedItem.class, SPacketGrabbedItem::encode, SPacketGrabbedItem::decode,
                SPacketGrabbedItem::handle);
        INSTANCE.registerMessage(nextID(), SPacketSetIcons.class, SPacketSetIcons::encode, SPacketSetIcons::decode,
                SPacketSetIcons::handle);
        INSTANCE.registerMessage(nextID(), SPacketSyncRender.class, SPacketSyncRender::encode, SPacketSyncRender::decode,
                SPacketSyncRender::handle);
        INSTANCE.registerMessage(nextID(), SPacketSyncModifiers.class, SPacketSyncModifiers::encode, SPacketSyncModifiers::decode,
                SPacketSyncModifiers::handle);

        // Assignment of curio breaking to the network instance
        //CuriosApi.getCuriosHelper().setBrokenCurioConsumer((id, index, livingEntity) -> INSTANCE
          //      .send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> livingEntity),
            //            new SPacketBreak(livingEntity.getId(), id, index)));

    }
}
