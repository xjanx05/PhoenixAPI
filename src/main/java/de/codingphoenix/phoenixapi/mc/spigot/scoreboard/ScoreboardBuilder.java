package de.codingphoenix.phoenixapi.mc.spigot.scoreboard;

import de.codingphoenix.phoenixapi.mc.spigot.VersionHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public abstract class ScoreboardBuilder {
    protected Scoreboard scoreboard;
    protected Objective objective;
    protected Player player;

    public ScoreboardBuilder(final Player player, final String displayName) {
        this.player = player;
        if (player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
        this.scoreboard = player.getScoreboard();
        if (this.scoreboard.getObjective("display") != null) {
            this.scoreboard.getObjective("display").unregister();
        }
        (this.objective = this.scoreboard.registerNewObjective("display", "dummy")).setDisplayName(displayName);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.createScoreboard();
    }

    public void resetScores() {
        for (int i = 15; i >= 0; --i) {
            this.setScore("", "", i);
            this.removeScore(i);
        }
    }

    public abstract void createScoreboard();

    public void setScore(String content, final int score) {
        final Team team = this.getTeamByScore(score);
        if (team == null) {
            return;
        }
        if (content.getBytes().length > 16) {
            content = content.substring(0, content.getBytes().length - 16);
        }
        team.setPrefix(content);
        this.showScore(score);
    }

    public void setScore(String prefix, String suffix, final int score) {
        final Team team = this.getTeamByScore(score);
        if (team == null) {
            return;
        }
        if (!VersionHelper.getServerVersion().equals(VersionHelper.Version.UPPER17)) {
            if (prefix.getBytes().length > 16) {
                prefix = prefix.substring(0, prefix.getBytes().length - 16);
            }
            if (suffix.getBytes().length > 16) {
                suffix = suffix.substring(0, suffix.getBytes().length - 16);
            }
        }
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        this.showScore(score);
    }

    public void removeScore(final int score) {
        this.hideScore(score);
    }

    private EntryName getEntryNameByScore(final int score) {
        for (final EntryName name : EntryName.values()) {
            if (score == name.getEntry()) {
                return name;
            }
        }
        return null;
    }

    private Team getTeamByScore(final int score) {
        final EntryName name = this.getEntryNameByScore(score);
        if (name == null) {
            return null;
        }
        Team team = this.scoreboard.getEntryTeam(name.getEntryName());
        if (team != null) {
            return team;
        }
        team = this.scoreboard.registerNewTeam(name.name());
        team.addEntry(name.getEntryName());
        return team;
    }

    private void showScore(final int score) {
        final EntryName name = this.getEntryNameByScore(score);
        if (name == null) {
            return;
        }
        if (this.objective.getScore(name.getEntryName()).isScoreSet()) {
            return;
        }
        this.objective.getScore(name.getEntryName()).setScore(score);
    }

    private void hideScore(final int score) {
        final EntryName name = this.getEntryNameByScore(score);
        if (name == null) {
            return;
        }
        if (!this.objective.getScore(name.getEntryName()).isScoreSet()) {
            return;
        }
        this.scoreboard.resetScores(name.getEntryName());
    }

    public Player getPlayer() {
        return player;
    }
}
