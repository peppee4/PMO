package main;

import java.util.Random;

import entity.NormalMonster;
import entity.SlimeMonster;
import object.ObjChest;

public class AssetSetter {
    GamePanel gp;                           // Riferimento al GamePanel
    private Random rdm = new Random();      // Generatore di numeri casuali
    private boolean validPosition = false;  // Posizione valida per lo spawn
    private int spawnX, spawnY;             // Coordinate per lo spawn
    private KeyHandler keyH;				// Riferimento al KeyHandler

    // Costruttore
    public AssetSetter (GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    }

    // Metodo per posizionare gli oggetti di gioco
    public void setObject(){

        // Chest
        for(int i = 0; i < 3; i++) {
        	this.findTile();
        	gp.obj[i] = new ObjChest(this.keyH, this.gp);
            gp.obj[i].setWorldX(gp.getTileSize() * spawnX);
            gp.obj[i].setWorldY(gp.getTileSize() * spawnY);
        }
    }

    // Metodo per posizionare i mostri
    public void setMonster() {
        
        // NormalMonster
        for(int i = 0; i < 9; i++){
            this.findTile();

            if(i < 4){
                // Genera il mostro normale alle coordinate trovate
                gp.mons[i] = new NormalMonster(this.gp);
                gp.mons[i].setWorldX(spawnX * gp.getTileSize());
                gp.mons[i].setWorldY(spawnY * gp.getTileSize());
            }else if(i > 4){
                // Genera il mostro slime alle coordinate trovate
                gp.mons[i] = new SlimeMonster(this.gp);
                gp.mons[i].setWorldX(spawnX * gp.getTileSize());
                gp.mons[i].setWorldY(spawnY * gp.getTileSize());
            }
        }    
    }

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
}
