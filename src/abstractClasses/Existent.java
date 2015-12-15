package abstractClasses;

import interfaces.Drawable;
import main.Map;

abstract public class Existent implements Drawable {
	
	protected static Map MAP;
	
	public static void setMap(Map map) {
		MAP = map;
	}
	
	public static Map getMap() {
		return MAP;
	}
	
	abstract public void update();
}
