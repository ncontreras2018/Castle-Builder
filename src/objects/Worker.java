package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import abstractClasses.Person;

public class Worker extends Person {

	public Worker(double xPos, double yPos, double speed) {
		super(xPos, yPos, speed);
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.BLUE);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
