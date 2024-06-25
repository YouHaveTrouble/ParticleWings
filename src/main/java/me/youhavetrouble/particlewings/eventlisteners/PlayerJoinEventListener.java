package me.youhavetrouble.particlewings.eventlisteners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.youhavetrouble.particlewings.WingedPlayer;
import me.youhavetrouble.particlewings.ParticleWings;
import me.youhavetrouble.particlewings.wing.Wing;

/*
 * This EventListener Listends for when a player joins
 * If the player had a wing equipped before the player wil be added back to the playersWithWingActive list of the wing
 */

public class PlayerJoinEventListener implements Listener {

	private final ParticleWings plugin;

	public PlayerJoinEventListener() {
		plugin = ParticleWings.getInstance();
	}

	@EventHandler
	public void event(PlayerJoinEvent event) {

		Player player = event.getPlayer();
		WingedPlayer wingedPlayer = plugin.getCWPlayer(player);

		String wingId = plugin.getDatabase().getPlayerEquippedWingID(player);
        boolean hideOtherPlayerWings = plugin.getDatabase().getPlayerHideOtherPlayerWings(player);
        boolean showWing = plugin.getDatabase().getPlayerShowWing(player);

		if (wingId != null) {
			Wing wing = plugin.getWingByID(wingId);
			if (wing != null)
				wingedPlayer.setEquippedWing(wing);
		}

        wingedPlayer.setHideOtherPlayerWings(hideOtherPlayerWings);
        wingedPlayer.setShowWing(showWing);
	}

}
