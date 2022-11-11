//package me.valdoveste.nosleepdebuff.commands;
//
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//
//public class TogglePlugin implements CommandExecutor {
//    public static boolean pluginStatus = true;
//
//    @Override
//    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        if (!(sender instanceof Player) || !(sender.isOp())) {
//            sender.sendMessage("You don't hava the permission to use this command.");
//            return true;
//        }
//
//        if (command.getName().equalsIgnoreCase("sd on")) {
//            pluginStatus = true;
//            return true;
//        }
//
//        if (command.getName().equalsIgnoreCase("sd off")) {
//            pluginStatus = false;
//            return false;
//        }
//
//        return true;
//    }
//}
