package people;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import tasks.Task;
import threads.PathfindingThread;
import util.Util;
import abstractClasses.UnlockedFromGrid;

abstract public class Person extends UnlockedFromGrid implements Drawable {

	protected int player;
	private static PathfindingThread pathfinder;

	private Task currentTask;

	public Person(double xPos, double yPos, double speed, int player) {
		super(xPos, yPos, speed);
		this.player = player;
	}

	public static void setPathfinder(PathfindingThread pf) {
		pathfinder = pf;
	}

	@Override
	public void update() {
		if (shouldDoTasks()) {
			doTasks();
		} else {
			doesntDoTasks();
		}
	}
	
	public int getPlayer() {
		return player;
	}

	/**
	 * Returns if the person object should continue on it's task list (calls
	 * doTasks) or not (calls doesntDoTasks)
	 * 
	 * @return Should the person work on his tasks
	 */

	protected boolean shouldDoTasks() {
		return Task.hasApplicableTaskFor(this);
	}

	protected void doTasks() {

		if (currentTask == null) {

			currentTask = Task.takeNextApplicableTaskFor(this);

			currentTask.getNext().assignPerson(this);
		}

		if (pathfinder.getFinalDestFor(this) != null) {

			if (!Util.isAdjacentTo(currentTask.getNext(),
					pathfinder.getFinalDestFor(this))) {
				pathfinder.removePathFor(this);
				pathfinder.requestPath(this,
						currentTask.getNext().getCenterX(), currentTask
								.getNext().getCenterY());
			}

			if (!pathfinder.getPathFor(this).isEmpty()) {
				setLocation(pathfinder.getPathFor(this).remove(0));
			}
		}

		if (Util.isAdjacentTo(this, currentTask.getNext())) {
			currentTask.getNext().doWork(millisSinceLastUpdate);

			if (currentTask.isDone()) {
				currentTask = null;
			}
		}
	}

	abstract protected void doesntDoTasks();
}
