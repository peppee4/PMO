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
    private int worldX;         // Coordinata X
    private int worldY;         // Coordinata Y
    private GamePanel gp;       // Riferimento al gamepanel

    public Slime(int worldX, int worldY, GamePanel gp){
        super("Slime", gp);
        this.worldX = worldX;
        this.worldY = worldY;
        this.gp = gp;

        // Impostiamo le dimensioni
        width = 30;
		height = 20;

        // Impostiamo il danno
		//damage = 0.25;

        // Imposta l'area solida per la collisione
		setSolidAreaX(0);
		setSolidAreaY(0);

		// Dimensioni dell'area solida
		setSolidAreaWidth(5);
		setSolidAreaHeight(5);

		// Posizione di default dell'area solida
		setSolidAreaDefaultX(getSolidArea().x);
		setSolidAreaDefaultY(getSolidArea().y);
    }

    public void draw(Graphics2D g2){
        try {
            
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/monsters/monstersElements/Slime.png"));
            
            int screenX = worldX - gp.player.getWorldX() + gp.player.getCenterX();
		    int screenY = worldY - gp.player.getWorldY() + gp.player.getCenterY();
		
            // Disegna solo i tile che si trovano entro i confini dello schermo
		    if(worldX + gp.getTileSize() > gp.player.getWorldX() - gp.player.getCenterX() &&
		        worldX - gp.getTileSize() < gp.player.getWorldX() + gp.player.getCenterX() &&
		        worldY + gp.getTileSize() > gp.player.getWorldY()- gp.player.getCenterY() &&
                worldY - gp.getTileSize() < gp.player.getWorldY() + gp.player.getCenterY()) {
                
		    	// Disegna l’immagine della tile sullo schermo
		        g2.drawImage(image, screenX, screenY, this.width, this.height, null);
		    }
        } catch (IOException e) {
            System.out.println(e);        
        }
    }

    public void effect(Player p){
        collisionPlayer = false;
        gp.cChecker.checkPlayer(this);

        System.out.println(collisionPlayer);

        if(collisionPlayer == true){
            p.setSpeed(1);
        }
    }
}