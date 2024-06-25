package me.youhavetrouble.particlewings.database;

import org.bukkit.entity.Player;
import me.youhavetrouble.particlewings.wing.Wing;

public interface Database {

    void savePlayerEquippedWing(Player player, Wing wing);
    String getPlayerEquippedWingID(Player player);
    void savePlayerHideOtherPlayerWings(Player player, Boolean hideOtherPlayerWings);
    boolean getPlayerHideOtherPlayerWings(Player player);
    void savePlayerShowWing(Player player, Boolean show);
    boolean getPlayerShowWing(Player player);

}
