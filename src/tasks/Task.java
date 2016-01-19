package tasks;

import java.util.ArrayList;

import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;
import interfaces.Drawable;
import main.Player;
import people.Person;

public abstract class Task extends LockedToGrid implements Drawable {

	private static ArrayList<Task> globalTaskList = new ArrayList<Task>();

	private Player player;

	private long timeCost, timeSpent;

	private Person personAssigned;

	private ArrayList<Task> prerequisiteTasks;

	private Task prerequisiteFor;

	public Task(int row, int col, Player player, double timeCost) {
		this(row, col, player, timeCost, null);
		System.out.println("Used Short Constructer for Task, passed");
	}

	public Task(int row, int col, Player player, double timeCost, Task prerequisiteFor) {
		super(row, col);
		this.player = player;
		this.timeCost = Math.round(timeCost * 1000);
		prerequisiteTasks = new ArrayList<Task>();
		prerequisiteTasks.addAll(createPrerequisiteTasks());
		globalTaskList.addAll(prerequisiteTasks);
		this.prerequisiteFor = prerequisiteFor;
		
		System.out.println("Task Constructor Input Row: " + row + " Input Col: " + col);
		
		System.out.println("Created Task At Row: " + getRow() + " Col: " + getCol());
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
	
	public Person getAssignedPerson() {
		return personAssigned;
	}

	private boolean canDoTask(Person p) {

		if (this.player.equals(p.getPlayer())) {
			if (this.getTypeNeeded().isInstance(p)) {
				if (this.getPrerequisites().isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	public abstract boolean shouldBeAdjacent();

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

	public Player getPlayer() {
		return player;
	}

	public boolean isDone() {
		return timeSpent >= timeCost;
	}

	public void assignPerson(Person p) {
		personAssigned = p;
	}

	public void remove() {

		System.out.println("Removing: " + this);

		if (personAssigned != null) {

			personAssigned.releaseTask();

			personAssigned = null;

		}

		globalTaskList.remove(this);

		getMap().getGrid()[getRow()][getCol()][2] = null;
	}

	public void doWork(long timeSpent) {
		System.out.println("Did work: " + timeSpent);
		this.timeSpent += timeSpent;
	}
	
	public boolean isAtLocation(UnlockedFromGrid object) {
		// return Util.isAdjacentTo(object, this);

		if (shouldBeAdjacent()) {
			for (int[] curPos : getNearbyLocs()) {
				if (object.getApproxX() == curPos[0] && object.getApproxY() == curPos[1]) {
					return true;
				}
			}
			return false;
		} else {
			return getApproxX() == object.getApproxX() && getApproxY() == object.getApproxY();
		}
	}
	
	public static ArrayList<Task> getTaskList() {
		return globalTaskList;
	}

	@Override
	public String toString() {
		String toReturn = super.toString();

		toReturn += " Type Needed: " + getTypeNeeded() + " Person Assigned: " + personAssigned + " Time Cost: "
				+ timeCost + " Work Done " + timeSpent;

		return toReturn;
	}

	public void finish() {
		if (isPrerequisite()) {
			prerequisiteFor.getPrerequisites().remove(this);
		}
	}
	
	@Override
	public double movementPenalty(UnlockedFromGrid other) {
		return 2;
	}
	
	@Override
	public boolean taskCanBePreformed(Task attemptedTask) {
		return false;
	}
}
