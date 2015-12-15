package main;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	private JFrame frame;
	
	private Map map;
	
	private int cameraX, cameraY;
	
	private double zoom;

	public GamePanel(int width, int height, Map map) {
		
		this.map = map;
		
		cameraX = 0;
		cameraY = 0;
		zoom = 1;
		
		setUpFrame(width, height);
	}
	
	public void moveCamera(int deltaX, int deltaY) {
		cameraX += deltaX;
		cameraY += deltaY;
	}

	private void setUpFrame(int width, int height) {
		frame = new JFrame();

		frame.add(this);

		setPreferredSize(new Dimension(width, height));

		frame.setResizable(false);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.pack();

		frame.setVisible(true);
	}
	
	public void update() {
		for (LockedToGrid[] row : map.getGrid()) {
			for (LockedToGrid cur : row) {
				cur.update();
			}
		}
		
		for (UnlockedFromGrid cur : map.getUnlockedObjects()) {
			cur.update();
		}
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		AffineTransform camera = new AffineTransform();
		
		camera.translate(-cameraX, -cameraY);
		
		camera.scale(zoom, zoom);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.BLACK);
		
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		g2d.transform(camera);
		
		for (LockedToGrid[] row : map.getGrid()) {
			for (LockedToGrid cur : row) {
				cur.draw(g2d);
			}
		}
		
		for (UnlockedFromGrid cur : map.getUnlockedObjects()) {
			cur.draw(g2d);
		}
	}
}
