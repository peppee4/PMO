import java.awt.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    // Impostazione della finestra
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;
	public final int tileSize = originalTileSize * scale; // 48 * 48
	
	final int maxScreenCol = 16 ;
	final int maxScreenRow = 12 ;
	final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	final int screenHeight = tileSize * maxScreenRow; // 576 pixels 

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));    // Dimensione predefinita del JPanel
        this.setBackground(Color.black);                                    // Colore di sfondo del JPanel
        this.setDoubleBuffered(true);                                 // Per ridurre il flickering

    }
}
