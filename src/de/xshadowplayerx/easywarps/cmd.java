package de.xshadowplayerx.easywarps;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class cmd implements CommandExecutor
{
    public main plugin;
    
    public cmd(final main instance) {
        this.plugin = instance;
    }
    
    public boolean onCommand(final CommandSender cmds, final Command cmd, final String label, final String[] args) {
        final String prefix = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("Prefix"));
        final Player p = (Player)cmds;
        if (cmd.getName().equalsIgnoreCase("warp")) {
            if (args.length == 0) {
                p.sendMessage(String.valueOf(prefix) + " §cDu must ein Argument angeben §7| §4/warp <WARPPUNKT> §7|");
            }
            else if (this.plugin.getConfig().contains("warp." + args[0])) {
                final double x1 = this.plugin.getConfig().getDouble("warp." + args[0] + ".x");
                final double y1 = this.plugin.getConfig().getDouble("warp." + args[0] + ".y");
                final double z1 = this.plugin.getConfig().getDouble("warp." + args[0] + ".z");
                final String w1 = this.plugin.getConfig().getString("warp." + args[0] + ".world");
                final Location loc = new Location(this.plugin.getServer().getWorld(w1), x1, y1, z1);
                p.teleport(loc);
            }
            else {
                p.sendMessage(String.valueOf(prefix) + " §cDer Warppunkt ist uns Unbekannt.");
            }
        }
        if (cmd.getName().equalsIgnoreCase("setwarp") && p.hasPermission("system.admin")) {
            if (args.length == 0) {
                p.sendMessage(String.valueOf(prefix) + " §cDu must ein Argument angeben §7| §4/setwarp <WARPPUNKT> §7|");
            }
            else if (this.plugin.getConfig().contains("warp." + args[0])) {
                p.sendMessage(String.valueOf(prefix) + " §cDer Warppunkt ist uns bereits Bekannt.");
            }
            else {
                final double x2 = p.getLocation().getX();
                final double y2 = p.getLocation().getY();
                final double z2 = p.getLocation().getZ();
                final String w2 = p.getLocation().getWorld().getName();
                this.plugin.getConfig().set("warp." + args[0] + ".x", (Object)x2);
                this.plugin.getConfig().set("warp." + args[0] + ".y", (Object)y2);
                this.plugin.getConfig().set("warp." + args[0] + ".z", (Object)z2);
                this.plugin.getConfig().set("warp." + args[0] + ".world", (Object)w2);
                this.plugin.saveConfig();
                this.plugin.reloadConfig();
                p.sendMessage(String.valueOf(prefix) + " §2Warppunkt wurde erfolgreich gesetzt");
            }
        }
        return false;
    }
}
