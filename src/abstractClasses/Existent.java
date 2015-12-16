package abstractClasses;

import interfaces.Drawable;
import main.Map;

abstract public class Existent {
	
	protected static Map MAP;
	
	private long timeAtLastUpdate;
	protected long millisSinceLastUpdate;
	
	public Existent() {
		updateInternalTime();
	}
	
	public static void setMap(Map map) {
		MAP = map;
	}
	
	public static Map getMap() {
		return MAP;
	}
	
	abstract public void update();
	
	public void updateInternalTime() {
		millisSinceLastUpdate = System.currentTimeMillis() - timeAtLastUpdate;
		timeAtLastUpdate = System.currentTimeMillis();
	}
	
	public abstract double getX();
	
	public abstract double getY();
}
