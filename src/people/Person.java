package people;

import interfaces.Drawable;
import main.Player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import tasks.Task;
import threads.PathfindingThread;
import throwables.IllegalLocationException;
import abstractClasses.UnlockedFromGrid;

abstract public class Person extends UnlockedFromGrid {

	private static PathfindingThread pathfinder;

	private Task currentTask;

	public Person(int xPos, int yPos, double speed, Player player) {
		super(xPos, yPos, speed, 20, player);

		System.out.println("New person at X: " + getX() + " Y: " + getY());
	}

	public static void setPathfinder(PathfindingThread pf) {
		pathfinder = pf;
	}

	@Override
	public void update() {

		System.out.println("UPDATE " + this + " ----------------------------");

		if (shouldDoTasks()) {
			doTasks();
		} else {
			doesntDoTasks();
		}
	}

	protected PathfindingThread getPathfinder() {
		return pathfinder;
	}

	/**
	 * Returns if the person object should continue on it's task list (calls
	 * doTasks) or not (calls doesntDoTasks)
	 * 
	 * @return Should the person work on his tasks
	 */

	protected boolean shouldDoTasks() {
		System.out.println("Has Task: " + (currentTask != null) + " or " + Task.hasApplicableTaskFor(this));
		return currentTask != null || Task.hasApplicableTaskFor(this);
	}

	/**
	 * Moves the Person towards the row/col tile at the speed set by getSpeed()
	 * 
	 * @return the result, where 0 indicates normal movement, 1 indicates an
	 *         arrival, and -1 indicates an inability to move
	 */

	public void releaseTask() {
		currentTask = null;
	}

	private boolean moveTowardsTile(int row, int col) {

		double newX = getX();

		double newY = getY();

		boolean arrived = true;

		int targX = (col * getMap().getTileSize()) + (getMap().getTileSize() / 2);
		int targY = (row * getMap().getTileSize()) + (getMap().getTileSize() / 2);

		if (Math.abs(getX() - targX) > getSpeed()) {
			newX += ((targX - getX()) / Math.abs(targX - getX())) * getSpeed();
			arrived = false;
		} else {
			newX = targX;
		}

		if (Math.abs(getY() - targY) > getSpeed()) {
			newY += ((targY - getY()) / Math.abs(targY - getY())) * getSpeed();
			arrived = false;
		} else {
			newY = targY;
		}

		this.setLocation(newX, newY);

		return arrived;
	}

	protected void autoMoveTo(int row, int col, boolean adjacent) {

		System.out.println("Auto move to Row: " + row + " Col " + col);

		if (pathfinder.getPathFor(this) == null) {

			System.out.println("Path is null");

			if (!pathfinder.hasRequestFor(this)) {
				System.out.println("There is no request, placing one");
				pathfinder.requestPath(this, row, col, adjacent);
			}
		} else if (pathfinder.getPathFor(this).isEmpty()) {

			System.out.println("Path is empty, requesting new one");
			pathfinder.requestPath(this, row, col, adjacent);
		} else {

			System.out.println("Path is not null or empty");

			int[] nextTile = pathfinder.getPathFor(this).get(0);

			System.out.println("Next Tile: Row: " + nextTile[0] + " Col: " + nextTile[1]);

			if (!getMap().canPassThroughTile(nextTile[0], nextTile[1], this)) {
				System.out
						.println(this + " cannot pass through next tile: Row: " + nextTile[0] + " Col: " + nextTile[1]);

				if (getMap().canPassThroughTile(getRow(), getCol(), this)) {
					System.out.println(this + " is in a passable tile, removing old path");
					pathfinder.removePathFor(this);
				} else {
					System.out.println(this + " is stuck and will be allowed to free itself");
				}
			}

			boolean arrived = moveTowardsTile(nextTile[0], nextTile[1]);

			System.out.println("Arrived at next tile: " + arrived);

			if (arrived) {
				pathfinder.getPathFor(this).remove(0);
			}
		}
	}

	protected void doTasks() {

		System.out.println("Doing Tasks");

		if (currentTask == null) {

			System.out.println("Current Task is null");

			currentTask = Task.takeNextApplicableTaskFor(this);

			if (currentTask != null) {
				currentTask.assignPerson(this);
			}

			System.out.println("Got new Task: " + currentTask);

		} else {
			System.out.println("Current Task is not null: " + currentTask);

			if (currentTask.isAtLocation(this)) {

				System.out.println("At Task Location");

				currentTask.doWork(getMillisSinceLastUpdate());

				if (currentTask.isDone()) {

					System.out.println(this + " has finished task " + currentTask);

					currentTask.finish();
					currentTask = null;
					pathfinder.removePathFor(this);
				}
			} else {
				autoMoveTo(currentTask.getRow(), currentTask.getCol(), currentTask.shouldBeAdjacent());
			}
		}
	}

	abstract protected void doesntDoTasks();
}
