package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Queue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;

public class Monsters extends Entity {
    private BufferedImage image;        		// Immagine del mostro
	private int actionCounter = 0;				// Contatore per gestire le azioni del mostro
    protected GamePanel gp;             		// Riferimento al GamePanel
	private int lifeCounter = 180;				// Contatore per la gestione della vita del player
	private Integer targetCol = null,			// Colonna del tile di destinazione
			    	targetRow = null;			// Riga del tile di destinazione		
	protected int width,						// Larghezza del mostro
				  height;						// Altezza del mostro
	protected double damage;					// Danno inflitto
	private int soundCooldown = 0;				// Tempo di riproduzione del suono
	protected ArrayList<Clip> clips;			// Suoni del mostro
	public boolean alive;						// Variabile per capire se stampare o meno il mostro
	
	private boolean invincibleTime = false;     // Tempo in cui il player è invincibile
	
    // Costruttore
    public Monsters(String name, GamePanel gp) {
		this.gp = gp;
		this.clips = new ArrayList<Clip>();
		
		// Imposta l'area solida per la collisione
		solidArea = new Rectangle();
    }

    public void draw(Graphics2D g2, GamePanel gp){
        int screenX = getWorldX() - gp.getPlayer().getWorldX() + gp.getPlayer().getCenterX();
		int screenY = getWorldY() - gp.getPlayer().getWorldY() + gp.getPlayer().getCenterY();
		
        // Disegna solo i tile che si trovano entro i confini dello schermo
		if(getWorldX() + gp.getTileSize() > gp.getPlayer().getWorldX() - gp.getPlayer().getCenterX() &&
		    getWorldX() - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getCenterX() &&
		    getWorldY() + gp.getTileSize() > gp.getPlayer().getWorldY()- gp.getPlayer().getCenterY() &&
            getWorldY() - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getCenterY()) {
			
            switch(this.getDirection()) {
			case "up":
				if(this.spriteNum == 1) {
					image = up1;
				}else if(this.spriteNum == 2) {
					image = up2;
				}
				break;
			case "down":
				if(this.spriteNum == 1) {
					image = down1;
				}else if(this.spriteNum == 2) {
					image = down2;
				}
				break;
			case "left":
				if(this.spriteNum == 1) {
					image = left1;
				}else if(this.spriteNum == 2) {
					image = left2;
				}
				break;
			case "right":
				if(this.spriteNum == 1) {
					image = right1;
				}else if(this.spriteNum == 2) {
					image = right2;
				}
				break;
			default:
				image = stop;
				break;
		}

			// Disegna l’immagine della tile sullo schermo
		    g2.drawImage(image, screenX, screenY, this.width, this.height, null);
		}
    }

	public void update() {
		// Collisione con i tile
		collisionOn = false;
		gp.cChecker.checkTile(this);

		setAction();

		// Se non c’è collisione, il mostro si muove
		if(collisionOn == false) {
			switch(this.getDirection()) {
				case "up":
					this.setWorldY(this.getWorldY() - (int)this.getSpeed());
					break;
				case "down":
					this.setWorldY(this.getWorldY() + (int)this.getSpeed());
					break;
				case "left":
					this.setWorldX(this.getWorldX() - (int)this.getSpeed());
					break;
				case "right":
					this.setWorldX(this.getWorldX() + (int)this.getSpeed());
					break;
			}
		}

		if(this.lifeCounter < 200){
			this.lifeCounter++;
		}
		
		// Se c'è collisione con il player
		if((gp.cChecker.checkPlayer(this) == true && this.lifeCounter == 200) && !gp.getPlayer().isInvincible) {
			if(gp.getPlayer().life > 0){
				
				if(this instanceof ExplosiveMonster) {
					ExplosiveMonster e = (ExplosiveMonster) this;
					e.explosion();
				}
				
				this.lifeCounter = 0;

				gp.getPlayer().takeDamage(this.damage);
			    gp.playSoundEffect(3);
			    this.lifeCounter = 0;
				

			}
			
			// Fine del gioco se la vita del player è 0
			if(gp.getPlayer().life <= 0){
				gp.playSoundEffect(2);
				gp.setGameState(gp.gameOverState);
			}
		}

		// Gestione dell’animazione del mostro
		this.spriteCounter++;

		// Avanza l’animazione ogni 12 frame
		if(spriteCounter > 12) {
			if(spriteNum == 1) {
				this.spriteNum = 2;
			}else if(this.spriteNum == 2) {
				this.spriteNum = 1;
			}
			
			// Resetta il contatore
			this.spriteCounter = 0;
		}
		
		if(this instanceof ExplosiveMonster) {
			ExplosiveMonster e = (ExplosiveMonster) this;
			e.attack();
		}
	}

