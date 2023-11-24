package me.valdoveste.nosleepdebuff;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public final class NoSleepDebuff extends JavaPlugin implements Listener {
    public static int onBedPlayersCount = 0;
    public static List<Player> sleptPlayers = new ArrayList<>();
    private static final Map<Player, PlayerClone> players = new HashMap<>();
    private final SleepTicksTask sleepTicksTask = new SleepTicksTask(this);

@Override
public void onEnable() {
    getServer().getPluginManager().registerEvents(this, this);
    BukkitTask worldTimeTask = new WorldTimeTask(this).runTaskTimer(this, 80L, 400L);
}

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerClone playerClone = new PlayerClone(player);
        players.put(player, playerClone);
        players.get(player).setNonSleptDays(60);
    }

@EventHandler
private void enteredBed(PlayerBedEnterEvent event) {
    try {
        World world = getServer().getWorld("world");
        assert world != null;
        if ((world.getTime() >= 13000 && world.getTime() < 25000) || world.hasStorm()) {
            onBedPlayersCount++;
            new SleepTicksTask(this).runTaskTimer(this, 0L, 20L);
        }
    } catch (Exception e) {
        System.out.println("An exception error has occurred while trying to get the world: world "
                + " Exception Error --> "
                + e);
    }
}

@EventHandler
private void justLeavedBed(PlayerBedLeaveEvent event) {
    Player player = event.getPlayer();
    onBedPlayersCount--;
    if (player.getSleepTicks() > 5) sleptPlayers.add(player);
}

private static void setDrowsiness(Player player) {
    try {
        if(player.getWorld().getName().equals("world")) {
            if (players.get(player).getNonSleptDays() >= 3)
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.HUNGER), (120), 2));

            player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName("SLOW")), (players.get(player).getNonSleptDays()), 1));
            player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName("BLINDNESS")), (players.get(player).getNonSleptDays()), 1));
            players.get(player).addNonSleptDays();
            sleptPlayers.remove(player);
        }
    } catch (Exception e) {
        System.out.println("An exception error has occurred while trying to set Drowsiness on player: "
                + player.getName()
                + " Exception Error --> "
                + e);
    }
}

public void isPlayerSlept(Player player) {
    if (sleptPlayers.isEmpty() || !sleptPlayers.contains(player)) {
        setDrowsiness(player);
    } else {
        players.get(player).resetNonSleptDays();
    }

    System.out.println(sleptPlayers + "  " + player.getName() + "Removido");
    sleptPlayers.remove(player);
}

}