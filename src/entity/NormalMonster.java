package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class NormalMonster extends Monsters {
    
    // Costruttore: riceve il riferimento al GamePanel e inizializza il mostro
    public NormalMonster(GamePanel gp) {
        super("NormalMonster", gp); 	// Chiama il costruttore della superclasse Monsters
        setup(); 						// Configura le proprietà del mostro
    }

    // Metodo di configurazione iniziale del mostro
    private void setup() {
        // Carica le immagini (sprite) del mostro per ogni direzione e frame di animazione
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster/NormalMonster_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster/NormalMonster_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster/NormalMonster_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster/NormalMonster_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster/NormalMonster_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster/NormalMonster_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster/NormalMonster_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster/NormalMonster_right_2.png"));
        } catch (IOException e) {
            System.out.println(e); 	// Stampa eventuali errori di caricamento
        }

        // Imposta dimensioni del mostro
        width = 60;
        height = 60;

        // Danno inflitto al player
        damage = 1;

        // Imposta l'area solida per la collisione
        setSolidAreaX(0);
        setSolidAreaY(0);

        // Dimensioni dell'area solida
        setSolidAreaWidth(50);
        setSolidAreaHeight(50);

        // Salva la posizione di default dell'area solida
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        // Imposta la velocità di movimento del mostro
        this.setSpeed(2.7);

        // Direzione iniziale del mostro
        this.setDirection("left");
        
        // Stato iniziale: vivo
        alive = true;
        
        // Carica i suoni associati al mostro e li aggiunge alla lista clips
        clips.add(loadClip("/sounds/NormalMonster_1.wav"));
        clips.add(loadClip("/sounds/NormalMonster_2.wav"));
        clips.add(loadClip("/sounds/NormalMonster_3.wav"));
        clips.add(loadClip("/sounds/NormalMonster_4.wav"));
    }
    
    /**
     * Restituisce l'immagine idle del mostro in base al valore passato.
     * @param value 1 o 2 per selezionare il frame
     * @return l'immagine corrispondente
     */
    public BufferedImage getImageIdle(int value) {
        BufferedImage image = null;
        
        if (value == 1) {
            image = this.right1; 	// Primo frame idle verso destra
        } else if (value == 2) {
            image = this.right2;	// Secondo frame idle verso destra
        }
        
        return image;
    }
}