	// Metodo per definire il comportamento del mostro
	public void setAction() {
		this.actionCounter++;																	// Incrementa il contatore delle azioni
		int monsterTileX = this.getWorldX() / gp.getTileSize();									// Calcola la colonna del tile del mostro
		int monsterTileY = this.getWorldY() / gp.getTileSize();									// Calcola la riga del tile del mostro
		int playerTileX = gp.getPlayer().getWorldX() / gp.getTileSize();						// Calcola la colonna del tile del player	
		int playerTileY = gp.getPlayer().getWorldY() / gp.getTileSize();						// Calcola la riga del tile del player
		int distance = getTileDistance(monsterTileX, monsterTileY, playerTileX, playerTileY);	// Distanza in tile tra mostro e player

		// Se il mostro non è allineato alla griglia, continua nella direzione attuale
    	if (!alignedToTile()) {
			// Se c'è una collisione, resetta il contatore e scegli una nuova direzione
			if(this.collisionOn) {
				this.actionCounter = 120;
				randomDirection();
			}
			
			return;
		}

		// Se non ho un target o ci sono arrivato, ne calcolo uno nuovo
    	if (targetCol == null || targetRow == null ||
     	   (monsterTileX == targetCol && monsterTileY == targetRow)) {

    	    int[] nextStep = getNextStep(monsterTileX, monsterTileY, playerTileX, playerTileY);
			if (nextStep != null) {
    	        targetCol = nextStep[0];
    	        targetRow = nextStep[1];
    	    }
    	}

    	// Se ho un target valido, aggiorno direzione
    	if (targetCol != null && targetRow != null) {
    	    if (monsterTileX < targetCol) setDirection("right");
    	    else if (monsterTileX > targetCol) setDirection("left");
    	    else if (monsterTileY < targetRow) setDirection("down");
     	  	else if (monsterTileY > targetRow) setDirection("up");
    	}

		int[] nextStep = getNextStep(monsterTileX, monsterTileY, playerTileX, playerTileY);

		// Se il player è entro 16 tile, il mostro inizia a inseguirlo
		if(distance <= 16 && nextStep != null) {
			if(nextStep != null) {	
				int nextCol = nextStep[0];
				int nextRow = nextStep[1];

				if(monsterTileX < nextCol) {
					this.setDirection("right");
				}else if(monsterTileX > nextCol) {
					this.setDirection("left");
				}else if(monsterTileY < nextRow) {
					this.setDirection("down");
				}else if(monsterTileY > nextRow) {
					this.setDirection("up");
				}
				
				
			}
		}else {
			this.actionCounter++;

			randomDirection();
		}
		
		tryPlayRandomSound();
	}

