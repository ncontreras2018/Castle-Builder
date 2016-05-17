package main;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import abstractClasses.Existent;
import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;
import interfaces.Valuable;
import listeners.KeyListener;
import listeners.MouseListener;
import objects.Wall;
import people.Farmer;
import people.Miner;
import people.Person;
import people.Worker;
import tasks.Construction;
import tasks.Demolition;
import tasks.Task;

public class Player implements Serializable {

	private Color playersColor;

	private int number;

	private int ore;
	private int reservedOre;

	private int food;

	private GamePanel gamePanel;

	private Class<?> itemToPlace;

	private KeyListener keyListener;
	private MouseListener mouseListener;

	public Player(Color playersColor, int number, GamePanel gamePanel, KeyListener keyListener,
			MouseListener mouseListener) {
		this.playersColor = playersColor;
		this.number = number;
		this.gamePanel = gamePanel;

		this.keyListener = keyListener;
		this.mouseListener = mouseListener;
		
		food = 1000;

		placeStartingPeople();
	}

	private void placeStartingPeople() {
		int centerX = (gamePanel.getMap().getGrid()[0].length * gamePanel.getMap().getTileSize()) / 2;
		int centerY = (gamePanel.getMap().getGrid().length * gamePanel.getMap().getTileSize()) / 2;

		for (int i = 0; i < 1; i++) {
			gamePanel.getMap().addUnlockedObject(new Worker(centerX - gamePanel.getMap().getTileSize(), centerY, this));

			gamePanel.getMap().addUnlockedObject(new Miner(centerX + gamePanel.getMap().getTileSize(), centerY, this));

			gamePanel.getMap().addUnlockedObject(new Farmer(centerX, centerY + gamePanel.getMap().getTileSize(), this));
		}
	}

	public int getNumber() {
		return number;
	}

	public Color getColor() {
		return playersColor;
	}

	public void addFood(int amount) {
		food += amount;
	}

	public void removeFood(int amount) {
		food -= amount;
	}

	public int getAmountOfFood() {
		return food;
	}

	public void addOre(int amount) {
		ore += amount;
		System.err.println("Added: " + amount + " Total: " + ore);
	}

	public void removeOre(int amount) {
		ore -= amount;
		System.err.println("Removed: " + amount + " Total: " + ore);
	}

	public int getAmountOfOre() {
		return ore;
	}

	public void update() {
	}

	public boolean reserveOre(int amount) {
		if (ore - reservedOre < amount) {
			return false;
		}
		reservedOre += amount;
		return true;
	}

	public void unReserveOre(int amount) {
		reservedOre -= amount;
	}

	public void mouseClicked(MouseEvent e) {

		System.out.println("Button Pressed: " + e.getButton());

		int[] adjustedPos = gamePanel.adjustPointForCamera(e.getX(), e.getY());

		int[] rowCol = gamePanel.getMap().convertXYtoRowCol(adjustedPos[0], adjustedPos[1]);

		if (rowCol != null) {

			if (rowCol[0] > 0 && rowCol[0] < gamePanel.getMap().numRows() - 1) {

				if (rowCol[1] > 0 && rowCol[1] < gamePanel.getMap().numCols() - 1) {

					if (e.getButton() == MouseEvent.BUTTON1) {

						System.out.println("ITEM TO PLACE: " + itemToPlace);

						if (itemToPlace.getSuperclass().equals(Person.class)) {
							Person p;
							try {

								p = (Person) itemToPlace.getConstructor(Integer.class, Integer.class, Player.class)
										.newInstance(adjustedPos[0], adjustedPos[1], this);
							} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
									| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
								e1.printStackTrace();
								return;
							}

							if (p.getFoodCost() <= getAmountOfFood()) {
								food -= p.getFoodCost();
								gamePanel.getMap().addUnlockedObject(p);
							}

						} else {

							Task newTask;
							try {
								newTask = new Construction(rowCol[0], rowCol[1], gamePanel.getPlayer(),
										(LockedToGrid) itemToPlace
												.getConstructor(Integer.class, Integer.class, Player.class)
												.newInstance(rowCol[0], rowCol[1], this));
							} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
									| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
								e1.printStackTrace();
								return;
							}

							gamePanel.getMap().getGrid()[rowCol[0]][rowCol[1]][2] = newTask;

							Task.addTask(newTask);
						}
					} else if (e.getButton() == MouseEvent.BUTTON3) {

						LockedToGrid[] tileClicked = gamePanel.getMap().getGrid()[rowCol[0]][rowCol[1]];

						if (tileClicked[2] != null) {
							((Task) tileClicked[2]).remove();
						}

						System.out.println("Create Demo? Object at = " + tileClicked[1]);

						if (tileClicked[1] != null) {

							System.out.println("Creating Demo Task");

							Task newTask = new Demolition(rowCol[0], rowCol[1], gamePanel.getPlayer(), null);

							tileClicked[2] = newTask;

							Task.addTask(newTask);
						}
					}
				}
			}
		} else {
			System.out.println("Click was off map");
		}
	}

	public void setItemToPlace(Class<?> item) {
		this.itemToPlace = item;
	}
}
