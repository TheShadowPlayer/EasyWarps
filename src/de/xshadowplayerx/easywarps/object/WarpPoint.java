package de.xshadowplayerx.easywarps.object;

import org.bukkit.Location;

import de.xshadowplayerx.easywarps.main;

public class WarpPoint {

	private main plugin;
	public String name;
	private Location loc;
	
	public WarpPoint(main instance, String name) {
		plugin = instance;
		

		double x = this.plugin.getConfig().getDouble("warp." + name + ".x");
        double y = this.plugin.getConfig().getDouble("warp." + name + ".y");
        double z = this.plugin.getConfig().getDouble("warp." + name + ".z");
        String w = this.plugin.getConfig().getString("warp." + name + ".world");
        double pitch = this.plugin.getConfig().getDouble("warp." + name + ".pitch");
        double yaw = this.plugin.getConfig().getDouble("warp." + name + ".yaw");
        Location loc = new Location(this.plugin.getServer().getWorld(w), x, y, z, (float)yaw, (float)pitch);
        this.loc = loc;
	}
	
	public WarpPoint(main instance, String name, Location loc) {
		plugin = instance;
		this.name = name;
		this.loc = loc;
	}

	public boolean save() {
        this.plugin.getConfig().set("warp." + name + ".x", loc.getX());
        this.plugin.getConfig().set("warp." + name + ".y", loc.getY());
        this.plugin.getConfig().set("warp." + name + ".z", loc.getZ());
        this.plugin.getConfig().set("warp." + name + ".world", loc.getWorld().getName());
        this.plugin.getConfig().set("warp." + name + ".pitch", loc.getPitch());
        this.plugin.getConfig().set("warp." + name + ".yaw", loc.getYaw());
        this.plugin.saveConfig();
        this.plugin.reloadConfig();
		return true;
	}
	
	public Location getLocation() {
        return loc;
	};
	
}
