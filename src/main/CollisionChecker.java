package main;

import entity.Entity;
import object.SuperObject;

//Classe per controllare le collisioni tra il player e le altre entita' presenti nel gioco
public class CollisionChecker {

	// Riferimento al pannello di gioco
	private GamePanel gp;
	
	// Coordinate dei bordi dell'entità nel mondo
	private int entityLeftWorldX = 0;
	private int entityRightWorldX = 0;
	private int entityTopWorldY = 0;
	private int entityBottomWorldY = 0;
	
	// Coordinate dei tile su cui si trovano i bordi dell'entità
	private int entityLeftCol = 0;
	private int entityRightCol = 0;
	private int entityTopRow = 0;
	private int entityBottomRow = 0;
	
	// Costruttore: prende il GamePanel per accedere alla mappa e ai tile
	public CollisionChecker(GamePanel gp) {
		
		this.gp = gp;
	}
	
	// Metodo principale per controllare le collisioni con le tile
	public void checkTile(Entity entity) {
		
		// Calcola i bordi dell'entity in coordinate del mondo
		checkObject(entity);
		
		// Numero dei tile in cui si trova l'entity
		int tileNum1, tileNum2;
		
		// Controlla la direzione dell'entity e verifica se il prossimo tile è "solido"
		// Se è "solido" attiva la collisione
		switch(entity.getDirection()) {
		case "up":
			// Calcola la riga superiore dove si muoverà l'entità
			entityTopRow = (entityTopWorldY - (int)entity.getSpeed())/gp.getTileSize();
			
			 // Recupera i numeri dei due tile che l'entità copre (sinistra e destra)
			tileNum1 = gp.getMap().getMapTileNumber()[entityLeftCol][entityTopRow];
			tileNum2 = gp.getMap().getMapTileNumber()[entityRightCol][entityTopRow];
			
			// Se almeno uno dei due tile è solido, attiva collisione
			if(gp.getMap().getTile()[tileNum1].isCollision() == true || gp.getMap().getTile()[tileNum2].isCollision() == true) {
				entity.setCollisionOn(true);
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + (int)entity.getSpeed())/gp.getTileSize();
			tileNum1 = gp.getMap().getMapTileNumber()[entityLeftCol][entityBottomRow];
			tileNum2 = gp.getMap().getMapTileNumber()[entityRightCol][entityBottomRow];
			if(gp.getMap().getTile()[tileNum1].isCollision() == true || gp.getMap().getTile()[tileNum2].isCollision() == true) {
				entity.setCollisionOn(true);
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - (int)entity.getSpeed())/gp.getTileSize();
			tileNum1 = gp.getMap().getMapTileNumber()[entityLeftCol][entityTopRow];
			tileNum2 = gp.getMap().getMapTileNumber()[entityLeftCol][entityBottomRow];
			if(gp.getMap().getTile()[tileNum1].isCollision() == true || gp.getMap().getTile()[tileNum2].isCollision() == true) {
				entity.setCollisionOn(true);
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + (int)entity.getSpeed())/gp.getTileSize();
			tileNum1 = gp.getMap().getMapTileNumber()[entityRightCol][entityTopRow];
			tileNum2 = gp.getMap().getMapTileNumber()[entityRightCol][entityBottomRow];
			if(gp.getMap().getTile()[tileNum1].isCollision() == true || gp.getMap().getTile()[tileNum2].isCollision() == true) {
				entity.setCollisionOn(true);
			}
			break;
		}
	}

	// Controlla la collisione tra i mostri e il player
	public boolean checkPlayer(Entity entity){
		// Calcola i bordi dell'entity
		checkObject(entity);

		// Calcola i bordi del player
		int playerLeftWorldX = gp.getPlayer().getWorldX() + gp.getPlayer().getSolidAreaX() + 5;
		int playerRightWorldX = gp.getPlayer().getWorldX() + gp.getPlayer().getSolidAreaX() + gp.getPlayer().getSolidAreaWidth() + 5;
		int playerTopWorldY =gp.getPlayer().getWorldY() + gp.getPlayer().getSolidAreaY() + 5;
		int playerBottomWorldY = gp.getPlayer().getWorldY() + gp.getPlayer().getSolidAreaY() + gp.getPlayer().getSolidAreaHeight() + 5;

		// Controlla la collisione
		boolean overlap = 
   	 		entityRightWorldX  > playerLeftWorldX &&
    		entityLeftWorldX   < playerRightWorldX &&
    		entityBottomWorldY > playerTopWorldY &&
    		entityTopWorldY    < playerBottomWorldY;

		if (overlap) {
			return true;
		}

		return false;
	}

	// Funzione per calolare i bordi dell'entity
	public void checkObject(Entity entity) {
		// Resetta i valori
		this.entityLeftWorldX = 0;
		this.entityRightWorldX = 0;
		this.entityTopWorldY = 0;
		this.entityBottomWorldY = 0;
		
		this.entityLeftCol = 0;
		this.entityRightCol = 0;
		this.entityTopRow = 0;
		this.entityBottomRow = 0;

		// Calcola i bordi dell'entity
		this.entityLeftWorldX = entity.getWorldX() + entity.getSolidAreaX();
		this.entityRightWorldX = entity.getWorldX() + entity.getSolidAreaX() + entity.getSolidAreaWidth();
		this.entityTopWorldY =entity.getWorldY() + entity.getSolidAreaY();
		this.entityBottomWorldY = entity.getWorldY() + entity.getSolidAreaY() + entity.getSolidAreaHeight();
		
		// Calcola i bordi delle tile in cui si trova l'entity
		this.entityLeftCol = entityLeftWorldX/gp.getTileSize();
		this.entityRightCol = entityRightWorldX/gp.getTileSize();
		this.entityTopRow = entityTopWorldY/gp.getTileSize();
		this.entityBottomRow = entityBottomWorldY/gp.getTileSize();
	}
	
	public boolean isPlayerNearObject(SuperObject obj) {
		int objLeftWorldX   = obj.getWorldX() + obj.getSolidAreaX();
	    int objRightWorldX  = obj.getWorldX() + obj.getSolidAreaX() + obj.getSolidAreaWidth();
	    int objTopWorldY    = obj.getWorldY() + obj.getSolidAreaY();
	    int objBottomWorldY = obj.getWorldY() + obj.getSolidAreaY() + obj.getSolidAreaHeight();

	    int playerLeftWorldX   = gp.getPlayer().getWorldX() + gp.getPlayer().getSolidAreaX();
	    int playerRightWorldX  = gp.getPlayer().getWorldX() + gp.getPlayer().getSolidAreaX() + gp.getPlayer().getSolidAreaWidth();
	    int playerTopWorldY    = gp.getPlayer().getWorldY() + gp.getPlayer().getSolidAreaY();
	    int playerBottomWorldY = gp.getPlayer().getWorldY() + gp.getPlayer().getSolidAreaY() + gp.getPlayer().getSolidAreaHeight();

	    return objRightWorldX > playerLeftWorldX &&
	           objLeftWorldX < playerRightWorldX &&
	           objBottomWorldY > playerTopWorldY &&
	           objTopWorldY < playerBottomWorldY;

	}
}
