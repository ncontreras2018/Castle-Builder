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

	public Wall(Integer row, Integer col, Player p) {
		this(row.intValue(), col.intValue(), p);
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {

		g2d.setColor(Color.DARK_GRAY);

		if (isTransparent) {
			g2d.setColor(new Color(g2d.getColor().getRed(), g2d.getColor().getGreen(), g2d.getColor().getBlue(), 200));
		}

		g2d.fillRect(getTopLeftX(), getTopLeftY(), getMap().getTileSize(), getMap().getTileSize());

		g2d.setColor(Color.BLACK);

		g2d.drawRect(getTopLeftX(), getTopLeftY(), getMap().getTileSize(), getMap().getTileSize());
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
