package main;

import entity.Entity;

public class CollisionChecker {

	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		
		int entityLeftWorldX = entity.getWorldX() + entity.getSolidAreaX();
		int entityRightWorldX = entity.getWorldX() + entity.getSolidAreaX() + entity.getSolidAreaWidth();
		int entityTopWorldY =entity.getWorldY() + entity.getSolidAreaY();
		int entityBottomWorldY = entity.getWorldY() + entity.getSolidAreaY() + entity.getSolidAreaHeight();
				
		int entityLeftCol = entityLeftWorldX/gp.getTileSize();
		int entityRightCol = entityRightWorldX/gp.getTileSize();
		int entityTopRow = entityTopWorldY/gp.getTileSize();
		int entityBottomRow = entityBottomWorldY/gp.getTileSize();
		
		int tileNum1, tileNum2;
		
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
}
