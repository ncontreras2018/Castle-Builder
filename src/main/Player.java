package main;

import java.awt.Color;

import abstractClasses.Existent;
import people.Miner;
import people.Worker;

public class Player {

	private Color playersColor;

	private int number;

	private int ore;

	private GamePanel gamePanel;

	public Player(Color playersColor, int number, GamePanel gamePanel) {
		this.playersColor = playersColor;
		this.number = number;
		this.gamePanel = gamePanel;

		placeStartingPeople();
	}

	private void placeStartingPeople() {
		int centerX = (gamePanel.getMap().getGrid()[0].length * gamePanel.getMap().getTileSize()) / 2;
		int centerY = (gamePanel.getMap().getGrid().length * gamePanel.getMap().getTileSize()) / 2;

		gamePanel.getMap().addUnlockedObject(new Worker(centerX - gamePanel.getMap().getTileSize(), centerY, this));
		
		gamePanel.getMap().addUnlockedObject(new Miner(centerX + gamePanel.getMap().getTileSize(), centerY, this));
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

	public int getAmountOfOre() {
		return ore;
	}
}
