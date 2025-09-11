import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.GamePanel;
import map.TileMap;

class TileMapTest {

    private GamePanel gamePanel;
    private TileMap tileMap;

    @BeforeEach
    void setUp() {
        // Creiamo un GamePanel minimale per i test
        gamePanel = new GamePanel() {
            @Override
            public int getMaxWorldCol() { return 3; }
            
            @Override
            public int getMaxWorldRow() { return 3; }
            
            @Override
            public int getTileSize() { return 48; }
            
            @Override
            public int getLevelNumber() { return 1; }
        };
        
        tileMap = new TileMap(gamePanel);
    }

    @Test
    void testTileArrayInitialization() {
        assertNotNull(tileMap.getTile());
        assertEquals(20, tileMap.getTile().length);
    }

    @Test
    void testMapTileNumberInitialization() {
        assertNotNull(tileMap.getMapTileNumber());
        assertEquals(3, tileMap.getMapTileNumber().length);
        assertEquals(3, tileMap.getMapTileNumber()[0].length);
    }

    // Aggiungiamo un metodo helper in TileMap per il testing
    public void loadMapFromStream(InputStream is) {
        try {
            // Simula il comportamento originale ma con stream sostituito
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;
            
            while(col < gamePanel.getMaxWorldCol() && row < gamePanel.getMaxWorldRow()) {
                String line = br.readLine();
                while(col < gamePanel.getMaxWorldCol()) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    this.gamePanel.getMap().getMapTileNumber()[col][row] = num;
                    col++;
                }
                if(col == gamePanel.getMaxWorldCol()) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch(Exception e) {
            fail("Exception during map loading: " + e.getMessage());
        }
    }

	
}
