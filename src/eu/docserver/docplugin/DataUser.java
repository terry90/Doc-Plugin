package eu.docserver.docplugin;

public class DataUser {
	private int logMinutes= 0;
	private String name = null;
	private int level = 0;
	private int money = 0;
	
	public DataUser(int logMinutes, String name, int level, int money) {
		this.level = level;
		this.logMinutes = logMinutes;
		this.money = money;
		this.name = name;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getLogMinutes() {
		return logMinutes;
	}
	
	public int getMoney() {
		return money;
	}
	
	public String getName() {
		return name;
	}
}
