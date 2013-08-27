package eu.docserver.docplugin;

import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerJoinEvent;

public class Handlers implements Listener {

	private JavaPlugin plugin;
	private DatabaseFunc dbFunc;

	public Handlers(JavaPlugin plug, DatabaseFunc dbFunc) {
		this.plugin = plug;
		this.dbFunc = dbFunc;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		p.sendMessage(ChatColor.DARK_PURPLE + "Bienvenue sur le serveur de " + ChatColor.AQUA + "Doc_CoBrA");
		p.sendMessage(ChatColor.BLUE + "Pour avoir une liste détaillée des commandes tapez /doc_help" + ChatColor.RESET);
		try {
			if (dbFunc.getRank(p.getName()) == 2) { // Donateur
				p.setDisplayName(ChatColor.GOLD + p.getName() + ChatColor.RESET);
			}
			dbFunc.closeCo();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
