package me.youhavetrouble.particlewings.wing;

import java.util.ArrayList;

import me.youhavetrouble.particlewings.WingedPlayer;
import me.youhavetrouble.particlewings.ParticleWings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import me.youhavetrouble.particlewings.configuration.Config;
import me.youhavetrouble.particlewings.configuration.WingConfig;
import tigeax.customwings.util.Util;

public class Wing {

	private final ParticleWings plugin;
	private final Config config;
	private final WingConfig wingConfig;

	private ArrayList<WingedPlayer> playersWithWing;
	private BukkitTask wingRunnable;

	public Wing(ParticleWings plugin, WingConfig wingConfig) {
		this.wingConfig = wingConfig;
		this.plugin = plugin;
		this.config = plugin.getPluginConfig();
		this.playersWithWing = new ArrayList<>();
	}

	public WingConfig getConfig() {
		return wingConfig;
	}

	public enum WingSide {
		LEFT, RIGHT
	}

	public void reload() {
		if (wingRunnable != null)
			wingRunnable.cancel(); // Temperatry stop the runnable

		wingConfig.reload();

		for (WingParticle wingParticle : getConfig().getWingParticles()) {
			wingParticle.reload();
		}

		if (wingRunnable != null)
			startWingRunnable(); // Restart the runnable again with the (possibly new) settings
	}

	public void removeAllPlayersWithWingActive() {
		playersWithWing = new ArrayList<>();
	}

	public boolean doesPlayerHaveWingEquipped(WingedPlayer wingedPlayer) {
		for (WingedPlayer wingedPlayerFromList : playersWithWing) {
			if (wingedPlayerFromList == wingedPlayer) {
				return true;
			}
		}
		return false;
	}

	public String getPermission() {
		return "customwings.wing." + wingConfig.getID();
	}

	public ArrayList<WingedPlayer> getPlayersWithWingActive() {
		return playersWithWing;
	}

	public void addPlayer(WingedPlayer wingedPlayer) {

		playersWithWing.add(wingedPlayer);

		// If there were no players that had the wing equipped before, start the wing
		// runnable
		if (playersWithWing.size() == 1)
			this.startWingRunnable();
	}

	public void removePlayer(WingedPlayer wingedPlayer) {
		playersWithWing.remove(wingedPlayer);
	}

	private void startWingRunnable() {

		wingRunnable = new BukkitRunnable() {

			boolean flapDirectionSwitch = false;
			int animationState = wingConfig.getStartOffset();

			@Override
			public void run() {

				// If no players have this wing equipped stop the timer
				if (getPlayersWithWingActive().isEmpty()) {
					this.cancel();
				}

				// If the wing should be animated change the offsetDegrees
				// To go back and forth between the start and stop offset
				if (wingConfig.getWingAnimation()) {

					animationState = flapDirectionSwitch ? animationState - wingConfig.getWingFlapSpeed()
							: animationState + wingConfig.getWingFlapSpeed();

					if (animationState >= wingConfig.getStopOffset())
						flapDirectionSwitch = true;

					if (animationState <= wingConfig.getStartOffset())
						flapDirectionSwitch = false;
				}

				// Loop through all the players that have the wing active, and spawn their wing
				getPlayersWithWingActive().forEach((wingOwner) -> showWing(wingOwner, animationState));

			}
		}.runTaskTimerAsynchronously(plugin, 0, wingConfig.getWingTimer());
	}

	private void showWing(WingedPlayer wingOwner, int animationState) {

        if (wingOwner.getShowWing()) {
            if (wingOwner.isPreviewingWing()) {
                Location wingLoc = wingOwner.getPreviewWingLocation();
                spawnPreviewWing(wingLoc, animationState);
            } else {
                spawnAttachedWing(wingOwner, animationState);
            }
        }
	}

	private void spawnPreviewWing(Location wingLoc, int animationState) {

		// Check if the wing should be shown in this world
		if (!shouldWingBeShownInWorld(wingLoc.getWorld())) {
			return;
		}

		ArrayList<Player> playersToShowWing = getPlayersWhoSeePreviewWing(wingLoc);
		spawnWingForPlayers(wingLoc, playersToShowWing, animationState);
	}

