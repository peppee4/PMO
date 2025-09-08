package entity;

import main.GamePanel;

import java.io.IOException;

import javax.imageio.ImageIO;

public class SlimeMonster extends Monsters{
    private int slimeCounter;   // Slime presenti nella mappa
    private int spawnSlime;     // Counter per lo spawn
    private GamePanel gp;       // Riferimento al GamePanel

    // Costruttore
    public SlimeMonster(GamePanel gp){
        super("SlimeMonster", gp);
        setup();

        this.gp = gp;
        this.slimeCounter = 0;
        this.spawnSlime = 0;
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
    }

    // Metodo per rialsciare slime
    public void releaseSlime(){
        if(this.slimeCounter < 30){
            this.spawnSlime++;

            // Spawna uno slime
            if(this.spawnSlime == 1500){
                gp.slime[this.slimeCounter] = new Slime(gp);
                gp.slime[this.slimeCounter].setWorldX(this.getWorldX());
                gp.slime[this.slimeCounter].setWorldY(this.getWorldY());

                this.slimeCounter++;
                this.spawnSlime = 0;
            }
        }
    }
}
