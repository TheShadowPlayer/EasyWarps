package de.xshadowplayerx.easywarps;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener
{
    public void onEnable() {
        this.getConfig().options().header("Developed by XShadowPlayerX");
        final PluginManager pm = Bukkit.getPluginManager();
        this.getConfig().addDefault("Prefix", (Object)"&8[&2EasyWarps&8] ");
        this.getCommand("setwarp").setExecutor((CommandExecutor)new cmd(this));
        this.getCommand("warp").setExecutor((CommandExecutor)new cmd(this));
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        pm.registerEvents((Listener)this, (Plugin)this);
    }
}
