package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class NormalMonster extends Monsters {
    // Costruttore
    public NormalMonster(GamePanel gp) {
        super("NormalMonster", gp);
        setup();
    }

    private void setup() {
        // Carica l'immagine del mostro
        try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_right_2.png"));
		}catch(IOException e) {
			System.out.println(e);
		}

		// Setting dimensione mostro
		width = 60;
		height = 60;

		// Danno
		damage = 1;

		// Imposta l'area solida per la collisione
		setSolidAreaX(0);
		setSolidAreaY(0);

		// Dimensioni dell'area solida
		setSolidAreaWidth(30);
		setSolidAreaHeight(45);

		// Posizione di default dell'area solida
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		// Imposta la velocità del mostro
		this.setSpeed(2);

        this.setDirection("left");
    }
    
    public BufferedImage getImageIdle(int value) {
		
		BufferedImage image= null;
		
		if(value == 1) {
			image = this.right1;
		}else if(value == 2){
			image = this.right2;
		}
		
		return image;
	}
}
