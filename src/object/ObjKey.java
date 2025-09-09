package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class ObjKey extends SuperObject{
	
	private GamePanel gp;
	
	public ObjKey(GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
		name = "Key";
		this.gp = gp;
		
		// Carica l'immagine della chiave
	    try {
	        image1 = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
	        image1 = iScaler.scaleImage(image1, gp.getTileSize() - 30, gp.getTileSize() - 30);
	        
	    } catch (IOException e) {
	        System.out.println(e);
	    }
	}

}
