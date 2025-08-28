package me.miniblacktw.packettimer;

import org.bukkit.plugin.java.JavaPlugin;

public class PacketTimerAPI extends JavaPlugin {

    @Override
    public void onEnable() {
        PacketCounter.register(this);
        getLogger().info("[PacketTimerAPI] Running PacketTimerAPI");
    }

    @Override
    public void onDisable() {
        getLogger().info("[PacketTimerAPI] Unloaded PacketTimerAPI");
    }
}