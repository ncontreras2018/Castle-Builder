package abstractClasses;

import interfaces.Drawable;
import main.Map;

abstract public class Existent {

	private static Map MAP;

	private long timeAtLastUpdate;
	private long millisSinceLastUpdate;

	public Existent() {
		updateInternalTime();
	}

	public static void setMap(Map map) {
		MAP = map;
	}

	abstract public void update();

	public void updateInternalTime() {
		millisSinceLastUpdate = System.currentTimeMillis() - timeAtLastUpdate;
		timeAtLastUpdate = System.currentTimeMillis();
	}

	public abstract double getX();

	public abstract double getY();

	public static Map getMap() {
		return MAP;
	}

	protected long getMillisSinceLastUpdate() {
		return millisSinceLastUpdate;
	}
}
