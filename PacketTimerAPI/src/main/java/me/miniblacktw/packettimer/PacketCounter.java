package me.miniblacktw.packettimer;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.atomic.AtomicInteger;

public class PacketCounter {

    private static AtomicInteger packetCounter = new AtomicInteger(0);

    public static void register(Plugin plugin) {
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(plugin, ListenerPriority.MONITOR,
                        PacketType.Play.Client.getInstance().values()
                ) {
                    @Override
                    public void onPacketReceiving(PacketEvent e) {
                        packetCounter.incrementAndGet();
                    }
                }
        );
    }

    public static int getCount() {
        return packetCounter.get();
    }
}