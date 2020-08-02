package de.xshadowplayerx.easywarps.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xshadowplayerx.easywarps.main;
import de.xshadowplayerx.easywarps.object.WarpPoint;

public class CMDsetwarp implements CommandExecutor {

	private main plugin;

	public CMDsetwarp(main instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if (!(sender instanceof Player))
			return false;

		String prefix = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("Prefix"));
		Player p = (Player) sender;

		if (p.hasPermission("system.admin")) {
			if (args.length == 0) {
				p.sendMessage(String.valueOf(prefix) + " §cDu must ein Argument angeben §7| §4/setwarp <WARPPUNKT>");
			} else if (WarpPoint.WarpPoints.containsKey(args[0])) {
				p.sendMessage(String.valueOf(prefix) + " §cDer Warppunkt ist uns bereits Bekannt.");
			} else {
				WarpPoint point = new WarpPoint(args[0], p.getLocation());
				point.save();
				WarpPoint.WarpPoints.put(args[0], point);
				p.sendMessage(String.valueOf(prefix) + " §2Warppunkt wurde erfolgreich gesetzt");
				plugin.messageSystem.sendMessageToPlayer(p, "test1");
			}
		} else {

		}

		return true;
	}

}
