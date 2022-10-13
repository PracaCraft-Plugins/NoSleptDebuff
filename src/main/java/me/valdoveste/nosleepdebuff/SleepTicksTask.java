package me.valdoveste.nosleepdebuff;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class SleepTicksTask extends BukkitRunnable {
    NoSleptDebuff plugin;
    public SleepTicksTask(NoSleptDebuff plugin) {
        this.plugin = plugin;
    }

    public static int sleepTicks = 0;

    @Override
    public void run() {
        if(!(sleepTicks == 5) && NoSleptDebuff.onBedPlayersCount > 0) {
            sleepTicks++;
        }else{
            this.cancel();
            sleepTicks = 0;
        }

        if(sleepTicks == 5)
            this.cancel();
            sleepTicks = 0;
            Objects.requireNonNull(plugin.getServer().getWorld("world")).setTime(0);
    }
}