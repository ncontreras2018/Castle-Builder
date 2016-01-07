package tasks;

import java.awt.Graphics2D;
import java.util.ArrayList;

import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;
import main.Player;
import objects.Ore;
import people.Miner;
import people.Person;

public class Mining extends Task {

	public Mining(int row, int col, Player player) {
		super(row, col, player, 5);
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {
	}

	@Override
	protected ArrayList<Task> createPrerequisiteTasks() {
		return new ArrayList<Task>();
	}

	@Override
	public boolean shouldBeAdjacent() {
		return false;
	}

	@Override
	public int getPriorty() {
		return 1;
	}

	@Override
	public void update() {
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<Person> getTypeNeeded() {
		try {
			return (Class<Person>) Class.forName("people.Miner");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void createTask(Miner m) {

		Ore closestFree = getClosestFreeOre();

		if (closestFree != null) {
			Task.addTask(new Mining(closestFree.getRow(), closestFree.getApproxY(), m.getPlayer()));
		}
	}

	private static Ore getClosestFreeOre() {

		double closestDist = Double.MAX_VALUE;

		Ore closest = null;

		for (LockedToGrid[][] row : getMap().getGrid()) {
			for (LockedToGrid[] curTile : row) {
				if (curTile[1] instanceof Ore) {
					if (getNexus().getDistFrom(curTile[1].getX(), curTile[1].getY()) < closestDist) {

						boolean alreadyTaken = false;

						for (Task t : Task.getTaskList()) {
							if (t.getRow() == curTile[1].getRow() && t.getCol() == curTile[1].getCol()) {
								alreadyTaken = true;
								break;
							}
						}

						if (!alreadyTaken) {
							closest = (Ore) curTile[1];
							closestDist = getNexus().getDistFrom(curTile[1].getX(), curTile[1].getY());
						}
					}
				}
			}
		}
		return closest;
	}
}
