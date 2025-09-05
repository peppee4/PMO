package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class ObjChest extends SuperObject{
    
    // Costruttore
    public ObjChest() {
        name = "Cassa";     // Nome dell'oggetto
        collision = false;  // La cassa non ha una collisione attiva
        
        // Carica l'immagine della cassa
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest_1.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
        
        // Imposta l'area solida per la collisione
     	setSolidAreaX(0);
     	setSolidAreaY(0);

     	// Dimensioni dell'area solida
     	setSolidAreaWidth(30);
     	setSolidAreaHeight(45);

     	// Posizione di default dell'area solida
     	solidAreaDefaultX = solidArea.x;
     	solidAreaDefaultY = solidArea.y;
    }
    
}
