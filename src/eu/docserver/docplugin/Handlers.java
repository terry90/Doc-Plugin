package eu.docserver.docplugin;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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

	private void opPlayer(Player p) {
		p.setWalkSpeed((float) 0.5);
		p.setAllowFlight(true);
		p.setFlySpeed((float) 0.8);
		p.setDisplayName(ChatColor.AQUA + p.getName() + ChatColor.RESET);
	}

	@EventHandler
	public void PlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		p.sendMessage(ChatColor.DARK_PURPLE + "Bienvenue sur le serveur de " + ChatColor.AQUA + "Doc_CoBrA");
		p.sendMessage(ChatColor.BLUE + "Pour avoir une liste détaillée des commandes tapez /doc_help" + ChatColor.RESET);
		if (p.isOp()) {
			opPlayer(p);
		}
	}

	@EventHandler
	public void PlayerGameModeChange(final Player player, final GameMode newGameMode) {
		if (player.isOp()) {
			opPlayer(player);
		}
	}
}
