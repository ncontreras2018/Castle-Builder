package main;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import abstractClasses.Existent;
import abstractClasses.LockedToGrid;
import interfaces.Valuable;
import listeners.KeyListener;
import listeners.MouseListener;
import objects.Wall;
import people.Miner;
import people.Worker;
import tasks.Construction;
import tasks.Demolition;
import tasks.Task;

public class Player implements Serializable {

	private Color playersColor;

	private int number;

	private int ore;
	private int reservedOre;

	private GamePanel gamePanel;

	private KeyListener keyListener;
	private MouseListener mouseListener;

	public Player(Color playersColor, int number, GamePanel gamePanel, KeyListener keyListener,
			MouseListener mouseListener) {
		this.playersColor = playersColor;
		this.number = number;
		this.gamePanel = gamePanel;

		this.keyListener = keyListener;
		this.mouseListener = mouseListener;

		placeStartingPeople();
	}

	private void placeStartingPeople() {
		int centerX = (gamePanel.getMap().getGrid()[0].length * gamePanel.getMap().getTileSize()) / 2;
		int centerY = (gamePanel.getMap().getGrid().length * gamePanel.getMap().getTileSize()) / 2;

		for (int i = 0; i < 1; i++) {
			gamePanel.getMap().addUnlockedObject(new Worker(centerX - gamePanel.getMap().getTileSize(), centerY, this));

			gamePanel.getMap().addUnlockedObject(new Miner(centerX + gamePanel.getMap().getTileSize(), centerY, this));
		}
	}

	public int getNumber() {
		return number;
	}

	public Color getColor() {
		return playersColor;
	}

	public void addOre(int amount) {
		ore += amount;
	}
	
	public void removeOre(int amount) {
		ore -= amount;
	}

	public int getAmountOfOre() {
		return ore;
	}

	public void update() {
		createNewPeople();
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

	private void createNewPeople() {

		System.out.println("KeyEvent: " + KeyEvent.VK_1);

		int[] mouseLoc = mouseListener.getAdjustedLocation();

		if (keyListener.keyPressed(KeyEvent.VK_1)) {

			if (gamePanel.isPointOnMap(mouseLoc[0], mouseLoc[1])) {

				System.out.println("Create new worker");

				gamePanel.getMap().addUnlockedObject(new Worker(mouseLoc[0], mouseLoc[1], this));
			}
		} else if (keyListener.keyPressed(KeyEvent.VK_2)) {

			if (gamePanel.isPointOnMap(mouseLoc[0], mouseLoc[1])) {

				System.out.println("Create new miner");

				gamePanel.getMap().addUnlockedObject(new Miner(mouseLoc[0], mouseLoc[1], this));
			}
		}
	}

	public void mouseClicked(MouseEvent e) {

		System.out.println("Button Pressed: " + e.getButton());

		int[] adjustedPos = gamePanel.adjustPointForCamera(e.getX(), e.getY());

		int[] rowCol = gamePanel.getMap().convertXYtoRowCol(adjustedPos[0], adjustedPos[1]);

		if (rowCol != null) {

			if (rowCol[0] > 0 && rowCol[0] < gamePanel.getMap().numRows() - 1) {

				if (rowCol[1] > 0 && rowCol[1] < gamePanel.getMap().numCols() - 1) {

					if (e.getButton() == MouseEvent.BUTTON1) {

						Task newTask = new Construction(rowCol[0], rowCol[1], gamePanel.getPlayer(),
								new Wall(rowCol[0], rowCol[1]));

						gamePanel.getMap().getGrid()[rowCol[0]][rowCol[1]][2] = newTask;

						Task.addTask(newTask);
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
}
