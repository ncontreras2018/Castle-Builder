package people;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import tasks.Task;
import threads.PathfindingThread;
import throwables.IllegalLocationException;
import util.Util;
import abstractClasses.UnlockedFromGrid;

abstract public class Person extends UnlockedFromGrid {

	private int player;
	private static PathfindingThread pathfinder;

	private Task currentTask;

	public Person(int xPos, int yPos, double speed, int player) throws IllegalLocationException {
		super(xPos, yPos, speed, 20);
		this.player = player;

		System.out.println("New person at X: " + getX() + " Y: " + getY());
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

	/**
	 * Moves the Person towards the row/col tile at the speed set by getSpeed()
	 * 
	 * @return the result, where 0 indicates normal movement, 1 indicates an
	 *         arrival, and -1 indicates an inability to move
	 */

	private int moveTowardsTile(int row, int col) {

		double newX = getX();

		double newY = getY();

		int state = 1;

		int targX = (col * getMap().getTileSize()) + (getMap().getTileSize() / 2);
		int targY = (row * getMap().getTileSize()) + (getMap().getTileSize() / 2);

		if (Math.abs(getX() - targX) > getSpeed()) {
			newX += ((targX - getX()) / Math.abs(targX - getX())) * getSpeed();
			state = 0;
		} else {
			newX = targX;
		}

		if (Math.abs(getY() - targY) > getSpeed()) {
			newY += ((targY - getY()) / Math.abs(targY - getY())) * getSpeed();
			state = 0;
		} else {
			newY = targY;
		}

		if (!this.setLocation(newX, newY)) {
			state = -1;
		}

		return state;
	}

	protected void doTasks() {

		System.out.println("-----------------------------------");

		System.out.println("Doing Tasks");

		if (currentTask == null) {

			System.out.println("Current Task is null");

			currentTask = Task.takeNextApplicableTaskFor(this);

			currentTask.assignPerson(this);

			pathfinder.requestPath(this, currentTask.getRow(), currentTask.getCol());

			System.out.println("Got new Task: " + currentTask);
		} else {

			System.out.println("Current Task is not null");

			if (pathfinder.getPathFor(this) != null) {

				System.out.println("Path is not null");

				if (currentTask.isAtLocation(this)) {

					System.out.println("At job location, doing work");

					currentTask.doWork(getMillisSinceLastUpdate());

					if (currentTask.isDone()) {
						currentTask.finish();
						currentTask = null;
					}
				} else {

					if (!pathfinder.getPathFor(this).isEmpty()) {

						int[] nextTile = (pathfinder.getPathFor(this).get(0));

						int result = moveTowardsTile(nextTile[0], nextTile[1]);

						if (result == 1) {
							pathfinder.getPathFor(this).remove(0);
						} else if (result == -1) {
							pathfinder.requestPath(this, currentTask.getRow(), currentTask.getCol());
						}

						System.out.println("Moved up path to: " + getX() + " " + getY());
					}
				}
			} else {
				System.out.println("Path is null");
			}
		}
	}

	abstract protected void doesntDoTasks();
}
