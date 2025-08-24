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
    }
}
