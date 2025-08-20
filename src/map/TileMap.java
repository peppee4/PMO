package map;

import main.GamePanel;

public class TileMap {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNumber[][];
	
	public TileMap(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[20];                                       // Numero di tiles utilizzabili
		mapTileNumber = new int [gp.maxWorldCol][gp.maxWorldRow];  /* Crea una matrice 2D di interi 50×50, 
																	  che rappresenta la mappa del mondo.
																	  Ogni cella della matrice indica quale tile 
																	  si trova in quella posizione. */
			
	}
}
