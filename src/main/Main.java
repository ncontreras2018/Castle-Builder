package main;

import people.Worker;
import listeners.KeyListener;
import listeners.MouseListener;
import abstractClasses.Existent;
import threads.ControlThread;
import threads.GameThread;
import threads.GraphicsThread;
import threads.PathfindingThread;
import util.Util;

public class Main {

	public static void main(String[] args) {
		
		int mapRows = 81, mapCols = 101;
		
		int windowWidth = 1000, windowHeight = 600;
		
		int tileSize = 25;
		
		Map map = new Map(mapRows, mapCols, tileSize);
		
		Existent.setMap(map);
		
		Util.setMap(map);
		
		GamePanel panel = new GamePanel(windowWidth, windowHeight, map);
		
		KeyListener kl = new KeyListener();
		
		panel.getFrame().addKeyListener(kl);
		
		MouseListener ml = new MouseListener(panel);
		
		panel.getFrame().addMouseListener(ml);
		
		panel.getFrame().addMouseMotionListener(ml);
		
		panel.getFrame().addMouseWheelListener(ml);
		
		GameThread gameThread = new GameThread(60, panel);
		
		GraphicsThread graphicsThread = new GraphicsThread(60, panel);
		
		ControlThread controlThread = new ControlThread(30, panel, kl, ml);
		
		PathfindingThread pathfindingThread = new PathfindingThread(map);
		
		gameThread.start();
		
		graphicsThread.start();
		
		controlThread.start();
		
		pathfindingThread.start();
		
		map.getUnlockedObjects().add(new Worker(600,600, 1, 1));
		
	}

}
