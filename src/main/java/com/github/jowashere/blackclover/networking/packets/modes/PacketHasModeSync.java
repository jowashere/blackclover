package com.github.jowashere.blackclover.networking.packets.modes;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketHasModeSync {

    //1 = Black Mode
    //2 = Thunder God Gear

    private int mode;
    private int value;
    private boolean toClient;

    public PacketHasModeSync(int mode, int value, boolean toClient)
    {
        this.mode = mode;
        this.value = value;
        this.toClient = toClient;
    }

    public static void encode(PacketHasModeSync msg, PacketBuffer buf)
    {
        buf.writeInt(msg.mode);
        buf.writeInt(msg.value);
        buf.writeBoolean(msg.toClient);
    }

    public static PacketHasModeSync decode(PacketBuffer buf)
    {
        int bodyMode = buf.readInt();
        int value = buf.readInt();
        boolean toClient = buf.readBoolean();
        return new PacketHasModeSync(bodyMode, value, toClient);
    }

    public static void handle(PacketHasModeSync msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            switch (msg.mode) {
                case 1:
                    if (msg.toClient) {
                        ClientPlayerEntity player = Minecraft.getInstance().player;
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        //playercap.setPlayerCurseMark(msg.value);
                    } else {
                        LazyOptional<IPlayerHandler> capabilities = ctx.get().getSender().getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        //playercap.setPlayerCurseMark(msg.value);
                    }
                    break;
                case 2:
                    if (msg.toClient) {
                        ClientPlayerEntity player = Minecraft.getInstance().player;
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        //playercap.setPlayerToadSageMode(msg.value);
                    } else {
                        LazyOptional<IPlayerHandler> capabilities = ctx.get().getSender().getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        //playercap.setPlayerToadSageMode(msg.value);
                    }
                    break;
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
