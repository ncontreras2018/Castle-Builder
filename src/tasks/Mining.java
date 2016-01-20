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
	
	private static ArrayList<Mining> jobsInProgress = new ArrayList<Mining>();

	public Mining(int row, int col, Player player) {
		super(row, col, player, 5);
		System.out.println("Passed Mining info to Task, constructed");
		
		System.out.println("Input Row: " + row + " Input Col: " + col);
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
		if (isDone()) {
			((Miner) getAssignedPerson()).collectOre();
		}
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
			
			Mining newTask = new Mining(closestFree.getRow(), closestFree.getCol(), m.getPlayer());
			
			getMap().getGrid()[closestFree.getRow()][closestFree.getCol()][2] = newTask;
			
			Task.addTask(newTask);
			
			jobsInProgress.add(newTask);
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

						for (Mining t : jobsInProgress) {
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
		
		System.out.println("Closest Ore: " + closest);
		
		return closest;
	}

	@Override
	public boolean canMoveThrough(UnlockedFromGrid obj) {
		return true;
	}
	
	@Override
	public void finish() {
		super.finish();
		
		jobsInProgress.remove(this);
	}
}
