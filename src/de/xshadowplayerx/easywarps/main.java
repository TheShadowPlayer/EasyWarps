package de.xshadowplayerx.easywarps;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import de.xshadowplayerx.easywarps.commands.CMDsetwarp;
import de.xshadowplayerx.easywarps.commands.CMDwarp;
import de.xshadowplayerx.easywarps.object.WarpPoint;
import de.xshadowplayerx.utilities.MessageSystem;

public class main extends JavaPlugin implements Listener {
	public MessageSystem messageSystem;

	public void onEnable() {
		this.getConfig().options().header("Developed by XShadowPlayerX");
		this.getConfig().addDefault("Prefix", "&8[&2EasyWarps&8] ");
		this.getConfig().addDefault("Language", "de-DE");
		this.getConfig().addDefault("FallbackLanguage", "de-DE");
		@SuppressWarnings("unused")
		WarpPoint initWarpPoint = new WarpPoint(this);
		this.getCommand("setwarp").setExecutor(new CMDsetwarp(this));
		this.getCommand("warp").setExecutor(new CMDwarp(this));
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		this.messageSystem = new MessageSystem(this);
	}
}
