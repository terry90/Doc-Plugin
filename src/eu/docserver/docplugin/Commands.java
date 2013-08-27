package eu.docserver.docplugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Commands {

	private DatabaseFunc dbFunc;
	private JavaPlugin plugin;

	public Commands(DatabaseFunc dbFunc, JavaPlugin plugin) {
		this.dbFunc = dbFunc;
		this.plugin = plugin;
	}

	public boolean execCmd(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("doc_money"))
			return cmdDocMoney(sender);
		else if (command.getName().equalsIgnoreCase("doc_info"))
			return cmdDocInfo(sender, args);
		else if (command.getName().equalsIgnoreCase("doc_top"))
			return cmdDocTop(sender, args);
		else if (command.getName().equalsIgnoreCase("doc_op"))
			return cmdDocOp(sender, args);
		else if (command.getName().equalsIgnoreCase("doc_help")) {
			sender.sendMessage(ChatColor.GREEN + "/doc_money -> votre solde actuel");
			sender.sendMessage(ChatColor.GREEN + "/doc_help -> commandes du Doc's plugin");
			sender.sendMessage(ChatColor.GREEN + "/doc_info [player] -> infos sur le joueur");
			sender.sendMessage(ChatColor.GREEN + "/doc_top [nb] -> affiche les [nb] joueurs les plus fidèles (nb entre 1 et 20)");
			return true;
		}
		return false;
	}

	private boolean cmdDocInfo(CommandSender sender, String[] args) {
		if (args.length != 1)
			return false;
		try {
			if (dbFunc.checkOfflinePlayer(args[0])) {
				sender.sendMessage(ChatColor.BLUE + args[0] + ":");
				try {
					String stringLog = getFormattedTime(dbFunc.getLogTime(args[0]));
					sender.sendMessage(ChatColor.DARK_GREEN + "Argent: " + ChatColor.AQUA + dbFunc.getMoney(args[0]) + "$");
					sender.sendMessage(ChatColor.DARK_GREEN + "Rang: " + ChatColor.AQUA + dbFunc.getRankName(args[0]));
					sender.sendMessage(ChatColor.DARK_GREEN + "Temps de jeu: " + ChatColor.AQUA + stringLog);
					dbFunc.closeCo();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean cmdDocTop(CommandSender sender, String[] args) {
		ArrayList<DataUser> topLog;
		if (args.length != 1 || Integer.parseInt(args[0]) < 1 || Integer.parseInt(args[0]) > 20)
			return false;
		try {
			topLog = dbFunc.getTop(Integer.parseInt(args[0]));
			for (int i = 0; i < topLog.size(); i++) {
				sender.sendMessage(ChatColor.GOLD + "" + (i + 1) + ") " + ChatColor.BLUE + topLog.get(i).getName() + ": " + ChatColor.GREEN + getFormattedTime(topLog.get(i).getLogMinutes()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbFunc.closeCo();
		return true;
	}
	
	private boolean cmdDocMoney(CommandSender sender) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");
			return true;
		}
		try {
			dbFunc.showMoney(sender.getName());
			dbFunc.closeCo();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private boolean cmdDocOp(CommandSender sender, String[] args) {
		Player pl = null;
		if (!sender.isOp()) {
			sender.sendMessage(ChatColor.RED + "Vous n'avez pas le droit de faire ça !");
			return true;
		}
		if (args.length != 1 || (pl = plugin.getServer().getPlayer(args[0])) == null)
			return false;
		if (pl.getWalkSpeed() != (float) 0.2) {
			pl.setWalkSpeed((float) 0.2);
			pl.setAllowFlight(false);
			pl.setFlySpeed((float) 0.2);
			sender.sendMessage(ChatColor.YELLOW + "Player " + pl.getName() + " deOP !");
		} else {
			pl.setWalkSpeed((float) 0.5);
			pl.setAllowFlight(true);
			pl.setFlySpeed((float) 0.8);
			sender.sendMessage(ChatColor.YELLOW + "Player " + pl.getName() + " OP !");
		}
		return true;
	}
	
	private String getFormattedTime(int minutes) {
		return String.format("%dj %dh %dm", TimeUnit.MINUTES.toDays(minutes),
	            TimeUnit.MINUTES.toHours(minutes) - TimeUnit.DAYS.toHours(TimeUnit.MINUTES.toDays(minutes)),
	            TimeUnit.MINUTES.toMinutes(minutes) - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(minutes)));
	}
}
