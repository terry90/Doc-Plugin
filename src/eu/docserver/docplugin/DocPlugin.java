package eu.docserver.docplugin;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class DocPlugin extends JavaPlugin {
	
	private Commands cmd = null;
	public Handlers handler = null;
	private DatabaseFunc dbFunc = null;
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.dbFunc = new DatabaseFunc(this);
		this.handler = new Handlers(this, dbFunc);
		this.cmd = new Commands(dbFunc, this);
		getServer().getPluginManager().registerEvents(this.handler, this);
		try {
			this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Loyal(this, dbFunc), 40, 20 * 60 * 30); // (30 minutes)
			this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new LogTime(this, dbFunc), 40, 20 * 60); // (1 minute)
		} catch (SQLException e) {
			e.printStackTrace();
		}
		getLogger().info("Doc Plugin successfully loaded");
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		Bukkit.getScheduler().cancelAllTasks();
		super.onDisable();
	}
	
	@Override	
	public boolean onCommand(CommandSender sender, Command command,
		String label, String[] args) {
		return cmd.execCmd(sender, command, label, args);
	}
}
