package main;

import listeners.KeyListener;
import listeners.MouseListener;
import abstractClasses.Existent;
import threads.GameThread;
import threads.GraphicsThread;

public class Main {

	public static void main(String[] args) {
		
		int mapRows = 800, mapCols = 100;
		
		int windowWidth = 1000, windowHeight = 600;
		
		int tileSize = 25;
		
		Map map = new Map(mapRows, mapCols, tileSize);
		
		Existent.setMap(map);
		
		GamePanel panel = new GamePanel(windowWidth, windowHeight, map);
		
		panel.getFrame().addKeyListener(new KeyListener(panel));
		
		MouseListener ml = new MouseListener();
		
		panel.getFrame().addMouseListener(ml);
		
		panel.getFrame().addMouseMotionListener(ml);
		
		GameThread gameThread = new GameThread(60, panel);
		
		GraphicsThread graphicsThread = new GraphicsThread(60, panel);
		
		gameThread.start();
		
		graphicsThread.start();
		
	}

}
