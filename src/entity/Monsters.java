package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Queue;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Monsters extends Entity {
    public BufferedImage image;        			// Immagine del mostro
	private int actionCounter = 0;				// Contatore per gestire le azioni del mostro
    private GamePanel gp;             			// Riferimento al GamePanel
	private int lifeCounter = 180;				// Contatore per la gestione della vita del player
	private Integer targetCol = null,
			    targetRow = null;				// Colonna e riga del tile di destinazione		
	
    // Costruttore
    public Monsters(String name, GamePanel gp) {
        //this.name = name;
		this.gp = gp;

		// Imposta l'area solida per la collisione
		setSolidArea(new Rectangle());
		setSolidAreaX(8);
		setSolidAreaY(8);

		// Dimensioni dell'area solida
		setSolidAreaWidth(30);
		setSolidAreaHeight(45);

		// Posizione di default dell'area solida
		setSolidAreaDefaultX(getSolidArea().x);
		setSolidAreaDefaultY(getSolidArea().y);

		// Imposta la velocità del mostro
		this.setSpeed(2);
    }

    public void draw(Graphics2D g2, GamePanel gp){
        int screenX = getWorldX() - gp.player.getWorldX() + gp.player.getCenterX();
		int screenY = getWorldY() - gp.player.getWorldY() + gp.player.getCenterY();
		
        // Disegna solo i tile che si trovano entro i confini dello schermo
		if(getWorldX() + gp.getTileSize() > gp.player.getWorldX() - gp.player.getCenterX() &&
		    getWorldX() - gp.getTileSize() < gp.player.getWorldX() + gp.player.getCenterX() &&
		    getWorldY() + gp.getTileSize() > gp.player.getWorldY()- gp.player.getCenterY() &&
            getWorldY() - gp.getTileSize() < gp.player.getWorldY() + gp.player.getCenterY()) {
			
            switch(this.getDirection()) {
			case "up":
				if(this.getSpriteNum() == 1) {
					image = up1;
				}else if(this.getSpriteNum() == 2) {
					image = up2;
				}
				break;
			case "down":
				if(this.getSpriteNum() == 1) {
					image = down1;
				}else if(this.getSpriteNum() == 2) {
					image = down2;
				}
				break;
			case "left":
				if(this.getSpriteNum() == 1) {
					image = left1;
				}else if(this.getSpriteNum() == 2) {
					image = left2;
				}
				break;
			case "right":
				if(this.getSpriteNum() == 1) {
					image = right1;
				}else if(this.getSpriteNum() == 2) {
					image = right2;
				}
				break;
			default:
				image = stop;
				break;
		}

			// Disegna l’immagine della tile sullo schermo
		    g2.drawImage(image, screenX, screenY, 60, 60, null);
		}
    }

	public void update() {
		setAction();

		// Collisione con i tile
		collisionOn = false;
		gp.cChecker.checkTile(this);

		// Se non c’è collisione, il mostro si muove
		if(collisionOn == false) {
			switch(this.getDirection()) {
				case "up":
					this.setWorldY(this.getWorldY() - this.getSpeed());
					break;
				case "down":
					this.setWorldY(this.getWorldY() + this.getSpeed());
					break;
				case "left":
					this.setWorldX(this.getWorldX() - this.getSpeed());
					break;
				case "right":
					this.setWorldX(this.getWorldX() + this.getSpeed());
					break;
			}
		}

		// Collisione con il player
		collisionPlayer = false;
		gp.cChecker.checkPlayer(this);

		if(this.lifeCounter < 180){
			this.lifeCounter++;
		}

		// Se c'è collisione con il player
		if(collisionPlayer == true && this.lifeCounter == 180){
			if(gp.player.getLife() > 0){
				gp.player.setLife(gp.player.getLife() - 1);
				this.lifeCounter = 0;
			}
			
			// Fine del gioco se la vita del player è 0
			if(gp.player.getLife() == 0){
				System.out.println("Game Over");				// Messaggio di Game Over
				gp.setGameStatus(false);				// Imposta lo stato del gioco su "Game Over"
			}
		}

		// Gestione dell’animazione del mostro
		this.setSpriteCounter(this.getSpriteCounter() + 1);

		// Avanza l’animazione ogni 12 frame
		if(this.getSpriteCounter() > 12) {
			if(this.getSpriteNum() == 1) {
				this.setSpriteNum(2);
			}else if(this.getSpriteNum() == 2) {
				this.setSpriteNum(1);
			}
			
			// Resetta il contatore
			this.setSpriteCounter(0);
		}
	}

	// Metodo per definire il comportamento del mostro
	public void setAction() {
		this.actionCounter++;																	// Incrementa il contatore delle azioni
		int monsterTileX = this.getWorldX() / gp.getTileSize();									// Calcola la colonna del tile del mostro
		int monsterTileY = this.getWorldY() / gp.getTileSize();									// Calcola la riga del tile del mostro
		int playerTileX = gp.player.getWorldX() / gp.getTileSize();								// Calcola la colonna del tile del player	
		int playerTileY = gp.player.getWorldY() / gp.getTileSize();								// Calcola la riga del tile del player
		int distance = getTileDistance(monsterTileX, monsterTileY, playerTileX, playerTileY);	// Distanza in tile tra mostro e player

		// Se non sono centrato in una tile, continuo nella direzione attuale
    	if (!alignedToTile()) return;

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


		// Se il player è entro 16 tile, il mostro inizia a inseguirlo
		if(distance <= 16) {
			int[] nextStep = getNextStep(monsterTileX, monsterTileY, playerTileX, playerTileY);
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

			// Cambia direzione ogni 60 frame o se c'è una collisione
			if(this.actionCounter == 60 || collisionOn == true) {
			int random = (int)(Math.random() * 100) + 1; // Numero casuale tra 1 e 100

			// Cambia direzione in base al numero casuale
			if(random <= 25) {
				this.setDirection("up");
			}
			if(random > 25 && random <= 50) {
				this.setDirection("down");
			}
			if(random > 50 && random <= 75) {
				this.setDirection("left");
			}
			if(random > 75 && random <= 100) {
				this.setDirection("right");
			}

			// Resetta il contatore
			this.actionCounter = 0;
			}
		}
	}

	// Calcola la distanza in tile tra due punti usando la ricerca in ampiezza (BFS)
	private int getTileDistance(int col1, int row1, int col2, int row2) {
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
					if(!visited[newCol][newRow] && gp.tileM.mapTileNumber[newCol][newRow] == 0) {
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
					if(!visited[newCol][newRow] && gp.tileM.mapTileNumber[newCol][newRow] == 0) {
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
		return this.getWorldX() % gp.getTileSize() == 0 && this.getWorldY() % gp.getTileSize() == 0;
	}
}
