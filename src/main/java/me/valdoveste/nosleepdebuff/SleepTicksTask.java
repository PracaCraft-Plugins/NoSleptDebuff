package me.valdoveste.nosleepdebuff;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class SleepTicksTask extends BukkitRunnable {
    NoSleepDebuff plugin;
    public static int sleepTicks = 0;
    public SleepTicksTask(NoSleepDebuff plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        switch (sleepTicks) {
            case 5:
                if (NoSleepDebuff.onBedPlayersCount >= 1) {
                    sleepTicks++;
                } else {
                    cancelAndReset();
                    Objects.requireNonNull(plugin.getServer().getWorld("world")).setTime(0);
                }
                break;
            default:
                cancelAndReset();
                break;
        }
    }

    private void cancelAndReset() {
        this.cancel();
        sleepTicks = 0;
    }
}