package eu.docserver.docplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerJoinEvent;

public class Handlers implements Listener {

	private JavaPlugin plugin;

	public Handlers(JavaPlugin plug) {
		this.plugin = plug;
	}
	
	@EventHandler
	public void PlayerJoin(PlayerJoinEvent event) {
		Player p =  event.getPlayer();
		p.sendMessage(ChatColor.DARK_PURPLE + "Bienvenue sur le serveur de " + ChatColor.AQUA + "Doc_CoBrA");
		p.sendMessage(ChatColor.BLUE + "Pour avoir une liste détaillée des commandes tapez /doc_help" + ChatColor.RESET);
        /*if (p.getDisplayName().equals("Doc_CoBrA")) {
        	p.setDisplayName(ChatColor.AQUA + p.getDisplayName() + ChatColor.RESET);
        	p.setPlayerListName(ChatColor.AQUA + p.getDisplayName());
        }*/
	}
}
