package me.miniblacktw.example;

import me.miniblacktw.packettimer.PacketCounter;
import me.miniblacktw.packettimer.PacketTimer;
import me.miniblacktw.packettimer.TimerMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class Main extends JavaPlugin implements Listener {
    private PacketTimer timer;
    private Set<Player> moving = new HashSet<>();
    private Set<Player> paused = new HashSet<>();
    private final Util util;

    public Main(Util util) {
        this.util = util;
    }

    @Override
    public void onEnable() {
        timer = new PacketTimer();
        PacketCounter.register(this);
        getServer().getPluginManager().registerEvents(this, this);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : moving) {
                    if (player.isOnline()) {
                        long millis = timer.getTime(TimerMode.MILLIS);
                        long ticks = timer.getTime(TimerMode.TICK);
                        util.sendActionBar(player, "Time: " + millis + " ms | " + ticks + " ticks");
                    }
                }
            }
        }.runTaskTimer(this, 0L, 5L);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        boolean isMoving = !e.getFrom().toVector().equals(e.getTo().toVector());
        if (isMoving) {
            if (!moving.contains(p)) {
                moving.add(p);
                paused.remove(p);
                if (!timer.isRunning()) {
                    timer.start();
                } else {
                    timer.resume();
                }
            }
        } else {
            if (!paused.contains(p)) {
                paused.add(p);
                moving.remove(p);
                timer.pause();
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        timer.pause();
        long millis = timer.getTime(TimerMode.MILLIS);
        long ticks = timer.getTime(TimerMode.TICK);
        getLogger().info(p.getName() + " left, played for " + millis + " ms | " + ticks + " ticks");
        moving.remove(p);
        paused.remove(p);
    }
}