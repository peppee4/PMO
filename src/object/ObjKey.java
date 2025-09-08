package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class ObjKey extends SuperObject{
	
	public ObjKey(KeyHandler keyH, GamePanel gp) {
		super(keyH, gp);
		// TODO Auto-generated constructor stub
		name = "Key";
		
		// Carica l'immagine della chiave
	    try {
	        image1 = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
	        
	    } catch (IOException e) {
	        System.out.println(e);
	    }
	}

}
