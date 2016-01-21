package abstractClasses;

import java.io.Serializable;

import interfaces.Drawable;
import main.Map;
import objects.Nexus;

abstract public class Existent implements Serializable {

	private static Map MAP;

	private long timeAtLastUpdate;
	private long millisSinceLastUpdate;
	
	private static Nexus nexus;

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
	
	protected static void setNexus(Nexus n) {
		nexus = n;
	}
	
	protected static Nexus getNexus() {
		return nexus;
	}

	public abstract double getX();

	public abstract double getY();

	public abstract int getApproxX();

	public abstract int getApproxY();

	public abstract int getRow();

	public abstract int getCol();

	public static Map getMap() {
		return MAP;
	}

	protected long getMillisSinceLastUpdate() {
		return millisSinceLastUpdate;
	}
	
	public double getDistFrom(double x, double y) {

		double xDist = x - getX();

		double yDist = y - getY();

		double distSqrd = (xDist * xDist) + (yDist * yDist);

		return Math.sqrt(distSqrd);
	}
	
	public String getDisplayName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String toString() {
		String toReturn = super.toString();

		toReturn += " At Row: " + getRow() + " Col: " + getCol() + "  [Center Location: (X: " + getX() + ", Y: " + getY() + " )]";
		return toReturn;
	}
}
