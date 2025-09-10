package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class ExplosiveMonster extends Monsters {
	
	private GamePanel gp;       										// Riferimento al GamePanel per accedere a dati e metodi del gioco
	private boolean flag;												// Flag per controllare quando il mostro deve accelerare verso il player
	private boolean exploding = false;									// Indica se il mostro sta esplodendo
	private int explosionFrame = 0;										// Frame corrente dell'animazione di esplosione
	private int explosionCounter = 0;									// Contatore per gestire la velocità dell'animazione
	private BufferedImage[] explosionSprites = new BufferedImage[5]; 	// Array di sprite per l'esplosione
	
    // Costruttore
    public ExplosiveMonster(GamePanel gp){
        super("ExplosiveMonster", gp); 		// Chiama il costruttore della superclasse Monsters
        setup(); 							// Configura il mostro

        this.gp = gp;
        this.flag = true; 					// All'inizio il mostro può attivare la corsa verso il player
    }

    // Metodo di configurazione iniziale del mostro
    private void setup(){
        try {
        	// Caricamento delle immagini di movimento
            up1 = ImageIO.read(getClass().getResourceAsStream("/monsters/ExplosiveMonster/ExplosiveMonster_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/monsters/ExplosiveMonster/ExplosiveMonster_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/monsters/ExplosiveMonster/ExplosiveMonster_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/monsters/ExplosiveMonster/ExplosiveMonster_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/monsters/ExplosiveMonster/ExplosiveMonster_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/monsters/ExplosiveMonster/ExplosiveMonster_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/monsters/ExplosiveMonster/ExplosiveMonster_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/monsters/ExplosiveMonster/ExplosiveMonster_right_2.png"));
			
			// Caricamento delle immagini dell'esplosione
			explosionSprites[0] = ImageIO.read(getClass().getResourceAsStream("/monsters/monstersElements/explosion_1.png"));
	        explosionSprites[1] = ImageIO.read(getClass().getResourceAsStream("/monsters/monstersElements/explosion_2.png"));
	        explosionSprites[2] = ImageIO.read(getClass().getResourceAsStream("/monsters/monstersElements/explosion_3.png"));
	        explosionSprites[3] = ImageIO.read(getClass().getResourceAsStream("/monsters/monstersElements/explosion_4.png"));
	        explosionSprites[4] = ImageIO.read(getClass().getResourceAsStream("/monsters/monstersElements/explosion_5.png"));

        } catch (IOException e) {
            System.out.println(e);
        }

        // Dimensioni del mostro
        width = 30;
		height = 30;

        // Danno inflitto al player
		damage = 1.5;

        // Impostazione area di collisione
		setSolidAreaX(0);
		setSolidAreaY(0);
		setSolidAreaWidth(35);
		setSolidAreaHeight(20);

		// Salvataggio posizione di default dell'area solida
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		// Velocità iniziale
		this.setSpeed(1.5);

        // Direzione iniziale
        this.setDirection("left");
        
        // Stato iniziale: vivo
        alive = true;
    }
    
    @Override
    public void update() {
        if (exploding) {
            // Gestione animazione esplosione
            explosionCounter++;
            if (explosionCounter % 10 == 0) { 						// Cambia frame ogni 10 tick
                explosionFrame++;
                if (explosionFrame >= explosionSprites.length) {
                    alive = false; 									// Quando l'animazione finisce, il mostro muore
                    return;
                }
            }
            return; 												// Durante l'esplosione non esegue altro
        }

        // Se non sta esplodendo, usa la logica standard dei mostri
        super.update();
    }
    
    // Logica di attacco del mostro
    public void attack() {
    	// Calcola posizione in tile del mostro e del player
	    int monsterTileX = this.getWorldX() / gp.getTileSize();
	    int monsterTileY = this.getWorldY() / gp.getTileSize();
	    int playerTileX = gp.getPlayer().getWorldX() / gp.getTileSize();
	    int playerTileY = gp.getPlayer().getWorldY() / gp.getTileSize();

	    // Calcola distanza in tile usando BFS (metodo già esistente)
	    int distance = getTileDistance(monsterTileX, monsterTileY, playerTileX, playerTileY);
	    
	    // Se il player non è raggiungibile, esce
	    if (distance == Integer.MAX_VALUE){
	    	return;
		}

	    // Se il player è entro 3 tile e il flag è attivo → accelera e riproduce suono
	    if (distance <= 3 && this.flag) {
	        this.setSpeed(2.9);
	        this.gp.playSoundEffect(9);
	        this.flag = false;
	    }
	    // Se il player si allontana oltre 3 tile → torna alla velocità normale
	    else if(distance > 3){
	    	this.setSpeed(1.5);
	    	this.flag = true;
	    }
    }
    
    // Avvia l'esplosione
    public void explosion() {
        gp.playSoundEffect(10); // Riproduce suono esplosione
        exploding = true;       // Attiva stato esplosione
        explosionFrame = 0;     // Parte dal primo frame
        explosionCounter = 0;   // Reset contatore animazione
    }

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
    	// Calcola posizione sullo schermo in base alla posizione del player
    	int screenX = getWorldX() - gp.getPlayer().getWorldX() + gp.getPlayer().getCenterX();
		int screenY = getWorldY() - gp.getPlayer().getWorldY() + gp.getPlayer().getCenterY();
		
		// Disegna solo se il mostro è visibile nell'area di gioco
		if(getWorldX() + gp.getTileSize() > gp.getPlayer().getWorldX() - gp.getPlayer().getCenterX() &&
		   getWorldX() - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getCenterX() &&
		   getWorldY() + gp.getTileSize() > gp.getPlayer().getWorldY()- gp.getPlayer().getCenterY() &&
	       getWorldY() - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getCenterY()) {
		
			if (exploding) {
				// Disegna il frame corrente dell'esplosione
				g2.drawImage(explosionSprites[explosionFrame], screenX, screenY, width, height, null);
			} else {
				// Disegna il mostro normalmente usando il metodo della superclasse
				super.draw(g2, gp);
        	}
		}
    }
}