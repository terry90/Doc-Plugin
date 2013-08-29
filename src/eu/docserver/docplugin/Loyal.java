package eu.docserver.docplugin;

import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
 
public class Loyal extends BukkitRunnable {
 
    private final JavaPlugin plugin;
	private DatabaseFunc dbFunc = null;
    
    public Loyal(JavaPlugin plugin, DatabaseFunc dbFunc) throws SQLException {
        this.plugin = plugin;
        this.dbFunc = dbFunc;
    }
 
    public void run() {
    	Player[] list = plugin.getServer().getOnlinePlayers();
    	plugin.getLogger().info("Nb Players: " + list.length);
    	float money = (float) 1.0;
        for (int i = 0; i < list.length; i++) {
        	try {
				dbFunc.addMoney(list[i].getName(), 1);
				money = (float) (1 + (Math.floor(dbFunc.getLogTime(list[i].getName()) / 720)) / 10);
				money = (float) (money >= 2.0 ? 2.0 : money);
				list[i].sendMessage(ChatColor.GOLD + "+ " + money + "$ !");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        }
        if (list.length != 0)
        	dbFunc.closeCo();
    }
}