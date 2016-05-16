package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;
import interfaces.Valuable;
import main.Player;
import people.Person;
import tasks.Demolition;
import tasks.Task;

public class Gate extends LockedToGrid implements Valuable {

	private boolean isOpen;

	public Gate(int row, int col, Player p) {
		super(row, col, p);

		isOpen = false;
	}

	public Gate(Integer row, Integer col, Player p) {
		this(row.intValue(), col.intValue(), p);
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {

		if (isTransparent) {
			g2d.setColor(Color.LIGHT_GRAY);
		} else {
			g2d.setColor(Color.DARK_GRAY);
		}

		if (!isOpen) {
			g2d.fillRect(getTopLeftX(), getTopLeftY(), getMap().getTileSize(), getMap().getTileSize());
		}

		g2d.setColor(g2d.getColor().darker());

		g2d.drawRect(getTopLeftX() + getMap().getTileSize() / 4, getTopLeftY() + getMap().getTileSize() / 4,
				getMap().getTileSize() / 2, getMap().getTileSize() / 2);
	}

	@Override
	public int getOreValue() {
		return 10;
	}

	@Override
	public double getReturnPercentage() {
		return 0.6;
	}

	@Override
	public double movementPenalty(UnlockedFromGrid other) {
		return 3;
	}

	@Override
	public boolean canMoveThrough(UnlockedFromGrid obj) {
		if (obj instanceof Person) {
			return ((Person) obj).getPlayer().equals(getPlayer());
		}
		return true;
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
	public void update() {
		ArrayList<UnlockedFromGrid> objects = getMap().getObjectsAt(getRow(), getCol());

		for (UnlockedFromGrid cur : objects) {
			if (cur.getPlayer().equals(getPlayer())) {
				isOpen = true;
				return;
			}
		}
		isOpen = false;
	}
}
