package gameIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import main.GamePanel;

public class GameIO {

	private static String fixName(String saveName) {

		saveName = saveName.trim();

		if (!saveName.startsWith("\\")) {
			saveName = "\\" + saveName;
		}

		if (saveName.endsWith(".")) {
			saveName = saveName.substring(0, saveName.length() - 1);
		}

		if (!saveName.endsWith(".castle")) {
			saveName += ".castle";
		}

		return saveName;
	}

	public static void save(GamePanel gamePanel, File saveFile) {
		
		try {
			save(gamePanel, saveFile.getParentFile(), saveFile.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void save(GamePanel gamePanel, File saveFile, String saveName) {

		saveName = fixName(saveName);

		try {

			FileOutputStream fos = new FileOutputStream(saveFile.getCanonicalPath() + saveName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(gamePanel);

			oos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void load(GamePanel oldPanel, File toLoad) {
		
		System.out.println("Load 1");

		Object obj = null;

		try {
			
			System.out.println("Load 2");
			
			FileInputStream f_in = new FileInputStream(toLoad.getCanonicalPath());
			ObjectInputStream obj_in = new ObjectInputStream(f_in);
			
			System.out.println("Load 3");

			obj = obj_in.readObject();

			obj_in.close();
			
			System.out.println("Load 4");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Load 5");

		if (obj instanceof GamePanel) {
			
			System.out.println("Load 6");
			
			GamePanel newPanel = (GamePanel) obj;
			
			System.out.println("Loaded New Panel: " + newPanel);
			
			System.out.println("Old Panel: " + oldPanel);
			
			newPanel.setVisible(true);
			
			System.out.println("Load 7");
			
			oldPanel.getFrame().dispose();
		}
	}
}
