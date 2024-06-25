package me.youhavetrouble.particlewings.eventlisteners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.youhavetrouble.particlewings.WingedPlayer;
import me.youhavetrouble.particlewings.ParticleWings;

/*
 * This EventListener Listends for when a player leaves the game
 */

public class PlayerQuitEventListener implements Listener {

	ParticleWings plugin;

	public PlayerQuitEventListener() {
		plugin = ParticleWings.getInstance();
	}

	@EventHandler
	public void PlayerQuitEvent(PlayerQuitEvent event) {

		Player player = event.getPlayer();
		WingedPlayer wingedPlayer = plugin.getCWPlayer(player);

		plugin.deleteCWPlayer(wingedPlayer);
	}
	
}
