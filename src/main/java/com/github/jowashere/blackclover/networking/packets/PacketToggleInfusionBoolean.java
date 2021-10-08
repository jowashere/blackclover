package com.github.jowashere.blackclover.networking.packets;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketToggleInfusionBoolean {

    //1 = Mana Skin
    //2 = Reinforcement

    private int caseInfusion;
    private boolean toClient;
    private boolean toggle;
    private int playerID;

    public PacketToggleInfusionBoolean(int caseInfusion, boolean toClient, boolean toggle, int playerID)
    {
        this.caseInfusion = caseInfusion;
        this.toClient = toClient;
        this.toggle = toggle;
        this.playerID = playerID;
    }

    public static void encode(PacketToggleInfusionBoolean msg, PacketBuffer buf)
    {
        buf.writeInt(msg.caseInfusion);
        buf.writeBoolean(msg.toClient);
        buf.writeBoolean(msg.toggle);
        buf.writeInt(msg.playerID);
    }

    public static PacketToggleInfusionBoolean decode(PacketBuffer buf)
    {
        int caseInfusion = buf.readInt();
        boolean toClient = buf.readBoolean();
        boolean toggle = buf.readBoolean();
        int playerID = buf.readInt();
        return new PacketToggleInfusionBoolean(caseInfusion, toClient, toggle, playerID);
    }

    public static void handle(PacketToggleInfusionBoolean msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            switch (msg.caseInfusion)
            {
                case 1:
                    if (msg.toClient)
                    {
                        ClientPlayerEntity player = (ClientPlayerEntity) Minecraft.getInstance().level.getEntity(msg.playerID);
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setManaSkinToggled(msg.toggle);
                    }
                    else {
                        LazyOptional<IPlayerHandler> capabilities = ctx.get().getSender().getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setManaSkinToggled(msg.toggle);
                    }
                    break;
                case 2:
                    if (msg.toClient)
                    {
                        ClientPlayerEntity player = (ClientPlayerEntity) Minecraft.getInstance().level.getEntity(msg.playerID);
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setReinforcementToggled(msg.toggle);
                    }
                    else {
                        LazyOptional<IPlayerHandler> capabilities = ctx.get().getSender().getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setReinforcementToggled(msg.toggle);
                    }
                    break;
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
