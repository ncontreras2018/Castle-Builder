package util;

import main.Map;
import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;

public class Util {

	private static Map map;

	public static void setMap(Map m) {
		map = m;
	}

	public static double getDistance(double x1, double y1, double x2, double y2) {
		double xDist = x2 - x1;
		double yDist = y2 - y1;

		double distSquared = (xDist * xDist) + (yDist * yDist);

		return Math.sqrt(distSquared);
	}

	public static boolean isAdjacentTo(double x1, double y1, double x2,
			double y2) {
		int row1 = (int) y1 / map.getTileSize();
		int col1 = (int) x1 / map.getTileSize();

		int row2 = (int) y2 / map.getTileSize();
		int col2 = (int) x2 / map.getTileSize();

		return (Math.abs(row1 - row2) + Math.abs(col1 - col2) == 1);
	}

	public static boolean isAdjacentTo(LockedToGrid locked, int[] location) {
		return isAdjacentTo(locked.getCenterX(), locked.getCenterY(),
				location[0], location[1]);
	}

	public static boolean isAdjacentTo(UnlockedFromGrid unlocked, double x,
			double y) {
		return isAdjacentTo(unlocked.getX(), unlocked.getY(), x, y);
	}

	public static boolean isAdjacentTo(UnlockedFromGrid unlocked,
			LockedToGrid locked) {
		return isAdjacentTo(unlocked.getX(), unlocked.getY(),
				locked.getCenterX(), locked.getCenterY());
	}

	public static LockedToGrid[] getGridObjectsAt(double x, double y) {
		int row = (int) y / map.getTileSize();
		int col = (int) x / map.getTileSize();

		return map.getGrid()[row][col];
	}

	public static boolean sameCol(double x1, double x2) {
		return (int) x1 / map.getTileSize() == (int) x2 / map.getTileSize();
	}

	public static boolean sameRow(double y1, double y2) {
		return (int) y1 / map.getTileSize() == (int) y2 / map.getTileSize();
	}

	public static boolean pointsLineUp(double x1, double y1, double x2,
			double y2) {
		int row1 = (int) y1 / map.getTileSize();
		int col1 = (int) x1 / map.getTileSize();

		int row2 = (int) y2 / map.getTileSize();
		int col2 = (int) x2 / map.getTileSize();

		if (row1 == row2 || col1 == col2) {
			return true;
		} else {
			return false;
		}
	}

	public static int[] getRowColAt(double x, double y) {
		return new int[] { (int) y / map.getTileSize(),
				(int) x / map.getTileSize() };
	}
}
