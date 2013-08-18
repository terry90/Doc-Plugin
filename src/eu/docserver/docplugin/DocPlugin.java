package eu.docserver.docplugin;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class DocPlugin extends JavaPlugin {
	
	public final Handlers handler = new Handlers(this);
	private DatabaseFunc dbFunc = null;
	
	@Override
	public void onEnable () {
		this.saveDefaultConfig();
		this.dbFunc = new DatabaseFunc(this);
		getServer().getPluginManager().registerEvents(this.handler, this);
		try {
			this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Loyal(this, dbFunc), 40, 20 * 60 * 30);
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
		if(command.getName().equalsIgnoreCase("doc_money")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players can use this command!");
				return true;
			}
			try {
				dbFunc.showMoney(sender.getName());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		}
		 if(command.getName().equalsIgnoreCase("doc_help")) {
		        sender.sendMessage(ChatColor.GREEN + "/doc_money -> votre solde actuel");
		        sender.sendMessage(ChatColor.GREEN + "/doc_help  -> commandes du Doc's plugin");
		        return true;
		    }
		    return false;
	}
}
