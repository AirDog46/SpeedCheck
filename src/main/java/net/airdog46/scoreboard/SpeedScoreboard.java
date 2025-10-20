package net.airdog46.scoreboard;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class SpeedScoreboard {
    Player p;
    private static boolean debugging = false; 
    private static ArrayList<Player> disabledScoreboards = new ArrayList<>();

    public SpeedScoreboard(Player p1) {
        p = p1;
    }

    ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

    public Scoreboard initializeScoreboard() {
        //Bukkit.getLogger().log(Level.INFO,"Hi " + p.getName());
        Scoreboard sb = scoreboardManager.getNewScoreboard();
        @SuppressWarnings("deprecation")
        Objective speed = sb.registerNewObjective("Speeds", "Speeds (*1k)");
        speed.setDisplaySlot(DisplaySlot.SIDEBAR);
        return sb;
    }

    public static void update(Player player) {
        Objective speed = player.getScoreboard().getObjective("Speeds");
        speed.getScore("Flying speed").setScore( (int) (player.getFlySpeed()*1000));
        if (isDebuggingEnabled())
            Bukkit.getLogger().log(Level.INFO,"[DEBUG] Updating fly speed for player " + player.getName() + " (Value: " + player.getFlySpeed() + ")");
        speed.getScore("Walking speed").setScore(  (int) (player.getWalkSpeed()*1000));
        if (isDebuggingEnabled())
            Bukkit.getLogger().log(Level.INFO,"[DEBUG] Updating walk speed for player " + player.getName() + " (Value: " + player.getWalkSpeed() + ")");
    }

    public static boolean isDebuggingEnabled() {
        return debugging;
    }

    public static void enableDebugging() {
        debugging = true;
    }

    public static void disableDebugging() {
        debugging = false;
    }

    public static void disableScoreboard(Player p) {
        disabledScoreboards.add(p);
        p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    public static void enableScoreboard(Player p)
    {
        disabledScoreboards.remove(p);
        SpeedScoreboard spSc = new SpeedScoreboard(p);
        Scoreboard sb = spSc.initializeScoreboard();
        p.setScoreboard(sb);
    }

    public static boolean hasScoreboardDisabled(Player p) {
        return disabledScoreboards.contains(p);
    }
}
