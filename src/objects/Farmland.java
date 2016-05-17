package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;
import interfaces.Valuable;
import main.Player;
import tasks.Construction;
import tasks.Farming;
import tasks.Task;

public class Farmland extends LockedToGrid implements Valuable {
	
	private static final long GROWING_TIME = 10000;
	
	private long timeSinceHarvest;
	private boolean isGrown;

	public Farmland(Integer row, Integer col, Player p) {
		super(row, col, p);
		
		isGrown = false;
		timeSinceHarvest = 0;
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {
		
		g2d.setColor(Color.YELLOW);
		
		if (isTransparent) {
			g2d.setColor(new Color(g2d.getColor().getRed(), g2d.getColor().getGreen(), g2d.getColor().getBlue(), 200));
		}

		g2d.fillRect(getTopLeftX(), getTopLeftY(), getMap().getTileSize(), getMap().getTileSize());
	
		if (isGrown) {
			g2d.setColor(Color.GREEN);
			
			g2d.fillRect(getTopLeftX() + getMap().getTileSize() / 4, getTopLeftY() + getMap().getTileSize() / 4,
					getMap().getTileSize() / 2, getMap().getTileSize() / 2);
		}
		
		g2d.setColor(Color.BLACK);

		g2d.drawRect(getTopLeftX(), getTopLeftY(), getMap().getTileSize(), getMap().getTileSize());
	}

	@Override
	public double movementPenalty(UnlockedFromGrid other) {
		return 2;
	}

	@Override
	public boolean canMoveThrough(UnlockedFromGrid obj) {
		return true;
	}

	@Override
	public boolean taskCanBePreformed(Task attemptedTask) {
		return attemptedTask instanceof Farming;
	}
	
	public void harvest() {
		isGrown = false;
		timeSinceHarvest = 0;
	}

	@Override
	public void update() {
		timeSinceHarvest += getMillisSinceLastUpdate();
		
		if (timeSinceHarvest >= GROWING_TIME && !isGrown) {
			isGrown = true;
			
			Farming farming = new Farming(getRow(), getCol(), getPlayer());
			
			getMap().getGrid()[getRow()][getCol()][2] = farming;
			
			Task.addTask(farming);
		}
	}

	@Override
	public int getOreValue() {
		return 5;
	}

	@Override
	public double getReturnPercentage() {
		return 0;
	}
}
