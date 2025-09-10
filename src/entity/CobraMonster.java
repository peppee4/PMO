package entity;

import main.GamePanel;

import java.io.IOException;

import javax.imageio.ImageIO;

public class CobraMonster extends Monsters{
    private GamePanel gp;       // Riferimento al GamePanel
    private boolean flag;		// Variabile per il controllo del cambio di visibilità
    private int blindTime = 0;	// Tempo di accecamento

    // Costruttore
    public CobraMonster(GamePanel gp){
        super("CobraMonster", gp);
        setup();

        this.gp = gp;
        this.flag = true;
    }

    private void setup(){
        // Carica l'immagine del mostro
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/monsters/CobraMonster/CobraMonster_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/monsters/CobraMonster/CobraMonster_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/monsters/CobraMonster/CobraMonster_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/monsters/CobraMonster/CobraMonster_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/monsters/CobraMonster/CobraMonster_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/monsters/CobraMonster/CobraMonster_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/monsters/CobraMonster/CobraMonster_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/monsters/CobraMonster/CobraMonster_right_2.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        // Impostiamo le dimensioni
        width = 40;
		height = 40;

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
		this.setSpeed(2);

        this.setDirection("left");
        
        // Impostiamo il mostro su vivo
        alive = true;
    }
    
    @Override
    public void update() {
        if ((gp.cChecker.checkPlayer(this) == true) || 
				this.blindTime > 0) {
        	// Se il player si trova su almeno uno slime rallentalo
        	this.blindTime++;
        	CobraMonster c = (CobraMonster)this;
        				
        	if(this.blindTime > 0 && this.blindTime < 1000){
        		c.blind(true);
        		this.blindTime++;
        	}else{
        		c.blind(false);
        		this.blindTime = 0;
        	}
        }

        super.update();
    }
    
    private void blind(boolean value) {
    	
    	if(value && this.flag) {
    		gp.playSoundEffect(6);
    		gp.getEManger().setLight(150);
    		
    		this.flag = false;
    	}else if(!value && !this.flag) {
    		gp.getEManger().setLight(400);
    		this.flag = true;
    	}
    }
}