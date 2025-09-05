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

    // Costruttore
    public AssetSetter (GamePanel gp) {
        this.gp = gp;
    }

    // Metodo per posizionare gli oggetti di gioco
    public void setObject(){

        // Chest
        gp.obj[0] = new ObjChest();
        gp.obj[0].setWorldX(gp.getTileSize() * 21);
        gp.obj[0].setWorldY(gp.getTileSize() * 24);
    }

    // Metodo per posizionare i mostri
    public void setMonster() {
        
        // NormalMonster
        for(int i = 0; i < 5; i++){
            this.findTile();

            if(i < 2){
                // Genera il mostro normale alle coordinate trovate
                gp.mons[i] = new NormalMonster(this.gp);
                gp.mons[i].setWorldX(spawnX * gp.getTileSize());
                gp.mons[i].setWorldY(spawnY * gp.getTileSize());
            }else if(i >= 1){
                // Genera il mostro slime alle coordinate trovate
                gp.mons[i] = new SlimeMonster(this.gp);
                gp.mons[i].setWorldX(spawnX * gp.getTileSize());
                gp.mons[i].setWorldY(spawnY * gp.getTileSize());
            }
        }    
    }

    private void findTile(){
        int tileNum = 0;
        this.spawnX = 0;    // Reset
        this.spawnY = 0;    // Reset

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
