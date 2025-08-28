package me.miniblacktw.packettimer;

public class PacketTimer {

    private boolean running = false;
    private boolean paused = false;
    private long startTime;
    private long pausedTime;
    private long totalPausedTime;

    public void start() {
        running = true;
        paused = false;
        startTime = System.currentTimeMillis();
        totalPausedTime = 0;
    }

    public void stop() {
        running = false;
        paused = false;
    }

    public void pause() {
        if (running && !paused) {
            paused = true;
            pausedTime = System.currentTimeMillis();
        }
    }

    public void resume() {
        if (running && paused) {
            paused = false;
            totalPausedTime += (System.currentTimeMillis() - pausedTime);
        }
    }

    public long getTime(TimerMode mode) {
        if (!running) return 0;
        long currentTime = paused ? pausedTime : System.currentTimeMillis();
        long elapsed = currentTime - startTime - totalPausedTime;

        if (mode == TimerMode.MILLIS) {
            return elapsed;
        } else if (mode == TimerMode.TICK) {
            return elapsed / 50;
        }
        return 0;
    }

    public boolean isRunning() {
        return running && !paused;
    }
}