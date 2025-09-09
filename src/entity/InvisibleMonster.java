package entity;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class InvisibleMonster extends Monsters{
	private GamePanel gp;       // Riferimento al GamePanel

    // Costruttore
    public InvisibleMonster(GamePanel gp){
        super("InvisibleMonster", gp);
        setup();

        this.gp = gp;
    }

    private void setup(){
        // Carica l'immagine del mostro
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/monsters/InvisibleMonster/InvisibleMonster_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/monsters/InvisibleMonster/InvisibleMonster_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/monsters/InvisibleMonster/InvisibleMonster_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/monsters/InvisibleMonster/InvisibleMonster_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/monsters/InvisibleMonster/InvisibleMonster_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/monsters/InvisibleMonster/InvisibleMonster_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/monsters/InvisibleMonster/InvisibleMonster_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/monsters/InvisibleMonster/InvisibleMonster_right_2.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        // Impostiamo le dimensioni
        width = 50;
		height = 50;

        // Impostiamo il danno
		damage = 0.5;

        // Imposta l'area solida per la collisione
		setSolidAreaX(0);
		setSolidAreaY(0);

		// Dimensioni dell'area solida
		setSolidAreaWidth(35);
		setSolidAreaHeight(20);

		// Posizione di default dell'area solida
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		// Imposta la velocità del mostro
		this.setSpeed(1.5);

        this.setDirection("left");
        
        // Impostiamo il mostro su vivo
        alive = true;
    }
}
