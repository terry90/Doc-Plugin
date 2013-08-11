package eu.docserver.docplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class DocPlugin extends JavaPlugin {
	@Override
	public void onEnable () {
		this.saveDefaultConfig();
		getLogger().info("Doc Plugin successfully loaded");
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
		String label, String[] args) {
		 if(command.getName().equalsIgnoreCase("listplayers")) {
		        if (!(sender instanceof Player)) {
		            sender.sendMessage("Only players can use this command!");
		            return true;
		        }
		        
		        Player s = (Player) sender;
		        s.sendMessage("Players:");
		        Player[] list = Bukkit.getServer().getOnlinePlayers();
		        String players = "";
		        for (int i = 0; i < list.length; i++) {
		        	players = players + list[i].getName(); 
		        }
		        s.sendMessage(players);
		        s.sendMessage(this.getConfig().getString("port"));
		        return true;
		    }
		    return false;
	}
}
