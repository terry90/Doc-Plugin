package eu.docserver.docplugin;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
 
public class LogTime extends BukkitRunnable {
 
    private final JavaPlugin plugin;
	private DatabaseFunc dbFunc = null;
    
    public LogTime(JavaPlugin plugin, DatabaseFunc dbFunc) throws SQLException {
        this.plugin = plugin;
        this.dbFunc = dbFunc;
    }
 
    public void run() {
    	Player[] list = plugin.getServer().getOnlinePlayers();
        for (int i = 0; i < list.length; i++) {
        	try {
				dbFunc.addLogTime(list[i].getName(), 1);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        }
        dbFunc.closeCo();
    }
}