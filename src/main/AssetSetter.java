package main;

import java.util.Random;

import entity.NormalMonster;
import object.ObjChest;

public class AssetSetter {
    GamePanel gp;                       // Riferimento al GamePanel
    private Random rdm = new Random();  // Generatore di numeri casuali

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
            boolean posizioneValida = false;
            int spawnX = 0;
            int spawnY = 0;

            // Trova una posizione casuale non solida
            while(!posizioneValida){
                spawnX = rdm.nextInt(gp.getMaxWorldCol());
                spawnY = rdm.nextInt(gp.getMaxWorldRow());
                int tileNum = gp.tileM.mapTileNumber[spawnX][spawnY];

                // Controlla se la tile non è solida
                if(!gp.tileM.tile[tileNum].collision){
                    posizioneValida = true;
                }
            }

            // Imposta il mostro nella posizione trovata
            gp.mons[i] = new NormalMonster(this.gp);
            gp.mons[i].setWorldX(spawnX * gp.getTileSize());
            gp.mons[i].setWorldY(spawnY * gp.getTileSize());
        }
    }
}
