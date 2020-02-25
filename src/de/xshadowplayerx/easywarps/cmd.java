package de.xshadowplayerx.easywarps;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

import de.xshadowplayerx.easywarps.object.WarpPoint;

public class cmd implements CommandExecutor
{
    private main plugin;
    private static HashMap<String, WarpPoint> WarpPoints = new HashMap<String, WarpPoint>();
    
    public cmd(main instance) {
        this.plugin = instance;
	    Set<String> keys = plugin.getConfig().getKeys(true);
	    for (String str: keys) {
			if (!str.contains(".y")) {
				if (!str.contains(".x")) {
					if (!str.contains(".z")) {
						if (!str.contains(".yaw")) {
							if (!str.contains(".pitch")) {
								if (!str.contains(".world")) {
									if (str.contains("warp.")) {
										String name = str.replaceAll("warp.", "");
										WarpPoints.put(name, new WarpPoint(plugin, name));
									}
								}
							}
						}
					}
				}
			}
		}
	    plugin.getLogger().info(WarpPoints.size() + " Warps found!");;
    }
    
    public boolean onCommand(CommandSender cmds, Command cmd, String label, String[] args) {
        String prefix = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("Prefix"));
        Player p = (Player)cmds;
        if (cmd.getName().equalsIgnoreCase("warp")) {
            if (args.length == 0) {
                p.sendMessage(String.valueOf(prefix) + " §cDu must ein Argument angeben §7| §4/warp <WARPPUNKT> §7|");
                return true;
            }
            else if (WarpPoints.containsKey(args[0])) {
                p.teleport(WarpPoints.get(args[0]).getLocation());
                return true;
            }
            else {
                p.sendMessage(String.valueOf(prefix) + " §cDer Warppunkt ist uns Unbekannt.");
                return true;
            }
        }
        if (cmd.getName().equalsIgnoreCase("setwarp") && p.hasPermission("system.admin")) {
            if (args.length == 0) {
                p.sendMessage(String.valueOf(prefix) + " §cDu must ein Argument angeben §7| §4/setwarp <WARPPUNKT> §7|");
                return true;
            }
            else if (WarpPoints.containsKey(args[0])) {
                p.sendMessage(String.valueOf(prefix) + " §cDer Warppunkt ist uns bereits Bekannt.");
                return true;
            }
            else {
            	WarpPoint point = new WarpPoint(plugin, args[0], p.getLocation());
            	point.save();
            	WarpPoints.put(args[0], point);
                p.sendMessage(String.valueOf(prefix) + " §2Warppunkt wurde erfolgreich gesetzt");
                return true;
            }
        }
        return false;
    }
}
