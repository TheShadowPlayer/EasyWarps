package de.xshadowplayerx.easywarps.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xshadowplayerx.easywarps.main;
import de.xshadowplayerx.easywarps.object.WarpPoint;

public class CMDwarp implements CommandExecutor {
	private main plugin;

	public CMDwarp(main instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender cmds, Command cmd, String label, String[] args) {
		String prefix = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("Prefix"));
		if (!(cmds instanceof Player))
			return false;
		Player p = (Player) cmds;
		if (args.length == 0) {
			p.sendMessage(String.valueOf(prefix) + " §cFolgende Warp-Punkte stehen zur verfügung: "
					+ WarpPoint.WarpPoints.keySet());
		} else if (WarpPoint.WarpPoints.containsKey(args[0])) {
			p.teleport(WarpPoint.WarpPoints.get(args[0]).getLocation());
		} else {
			p.sendMessage(String.valueOf(prefix) + " §cDer Warppunkt ist uns Unbekannt.");

		}
		return true;
	}
}
