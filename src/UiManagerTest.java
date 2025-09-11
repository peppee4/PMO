import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Font;


import main.*;

public class UiManagerTest {

    private GamePanel gp;
    private UiManager uiManager;

    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        uiManager = new UiManager(gp);
    }

    @Test
    void testGetXForCenteredText() {
        // Creiamo un'immagine fittizia per ottenere un Graphics2D
        BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();
        g2.setFont(new Font("Arial", Font.PLAIN, 40));

        // Impostiamo il Graphics2D nell'UiManager usando reflection
        try {
            Field g2Field = UiManager.class.getDeclaredField("g2");
            g2Field.setAccessible(true);
            g2Field.set(uiManager, g2);
        } catch (Exception e) {
            fail("Impossibile impostare g2 tramite reflection: " + e.getMessage());
        }

        String text = "Test";
        int x = uiManager.getXForCenteredText(text);

        // Calcoliamo manualmente la x attesa
        int screenWidth = gp.getScreenWidth();
        int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int expectedX = screenWidth / 2 - textWidth / 2;

        assertEquals(expectedX, x);

        g2.dispose();
    }

    @Test
    void testCommandNum() {
        uiManager.setCommandNum(2);
        assertEquals(2, uiManager.getCommandNum());

        uiManager.plusCommandNum();
        assertEquals(3, uiManager.getCommandNum());

        uiManager.minusCommandNum();
        assertEquals(2, uiManager.getCommandNum());
    }

    @Test
    void testOptionsCommandNum() {
        uiManager.setOptionsCommandNum(2);
        assertEquals(2, uiManager.getOptionsCommandNum());

        // Test plusOptionsCommandNum con wrap-around
        uiManager.setOptionsCommandNum(3);
        uiManager.plusOptionsCommandNum();
        assertEquals(0, uiManager.getOptionsCommandNum());

        // Test minusOptionsCommandNum con wrap-around
        uiManager.setOptionsCommandNum(0);
        uiManager.minusOptionsCommandNum();
        assertEquals(3, uiManager.getOptionsCommandNum());
    }

    @Test
    void testVolumeGetters() {
        // I volumi sono inizializzati a 100
        assertEquals(100, uiManager.getSoundVolume());
        assertEquals(100, uiManager.getMusicVolume());
    }

    @Test
    void testPlayerPositionSetters() {
        uiManager.setxPlayer(100);
        uiManager.setyPlayer(200);
        
        assertEquals(100, uiManager.getxPlayer());
        assertEquals(200, uiManager.getyPlayer());
    }

    @Test
    void testMonsterPositionSetters() {
        uiManager.setxMonster(150);
        uiManager.setyMonster(250);
        
        assertEquals(150, uiManager.getxMonster());
        assertEquals(250, uiManager.getyMonster());
    }

    @Test
    void testPreviousState() {
        uiManager.setPreviousState(2);
        assertEquals(2, uiManager.getPreviousState());
    }
}
