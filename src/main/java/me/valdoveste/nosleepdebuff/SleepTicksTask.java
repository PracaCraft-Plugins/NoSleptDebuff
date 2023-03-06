//package me.valdoveste.nosleepdebuff;
//
//import java.util.Objects;
//
//import org.bukkit.scheduler.BukkitRunnable;
//
//public class SleepTicksTask extends BukkitRunnable {
//    NoSleepDebuff plugin;
//
//    public static int sleepTicks = 0;
//
//    public SleepTicksTask(NoSleepDebuff plugin) {
//        this.plugin = plugin;
//    }
//
//    @Override
//    public void run() {
//        if (sleepTicks != 5 && NoSleepDebuff.getOnBedPlayersCount() >= Math.round((plugin.getServer().getOnlinePlayers().size() * 30) / 100)) {
//            sleepTicks++;
//        } else if (sleepTicks == 5) {
//            this.cancel();
//            sleepTicks = 0;
//            if (Objects.requireNonNull(plugin.getServer().getWorld("world")).isThundering() && Objects.requireNonNull(plugin.getServer().getWorld("world")).hasStorm())
//                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "weather clear");
//            Objects.requireNonNull(plugin.getServer().getWorld("world")).setTime(0);
//        } else {
//            this.cancel();
//            sleepTicks = 0;
//        }
//    }
//
//}