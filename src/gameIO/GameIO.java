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

		Object obj = null;

		try {
			
			FileInputStream f_in = new FileInputStream(toLoad.getCanonicalPath());
			ObjectInputStream obj_in = new ObjectInputStream(f_in);

			obj = obj_in.readObject();

			obj_in.close();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		if (obj instanceof GamePanel) {
			GamePanel newPanel = (GamePanel) obj;
			
			oldPanel.getFrame().dispose();
		}
	}
}
