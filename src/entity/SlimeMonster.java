package entity;

import main.GamePanel;

import java.io.IOException;

import javax.imageio.ImageIO;

public class SlimeMonster extends Monsters{
    // Costruttore
    public SlimeMonster(GamePanel gp){
        super("SlimeMonster", gp);
        setup();
    }

    private void setup(){
        // Carica l'immagine del mostro
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/monsters/SlimeMonster_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/monsters/SlimeMonster_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/monsters/SlimeMonster_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/monsters/SlimeMonster_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/monsters/SlimeMonster_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/monsters/SlimeMonster_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/monsters/SlimeMonster_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/monsters/SlimeMonster_right_2.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        // Impostiamo le dimensioni
        width = 30;
		height = 30;

        // Impostiamo il danno
		//damage = 0.5;

        // Imposta l'area solida per la collisione
		setSolidAreaX(0);
		setSolidAreaY(0);

		// Dimensioni dell'area solida
		setSolidAreaWidth(35);
		setSolidAreaHeight(20);

		// Posizione di default dell'area solida
		setSolidAreaDefaultX(getSolidArea().x);
		setSolidAreaDefaultY(getSolidArea().y);

		// Imposta la velocità del mostro
		this.setSpeed(1);

        this.setDirection("left");
    }
}
