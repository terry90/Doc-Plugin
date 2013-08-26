package eu.docserver.docplugin;

import java.sql.SQLException;
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
		else if (command.getName().equalsIgnoreCase("doc_help")) {
			sender.sendMessage(ChatColor.GREEN + "/doc_money -> votre solde actuel");
			sender.sendMessage(ChatColor.GREEN + "/doc_help -> commandes du Doc's plugin");
			sender.sendMessage(ChatColor.GREEN + "/doc_info [player] -> infos sur le joueur");
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
					int logTime = dbFunc.getLogTime(args[0]);
					String stringLog = String.format("%dj %dh %dm", TimeUnit.MINUTES.toDays(logTime),
				            TimeUnit.MINUTES.toHours(logTime) - TimeUnit.DAYS.toHours(TimeUnit.MINUTES.toDays(logTime)),
				            TimeUnit.MINUTES.toMinutes(logTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(logTime)));
					sender.sendMessage(ChatColor.DARK_GREEN + "Argent: " + ChatColor.AQUA + dbFunc.getMoney(args[0]) + "$");
					sender.sendMessage(ChatColor.DARK_GREEN + "Rang: " + ChatColor.AQUA + dbFunc.getRankName(args[0]));
					sender.sendMessage(ChatColor.DARK_GREEN + "Temps de jeu: " + ChatColor.AQUA + stringLog);
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

	private boolean cmdDocMoney(CommandSender sender) {
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
}
