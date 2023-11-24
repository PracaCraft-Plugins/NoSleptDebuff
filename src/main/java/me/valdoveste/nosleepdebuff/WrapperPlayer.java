package me.valdoveste.nosleepdebuff;

import org.bukkit.entity.Player;

public class WrapperPlayer {
    private final Player player;

    private int nonSleptDays = 0;

    private int nonSleptDebuffDuration = getDefaultEffectDebuffDuration();

    public WrapperPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    //    NonSleptDebuff methods
    public boolean isDebuffDurationAccumulative() {
        return NoSleepDebuff.plugin.getConfig().getBoolean("PerDay_debuff_nonSleep.Effects.effect_duration_isAccumulative");
    }

    public int getDefaultEffectDebuffDuration() {
        return NoSleepDebuff.plugin.getConfig().getInt("PerDay_debuff_nonSleep.Effects.effect_duration");
    }

    public int getNonSleptDebuffDuration() {
        return nonSleptDebuffDuration;
    }

    public void addNonSleptDebuffDuration() {
        this.nonSleptDebuffDuration += getDefaultEffectDebuffDuration();
    }
    public void setNonSleptDebuffDuration(int newSleptDebuffDuration) {
        this.nonSleptDebuffDuration = newSleptDebuffDuration;
    }

    //    NonSleptDays methods
    public int getNonSleptDays() {
        return nonSleptDays;
    }

    public void setNonSleptDays(int nonSleptDays) {
        this.nonSleptDays = nonSleptDays;
    }

    public void addNonSleptDays() {
        this.nonSleptDays++;
    }

    public void resetNonSleptDays() {
        nonSleptDays = 0;
    }
}
