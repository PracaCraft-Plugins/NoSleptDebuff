package me.valdoveste.nosleepdebuff.commands;

import me.valdoveste.nosleepdebuff.NoSleepDebuff;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class reloadPluginCommand implements CommandExecutor {

    NoSleepDebuff plugin;

    public reloadPluginCommand(NoSleepDebuff plugin) { this.plugin = plugin; }

    private static void reloadDefaultConfig() {
        NoSleepDebuff.plugin.reloadConfig();

        NoSleepDebuff.plugin.saveDefaultConfig();
        NoSleepDebuff.plugin.getConfig().options().copyDefaults();
        NoSleepDebuff.plugin.saveDefaultConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("nosleepreload")) {
            if (sender instanceof Player && sender.isOp()) {
                Player playerSender = (Player) sender;
                if (args.length > 0) {
                    playerSender.sendMessage(ChatColor.RED + "Este comando não aceita argumentos/jogadores.");
                    return false;
                }

                reloadDefaultConfig();
                playerSender.sendMessage(ChatColor.YELLOW + "NoSleepDebuff e sua config.yml foram reiniciados.");
            }
        }
        return true;
    }
}
