package tasks;

import java.util.ArrayList;

import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;
import interfaces.Drawable;
import people.Person;
import util.Util;

public abstract class Task extends LockedToGrid implements Drawable {

	private static ArrayList<Task> globalTaskList = new ArrayList<Task>();

	private int player;

	private long timeCost, timeSpent;

	private Person personAssigned;

	private ArrayList<Task> prerequisiteTasks;

	private Task prerequisiteFor;

	public Task(int row, int col, int player, double timeCost) {
		this(row, col, player, timeCost, null);
	}

	public Task(int row, int col, int player, double timeCost, Task prerequisiteFor) {
		super(row, col);
		this.player = player;
		this.timeCost = Math.round(timeCost * 1000);
		prerequisiteTasks = new ArrayList<Task>();
		prerequisiteTasks.addAll(createPrerequisiteTasks());
		globalTaskList.addAll(prerequisiteTasks);
		this.prerequisiteFor = prerequisiteFor;
	}

	/**
	 * Creates the tasks that must be completed before work on this task can
	 * begin The priority of these tasks should be higher than that of the
	 * parent Task
	 * 
	 * @return Tasks that must be completed before this one
	 */
	protected abstract ArrayList<Task> createPrerequisiteTasks();

	public boolean isPrerequisite() {
		return prerequisiteFor != null;
	}

	public Task getDependentTask() {
		return prerequisiteFor;
	}

	public static void addTask(Task t) {
		globalTaskList.add(t);
	}

	public ArrayList<Task> getPrerequisites() {
		return prerequisiteTasks;
	}

	public static boolean hasApplicableTaskFor(Person p) {

		System.out.println("Global Task List Length: " + globalTaskList.size());

		for (int i = 0; i < globalTaskList.size(); i++) {
			Task cur = globalTaskList.get(i);

			System.out.println("Checking Task: " + cur);

			if (cur.canDoTask(p)) {
				System.out.println("Task Good");
				return true;
			}
		}
		return false;
	}

	public static Task takeNextApplicableTaskFor(Person p) {

		Task mostImportant = null;

		int highestPriorty = 0;

		for (int i = 0; i < globalTaskList.size(); i++) {
			Task cur = globalTaskList.get(i);

			if (cur.canDoTask(p)) {
				if (cur.getPriorty() > highestPriorty) {
					mostImportant = cur;
					highestPriorty = cur.getPriorty();
				}
			}
		}

		globalTaskList.remove(mostImportant);

		return mostImportant;
	}

	private boolean canDoTask(Person p) {

		if (this.getPlayer() == p.getPlayer()) {
			if (this.getTypeNeeded().isInstance(p)) {
				if (this.getPrerequisites().isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Gets the priority of the task, where a higher number indicates a higher
	 * priority, and tasks marked as less than 1 will be temporarily ignored
	 * 
	 * @return The priority of the task
	 */
	public abstract int getPriorty();

	/**
	 * Gets the type of person needed to complete the task Return Person if
	 * anyone can accomplish it
	 * 
	 * @return The type of the object needed
	 */

	@SuppressWarnings("unchecked")
	public Class<Person> getTypeNeeded() {
		try {
			return (Class<Person>) Class.forName("people.Person");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getPlayer() {
		return player;
	}

	public boolean isDone() {
		return timeSpent >= timeCost;
	}

	public void assignPerson(Person p) {
		personAssigned = p;
	}

	public void doWork(long timeSpent) {
		System.out.println("Did work: " + timeSpent);
		this.timeSpent += timeSpent;
	}

	public boolean isAtLocation(UnlockedFromGrid object) {
		// return Util.isAdjacentTo(object, this);

		for (int[] curPos : getNearbyWorkingLocs()) {
			if (object.getApproxX() == curPos[0] && object.getApproxY() == curPos[1]) {
				return true;
			}
		}
		return false;
	}

	private ArrayList<int[]> getNearbyWorkingLocs() {
		ArrayList<int[]> locs = new ArrayList<int[]>();

		locs.add(new int[] { getApproxX() + getMap().getTileSize(), getApproxY() });
		locs.add(new int[] { getApproxX() - getMap().getTileSize(), getApproxY() });
		locs.add(new int[] { getApproxX(), getApproxY() + getMap().getTileSize() });
		locs.add(new int[] { getApproxX(), getApproxY() - getMap().getTileSize() });

		return locs;
	}

	@Override
	public boolean canPassThrough(UnlockedFromGrid other) {
		return true;
	}

	@Override
	public String toString() {
		String toReturn = super.toString();

		toReturn += " Location { Row:" + getRow() + " Col: " + getCol() + " } Type Needed: " + getTypeNeeded()
				+ " Person Assigned: " + personAssigned + " Time Cost: " + timeCost + " Work Done " + timeSpent;

		return toReturn;
	}

	public void finish() {
		if (isPrerequisite()) {
			prerequisiteFor.getPrerequisites().remove(this);
		}
	}
}
