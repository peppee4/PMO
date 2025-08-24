package main;

import object.ObjKey;

public class AssetSetter {
    GamePanel gp;   // Riferimento al GamePanel

    // Costruttore
    public AssetSetter (GamePanel gp) {
        this.gp = gp;
    }

    // Metodo per posizionare gli oggetti di gioco
    public void setObject(){

        gp.obj[0] = new ObjKey();
        gp.obj[0].worldX = gp.getTileSize() * 44;
        gp.obj[0].worldY = gp.getTileSize() * 1;

    }
}
