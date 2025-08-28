# Dependencies
- ProtocolLib

# Usage:
### API methods
```java
PacketTimer timer = new PacketTimer(); // create a timer

timer.start();   // start
timer.pause();   // pause (time is kept)
timer.resume();  // resume after pause
timer.stop();    // stop and reset (to 0)

long ms = timer.getTime(TimerMode.MILLIS); // get time in milliseconds
long ticks = timer.getTime(TimerMode.TICK); // get time in ticks
```

### It can work in 2 modes:
- single target — one timer for the whole server or a single activity
- per-player — each player has their own independent timer

### single target example
```java
public class SingleTargetExample extends JavaPlugin implements Listener {
    private PacketTimer globalTimer;

    @Override
    public void onEnable() {
        globalTimer = new PacketTimer();
        PacketCounter.register(this); // required for packet counting
        getServer().getPluginManager().registerEvents(this, this);
        // starts when the first player joins
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!globalTimer.isRunning()) {
            globalTimer.start();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (Bukkit.getOnlinePlayers().isEmpty()) {
            globalTimer.stop(); // stops when there are no players online
        }
    }
}
```

---

### per-player example
```java
public class PerPlayerExample extends JavaPlugin implements Listener {

    private Map<Player, PacketTimer> timers = new HashMap<>();

    @Override
    public void onEnable() {
        PacketCounter.register(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        PacketTimer timer = new PacketTimer();
        timer.start();
        timers.put(e.getPlayer(), timer);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        PacketTimer timer = timers.remove(player);
        if (timer != null) {
            long ms = timer.getTime(TimerMode.MILLIS);
            long ticks = timer.getTime(TimerMode.TICK);
            getLogger().info(player.getName() + " played for " + ms + " ms (" + ticks + " ticks).");
            timer.stop();
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        PacketTimer timer = timers.get(player);
        if (timer == null) return;

        if (!e.getFrom().toVector().equals(e.getTo().toVector())) {
            timer.resume();
        } else {
            timer.pause();
        }
    }
}
```
