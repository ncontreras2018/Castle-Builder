package abstractClasses;

import java.awt.Color;
import java.awt.Graphics2D;

abstract public class Person extends UnlockedFromGrid {
	
	protected int player;

	public Person(double xPos, double yPos, double speed) {
		super(xPos, yPos, speed);
	}
}
