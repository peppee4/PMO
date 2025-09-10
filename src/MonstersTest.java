import main.GamePanel;
import map.TileMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.Entity;
import entity.Monsters;
import entity.NormalMonster;

import java.awt.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class MonstersTest {
    private TestGamePanel gamePanel;
    private Monsters monster;

    // Classe di test per GamePanel che permette l'accesso ai campi necessari
    static class TestGamePanel extends GamePanel {
        public int[][] testMapTileNumber;
        
        public TestGamePanel() {
            super();
        }
        
        @Override
        public int getMaxWorldCol() {
            return 5; // Dimensione ridotta per i test (5x5 invece di 50x50)
        }
        
        @Override
        public int getMaxWorldRow() {
            return 5; // Dimensione ridotta per i test (5x5 invece di 50x50)
        }
        
        @Override
        public TileMap getMap() {
            return new TileMap(this) {
                public int[][] getMapTileNumber() {
                    return testMapTileNumber;
                }
            };
        }
    }

    // Metodo eseguito prima di ogni test
    @BeforeEach
    void setUp() {
        gamePanel = new TestGamePanel();
        gamePanel.testMapTileNumber = new int[5][5]; // Inizializza una mappa 5x5 senza ostacoli
        
        // Crea un mostro normale e posizionalo al centro della mappa
        monster = new NormalMonster(gamePanel);
        monster.setWorldX(2 * gamePanel.getTileSize());
        monster.setWorldY(2 * gamePanel.getTileSize());
        monster.setSpeed(2.0);
        monster.setDirection("down");
        
        // Posiziona anche il giocatore nella mappa di test
        gamePanel.getPlayer().setWorldX(3 * gamePanel.getTileSize());
        gamePanel.getPlayer().setWorldY(3 * gamePanel.getTileSize());
    }

    // Test per verificare l'inizializzazione corretta del mostro
    @Test
    void testMonsterInitialization() {
        assertNotNull(monster);
        assertEquals(2 * gamePanel.getTileSize(), monster.getWorldX());
        assertEquals(2 * gamePanel.getTileSize(), monster.getWorldY());
        assertEquals("down", monster.getDirection());
    }

    // Test per verificare il metodo alignedToTile
    @Test
    void testAlignedToTile() {
        // Il mostro è allineato al tile (96 è multiplo di 48)
        assertTrue(monster.alignedToTile());
        
        // Modifica la posizione per non essere allineata
        monster.setWorldX(50);
        assertFalse(monster.alignedToTile());
    }

    // Test per verificare il calcolo della distanza tra tile senza ostacoli
    @Test
    void testGetTileDistance() {
        int distance = monster.getTileDistance(1, 1, 3, 3);
        assertEquals(4, distance); // Distanza Manhattan: 2+2=4
    }

    // Test per verificare la direzione casuale del mostro
    @Test
    void testRandomDirection() {
        String originalDirection = monster.getDirection();
        monster.randomDirection();
        
        // La nuova direzione dovrebbe essere una delle quattro valide
        assertTrue(monster.getDirection().equals("up") || 
                  monster.getDirection().equals("down") || 
                  monster.getDirection().equals("left") || 
                  monster.getDirection().equals("right"));
    }

    // Test per verificare il movimento del mostro
    @Test
    void testUpdateMovement() throws Exception {
        monster.setDirection("right");
        int initialX = monster.getWorldX();
        
        monster.update();
        
        // La posizione X dovrebbe essere aumentata della velocità
        assertEquals(initialX + (int)monster.getSpeed(), monster.getWorldX());
    }

    // Test per verificare l'animazione degli sprite del mostro
    @Test
    void testSpriteAnimation() throws Exception {
        // Usa la reflection per accedere ai campi privati della classe Entity
        Field spriteCounterField = Entity.class.getDeclaredField("spriteCounter");
        spriteCounterField.setAccessible(true);
        
        Field spriteNumField = Entity.class.getDeclaredField("spriteNum");
        spriteNumField.setAccessible(true);
        
        // Imposta il contatore a un valore alto per forzare il cambio di sprite
        spriteCounterField.set(monster, 13);
        int initialSpriteNum = (int) spriteNumField.get(monster);
        
        monster.update();
        
        // Il numero dello sprite dovrebbe essere cambiato
        int newSpriteNum = (int) spriteNumField.get(monster);
        assertNotEquals(initialSpriteNum, newSpriteNum);
        
        // Il contatore dovrebbe essere resettato
        assertEquals(0, spriteCounterField.get(monster));
    }

    // Test per verificare che il mostro normale infligga danni
    @Test
    void testNormalMonsterDamage() {
        NormalMonster normalMonster = new NormalMonster(gamePanel);
        assertTrue(normalMonster.getDamage() > 0);
    }
}