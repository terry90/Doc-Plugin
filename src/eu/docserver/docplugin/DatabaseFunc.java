package eu.docserver.docplugin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class DatabaseFunc {
	private final JavaPlugin plugin;
	private MySQL db = null;
	private String adress, port, database, user, pass;
	public Connection c = null;

	public DatabaseFunc(JavaPlugin plugin) {
		this.plugin = plugin;
		this.adress = plugin.getConfig().getString("db_adress");
		this.port = plugin.getConfig().getString("db_port");
		this.database = plugin.getConfig().getString("db_database");
		this.user = plugin.getConfig().getString("db_user");
		this.pass = plugin.getConfig().getString("db_pass");
		db = new MySQL(adress, port, database, user, pass);
		checkCo();
	}

	public void checkCo() {
		if (db == null || db.checkConnection() == false) {
			plugin.getLogger().info("Restarting db connection");
			c = db.open();
		}
	}

	public int getMoney(String username) throws SQLException {
		checkCo();
		Statement state = c.createStatement();
		ResultSet res = state.executeQuery("SELECT money FROM user WHERE login = '" + username + "';");
		res.next();
		return (res.getInt("money"));
	}

	public void showMoney(String username) throws SQLException {
		checkCo();
		Statement state = c.createStatement();
		Player player = plugin.getServer().getPlayer(username);
		ResultSet res = state.executeQuery("SELECT money FROM user WHERE login = '" + player.getName() + "';");
		res.next();
		int money = res.getInt("money");
		player.sendMessage("Vous avez actuellement " + money + "$.");
	}

	public void addMoney(String username, int to_add) throws SQLException {
		checkCo();
		Statement state = c.createStatement();
		int money = getMoney(username) + to_add;
		state.executeUpdate("UPDATE user SET money = '" + money + "' WHERE login = '" + username + "';");
	}

	public Player getUser(String name) {
		return (plugin.getServer().getPlayer(name));
	}

	public int getRank(String username) throws SQLException {
		checkCo();
		Statement state = c.createStatement();
		ResultSet res = state.executeQuery("SELECT level FROM user WHERE login = '" + username + "';");
		res.next();
		return (res.getInt("level"));
	}

	public boolean checkOfflinePlayer(String username) throws SQLException {
		checkCo();
		Statement state = c.createStatement();
		ResultSet res = state.executeQuery("SELECT id FROM user WHERE login = '" + username + "';");
		if (res.next())
			return true;
		return false;
	}

	public String getRankName(String username) throws SQLException {
		int level = getRank(username);
		switch (level) {
		case -1:
			return ("Sous-fifre");
		case 0:
			return ("En attente");
		case 1:
			return ("Membre");
		case 2:
			return ("Donateur");
		case 3:
			return ("Modérateur");
		case 5:
			return ("Admin");
		default:
			return ("Unknown rank");
		}
	}
}
