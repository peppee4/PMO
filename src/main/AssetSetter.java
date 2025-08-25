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
        gp.obj[0].worldX = gp.getTileSize() * 44;
        gp.obj[0].worldY = gp.getTileSize() * 1;
    }

    // Metodo per posizionare i mostri
    public void setMonster() {
        
        // NormalMonster
        for(int i = 0; i < 2; i++){
            this.findTile();

            // Imposta il mostro nella posizione trovata
            gp.mons[i] = new NormalMonster(this.gp);
            gp.mons[i].setWorldX(spawnX * gp.getTileSize());
            gp.mons[i].setWorldY(spawnY * gp.getTileSize());
        }

        // SlimeMonster
        for(int i = 0; i < 1; i++){
            this.findTile();

            // Imposta il mostro nella posizione trovata
            gp.mons[i] = new SlimeMonster(this.gp);
            gp.mons[i].setWorldX(21 * gp.getTileSize());
            gp.mons[i].setWorldY(23 * gp.getTileSize());
        }
    }

    private void findTile(){
        int tileNum = 0;

        // Trova una posizione casuale non solida
            while(!this.validPosition){
                this.spawnX = rdm.nextInt(gp.getMaxWorldCol());
                this.spawnY = rdm.nextInt(gp.getMaxWorldRow());
                tileNum = gp.tileM.mapTileNumber[spawnX][spawnY];

                // Controlla se la tile non è solida
                if(!gp.tileM.tile[tileNum].collision){
                    this.validPosition = true;
                }
            }
    }
}
