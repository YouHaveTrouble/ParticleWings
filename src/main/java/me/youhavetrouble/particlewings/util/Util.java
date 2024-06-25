package tigeax.customwings.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import me.youhavetrouble.particlewings.ParticleWings;
import tigeax.customwings.util.commands.SubCommand;

public class Util {

    public static void runUpdateChecker(JavaPlugin plugin, int spigotResourceId) {
        new UpdateChecker(plugin, spigotResourceId).getVersion(version -> {
			if (plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                plugin.getLogger().info("You are running the latest version of this plugin");
			} else {
                plugin.getLogger().info("There is a new version of this plugin avaiable on Spigot: https://www.spigotmc.org/resources/" + spigotResourceId +  "/");
			}
		});
    }

    /**
     * Translates a string using the '&' color code character into a string that
     * uses the {@link ChatColor} color code character.
     * 
     * @param string String to translate
     * @return The parsed String
     */
    public static String parseChatColors(String string) {
        if (string == null) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Send a message to the {@link CommandSender} only if the msg is not empty. This is to prevent
     * sending blank lines.
     * 
     * @param sender Sender to send message to.
     * @param msg    Message to send.
     */
    public static void sendMessage(CommandSender sender, String msg) {

        if (msg.isEmpty()) {
            return;
        }

        sender.sendMessage(msg);
    }

    /**
     * Register a {@link Command} under a name. After plugin startup using reflection
     * 
     * @param name     Name of the command.
     * @param executor The {@link Command} to register.
     */
    public static void registerCommand(String name, Command executor) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register("seen", executor);

        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            ParticleWings.getInstance().getLogger().severe("Failed to register command: " + name);
            e.printStackTrace();
        }
    }

    /**
     * Get a {@link SubCommand} from a list of SubCommands based on its name or alias name.
     * @param name Name or alias of the sub command
     * @param subCommands List of sub commands
     * @return The {@link SubCommand}
     */
    public static SubCommand getSubCommand(String name, ArrayList<SubCommand> subCommands) {

        Iterator<SubCommand> subCommandsIterator = subCommands.iterator();

        while (subCommandsIterator.hasNext()) {
            SubCommand subCommand = (SubCommand) subCommandsIterator.next();

            if (subCommand.name().equalsIgnoreCase(name)) {
                return subCommand;
            }

            for (String alias : subCommand.getAliases()) {
                if (name.equalsIgnoreCase(alias)) {
                    return subCommand;
                }
            }
        }

        return null;
    }


    public static List<String> parseLoreChatColor(List<String> loreList) {

        String loreString = loreList.toString();

        loreString = parseChatColors(loreString).replace("]", "").replace("[", "");
        String[] loreArray = loreString.split(", ");
        loreList = Arrays.asList(loreArray);
        
		return loreList;
	}

    /**
     * Check if a player is vanished
     * @param player
     * @return
     */
	public static boolean isPlayerVanished(Player player) {
		for (MetadataValue meta : player.getMetadata("vanished")) {
			if (meta.asBoolean())
				return true;
		}
		return false;
	}

}
