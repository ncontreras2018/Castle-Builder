package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JPanel;

import interfaces.Drawable;

public class Menu extends Container implements Drawable {

	private GamePanel gamePanel;

	private double menuSize = .2;

	private Rectangle buttonMenu;
	private Rectangle hideButton;

	public Menu(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		JButton b = new JButton("Hello");
		
		this.add(b);
		
		System.out.println("button " + b.getX() + " " + b.getY());

	}

	@Override
	public void draw(Graphics2D g2d, boolean isTransparent) {

		Dimension windowSize = gamePanel.getSize();

		int menuWidth = (int) (windowSize.getWidth() * menuSize);

		int xPos = (int) (windowSize.getWidth() - menuWidth);

		int topBottomInsets = gamePanel.getFrame().getInsets().bottom + gamePanel.getFrame().getInsets().top;

		g2d.setColor(Color.GRAY);

		g2d.fillRect(xPos, 0, menuWidth, gamePanel.getFrame().getMinimumSize().height - topBottomInsets);
	}
}
