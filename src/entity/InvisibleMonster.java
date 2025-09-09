package entity;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class InvisibleMonster extends Monsters{
	private GamePanel gp;      					// Riferimento al GamePanel
	private boolean isInvisible = false;
	private boolean isBlinking = false;
	private long blinkStartTime = 0;
	private long invisibleStartTime = 0;
	private int blinkPhase = 0; 				// 0 = pre-invisibilità, 1 = post-invisibilità
	private boolean visible = true;
	private final int triggerDistance = 100; 	// distanza dal player per attivare
	private long lastVisibleTime = 0;
	private boolean soundPlayed = false;

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
		setSolidAreaWidth(50);
		setSolidAreaHeight(50);

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
        int dx = Math.abs(this.worldX - gp.getPlayer().worldX);
        int dy = Math.abs(this.worldY - gp.getPlayer().worldY);
        int distance = (int)Math.sqrt(dx * dx + dy * dy);
        long now = System.currentTimeMillis();
        
        if (distance < triggerDistance && !isInvisible && !isBlinking && (now - lastVisibleTime > 10000)) {
            isBlinking = true;
            blinkStartTime = System.currentTimeMillis();
            blinkPhase = 0;
        }

        if (isBlinking) {
            long elapsed = System.currentTimeMillis() - blinkStartTime;
            if (elapsed < 1000) {
                visible = (elapsed / 200) % 2 == 0; // lampeggio ogni 200ms
                soundPlayed = false;
            } else {
                isBlinking = false;
                if (blinkPhase == 0) {
                    isInvisible = true;
                    invisibleStartTime = System.currentTimeMillis();
                    visible = false;
                } else {
                    visible = true;
                    lastVisibleTime = now;
                }
            }
        }

        if (isInvisible) {
            long elapsed = System.currentTimeMillis() - invisibleStartTime;
            if (elapsed < 7000) {
                visible = false;
                if (!soundPlayed) {
                    gp.playSoundEffect(4);
                    soundPlayed = true;
                }

            } else {
                isInvisible = false;
                isBlinking = true;
                blinkStartTime = System.currentTimeMillis();
                blinkPhase = 1;
            }
        }

        // Puoi aggiungere qui il movimento o altre logiche
        super.update(); 
    }
    
    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        if (visible) {
            super.draw(g2, gp); // o disegna direttamente lo sprite
        }
    }
}