	// Calcola la distanza in tile tra due punti usando la ricerca in ampiezza (BFS)
	protected int getTileDistance(int col1, int row1, int col2, int row2) {
		int width = gp.getMaxWorldCol();					// Larghezza della mappa in tile
		int height = gp.getMaxWorldRow();					// Altezza della mappa in tile	
		boolean[][] visited = new boolean[width][height];	// Matrice per tracciare i tile visitati
		int[][] distance = new int[width][height];			// Matrice per tracciare la distanza dai tile di partenza

		// Inizializza tutte le distanze a un valore alto
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				distance[x][y] = Integer.MAX_VALUE;
			}
		}

		// Inizializza la coda per la BFS
		Queue<int[]> queue = new LinkedList<>();
		queue.add(new int[]{col1, row1});
		distance[col1][row1] = 0;
		visited[col1][row1] = true;

		// Direzioni di movimento (destra, sinistra, giù, su)
		int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

		// Esegui la BFS
		while(!queue.isEmpty()) {
			int[] current = queue.poll();
			int currCol = current[0];
			int currRow = current[1];

			// Se abbiamo raggiunto la destinazione, restituisci la distanza
			for(int[] dir : dirs) {
				int newCol = currCol + dir[0];
				int newRow = currRow + dir[1];

				if(newCol >= 0 && newCol < width && newRow >= 0 && newRow < height) {
					if(!visited[newCol][newRow] && gp.getMap().mapTileNumber[newCol][newRow] == 0) {
						visited[newCol][newRow] = true;
						distance[newCol][newRow] = distance[currCol][currRow] + 1;
						queue.add(new int[]{newCol, newRow});
					}
				}
			}
		}

		return distance[col2][row2];
	}

	// Trova il prossimo passo verso la destinazione usando la BFS
	private int[] getNextStep(int col1, int row1, int col2, int row2) {
		int width = gp.getMaxWorldCol();					// Larghezza della mappa in tile
		int height = gp.getMaxWorldRow();					// Altezza della mappa in tile
		boolean[][] visited = new boolean[width][height];	// Matrice per tracciare i tile visitati
		int[][] parentCol = new int[width][height];			// Matrice per tracciare il genitore di ogni tile colonna
		int[][] parentRow = new int[width][height];			// Matrice per tracciare il genitore di ogni tile riga

		// Inizializza tutte le posizioni dei genitori a -1
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				parentCol[x][y] = -1;
				parentRow[x][y] = -1;
			}
		}

		// Inizializza la coda per la BFS
		Queue<int[]> queue = new LinkedList<>();
		queue.add(new int[]{col1, row1});
		visited[col1][row1] = true;

		// Direzioni di movimento (destra, sinistra, giù, su)
		int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

		// Esegui la BFS
		while(!queue.isEmpty()) {
			int[] current = queue.poll();
			int currCol = current[0];
			int currRow = current[1];

			// Se abbiamo raggiunto la destinazione, ricostruisci il percorso
			if(currCol == col2 && currRow == row2) {
				// Ricostruisci il percorso
				LinkedList<int[]> path = new LinkedList<>();
				while(currCol != col1 || currRow != row1) {
					path.addFirst(new int[]{currCol, currRow});
					int tempCol = parentCol[currCol][currRow];
					int tempRow = parentRow[currCol][currRow];
					currCol = tempCol;
					currRow = tempRow;
				}
				return path.isEmpty() ? null : path.getFirst();
			}

			// Esplora i vicini
			for(int[] dir : dirs) {
				int newCol = currCol + dir[0];
				int newRow = currRow + dir[1];

				if(newCol >= 0 && newCol < width && newRow >= 0 && newRow < height) {
					if(!visited[newCol][newRow] && gp.getMap().mapTileNumber[newCol][newRow] == 0) {
						visited[newCol][newRow] = true;
						parentCol[newCol][newRow] = currCol;
						parentRow[newCol][newRow] = currRow;
						queue.add(new int[]{newCol, newRow});
					}
				}
			}
		}

		return null;
	}

	private boolean alignedToTile() {
		return this.getWorldX() % gp.getTileSize() <= 2 && this.getWorldY() % gp.getTileSize() <= 2;
	}

	private void randomDirection(){
		// Cambia direzione ogni 60 frame o se c'è una collisione
		if(this.actionCounter >= 120 || collisionOn) {
			int random = (int)(Math.random() * 60) + 1; // Numero casuale tra 1 e 100

			// Cambia direzione in base al numero casuale
			if(random <= 15) {
				this.setDirection("up");
			}
			if(random > 15 && random <= 30) {
				this.setDirection("down");
			}
			if(random > 30 && random <= 45) {
				this.setDirection("left");
			}
			if(random > 45 && random <= 60) {
				this.setDirection("right");
			}

			this.actionCounter = 0;
		}
	}
	
	protected Clip loadClip(String path) {
	    try {
	        AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource(path));
	        Clip clip = AudioSystem.getClip();
	        clip.open(ais);
	        return clip;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	protected void tryPlayRandomSound() {
		// Nessun suono caricato
	    if (clips == null || clips.isEmpty()) {
	    	return;
		}
	
	    // Coordinate tile di mostro e player
	    int monsterTileX = this.getWorldX() / gp.getTileSize();
	    int monsterTileY = this.getWorldY() / gp.getTileSize();
	    int playerTileX = gp.getPlayer().getWorldX() / gp.getTileSize();
	    int playerTileY = gp.getPlayer().getWorldY() / gp.getTileSize();

	    // Calcola distanza in tile usando il metodo BFS già esistente
	    int distance = getTileDistance(monsterTileX, monsterTileY, playerTileX, playerTileY);
	    
	    // Se il player non è raggiungibile, esci
	    if (distance == Integer.MAX_VALUE){
	    	return; // nessun suono caricato
		}

	    // Se entro la distanza massima
	    if (distance <= 20) {
	        if (soundCooldown <= 0) {
	            // Probabilità di verso (es. 5% per frame)
	            if (Math.random() < 1.0) {
	                // Scegli un clip casuale
	                Clip clip = clips.get((int) (Math.random() * clips.size()));

	                // Regola volume in base alla distanza
	                setClipVolume(clip, distance);

	                // Riproduci
	                if (clip.isRunning()) {
	                    clip.stop();
	                }
	                clip.setFramePosition(0);
	                clip.start();
	                
	                // Imposta cooldown
	                soundCooldown = 120;
	            }
	        }
	    }

	    // Decrementa cooldown
	    if (soundCooldown > 0) {
	        soundCooldown--;
	    }
	}

	// Regola il volume in base alla distanza
	private void setClipVolume(Clip clip, int distance) {
	    try {
	        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

	        // Volume normalizzato (1.0 vicino, 0.0 lontano)
	        float volume = Math.max(0.7f, 0.8f - (distance / (float) 20));

	        // Converte in dB
	        float min = gainControl.getMinimum();
	        float max = gainControl.getMaximum();
	        float gain = min + (max - min) * volume;

	        gainControl.setValue(gain);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public boolean isInvincibleTime() {
		return invincibleTime;
	}

	public void setInvincibleTime(boolean invincibleTime) {
		this.invincibleTime = invincibleTime;
	}
}
