package entity;

import java.io.IOException;

import javax.imageio.ImageIO;

public class NormalMonster extends Monsters {
    
    // Costruttore
    public NormalMonster() {
        super("NormalMonster");
        setup();
    }

    private void setup() {
        // Carica l'immagine del mostro
        try {
			//up1 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_up_1.png"));
			//up2 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_up_2.png"));
			//down1 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_down_1.png"));
			//down2 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/monsters/NormalMonster_right_2.png"));
		}catch(IOException e) {
			System.out.println(e);
		}

        this.setDirection("left");
    }
}
