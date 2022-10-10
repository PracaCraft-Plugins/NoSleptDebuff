package me.valdoveste.nosleepdebuff;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class SleepTicksTask extends BukkitRunnable {
    NoSleepDebuff plugin;
    public SleepTicksTask(NoSleepDebuff plugin) {
        this.plugin = plugin;
    }

    public static int sleepTicks = 0;

    @Override
    public void run() {
        if(sleepTicks != 5 && NoSleepDebuff.onBedPlayersCount > 0) {
            sleepTicks++;
        }else if(sleepTicks == 5){
            this.cancel();
            sleepTicks = 0;
            Objects.requireNonNull(plugin.getServer().getWorld("world")).setTime(0);
        } else {
            this.cancel();
            sleepTicks = 0;
        }
    }
}