package entity;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class InvisibleMonster extends Monsters {
	
	private GamePanel gp;      					// Riferimento al GamePanel per accedere a player, suoni, ecc.
	
	// Stati del mostro
	private boolean isInvisible = false;		// Indica se il mostro è attualmente invisibile
	private boolean isBlinking = false;			// Indica se il mostro sta lampeggiando (fase di transizione)
	private long blinkStartTime = 0;			// Momento di inizio del lampeggio
	private long invisibleStartTime = 0;		// Momento di inizio dell'invisibilità
	private int blinkPhase = 0; 				// 0 = pre-invisibilità, 1 = post-invisibilità
	private boolean visible = true;				// Indica se il mostro è visibile sullo schermo
	
	private final int triggerDistance = 100; 	// Distanza dal player per attivare il comportamento
	private long lastVisibleTime = 0;			// Ultima volta in cui il mostro era visibile
	private boolean soundPlayed = false;		// Per evitare di riprodurre il suono più volte

    // Costruttore
    public InvisibleMonster(GamePanel gp){
        super("InvisibleMonster", gp); 			// Chiama il costruttore della superclasse Monsters
        setup(); 								// Configura il mostro
        this.gp = gp;
    }

    // Configurazione iniziale del mostro
    private void setup(){
        try {
        	// Caricamento sprite per tutte le direzioni
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

        // Dimensioni del mostro
        width = 50;
		height = 50;

        // Danno inflitto al player
		damage = 1.0;

        // Area di collisione
		setSolidAreaX(0);
		setSolidAreaY(0);
		setSolidAreaWidth(50);
		setSolidAreaHeight(50);

		// Salvataggio posizione di default dell'area solida
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		// Velocità di movimento
		this.setSpeed(2);

        // Direzione iniziale
        this.setDirection("left");
        
        // Stato iniziale: vivo
        alive = true;
    }
    
    @Override
    public void update() {
    	// Calcola distanza dal player
        int dx = Math.abs(this.worldX - gp.getPlayer().worldX);
        int dy = Math.abs(this.worldY - gp.getPlayer().worldY);
        int distance = (int)Math.sqrt(dx * dx + dy * dy);
        long now = System.currentTimeMillis();
        
        // Se il player è vicino, il mostro non è invisibile o lampeggiante e sono passati 10s dall'ultima visibilità
        if (distance < triggerDistance && !isInvisible && !isBlinking && (now - lastVisibleTime > 10000)) {
            isBlinking = true; 								// Avvia lampeggio
            blinkStartTime = System.currentTimeMillis();
            blinkPhase = 0; 								// Fase pre-invisibilità
        }

        // Gestione fase di lampeggio
        if (isBlinking) {
            long elapsed = System.currentTimeMillis() - blinkStartTime;
            if (elapsed < 1000) {
                // Lampeggia ogni 200ms
                visible = (elapsed / 200) % 2 == 0;
                soundPlayed = false; 					// Reset suono
            } else {
                // Fine lampeggio
                isBlinking = false;
                if (blinkPhase == 0) {
                    // Passa a invisibile
                    isInvisible = true;
                    invisibleStartTime = System.currentTimeMillis();
                    visible = false;
                } else {
                    // Torna visibile
                    visible = true;
                    lastVisibleTime = now;
                }
            }
        }

        // Gestione fase invisibile
        if (isInvisible) {
            long elapsed = System.currentTimeMillis() - invisibleStartTime;
            if (elapsed < 7000) {
                // Rimane invisibile per 7 secondi
                visible = false;
                if (!soundPlayed) {
                    gp.playSoundEffect(4); // Suono di sparizione
                    soundPlayed = true;
                }
            } else {
                // Fine invisibilità → lampeggio di riapparizione
                isInvisible = false;
                isBlinking = true;
                blinkStartTime = System.currentTimeMillis();
                blinkPhase = 1; 	// Fase post-invisibilità
            }
        }

        // Logica di movimento o altro comportamento
        super.update(); 
    }
    
    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
    	// Disegna solo se il mostro è visibile
        if (visible) {
            super.draw(g2, gp);
        }
    }
}