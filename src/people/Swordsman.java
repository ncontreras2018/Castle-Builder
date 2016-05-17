package people;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import ezPoly.EzPoly;
import main.Player;

public class Swordsman extends Person {

	public Swordsman(Integer xPos, Integer yPos, Player player) {
		super(xPos, yPos, 1, player);
	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {
		g2d.setColor(getPlayer().getColor());

		g2d.draw(new EzPoly(getTopLeftX(), getTopLeftY(), EzPoly.FIVE_POINT_STAR, getSize() / 4));
	}

	@Override
	public int getFoodCost() {
		return 25;
	}

	@Override
	protected void doesntDoTasks() {
	}
}
