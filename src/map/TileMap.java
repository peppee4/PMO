package map;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.ImageScaler;

public class TileMap {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNumber[][];
	
	public TileMap(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[20];                                                 /* Numero di tiles utilizzabili */
		mapTileNumber = new int [gp.getMaxWorldCol()][gp.getMaxWorldRow()];  /* Crea una matrice 2D di interi 50×50, 
																	            che rappresenta la mappa del mondo.
																	  			Ogni cella della matrice indica quale tile 
																	  			si trova in quella posizione. */
		
		getTileImage();
		loadMap("/maps/finalMap1.txt");
	}
	
	public void setup(int index, String imageName, boolean collision) {
		
		ImageScaler iScaler = new ImageScaler();
		
		try {
			
			// Crea un nuovo Tile e lo inserisce nell’array
			tile[index] = new Tile();
			
			// Carica l’immagine del tile dalla cartella /tiles
	        // Esempio: se imageName = "grass" -> carica /tiles/grass.png
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			
			// Scala l’immagine alla dimensione standard del gioco (gp.tileSize × gp.tileSize)
			tile[index].image = iScaler.scaleImage(tile[index].image, gp.getTileSize(), gp.getTileSize());
			
			// Imposta se questa tile è solida o attraversabile
			tile[index].collision = collision;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getTileImage() {
		
		setup(0, "earth", false); // EARTH
		setup(1, "wall", true); 	// WALL
	}
	
	public void draw(Graphics2D g2) {
		
		// Variabili di appoggio
		int worldCol = 0;
		int worldRow = 0;
		
		// Ciclo che percorre tutte le righe e colonne della mappa
		while(worldCol < gp.getMaxWorldCol() && worldRow < gp.getMaxWorldRow()) {
			
			// Ottieni il numero del tile da disegnare in questa cella
			int tileNum = mapTileNumber[worldCol][worldRow];
			
			// Coordinate della tile nel mondo (coordinate assolute)
			int worldX = worldCol * gp.getTileSize();
			int worldY = worldRow * gp.getTileSize();
			
			 // Coordinate sullo schermo (relative al player, che sta al centro)
			int screenX = worldX - gp.player.getWorldX() + gp.player.getCenterX();
			int screenY = worldY - gp.player.getWorldY() + gp.player.getCenterY();
			
			// Disegna solo i tile che si trovano entro i confini dello schermo
			if(worldX + gp.getTileSize() > gp.player.getWorldX() - gp.player.getCenterX() &&
			   worldX - gp.getTileSize() < gp.player.getWorldX() + gp.player.getCenterX() &&
			   worldY + gp.getTileSize() > gp.player.getWorldY()- gp.player.getCenterY() &&
			   worldY - gp.getTileSize() < gp.player.getWorldY() + gp.player.getCenterY()) {
				
				// Disegna l’immagine della tile sullo schermo
			    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			
			// Passa alla prossima colonna
			worldCol++;
			
			// Se arriva alla fine della riga, passa alla successiva
			if(worldCol == gp.getMaxWorldCol()) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
	
	public void loadMap(String filePath) {
		
		try {
			// Usiamo uno stream per leggere il file della mappa dalle risorse
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			// Ciclo while che legge il file riga per riga e riempie la matrice della mappa
			while(col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
				
				// Legge una riga del file (es. "0 0 1 2 2 1 0")
				String line = br.readLine(); 
				
				// Scorre tutti i numeri riga per riga
				while(col < gp.getMaxWorldCol()) {
					
					// Divide la riga in numeri separati da spazio
					String numbers[] = line.split(" ");
					// Converte il numero (stringa -> int)
					int num = Integer.parseInt(numbers[col]); // USE COL AS AN INDEX FOR NUMBERS[] ARRAY
					// Salva il numero nella matrice della mappa
					mapTileNumber[col][row] = num;
					col++;
				}
				// Completata una riga, resetta col e va alla prossima riga
				if(col== gp.getMaxWorldCol()) {
					col = 0;
					row++;
				}
			}
			// Chiusura del file
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
