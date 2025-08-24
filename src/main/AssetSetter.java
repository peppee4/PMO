package main;

import entity.NormalMonster;
import object.ObjChest;
//import entity.NormalMonster;

public class AssetSetter {
    GamePanel gp;   // Riferimento al GamePanel

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
        
        // NomramlMonster
        gp.mons[0] = new NormalMonster(this.gp);
        gp.mons[0].setWorldX(gp.getTileSize() * 24);
        gp.mons[0].setWorldY(gp.getTileSize() * 24);

        gp.mons[1] = new NormalMonster(this.gp);
        gp.mons[1].setWorldX(gp.getTileSize() * 24);
        gp.mons[1].setWorldY(gp.getTileSize() * 25);
/* 
        gp.mons[0] = new NormalMonster(this.gp);
        gp.mons[0].setWorldX(gp.getTileSize() * 24);
        gp.mons[0].setWorldY(gp.getTileSize() * 24);

        gp.mons[0] = new NormalMonster(this.gp);
        gp.mons[0].setWorldX(gp.getTileSize() * 24);
        gp.mons[0].setWorldY(gp.getTileSize() * 24);

        gp.mons[0] = new NormalMonster(this.gp);
        gp.mons[0].setWorldX(gp.getTileSize() * 24);
        gp.mons[0].setWorldY(gp.getTileSize() * 24);*/
    }
}
