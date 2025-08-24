package main;

import entity.Entity;

public class CollisionChecker {

	private GamePanel gp;
	
	private int entityLeftWorldX = 0;
	private int entityRightWorldX = 0;
	private int entityTopWorldY = 0;
	private int entityBottomWorldY = 0;
		
	private int entityLeftCol = 0;
	private int entityRightCol = 0;
	private int entityTopRow = 0;
	private int entityBottomRow = 0;
	
	public CollisionChecker(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		
		// Calcola i bordi dell'entity
		checkObject(entity);
		
		// Numero delle tile in cui si trova l'entity
		int tileNum1, tileNum2;
		
		// Controlla la direzione dell'entity e verifica se la prossima tile è "solida"
		// Se è "solida" attiva la collisione
		switch(entity.getDirection()) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.getSpeed())/gp.getTileSize();
			tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.setCollisionOn(true);
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.getSpeed())/gp.getTileSize();
			tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.setCollisionOn(true);
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.getSpeed())/gp.getTileSize();
			tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.setCollisionOn(true);
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.getSpeed())/gp.getTileSize();
			tileNum1 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.setCollisionOn(true);
			}
			break;
		}
	}

	// Controlla la collisione tra i mostri e il player
	public void checkPlayer(Entity entity){
		// Calcola i bordi dell'entity
		checkObject(entity);

		// Calcola i bordi del player
		int playerLeftWorldX = gp.player.getWorldX() + gp.player.getSolidAreaX() + 5;
		int playerRightWorldX = gp.player.getWorldX() + gp.player.getSolidAreaX() + gp.player.getSolidAreaWidth() + 5;
		int playerTopWorldY =gp.player.getWorldY() + gp.player.getSolidAreaY() + 5;
		int playerBottomWorldY = gp.player.getWorldY() + gp.player.getSolidAreaY() + gp.player.getSolidAreaHeight() + 5;

		// Calcola i bordi delle tile in cui si trova il player
		int playerLeftCol = playerLeftWorldX/gp.getTileSize();
		int playerRightCol = playerRightWorldX/gp.getTileSize();
		int playerTopRow = playerTopWorldY/gp.getTileSize();
		int playerBottomRow = playerBottomWorldY/gp.getTileSize();

		// Controlla la collisione
		// Se c'è collisione attiva il flag
		if(entityLeftCol == playerLeftCol && entityRightCol == playerRightCol && entityTopRow == playerTopRow && entityBottomRow == playerBottomRow) {
			entity.setCollisionPlayer(true);
		}
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
}
