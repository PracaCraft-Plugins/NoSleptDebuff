package me.valdoveste.nosleepdebuff;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class WorldTimeTask extends BukkitRunnable {
    public static final long[] worldTime = new long[1];

    NoSleepDebuff plugin;
    public WorldTimeTask(NoSleepDebuff plugin) {
        this.plugin = plugin;
    }

    public void isDay(long worldTime) {
        if(worldTime >= 0 && worldTime <= 500){
            Player[] onlinePlayers = getServer().getOnlinePlayers().toArray(new Player[0]);
            for (Player onlinePlayer : onlinePlayers) plugin.isPlayerSlept(onlinePlayer);
        }else{
            if(NoSleepDebuff.sleptPlayers.size() > 0) NoSleepDebuff.sleptPlayers.removeAll(Collections.unmodifiableList(NoSleepDebuff.sleptPlayers));
        }
    }

    @Override
    public void run() {
        worldTime[0] = Objects.requireNonNull(plugin.getServer().getWorld("world")).getTime();
        isDay(worldTime[0]);
    }
}