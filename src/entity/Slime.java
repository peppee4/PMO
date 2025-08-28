package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Slime extends Monsters {
    //private double damage;    // Danno 
    private int width,          // Larghezza
                height;         // Altezza
    private GamePanel gp;       // Riferimento al gamepanel

    public Slime(GamePanel gp){
        super("Slime", gp);
        this.gp = gp;
        // Impostiamo le dimensioni
        width = 50;
		height = 30;

        // Impostiamo il danno
		damage = 0.25;

        // Imposta l'area solida per la collisione
		setSolidAreaX(0);
		setSolidAreaY(0);

		// Dimensioni dell'area solida
		setSolidAreaWidth(width);
		setSolidAreaHeight(height);

		// Posizione di default dell'area solida
		setSolidAreaDefaultX(getSolidArea().x);
		setSolidAreaDefaultY(getSolidArea().y);
    }

    public void draw(Graphics2D g2){
        try {
            
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/monsters/monstersElements/Slime.png"));
            
            int screenX = getWorldX() - gp.getPlayer().getWorldX() + gp.getPlayer().getCenterX();
		    int screenY = getWorldY() - gp.getPlayer().getWorldY() + gp.getPlayer().getCenterY();
		
            // Disegna solo i tile che si trovano entro i confini dello schermo
		    if(getWorldX() + gp.getTileSize() > gp.getPlayer().getWorldX() - gp.getPlayer().getCenterX() &&
		        getWorldX() - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getCenterX() &&
		        getWorldY() + gp.getTileSize() > gp.getPlayer().getWorldY()- gp.getPlayer().getCenterY() &&
                getWorldY() - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getCenterY()) {
                
		    	// Disegna l’immagine della tile sullo schermo
		        g2.drawImage(image, screenX, screenY, this.width, this.height, null);
		    }
        } catch (IOException e) {
            System.out.println(e);        
        }
    }
}