package net.airdog46.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.airdog46.enums.SpeedTypes;
import net.md_5.bungee.api.ChatColor;

public class SpeedCommands implements CommandExecutor {
    /* Variables */
    private String gcsUsage = ChatColor.RED + "Usage: /getcurrentspeed (fly|walk) (player)";
    private String scsUsage = ChatColor.RED + "Usage: /setcurrentspeed (fly|walk) (value) (player)";
    private String notFound = ChatColor.RED + "Player not found.";
    private Player player;
    private float speedValue;
    private SpeedTypes type;

    /* Main command logic
     * Handles which command is being processed
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (label) {
            case "getcurrentspeed":
            case "getspeed":
            case "gcs":
                switch (args.length) {
                    case 0:
                    case 1:
                        if (sender instanceof Player) {
                            player = (Player) sender;
                            if (args.length < 1) {
                                type = (player.isFlying() ? SpeedTypes.FLY : SpeedTypes.WALK);
                                sender.sendMessage(getSpeed(player, type));
                                break;
                            }
                            type = checkArg0(args[0]);
                            if (type != null) {
                                sender.sendMessage(getSpeed(player, type));
                                break;
                            }
                        }
                    default:
                        sender.sendMessage(gcsUsage);
                        break;
                    case 2:
                        player = Bukkit.getPlayer(args[1]);
                        if (player == null) {
                            sender.sendMessage(notFound);
                            break;
                        }
                        type = checkArg0(args[0]);
                        if (type != null) {
                            sender.sendMessage(getSpeed(player, sender, type));
                            break;
                        }
                        sender.sendMessage(gcsUsage);
                        break;
                }
                break;
            case "setcurrentspeed":
            case "setspeed":
            case "scs":
                switch (args.length) {
                    case 1:
                    case 2:
                        if (sender instanceof Player) {
                            player = (Player) sender;
                            if (args.length < 2) {
                                if (checkArg0(args[0]) == null) {
                                    try {
                                        speedValue = Float.parseFloat(args[0]);
                                    } catch (NumberFormatException e) {
                                        sender.sendMessage(scsUsage);
                                        break;
                                    }
                                    speedValue = validateValue(speedValue);
                                    type = (player.isFlying() ? SpeedTypes.FLY : SpeedTypes.WALK);
                                    sender.sendMessage(setSpeed(player, speedValue, type));
                                    break;
                                }
                            }
                            try {
                                speedValue = Float.parseFloat(args[1]);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(scsUsage);
                                break;
                            }
                            speedValue = validateValue(speedValue);
                            type = checkArg0(args[0]);
                            sender.sendMessage(setSpeed(player, speedValue, type));
                            break;
                        }
                    default:
                        sender.sendMessage(scsUsage);
                        break;
                    case 3:
                        player = Bukkit.getPlayer(args[2]);
                        try {
                            speedValue = Float.parseFloat(args[1]);
                        } catch (NumberFormatException e) {
                            sender.sendMessage(scsUsage);
                            break;
                        }
                        speedValue = validateValue(speedValue);
                        type = checkArg0(args[0]);
                        sender.sendMessage(setSpeed(player, sender, speedValue, type));
                }
        }
        return true;
    }

    /* Logic for both commands */
    private SpeedTypes checkArg0(String arg) {
        if (arg.equalsIgnoreCase("walk"))
            return SpeedTypes.WALK;
        if (arg.equalsIgnoreCase("fly"))
            return SpeedTypes.FLY;
        else
            return null;
    }

    private boolean checkSame(Player p, CommandSender sender) {
        if (sender instanceof Player)
            if ((Player)sender == p)
                return true;
        return false;
    }

    /* Logic for getspeed command */
    private String getSpeed(Player p, CommandSender sender, SpeedTypes type) {
        return ((checkSame(p, sender) ? "Your " : p.getName() + ChatColor.RESET + "'s ")) + 
        ((type == SpeedTypes.FLY) ? "flying" : "walking") + " speed is " + 
        ((type == SpeedTypes.FLY) ? p.getFlySpeed() : p.getWalkSpeed()) + ".";
    }

    private String getSpeed(Player p, SpeedTypes type) {
        return getSpeed(p, p, type);
    }

    /* Logic for setspeed command */
    private float validateValue(float value) {
        if (value > 1)
            return 1;
        if (value < 0)
            return 0;
        return value;
    }

    private String setSpeed(Player p, CommandSender sender, float value, SpeedTypes type) {
        switch (type) {
            case SpeedTypes.FLY:
                p.setFlySpeed(value);
                break;
            case SpeedTypes.WALK:
                p.setWalkSpeed(value);
                break;
            default:
                break;
        }
        return "Set " 
          + ((checkSame(p, sender)) ? "your" : p.getName() + ChatColor.RESET + "'s") + " "
          + ((type == SpeedTypes.FLY) ? "flying" : "walking") 
          + " speed to " + value + ".";
    }

    private String setSpeed(Player p, float value, SpeedTypes type) {
        return setSpeed(p, p, value, type);
    }
}
