package me.youhavetrouble.particlewings;

import java.io.File;
import java.util.*;

import me.youhavetrouble.particlewings.wing.Wing;
import me.youhavetrouble.particlewings.wing.WingParticle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import me.youhavetrouble.particlewings.configuration.Config;
import me.youhavetrouble.particlewings.configuration.Messages;
import me.youhavetrouble.particlewings.configuration.WingConfig;
import me.youhavetrouble.particlewings.database.Database;
import me.youhavetrouble.particlewings.database.YamlDatabase;
import me.youhavetrouble.particlewings.eventlisteners.PlayerJoinEventListener;
import me.youhavetrouble.particlewings.eventlisteners.PlayerMoveListener;
import me.youhavetrouble.particlewings.eventlisteners.PlayerQuitEventListener;
import tigeax.customwings.util.commands.Command;


public class ParticleWings extends JavaPlugin {

	private static ParticleWings instance;

	private Messages messages;
	private Config config;
	private Database database;


	private static HashMap<UUID, WingedPlayer> cwPlayerList;
	private static HashMap<String, Wing> wings;


	private static Economy econ = null;
	private static Permission perms = null;

	private static boolean vault = false;

	private static final int spigotResourceId = 59912;

	private final ArrayList<Command> commands = new ArrayList<>();

	@Override
	public void onEnable() {

		setInstance(this);

        // Setup Configuration
        config = new Config(this);
        messages = new Messages(this);


		// Setup database
		database = new YamlDatabase(this);

		cwPlayerList = new HashMap<>();
		wings = new HashMap<>();

		// Set up the wings
		setupWings();

		// Setup command


		// Register events
		Bukkit.getPluginManager().registerEvents(new PlayerJoinEventListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitEventListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);

		if (getServer().getPluginManager().getPlugin("Vault") != null) {
			try {
				setupEconomy();
				setupPermissions();
				vault = true;
				getLogger().info("Vault detected. Buy functionality enabled.");
			} catch (Exception e) {
				e.printStackTrace();
				getLogger().info("Failed to load vault. Buy functionality disabled.");
			}
		} else {
			getLogger().info("Vault not detected. Buy functionality disabled.");
		}

		// Load equipped wings and settings from database
		for (Player player : Bukkit.getOnlinePlayers()) {

			WingedPlayer wingedPlayer = getCWPlayer(player);

			String wingId = database.getPlayerEquippedWingID(player);
			boolean hideOtherPlayerWings = database.getPlayerHideOtherPlayerWings(player);
			boolean showWing = database.getPlayerShowWing(player);

			if (wingId != null) {
				Wing wing = getWingByID(wingId);
				if (wing != null) {
					wingedPlayer.setEquippedWing(wing);
				}
			}

			wingedPlayer.setHideOtherPlayerWings(hideOtherPlayerWings);
            wingedPlayer.setShowWing(showWing);
		}

		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "{CustomWings} CustomWings has been enabled");
	}

	@Override
	public void onDisable() {

		// Force save yaml database
		if (database instanceof YamlDatabase) ((YamlDatabase) database).save();

		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "{CustomWings} CustomWings has been disabled");
	}

	private static void setInstance(ParticleWings instance) {
		ParticleWings.instance = instance;
	}

	public static ParticleWings getInstance() {
		return instance;
	}

	private void registerCommand(Command commandObj) {
		commands.add(commandObj);
	}


	public Config getPluginConfig() {
		return config;
	}

	public Messages getMessages() {
		return messages;
	}

	public Database getDatabase() {
		return database;
	}

	public HashMap<String,Wing> getWings() {
		return wings;
	}

	public boolean isVaultEnabled() {
		return vault;
	}

	public static Economy getEconomy() {
		return econ;
	}

	public Permission getPermissions() {
		return perms;
	}

	public void reload() {

		config.update();
		messages.update();

		setupWings();

		for (Wing wing : getWings().values()) {
			wing.reload();
			for (WingParticle wingParticle : wing.getConfig().getWingParticles()) {
				wingParticle.reload();
			}
		}

	}

	private void setupWings() {

		File wingsFolder = new File(getDataFolder(), "wings");

		// Check if the wings folder exits, if not create it with the default wings
		if (!wingsFolder.exists()) {

			getLogger().info("Could not find wings folder. Creating it, with default wings");
			wingsFolder.mkdirs(); // Create the folder

			// Save the wing files
			saveResource("wings/_example.yml", false);
			saveResource("wings/angel.yml", false);
			saveResource("wings/bloodhound.yml", false);
			saveResource("wings/frost.yml", false);
			saveResource("wings/soulshadow.yml", false);
			saveResource("wings/wisdom.yml", false);
		}

		File[] wingFiles = wingsFolder.listFiles();

		for (File file : wingFiles) {

			String wingId = file.getName().replace(".yml", "").toLowerCase();

			Wing wing = getWingByID(wingId);

			if (wing == null) {
				// Add the new Wing
				WingConfig wingConfig = new WingConfig(this, file);
				Wing newWing = new Wing(this, wingConfig);
				wings.put(wingId, newWing);
			} else {
				// Reload the wing
				wing.reload();
			}
		}
	}

	public Command getPluginCommand(String name) {
		for (Command command : commands) {
			if (command.getName().equalsIgnoreCase(name)) return command;
			for (String alias : command.getAliases()) {
				if (name.equalsIgnoreCase(alias)) return command;
			}
		}
		return null;
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		econ = rsp.getProvider();
		return econ != null;
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}

	public Wing getWingByID(String id) {
		return getWings().get(id);
	}

	public WingedPlayer getCWPlayer(Player player) {

		UUID uuid = player.getUniqueId();
		WingedPlayer wingedPlayer = cwPlayerList.get(uuid);

		// Check if there already is a CWPlayer instance for player
		if (wingedPlayer != null) return wingedPlayer;

		// If not create it
		wingedPlayer = new WingedPlayer(player);
		cwPlayerList.put(uuid, wingedPlayer);
		return wingedPlayer;
	}

	public void deleteCWPlayer(WingedPlayer wingedPlayer) {

		Wing wing = wingedPlayer.getEquippedWing();
		if (wing != null) wing.removePlayer(wingedPlayer);

		cwPlayerList.remove(wingedPlayer.getPlayer().getUniqueId());
	}

}
