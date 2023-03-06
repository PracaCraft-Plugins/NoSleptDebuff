package me.valdoveste.nosleepdebuff;

import java.util.*;

import me.valdoveste.nosleepdebuff.commands.reloadPluginCommand;
//import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.player.PlayerJoinEvent;
//import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.configuration.file.FileConfiguration;

import static org.bukkit.Bukkit.getServer;

public final class NoSleepDebuff extends JavaPlugin implements Listener {
    public static NoSleepDebuff plugin;

    public static boolean wasDrowsinessApplyedToday = false;
    FileConfiguration config = getConfig();
    //    public static int onBedPlayersCount = 0;
    public static List<Player> sleptPlayers = new ArrayList<>();
    private static final Map<Player, WrapperPlayer> wrapperPlayersHMap = new HashMap<>();
//    private final SleepTicksTask sleepTicksTask = new SleepTicksTask(this);

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();
        getConfig().options().copyDefaults();

        getServer().getPluginManager().registerEvents(this, this);
        BukkitTask worldTimeTask = new WorldTimeTask(this).runTaskTimer(this, 80L, 400L);
        Objects.requireNonNull(getCommand("nosleepreload")).setExecutor(new reloadPluginCommand(this));
    }

    @Override
    public void onDisable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults();
    }

    public static boolean getWasDrowsinessAppliedToday() { return wasDrowsinessApplyedToday; }

    public static void setWasDrowsinessAppliedToday(boolean newState) {
        wasDrowsinessApplyedToday = newState;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        WrapperPlayer wrapperPlayer = new WrapperPlayer(player);
        wrapperPlayersHMap.put(player, wrapperPlayer);
        wrapperPlayersHMap.get(player).setNonSleptDays(0);
    }

    @EventHandler
    private void leavedBed(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        long worldTime = Objects.requireNonNull(getServer().getWorld("world")).getTime();

        if (player.getSleepTicks() >= 50 && player.getSleepTicks() <= 100) {
            sleptPlayers.add(player);
        }

        if (worldTime >= 23000 && worldTime <= 24000) {
            setWasDrowsinessAppliedToday(true);
            Player[] onlinePlayers = getServer().getOnlinePlayers().toArray(new Player[0]);
            for (Player onlinePlayer : onlinePlayers) isPlayerSlept(onlinePlayer);
        }
    }

    private void setDrowsiness(Player player) {
        if (player.getWorld().getName().equals("world")) {

            if (wrapperPlayersHMap.get(player).getNonSleptDays() >= getConfig().getInt("PerDays_debuff_nonSleep.days_threshold")) {
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(
                        PotionEffectType.getByName(Objects.requireNonNull(getConfig().getString("PerDays_debuff_nonSleep.Effect.effect_typeI")))),
                        getConfig().getInt("PerDays_debuff_nonSleep.Effect.effect_duration"),
                        getConfig().getInt("PerDays_debuff_nonSleep.Effect.effect_level"))
                );
            }

            player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(
                    Objects.requireNonNull(getConfig().getString("PerDay_debuff_nonSleep.Effects.effect_typeI")))),
                    (wrapperPlayersHMap.get(player).getNonSleptDebuffDuration()),
                    getConfig().getInt("PerDay_debuff_nonSleep.effect_level"))
            );

            player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(
                    Objects.requireNonNull(getConfig().getString("PerDay_debuff_nonSleep.Effects.effect_typeII")))),
                    (wrapperPlayersHMap.get(player).getNonSleptDebuffDuration()),
                    getConfig().getInt("PerDay_debuff_nonSleep.effect_level"))
            );

            if (wrapperPlayersHMap.get(player).isDebuffDurationAccumulative()) {
                wrapperPlayersHMap.get(player).addNonSleptDebuffDuration();
            } else {
                wrapperPlayersHMap.get(player).setNonSleptDebuffDuration(config.getInt("PerDays_debuff_nonSleep.Effect.effect_duration"));
            }
            wrapperPlayersHMap.get(player).addNonSleptDays();
        }
    }

    public void isPlayerSlept(Player player) {
        if (sleptPlayers.size() >= 1) {
            if (!sleptPlayers.contains(player)) {
                setDrowsiness(player);
            } else {
                sleptPlayers.remove(player);
                wrapperPlayersHMap.get(player).resetNonSleptDays();
            }
        } else {
            setDrowsiness(player);
        }
    }
}

// LeaveBedEvent
//        Math.round(onBedPlayersCount--);
//        getServer().broadcastMessage(
//                ChatColor.GRAY + "[" + ChatColor.AQUA + "NoSleepDebuff" + ChatColor.GRAY + "]"
//                        + ChatColor.YELLOW + " Sleeping to skip night " + ChatColor.GRAY +
//                        "[" + ChatColor.RED + onBedPlayersCount + ChatColor.GRAY + "/" + ChatColor.RED
//                        + Math.round((plugin.getServer().getOnlinePlayers().size() * 30) / 100) + ChatColor.GRAY + "]" + ChatColor.YELLOW + "!");

//    public static int getOnBedPlayersCount() {
//        return onBedPlayersCount;
//    }

//    @EventHandler
//    private void enteredBed(PlayerBedEnterEvent event) {
//        World world = getServer().getWorld("world");
//        assert world != null;
//        if (!Objects.equals(event.getBedEnterResult().toString(), "OK") &&
//                (world.getTime() >= 13000 && world.getTime() < 25000 || world.hasStorm() && world.isThundering())) {
//            onBedPlayersCount++;
//            getServer().broadcastMessage(
//                    ChatColor.GRAY + "[" + ChatColor.AQUA + "NoSleepDebuff" + ChatColor.GRAY + "]"
//                            + ChatColor.YELLOW + " Sleeping to skip night " + ChatColor.GRAY +
//                            "[" + ChatColor.RED + onBedPlayersCount + ChatColor.GRAY + "/" + ChatColor.RED
//                            + Math.round((plugin.getServer().getOnlinePlayers().size() * 30) / 100) + ChatColor.GRAY + "]" + ChatColor.YELLOW + "!");
//            new SleepTicksTask(this).runTaskTimer(this, 0L, 20L);
//        }
//    }
