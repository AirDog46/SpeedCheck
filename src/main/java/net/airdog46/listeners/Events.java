package net.airdog46.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;

import net.airdog46.scoreboard.SpeedScoreboard;

public class Events implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        SpeedScoreboard spSc = new SpeedScoreboard(p);
        Scoreboard sb = spSc.initializeScoreboard();
        p.setScoreboard(sb);
    }
}
