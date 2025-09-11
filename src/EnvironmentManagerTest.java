import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import environment.EnvironmentManager;
import main.GamePanel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class EnvironmentManagerTest {

    private GamePanel gp;
    private EnvironmentManager environmentManager;

    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        environmentManager = new EnvironmentManager(gp);
    }

    @Test
    void testSetLight() {
        // Verifica che setLight non generi eccezioni
        assertDoesNotThrow(() -> environmentManager.setLight(400));
        assertDoesNotThrow(() -> environmentManager.setLight(1200));
    }

    @Test
    void testDrawWithLightSet() {
        // Prima impostiamo la luce
        environmentManager.setLight(400);
        
        // Creiamo un oggetto graphics di test
        BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();
        
        // Verifica che draw non generi eccezioni quando la luce è impostata
        assertDoesNotThrow(() -> environmentManager.draw(g2));
        
        g2.dispose();
    }

    @Test
    void testDrawWithoutLightSet() {
        // Creiamo un oggetto graphics di test
        BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();
        
        // Verifica che draw generi una NullPointerException quando la luce non è impostata
        assertThrows(NullPointerException.class, () -> environmentManager.draw(g2));
        
        g2.dispose();
    }
}
