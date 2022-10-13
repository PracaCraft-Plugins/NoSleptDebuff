package me.valdoveste.nosleepdebuff;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class WorldTimeTask extends BukkitRunnable {
    public static final long[] worldTime = new long[1];

    NoSleptDebuff plugin;

    public WorldTimeTask(NoSleptDebuff plugin) {
        this.plugin = plugin;
    }

    public void isDay(long worldTime) {
        if (worldTime >= 0 && worldTime <= 500) {
            Player[] onlinePlayers = getServer().getOnlinePlayers().toArray(new Player[0]);
            for (Player onlinePlayer : onlinePlayers) plugin.isPlayerSlept(onlinePlayer);
        } else {
            if (NoSleptDebuff.sleptPlayers.size() > 0)
                NoSleptDebuff.sleptPlayers.removeAll(Collections.unmodifiableList(NoSleptDebuff.sleptPlayers));
        }
    }

    @Override
    public void run() {
        try {
            worldTime[0] = Objects.requireNonNull(plugin.getServer().getWorld("world")).getTime();
            isDay(worldTime[0]);
        } catch (Exception e) {
            System.out.println("An exception error has occurred while trying to get world: "
                    + Objects.requireNonNull(Objects.requireNonNull(plugin.getServer().getWorld("world")).getName())
                    +  "world time. "
                    + " Exception Error --> "
                    + e);
        }
    }
}