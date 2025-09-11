import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.Player;
import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

// PlayerTester.java
class PlayerTest {
    private GamePanel gp;
    private KeyHandler keyH;
    private Player player;

    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        keyH = new KeyHandler(gp);
        player = new Player(gp, keyH);
    }

    @Test
    void testPlayerInitialization() {
        assertNotNull(player);
        assertEquals(3.0, player.getLife());
        assertEquals(0, player.getNumberOfKey());
    }

    @Test
    void testPlayerDamage() {
        double initialLife = player.getLife();
        player.takeDamage(1.0);
        assertTrue(player.getLife() < initialLife);
        assertTrue(player.isInvincible());
    }

    @Test
    void testPlayerReset() {
        player.takeDamage(1.0);
        player.restorePlayerValues();
        assertEquals(3.0, player.getLife());
        assertFalse(player.isInvincible());
        assertEquals(0, player.getNumberOfKey());
    }
}