	private void spawnAttachedWing(WingedPlayer cwWingOwner, int animationState) {

		Player wingOwner = cwWingOwner.getPlayer();
		Location wingLoc = wingOwner.getLocation();

		// Check if the wing should be shown in this world
		if (!shouldWingBeShownInWorld(wingLoc.getWorld())) {
			return;
		}

		// Don't show the wing if the wingOwner is in certain states
		if (!shouldWingBeShown(cwWingOwner)) {
			return;
		}

		// Instead of using the Yaw of the head of the player we will try to use the Yaw
		// of the player's body
		// TODO set yaw to body rotation
//		float bodyYaw = NMSSupport.getBodyRotation(wingOwner);
//		wingLoc.setYaw(bodyYaw);

		// Shift the wing down if the player is sneaking
		if (wingOwner.isSneaking() && !wingOwner.isFlying()) {
			wingLoc = wingLoc.add(0, -0.25, 0);
		}

		ArrayList<Player> playersToShowWing = getPlayersWhoSeeAttachedWing(cwWingOwner);

		spawnWingForPlayers(wingLoc, playersToShowWing, animationState);
	}

	/**
	 * Check if a wing should be displayed based on the player's state
	 * Most of the states returning false prevent general position desync between the wing and the player
	 */
	private boolean shouldWingBeShown(WingedPlayer cwWingOwner) {

		// Continue if the wing should hidde when moving and the player is moving
		if (!wingConfig.getShowWhenMoving() && cwWingOwner.isMoving()) {
			return false;
		}

		Player wingOwner = cwWingOwner.getPlayer();

		// Not if dead
		if (wingOwner.isDead())
			return false;

		// Not if in spectator or in vanish
		if (wingOwner.getGameMode().equals(GameMode.SPECTATOR) || Util.isPlayerVanished(wingOwner))
			return false;

		// Not when they have invisibility potion effect
		if (wingOwner.hasPotionEffect(PotionEffectType.INVISIBILITY) && config.getInvisibilityPotionHidesWing())
			return false;

		// Not when sleeping
		if (wingOwner.isSleeping())
			return false;

		// Not when in vehicle
		if (wingOwner.isInsideVehicle())
			return false;

		// Not when swimming
		if (wingOwner.isSwimming())
			return false;

		// Not when gliding gliding
		if (wingOwner.isGliding())
			return false;

		// Else the wing shoud be spawned
		return true;
	}

	
	/**
	 * Get the players that are able to see a previewed wing
	 * @param wingLoc Location the wing is spawned at
	 */
	private ArrayList<Player> getPlayersWhoSeePreviewWing(Location wingLoc) {

		ArrayList<Player> playersWhoCanSeeWing = new ArrayList<>();

		// Loop thought all the online players
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

			// Skip if the player is not in the same world as the wing
			if (!(onlinePlayer.getWorld() == wingLoc.getWorld()))
				continue;

			WingedPlayer wingedPlayer = ParticleWings.getInstance().getCWPlayer(onlinePlayer);

			// Skip if onlinePlayer doesn't want to see other players wings
			if (wingedPlayer.getHideOtherPlayerWings())
				continue;

			Location onlinePlayerLoc = onlinePlayer.getLocation();

			// Skip if the player is more then the wingViewDistance away from the wing
			if (onlinePlayerLoc.distance(wingLoc) > config.getWingViewDistance())
				continue;

			playersWhoCanSeeWing.add(onlinePlayer);
		}

