package me.valdoveste.nosleepdebuff;

import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Bukkit.getServer;

public class WorldTimeTask extends BukkitRunnable {
    public static final long[] worldTime = new long[1];

    NoSleepDebuff plugin;

    public WorldTimeTask(NoSleepDebuff plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        try {
            worldTime[0] = Objects.requireNonNull(plugin.getServer().getWorld("world")).getTime();
            isDay(worldTime[0]);
        } catch (Exception e) {
            System.out.println("An exception error has occurred while trying to get world: world"
                    + " world time. "
                    + " Exception Error --> "
                    + e);
        }
    }

    private void isDay(long worldTime) {
        if (worldTime >= 0 && worldTime <= 350) {
            if(!NoSleepDebuff.getWasDrowsinessAppliedToday()) {
                Player[] onlinePlayers = getServer().getOnlinePlayers().toArray(new Player[0]);
                for (Player onlinePlayer : onlinePlayers) plugin.isPlayerSlept(onlinePlayer);
            }else{
                NoSleepDebuff.setWasDrowsinessAppliedToday(false);
            }
        }
    }
}
