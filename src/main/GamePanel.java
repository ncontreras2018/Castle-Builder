package main;

import interfaces.Drawable;
import listeners.KeyListener;
import listeners.MouseListener;
import menus.Menu;
import menus.PauseButton;
import people.Person;

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
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import tasks.Construction;
import threads.ControlThread;
import threads.GameThread;
import threads.GraphicsThread;
import threads.PathfindingThread;
import abstractClasses.LockedToGrid;
import abstractClasses.UnlockedFromGrid;
import gameIO.GameIO;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Serializable {

	private JFrame frame;
	private Map map;

	private boolean gamePaused;

	private int cameraX, cameraY;
	private double zoom;
	private final double MIN_ZOOM = .3, MAX_ZOOM = 2;
	private AffineTransform cameraTransform;

	private Player player;

	private Menu menu;

	private int mapRows = 121, mapCols = 81;

	private int windowWidth = 1000, windowHeight = 600;

	private int tileSize = 30;

	private KeyListener keyListener;
	private MouseListener mouseListener;

	private GameThread gameThread;
	private ControlThread controlThread;
	private GraphicsThread graphicsThread;
	private PathfindingThread pathfindingThread;

	public GamePanel() {

		map = new Map(mapRows, mapCols, tileSize);

		setUpFrame(windowWidth, windowHeight);

		setUpCamera();

		setUpListeners();

		player = new Player(Color.BLUE, 1, this, keyListener, mouseListener);

		setUpThreads();

		setUpMenu();
	}

	private void setUpMenu() {
		menu = new Menu(this);
	}

	private void setUpThreads() {

		gameThread = new GameThread(60, this);

		graphicsThread = new GraphicsThread(60, this);

		controlThread = new ControlThread(60, this, keyListener, mouseListener);

		pathfindingThread = new PathfindingThread(map);

		Person.setPathfinder(pathfindingThread);

		gameThread.start();

		graphicsThread.start();

		controlThread.start();

		pathfindingThread.start();
	}

	private void setUpListeners() {
		keyListener = new KeyListener();

		getFrame().addKeyListener(keyListener);

		mouseListener = new MouseListener(this, getPlayer());

		this.addMouseListener(mouseListener);

		this.addMouseMotionListener(mouseListener);

		this.addMouseWheelListener(mouseListener);
	}

	public Map getMap() {
		return map;
	}

	public Player getPlayer() {
		return player;
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
		} catch (NullPointerException e) {
			return new int[] { oldX, oldY };
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

		for (int i = 0; i < map.getUnlockedObjects().size(); i++) {
			UnlockedFromGrid cur = map.getUnlockedObjects().get(i);
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

		for (int i = 0; i < map.getUnlockedObjects().size(); i++) {
			map.getUnlockedObjects().get(i).draw(g2d, false);
		}

		g2d.setTransform(defaultTransform);

		menu.display(g2d);
	}

	public boolean isPointOnMap(int x, int y) {
		if (x > 0 && x < map.numCols() * map.getTileSize()) {
			if (y > 0 && y < map.numRows() * map.getTileSize()) {
				return true;
			}
		}
		return false;
	}

	public Menu getMenu() {
		return menu;
	}

	public void closeGame() {
		// TODO Auto-generated method stub

	}

	public boolean saveGame() {

		System.out.println("Saving");

		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		fileChooser.setFileFilter(new FileNameExtensionFilter("Castle Builder Saves", "castle"));

		fileChooser.setDialogTitle("Save Game");

		File saveFile = fileChooser.getCurrentDirectory();

		while (true) {

			while (true) {

				System.out.println("Selected File: " + saveFile);

				String path = null;
				try {
					path = saveFile.getCanonicalPath();
				} catch (IOException e) {
					e.printStackTrace();
				}

				String[] buttonNames = new String[] { "Save Game", "Change Save Folder" };

				int confirmSaveDirResult = JOptionPane.showOptionDialog(null, "The Game Will Be Saved In:\n" + path,
						"Save Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttonNames, null);

				if (confirmSaveDirResult == JOptionPane.YES_OPTION) {
					break;
				} else if (confirmSaveDirResult == JOptionPane.NO_OPTION) {

					int result = fileChooser.showSaveDialog(null);

					if (result == JFileChooser.APPROVE_OPTION) {
						saveFile = fileChooser.getSelectedFile();
					}

				} else if (confirmSaveDirResult == JOptionPane.CLOSED_OPTION) {
					return false;
				}
			}

			if (saveFile.isDirectory()) {

				String[] options = { "Save", "Go Back" };
				JPanel panel = new JPanel();
				JLabel lbl = new JLabel("Enter Save Name:");
				JTextField txt = new JTextField(10);

				panel.add(lbl);
				panel.add(txt);

				System.out.println("Displaying Name Popup");

				int chooseSaveNameResult = JOptionPane.showOptionDialog(null, panel, "Save Game",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

				System.out.println("Option Picked: " + chooseSaveNameResult + " Yes = " + JOptionPane.YES_OPTION);

				if (chooseSaveNameResult == JOptionPane.YES_OPTION) {

					System.out.println("About to save");

					String userEnteredName = txt.getText();

					GameIO.save(this, saveFile, userEnteredName);
					return true;
				}

				if (chooseSaveNameResult == JOptionPane.CLOSED_OPTION) {
					return false;
				}
			} else {

				try {
					System.out.println("Path to existing file given: " + saveFile.getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}

				GameIO.save(this, saveFile);
				return true;
			}
		}
	}

	public boolean loadGame() {
		System.out.println("Loading");

		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		fileChooser.setFileFilter(new FileNameExtensionFilter("Castle Builder Saves", "castle"));

		fileChooser.setDialogTitle("Load Game");

		int fileChooserResult = fileChooser.showOpenDialog(null);

		if (fileChooserResult == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();

			GameIO.load(this, selectedFile);

		} else {
			return false;
		}

		return true;
	}

	public void setPaused(boolean b) {
		gamePaused = b;
	}

	public KeyListener getKeyListener() {
		return keyListener;
	}
}
