package main;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import objects.VisibleObject;
import tasks.Construction;
import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	private JFrame frame;
	private Map map;

	private int cameraX, cameraY;
	private double zoom;
	private final double MIN_ZOOM = .4, MAX_ZOOM = 2;
	private AffineTransform cameraTransform;

	private Menu sideMenu;

	public GamePanel(int width, int height, Map map) {

		this.map = map;

		setUpFrame(width, height);

		setUpCamera();
	}

	public Map getMap() {
		return map;
	}

	private void setUpCamera() {
		cameraX = 500;
		cameraY = 300;

		zoom = MIN_ZOOM;
	}

	public int[] adjustPointForCamera(int oldX, int oldY) {

		AffineTransform inverse = null;

		try {
			inverse = cameraTransform.createInverse();
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}

		Point point = new Point(oldX, oldY);

		inverse.transform(point, point);

		return new int[] { point.x, point.y };

	}

	public void moveCamera(int deltaX, int deltaY) {
		cameraX += deltaX;
		cameraY += deltaY;
	}

	public void zoomCamera(double deltaZoom) {
		zoom += deltaZoom;

		zoom = (zoom < MIN_ZOOM) ? MIN_ZOOM : zoom;

		zoom = (zoom > MAX_ZOOM) ? MAX_ZOOM : zoom;
	}

	private void setUpFrame(int width, int height) {

		frame = new JFrame();

		frame.add(this);

		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		frame.setPreferredSize(screenSize);

		frame.setMaximumSize(screenSize);

		frame.setMinimumSize(new Dimension(width, height));

		sideMenu = new Menu(this);

		sideMenu.setBounds(600, 600, width, height);

		this.add(sideMenu);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.pack();

		frame.setVisible(true);
	}

	public void update() {
		for (LockedToGrid[][] row : map.getGrid()) {
			for (LockedToGrid cur[] : row) {
				for (LockedToGrid layer : cur) {
					if (layer != null) {
						layer.update();
					}
				}
			}
		}

		for (UnlockedFromGrid cur : map.getUnlockedObjects()) {
			cur.update();
			cur.updateInternalTime();
		}

	}

	public JFrame getFrame() {
		return frame;
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		AffineTransform defaultTransform = g2d.getTransform();

		g2d.setColor(Color.BLACK);

		g2d.fillRect(0, 0, getWidth(), getHeight());

		cameraTransform = new AffineTransform();

		cameraTransform.translate(getWidth() / 2, getHeight() / 2);

		cameraTransform.scale(zoom, zoom);

		cameraTransform.translate(-getWidth() / 2, -getHeight() / 2);

		cameraTransform.translate(-cameraX, -cameraY);

		cameraTransform.translate(getWidth() / 2, getHeight() / 2);

		g2d.transform(cameraTransform);

		for (LockedToGrid[][] row : map.getGrid()) {
			for (LockedToGrid cur[] : row) {
				for (LockedToGrid layer : cur) {
					if (layer != null) {
						if (layer instanceof Drawable) {
							((Drawable) layer).draw(g2d, false);
						}
					}
				}
			}
		}

		for (UnlockedFromGrid cur : map.getUnlockedObjects()) {
			cur.draw(g2d, false);
		}

		g2d.setTransform(defaultTransform);

		sideMenu.draw(g2d, false);
	}
}
