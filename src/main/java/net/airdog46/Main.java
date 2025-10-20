package net.airdog46;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.airdog46.commands.SpeedCommands;
import net.airdog46.commands.ToggleCommands;
import net.airdog46.listeners.Events;
import net.airdog46.scoreboard.SpeedScoreboard;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Events(), this);
        Bukkit.getPluginCommand("togglescoreboard").setExecutor(new ToggleCommands());
        Bukkit.getPluginCommand("toggledebug").setExecutor(new ToggleCommands());
        Bukkit.getPluginCommand("getcurrentspeed").setExecutor(new SpeedCommands());
        Bukkit.getPluginCommand("setcurrentspeed").setExecutor(new SpeedCommands());
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (!SpeedScoreboard.hasScoreboardDisabled(online))
                        SpeedScoreboard.update(online);
                }
            }
        };
        runnable.runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {
        
    }
}