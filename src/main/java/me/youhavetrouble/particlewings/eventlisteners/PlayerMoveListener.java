package me.youhavetrouble.particlewings.eventlisteners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import me.youhavetrouble.particlewings.WingedPlayer;
import me.youhavetrouble.particlewings.ParticleWings;
import org.bukkit.event.player.PlayerMoveEvent;
import java.time.Instant;

public class PlayerMoveListener implements Listener {

    ParticleWings plugin;

	public PlayerMoveListener() {
		plugin = ParticleWings.getInstance();
	}

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onMoveEvent(PlayerMoveEvent event) {

        Bukkit.getRegionScheduler().run(ParticleWings.getPlugin(ParticleWings.class), event.getTo(), (task) -> {

            Player player = event.getPlayer();
            WingedPlayer wingedPlayer = plugin.getCWPlayer(player);

            if (wingedPlayer.getEquippedWing() == null) {
                return;
            }

            long now = Instant.now().getEpochSecond();

            if (event.getFrom().distance(event.getTo()) > 0.2) {
                plugin.getCWPlayer(player).setLastTimeMoving(now);
            }
        });


    }

}
