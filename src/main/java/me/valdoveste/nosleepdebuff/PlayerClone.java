package me.valdoveste.nosleepdebuff;

import org.bukkit.entity.Player;

public class PlayerClone {
    private final Player player;
    private int nonSleptDays = 0;

    public PlayerClone(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setNonSleptDays(int nonSleptDays) { this.nonSleptDays = nonSleptDays; }

    public int getNonSleptDays() { return nonSleptDays; }

    public void addNonSleptDays() { this.nonSleptDays += 60; }

    public void resetNonSleptDays() {
        nonSleptDays = 0;
    }
}
