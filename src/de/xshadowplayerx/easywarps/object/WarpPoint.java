package de.xshadowplayerx.easywarps.object;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Location;

import de.xshadowplayerx.easywarps.main;

public class WarpPoint {

	private static main plugin;
	public String name;
	private Location loc;
	public static HashMap<String, WarpPoint> WarpPoints = new HashMap<String, WarpPoint>();

	public WarpPoint(main instance) {
		plugin = instance;
		loadWarpPoints();
	}

	public WarpPoint(String name) {
		double x = plugin.getConfig().getDouble("warp." + name + ".x");
		double y = plugin.getConfig().getDouble("warp." + name + ".y");
		double z = plugin.getConfig().getDouble("warp." + name + ".z");
		String w = plugin.getConfig().getString("warp." + name + ".world");
		double pitch = plugin.getConfig().getDouble("warp." + name + ".pitch");
		double yaw = plugin.getConfig().getDouble("warp." + name + ".yaw");
		Location loc = new Location(plugin.getServer().getWorld(w), x, y, z, (float) yaw, (float) pitch);
		this.loc = loc;
	}

	public WarpPoint(String name, Location loc) {
		this.name = name;
		this.loc = loc;
	}

	public boolean save() {
		plugin.getConfig().set("warp." + name + ".x", loc.getX());
		plugin.getConfig().set("warp." + name + ".y", loc.getY());
		plugin.getConfig().set("warp." + name + ".z", loc.getZ());
		plugin.getConfig().set("warp." + name + ".world", loc.getWorld().getName());
		plugin.getConfig().set("warp." + name + ".pitch", loc.getPitch());
		plugin.getConfig().set("warp." + name + ".yaw", loc.getYaw());
		plugin.saveConfig();
		plugin.reloadConfig();
		return true;
	}

	public Location getLocation() {
		return loc;
	};

	private void loadWarpPoints() {
		Set<String> keys = plugin.getConfig().getKeys(true);
		plugin.getLogger().info("Loading Warp Points...");
		for (String str : keys) {
			if (!str.contains(".y")) {
				if (!str.contains(".x")) {
					if (!str.contains(".z")) {
						if (!str.contains(".yaw")) {
							if (!str.contains(".pitch")) {
								if (!str.contains(".world")) {
									if (str.contains("warp.")) {
										String name = str.replaceAll("warp.", "");
										WarpPoints.put(name, new WarpPoint(name));
									}
								}
							}
						}
					}
				}
			}
		}
		plugin.getLogger().info(WarpPoints.size() + " Warps found!");
		;
	}

}