		return playersWhoCanSeeWing;
	}


	/**
	 * Get the players that are able to see a wing attached to the wing owner
	 */
	private ArrayList<Player> getPlayersWhoSeeAttachedWing(WingedPlayer cwWingOwner) {

		Player wingOwner = cwWingOwner.getPlayer();

		Location wingOwnerLoc = wingOwner.getLocation();
		ArrayList<Player> playersWhoCanSeeWing = new ArrayList<>();

		// Only spawn for the wing owner if they don't look to far down.
		if (wingOwnerLoc.getPitch() < config.getWingMaxPitch()) {
			playersWhoCanSeeWing.add(wingOwner.getPlayer());
		}

		// Loop thought all the online players
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

			// Skip if it is the wingOwner
			if (onlinePlayer == wingOwner.getPlayer()) {
				continue;
			}

			// Skip if the player is not in the same world as the wing
			if (!(onlinePlayer.getWorld() == wingOwnerLoc.getWorld()))
				continue;

			WingedPlayer wingedPlayer = ParticleWings.getInstance().getCWPlayer(onlinePlayer);

			// Skip if onlinePlayer doesn't want to see other players wings
			if (wingedPlayer.getHideOtherPlayerWings())
				continue;

			Location onlinePlayerLoc = onlinePlayer.getLocation();

			// Skip if the player is more then the wingViewDistance away from the wing
			if (onlinePlayerLoc.distance(wingOwnerLoc) > config.getWingViewDistance())
				continue;

			playersWhoCanSeeWing.add(onlinePlayer);
		}

		return playersWhoCanSeeWing;

	}

	/**
	 * Spawn the wing for a list of players
	 * 
	 * @param wingLoc Location to spawn the wing
	 * @param players Players to spawn the wing for
	 */
	private void spawnWingForPlayers(Location wingLoc, ArrayList<Player> players, int animationState) {

		wingLoc = wingLoc.clone();

		// Change the horzontal starting location based on the horizontal start offset and direction of the wing
		double yawRad = Math.toRadians(wingLoc.getYaw());
		double xOffset = Math.cos(yawRad) * wingConfig.getStartHorizontalOffset();
		double zOffset = Math.sin(yawRad) * wingConfig.getStartHorizontalOffset();
		wingLoc = wingLoc.add(xOffset, 0, zOffset);

		// Loop through all the coordinates of the wing and spawn a particle for both
		// the left and right part at that location
		for (double[] coordinate : wingConfig.getParticleCoordinates().keySet()) {

			WingParticle wingParticle = wingConfig.getParticleCoordinates().get(coordinate);
			double x = coordinate[0];
			double y = coordinate[1];

			if (!wingConfig.getOnlyOneSide()) {
				// Spawn left side
				Location particleLocLeft = getParticleSpawnLoc(wingLoc, x, y, WingSide.LEFT, animationState);
				wingParticle.spawnParticle(particleLocLeft, players, WingSide.LEFT);
			}

			// Spawn right side
			Location particleLocRight = getParticleSpawnLoc(wingLoc, x, y, WingSide.RIGHT, animationState);
			wingParticle.spawnParticle(particleLocRight, players, WingSide.RIGHT);
		}
	}


	/**
	 * Return the location the particle should be spawned at
	 * Relative to the location the wing is spawned at
	 * Based on the x and y offset from the center of the wing and which side of the Wing the particle is on
	 */
	private Location getParticleSpawnLoc(Location loc, double x, double y, WingSide wingSide, int animationState) {

		Location wingParticleLoc = loc.clone();
		float yaw = wingParticleLoc.getYaw();

		if (wingSide == WingSide.LEFT)
			yaw = yaw - animationState;
		if (wingSide == WingSide.RIGHT)
			yaw = yaw + 180 + animationState;

		double yawRad = Math.toRadians(yaw);
		Vector vector = new Vector(Math.cos(yawRad) * x, y, Math.sin(yawRad) * x);
		wingParticleLoc.add(vector);
		wingParticleLoc.setYaw((float) Math.toDegrees(yawRad)); // For directional particles

		return wingParticleLoc;
	}

	/**
	 * Get if a wing should be shown in a world
	 * @param world The world the wing is shown in
	 */
	private boolean shouldWingBeShownInWorld(World world) {

		if (wingConfig.getWhitelistedWorlds().contains("all")) {
			return true;
		}

		if (wingConfig.getWhitelistedWorlds().contains(world.getName())) {
			return true;
		}

		return false;
	}

}
