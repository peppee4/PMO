import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.GamePanel;
import object.ObjChest;
import object.ObjDoor;
import object.ObjHeart;
import object.ObjKey;
import object.SuperObject;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

// Classe di test per tutti gli oggetti del gioco
public class ObjTest {
    
    private GamePanel gamePanel;
    
    @BeforeEach
    void setUp() {
        // Inizializza un GamePanel di base per i test
        gamePanel = new GamePanel();
    }
    
    @Test
    void testSuperObjectConstructor() {
        SuperObject obj = new SuperObject(gamePanel) {};
        
        assertNotNull(obj);
        assertEquals(0, obj.getWorldX());
        assertEquals(0, obj.getWorldY());
        assertNotNull(obj.getSolidArea());
        assertEquals(48, obj.getSolidArea().width);
        assertEquals(48, obj.getSolidArea().height);
    }
    
    @Test
    void testSuperObjectGettersAndSetters() {
        SuperObject obj = new SuperObject(gamePanel) {};
        
        obj.setWorldX(100);
        obj.setWorldY(200);
        assertEquals(100, obj.getWorldX());
        assertEquals(200, obj.getWorldY());
        
        obj.setSolidAreaX(10);
        obj.setSolidAreaY(20);
        obj.setSolidAreaWidth(30);
        obj.setSolidAreaHeight(40);
        assertEquals(10, obj.getSolidAreaX());
        assertEquals(20, obj.getSolidAreaY());
        assertEquals(30, obj.getSolidAreaWidth());
        assertEquals(40, obj.getSolidAreaHeight());
        
        obj.setObjStatus(true);
        assertTrue(obj.isObjStatus());
    }
    
    @Test
    void testObjChestConstructor() {
        ObjChest chest = new ObjChest(gamePanel);
        
        assertEquals("Chest", chest.getName());
        assertFalse(chest.isCollision());
        assertEquals(0, chest.getSolidAreaX());
        assertEquals(0, chest.getSolidAreaY());
        assertEquals(30, chest.getSolidAreaWidth());
        assertEquals(45, chest.getSolidAreaHeight());
        assertEquals(35, chest.getWidth());
        assertEquals(35, chest.getHeight());
    }
    
    @Test
    void testObjDoorConstructor() {
        ObjDoor door = new ObjDoor(gamePanel);
        
        assertEquals("Door", door.getName());
        assertFalse(door.isCollision());
        assertEquals(0, door.getSolidAreaX());
        assertEquals(20, door.getSolidAreaY());
        assertEquals(30, door.getSolidAreaWidth());
        assertEquals(45, door.getSolidAreaHeight());
        assertEquals(60, door.getWidth());
        assertEquals(60, door.getHeight());
    }
    
    @Test
    void testObjHeartConstructor() {
        ObjHeart heart = new ObjHeart(gamePanel);
        
        assertEquals("Heart", heart.getName());
        assertNotNull(heart.getImage1());
        assertNotNull(heart.getImage2());
        assertNotNull(heart.getImage3());
        assertEquals(30, heart.getWidth());
        assertEquals(30, heart.getHeight());
    }
    
    @Test
    void testObjKeyConstructor() {
        ObjKey key = new ObjKey(gamePanel);
        
        assertEquals("Key", key.getName());
        assertNotNull(key.getImage1());
    }
    
    @Test
    void testSuperObjectDefaultUpdate() {
        SuperObject obj = new SuperObject(gamePanel) {};
        
        // Testa che l'update non generi eccezioni
        assertDoesNotThrow(() -> obj.update());
    }
    
    @Test
    void testSolidAreaDefaultValues() {
        SuperObject obj = new SuperObject(gamePanel) {};
        
        assertEquals(0, obj.getSolidAreaDefaultX());
        assertEquals(0, obj.getSolidAreaDefaultY());
    }
    
    @Test
    void testImageScalerInitialization() {
        SuperObject obj = new SuperObject(gamePanel) {};
        
        assertNotNull(obj.getiScaler());
    }
    
    @Test
    void testObjStatusInitialization() {
        SuperObject obj = new SuperObject(gamePanel) {};
        
        assertFalse(obj.isObjStatus());
    }
}