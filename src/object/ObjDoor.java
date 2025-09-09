package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class ObjDoor extends SuperObject{
	
	GamePanel gp;
	
	public ObjDoor(GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
		name = "Door";
		this.gp = gp;
		// Carica le immagini dei cuori 
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/objects/closed_door.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/objects/opened_door.png"));
            
        } catch (IOException e) {
            System.out.println(e);
        }
	}

}
