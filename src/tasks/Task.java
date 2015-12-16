package tasks;

import java.util.ArrayList;

import abstractClasses.LockedToGrid;
import people.Person;

public abstract class Task extends LockedToGrid {

	private static ArrayList<Task> globalTaskList = new ArrayList<Task>();

	protected int player;

	protected long timeCost, timeSpent;

	protected Person personAssigned;

	protected ArrayList<Task> dependentTasks;

	public Task(int row, int col, int player, double timeCost) {
		super(row, col);
		this.player = player;
		this.timeCost = Math.round(timeCost * 1000);
		dependentTasks = new ArrayList<Task>();
	}

	public Task getNext() {
		if (dependentTasks.size() != 0) {
			if (!dependentTasks.get(0).isDone()) {
				return dependentTasks.get(0);
			} else {
				dependentTasks.remove(0);
				return getNext();
			}
		} else {
			return this;
		}
	}

	public static void addTask(Task t) {
		globalTaskList.add(t);
	}

	public static boolean hasApplicableTaskFor(Person p) {
		for (int i = 0; i < globalTaskList.size(); i++) {
			Task cur = globalTaskList.get(i);

			if (cur.getTypeNeeded().isInstance(p)
					&& cur.getPlayer() == p.getPlayer()) {
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

			if (cur.getTypeNeeded().isInstance(p)) {
				if (cur.getPriorty() > highestPriorty) {
					mostImportant = cur;
					highestPriorty = cur.getPriorty();
				}
			}
		}

		globalTaskList.remove(mostImportant);

		return mostImportant;
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
			return (Class<Person>) Class.forName("Person");
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
		this.timeSpent += timeSpent;
	}
}
