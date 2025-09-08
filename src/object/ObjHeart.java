package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class ObjHeart extends SuperObject{
	
	GamePanel gp;
	
	public ObjHeart(GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
		name = "Heart";
		this.gp = gp;
		// Carica le immagini dei cuori 
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_blank.png"));
            image1 = iScaler.scaleImage(image1, gp.getTileSize() - 30, gp.getTileSize() - 30);
            image2 = iScaler.scaleImage(image2, gp.getTileSize() - 30, gp.getTileSize() - 30);
            image3 = iScaler.scaleImage(image3, gp.getTileSize() - 30  , gp.getTileSize() - 30);
        } catch (IOException e) {
            System.out.println(e);
        }
	}

}
