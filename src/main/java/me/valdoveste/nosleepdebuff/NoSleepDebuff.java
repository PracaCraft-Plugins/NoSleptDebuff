package me.valdoveste.nosleepdebuff;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public final class NoSleepDebuff extends JavaPlugin implements Listener {
    public static int onBedPlayersCount = 0;
    public static List<Player> sleptPlayers = new ArrayList<>();
    private final WorldTimeTask worldTimeTask = new WorldTimeTask(this);
    private final SleepTicksTask sleepTicksTask = new SleepTicksTask(this);

    @Override
    public void onEnable() {
        BukkitTask worldTimeTask = new WorldTimeTask(this).runTaskTimer( this, 80L, 400L);
        getServer().getPluginManager().registerEvents(this, this);
    }

    private void setDrowsiness(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 75, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 75, 1));
    }

    public void isPlayerSlept(Player player){
        if(sleptPlayers.size() >= 1){
            if(!sleptPlayers.contains(player)) setDrowsiness(player);
        }else{
            Player[] onlinePlayers = getServer().getOnlinePlayers().toArray(new Player[0]);
            for (Player onlinePlayer : onlinePlayers) setDrowsiness(onlinePlayer);
        }
    }

    @EventHandler
    private void enteredBed(PlayerBedEnterEvent event) {
        World world = getServer().getWorld("world");
        assert world != null;
        if((world.getTime() >= 13000 && world.getTime() < 25000) || world.hasStorm()) {
            onBedPlayersCount++;
            BukkitTask sleepTicksTask = new SleepTicksTask(this).runTaskTimer(this, 0L, 20L);
        }
    }

    @EventHandler
    private void justLeavedBed(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        onBedPlayersCount--;
        if(player.getSleepTicks() > 0) sleptPlayers.add(player);
    }
}