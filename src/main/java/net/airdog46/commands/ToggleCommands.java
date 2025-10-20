package net.airdog46.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.airdog46.scoreboard.SpeedScoreboard;
import net.md_5.bungee.api.ChatColor;

public class ToggleCommands implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (label) {
            case "togglescoreboard":
            case "togglesb":
            case "sbtoggle":
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
                    break;
                }
                if (SpeedScoreboard.hasScoreboardDisabled((Player) sender))
                    SpeedScoreboard.enableScoreboard((Player) sender);
                else
                    SpeedScoreboard.disableScoreboard((Player) sender);
                sender.sendMessage("Your scoreboard has been " +
                    (SpeedScoreboard.hasScoreboardDisabled((Player) sender) ?
                    ChatColor.RED + "disabled" :
                    ChatColor.GREEN + "enabled") + 
                    ChatColor.RESET + ".");
                break;
            case "toggledebug":
            case "toggledbg":
            case "togglesbdbg":
            case "togglespeeddbg":
                if (SpeedScoreboard.isDebuggingEnabled())
                    SpeedScoreboard.disableDebugging();
                else
                    SpeedScoreboard.enableDebugging();
                sender.sendMessage("Speed Scoreboard debug has been " +
                    (SpeedScoreboard.isDebuggingEnabled() ? 
                    ChatColor.GREEN + "enabled" : 
                    ChatColor.RED + "disabled") + 
                    ChatColor.RESET + ".");
                break;
            default:
                break;
        }
        return true;
    }
}