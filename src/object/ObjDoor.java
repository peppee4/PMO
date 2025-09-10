package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class ObjDoor extends SuperObject{
    
    // Costruttore
    public ObjDoor(GamePanel gp) {
    	super(gp);
        name = "Door";     // Nome dell'oggetto
        collision = false;  // La cassa non ha una collisione attiva
        
        // Carica l'immagine della cassa
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/objects/closed_door.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/objects/opened_door.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
        
        // Imposta l'area solida per la collisione
     	setSolidAreaX(0);
     	setSolidAreaY(20);

     	// Dimensioni dell'area solida
     	setSolidAreaWidth(30);
     	setSolidAreaHeight(45);

     	// Impostiamo le dimensioni
        width = 60;
		height = 60;     	
     	// Posizione di default dell'area solida
     	solidAreaDefaultX = solidArea.x;
     	solidAreaDefaultY = solidArea.y;
    }
    
    @Override
    public void update() {
    	if((this != null && this.gp.getCChecker().isPlayerNearObject(this)) && (this.objStatus == false) && this.gp.getPlayer().getNumberOfKey() == 3) {
    		this.gp.setGameState(this.gp.getDialogueState());
    		
    		if(this.gp.getKeyH().isePressed() == true) {
    			this.objStatus = true;
    			gp.playSoundEffect(1);
    			if(this.gp.levelNumber < 3) {
    				this.gp.setGameState(this.gp.getNextLevelState());
    				this.gp.levelNumber++;
        			this.gp.reset();
        			
    			} else {
    				this.gp.setGameState(this.gp.getNextLevelState());
    				this.gp.levelNumber++;
    			}
    		}
    	}
    }
}
