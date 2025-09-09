package main;

import java.util.Random;

import entity.CobraMonster;
import entity.ExplosiveMonster;
import entity.InvisibleMonster;
import entity.NormalMonster;
import entity.SlimeMonster;
import object.ObjChest;
import object.ObjDoor;

public class AssetSetter {
    GamePanel gp;                           // Riferimento al GamePanel
    private Random rdm = new Random();      // Generatore di numeri casuali
    private boolean validPosition = false;  // Posizione valida per lo spawn
    private int spawnX, spawnY;             // Coordinate per lo spawn

    // Costruttore
    public AssetSetter (GamePanel gp) {
        this.gp = gp;
    }

    // Metodo per posizionare gli oggetti di gioco
    public void setObject(){

        // Chest
        for(int i = 0; i < 3; i++) {
    	
        	this.findTile();
        	gp.obj[i] = new ObjChest(gp);
            gp.obj[i].setWorldX(gp.getTileSize() * spawnX);
            gp.obj[i].setWorldY(gp.getTileSize() * spawnY);
        }  
        
        // Door
        findDoorTile();
        gp.obj[3] = new ObjDoor(this.gp);
        gp.obj[3].setWorldX(gp.getTileSize() * spawnX);
        gp.obj[3].setWorldY(gp.getTileSize() * spawnY);
    }

    // Metodo per posizionare i mostri
    public void setMonster() {
    	
        switch(gp.levelNumber) {
        	case 1:
        		for(int i = 0; i < 12; i++){
                    this.findTile();

                    if(i < 4){
                        // Genera il mostro normale alle coordinate trovate
                    	gp.mons[i] = new NormalMonster(this.gp);
                        gp.mons[i].setWorldX(spawnX * gp.getTileSize());
                        gp.mons[i].setWorldY(spawnY * gp.getTileSize());
                    }else {
                        // Genera il mostro slime alle coordinate trovate
                        gp.mons[i] = new SlimeMonster(this.gp);
                        gp.mons[i].setWorldX(spawnX * gp.getTileSize());
                        gp.mons[i].setWorldY(spawnY * gp.getTileSize());
                    }
                } 
        		break;
        	case 2:
        		for(int i = 0; i < 12; i++){
                    this.findTile();

                    if(i < 4){
                        // Genera il mostro normale alle coordinate trovate
                    	gp.mons[i] = new InvisibleMonster(this.gp);
                        gp.mons[i].setWorldX(spawnX * gp.getTileSize());
                        gp.mons[i].setWorldY(spawnY * gp.getTileSize());
                    }else{
                        // Genera il mostro slime alle coordinate trovate
                        gp.mons[i] = new SlimeMonster(this.gp);
                        gp.mons[i].setWorldX(spawnX * gp.getTileSize());
                        gp.mons[i].setWorldY(spawnY * gp.getTileSize());
                    }
                } 
        		break;
        	case 3:
        		for(int i = 0; i < 12; i++){
                    this.findTile();

                    if(i < 4){
                        // Genera il mostro normale alle coordinate trovate
                    	gp.mons[i] = new ExplosiveMonster(this.gp);
                        gp.mons[i].setWorldX(spawnX * gp.getTileSize());
                        gp.mons[i].setWorldY(spawnY * gp.getTileSize());
                    }else{
                        // Genera il mostro slime alle coordinate trovate
                        gp.mons[i] = new CobraMonster(this.gp);
                        gp.mons[i].setWorldX(spawnX * gp.getTileSize());
                        gp.mons[i].setWorldY(spawnY * gp.getTileSize());
                    }
                } 
        		break;
        }
    }

    // Spawn casuale fuori dal muro
    private void findTile(){
        int tileNum = 0;
        this.spawnX = 0;    			// Reset
        this.spawnY = 0;    			// Reset
        this.validPosition = false;		// Reset

        // Trova una posizione casuale non solida
        while(!this.validPosition){
        	this.spawnX = rdm.nextInt(gp.getMaxWorldCol());
            this.spawnY = rdm.nextInt(gp.getMaxWorldRow());
            tileNum = gp.getMap().mapTileNumber[spawnX][spawnY];

            // Controlla se la tile non è solida
            if(!gp.getMap().tile[tileNum].collision){
            	this.validPosition = true;
            }
        }
    }
    
    // Spawn casuale per la porta
    private void findDoorTile() {
        int tileNum = 0;
        int tileBelowNum = 0;
        this.spawnX = 0;              // Reset
        this.spawnY = 0;              // Reset
        this.validPosition = false;  // Reset

        while (!this.validPosition) {
            this.spawnX = rdm.nextInt(gp.getMaxWorldCol());
            this.spawnY = rdm.nextInt(gp.getMaxWorldRow() - 1); // -1 per evitare IndexOutOfBounds

            tileNum = gp.getMap().mapTileNumber[spawnX][spawnY];
            tileBelowNum = gp.getMap().mapTileNumber[spawnX][spawnY + 1];

            boolean isWall = (tileNum == 1);
            boolean isBelowWalkable = !gp.getMap().tile[tileBelowNum].collision;

            if (isWall && isBelowWalkable) {
                this.validPosition = true;
            }
        }
    }
}
