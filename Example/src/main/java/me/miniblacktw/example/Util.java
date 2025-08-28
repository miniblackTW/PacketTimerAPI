package me.miniblacktw.example;

import org.bukkit.entity.Player;

public class Util {
    public void sendActionBar(Player p, String msg) {
        net.minecraft.server.v1_8_R3.IChatBaseComponent comp =
                net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + msg + "\"}");
        net.minecraft.server.v1_8_R3.PacketPlayOutChat packet =
                new net.minecraft.server.v1_8_R3.PacketPlayOutChat(comp, (byte) 2);
        ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer) p)
                .getHandle().playerConnection.sendPacket(packet);
    }
}