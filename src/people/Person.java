package people;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import tasks.Task;
import threads.PathfindingThread;
import util.Util;
import abstractClasses.UnlockedFromGrid;

abstract public class Person extends UnlockedFromGrid {

	protected int player;
	private static PathfindingThread pathfinder;

	private Task currentTask;

	public Person(double xPos, double yPos, double speed, int player) {
		super(xPos, yPos, speed, 20);
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
		System.out.println("Has Task: " + Task.hasApplicableTaskFor(this) + " or " + (currentTask != null));
		return Task.hasApplicableTaskFor(this) || currentTask != null;
	}

	protected void doTasks() {

		System.out.println("-----------------------------------");

		System.out.println("Doing Tasks");

		if (currentTask == null) {

			System.out.println("Current Task is null");

			currentTask = Task.takeNextApplicableTaskFor(this);

			currentTask.getNext().assignPerson(this);

			pathfinder.requestPath(this, currentTask.getNext().getCenterX(), currentTask.getNext().getCenterY());

			System.out.println("Got new Task: " + currentTask);
		} else {

			System.out.println("Current Task is not null");

			if (pathfinder.getPathFor(this) != null) {

				System.out.println("Path is not null");

				if (!pathfinder.getPathFor(this).isEmpty()) {

					setCenterLocation(pathfinder.getPathFor(this).remove(0));

					System.out.println("Moved up path to: " + getX() + " " + getY());
				}

				if (currentTask.getNext().isAtLocation(this)) {

					System.out.println("At job location, doing work");

					currentTask.getNext().doWork(millisSinceLastUpdate);

					if (currentTask.isDone()) {
						currentTask = null;
					}
				}
			} else {
				System.out.println("Path is null");
			}
		}
	}

	abstract protected void doesntDoTasks();
}
