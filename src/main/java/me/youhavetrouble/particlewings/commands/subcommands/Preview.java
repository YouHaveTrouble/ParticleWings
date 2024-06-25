package me.youhavetrouble.particlewings.commands.subcommands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import me.youhavetrouble.particlewings.WingedPlayer;
import tigeax.customwings.util.commands.SubCommand;
import me.youhavetrouble.particlewings.wing.Wing;

public class Preview extends SubCommand {

    public Preview(String name, String permission) {
        super(name);
		setPermission(permission);
    }

    @Override
    public void onCommandHasPermAndIsPlayer(Player player, ArrayList<String> args) {

		WingedPlayer wingedPlayer = plugin.getCWPlayer(player);

		Wing wing = wingedPlayer.getEquippedWing();

		// Send an error if the player does not have a wing equipped
		if (wing == null) {
			wingedPlayer.sendMessage(plugin.getMessages().noWingToPreviewError());
			return;
		}

		// Toggle the wing previewing
		wingedPlayer.setPreviewingWing(!wingedPlayer.isPreviewingWing());
	}
    
}
