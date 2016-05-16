package objects;

import interfaces.Drawable;
import interfaces.Valuable;
import main.Player;
import tasks.Demolition;
import tasks.Mining;

import java.awt.Color;
import java.awt.Graphics2D;

import abstractClasses.LockedToGrid;
import tasks.Task;
import abstractClasses.UnlockedFromGrid;

public class Wall extends LockedToGrid implements Valuable {
	
	public Wall(int row, int col, Player p) {
		super(row, col, p);
	}
	
	public Wall (Integer row, Integer col, Player p) {
		this(row.intValue(), col.intValue(), p);
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {

		if (isTransparent) {
			g2d.setColor(Color.LIGHT_GRAY);
		} else {
			g2d.setColor(Color.DARK_GRAY);
		}

		g2d.fillRect(getTopLeftX(), getTopLeftY(), getMap().getTileSize(), getMap().getTileSize());
	}

	@Override
	public void update() {
	}

	@Override
	public double movementPenalty(UnlockedFromGrid other) {
		return 10000;
	}

	@Override
	public boolean canMoveThrough(UnlockedFromGrid obj) {
		return false;
	}
	
	@Override
	public boolean taskCanBePreformed(Task attemptedTask) {
		
		if (attemptedTask instanceof Demolition) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getOreValue() {
		return 5;
	}

	@Override
	public double getReturnPercentage() {
		return 0.6;
	}
}
