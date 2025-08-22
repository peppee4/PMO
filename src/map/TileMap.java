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
		
		tile = new Tile[20];                                       /* Numero di tiles utilizzabili */
		mapTileNumber = new int [gp.maxWorldCol][gp.maxWorldRow];  /* Crea una matrice 2D di interi 50×50, 
																	  che rappresenta la mappa del mondo.
																	  Ogni cella della matrice indica quale tile 
																	  si trova in quella posizione. */
		
		getTileImage();
		loadMap("/maps/lago.txt");
	}
	
	public void setup(int index, String imageName, boolean collision) {
		
		ImageScaler iScaler = new ImageScaler();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = iScaler.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getTileImage() {
		
		setup(0, "earth", false); // EARTH
		setup(1, "wall", true); // WALL
	}
	
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNumber[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
			    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			
			worldCol++;
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
	
	public void loadMap(String filePath) {
		
		try {
			// USIAMO INPUTSTREAM PER IMPORTARE IL FILE
			InputStream is = getClass().getResourceAsStream(filePath);
			// USIAMO BUFFEREDREADER PER LEGGERE IL CONTENUTO
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine(); // LEGGE UNA SINGOLA RIGA DI TESTO
				
				while(col < gp.maxWorldCol) {
					// ANDIAMO A METTERE NELL'ARRAY LE RIGHE
					String numbers[] = line.split(" ");
					// TRASFORMIAMO LE STRINGHE IN ARRAY
					int num = Integer.parseInt(numbers[col]); // USE COL AS AN INDEX FOR NUMBERS[] ARRAY
					// WE STORE THE EXTRACTED NUMBER IN THE MAPTILENUMBER
					mapTileNumber[col][row] = num;
					col++;
				}
				if(col== gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
