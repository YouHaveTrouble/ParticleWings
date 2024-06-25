package me.youhavetrouble.particlewings.commands.subcommands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.youhavetrouble.particlewings.WingedPlayer;
import tigeax.customwings.util.Util;
import tigeax.customwings.util.commands.SubCommand;
import me.youhavetrouble.particlewings.wing.Wing;

public class SetWing extends SubCommand {

    public SetWing(String name, String permission) {
        super(name);
		setPermission(permission);
    }

    @Override
    public void onCommandHasPerm(CommandSender sender, ArrayList<String> args) {

		// If no player is specified send an error
		if (args.size() == 0) {
			Util.sendMessage(sender, plugin.getMessages().missingArgumentsSetwingError());
			return;
		}

        String playerName = args.get(0);
		Player wingSetPlayer = Bukkit.getPlayer(playerName);

		// Send an error if the supplied playername doesn't exist
		if (wingSetPlayer == null) {
			Util.sendMessage(sender, plugin.getMessages().invalidPlayerError(playerName));
			return;
		}

		WingedPlayer wingedPlayer = plugin.getCWPlayer(wingSetPlayer);

		// If no wing was specified set the wing to null
		if (args.size() == 1) {
			wingedPlayer.setEquippedWing(null);
			Util.sendMessage(sender, plugin.getMessages().setWingCommandSucces(wingSetPlayer, "none"));
			return;
		}

		// If an invalid wing was specified set the wing to null
		Wing wing = plugin.getWingByID(args.get(1));

		if (wing == null) {
			wingedPlayer.setEquippedWing(null);
			Util.sendMessage(sender, plugin.getMessages().setWingCommandSucces(wingSetPlayer, "none"));
			return;
		}

		// If the wing was valid let the player equip the wing
		wingedPlayer.setEquippedWing(wing);
		Util.sendMessage(sender, plugin.getMessages().setWingCommandSucces(wingSetPlayer, wing.getConfig().getID()));
	}
    
}
