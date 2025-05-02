import java.awt.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    // Impostazione della finestra
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;
	public final int tileSize = originalTileSize * scale; // 48 * 48
	
	final int maxScreenCol = 16 ;
	final int maxScreenRow = 12 ;
	final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	final int screenHeight = tileSize * maxScreenRow; // 576 pixels 

	// FPS
	int FPS = 60;
	
	Thread gameThread;
	
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));    // Dimensione predefinita del JPanel
        this.setBackground(Color.black);                                    // Colore di sfondo del JPanel
        this.setDoubleBuffered(true);                                 // Per ridurre il flickering

    }

    public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
    }
    
    // Metodo principale del thread del gioco
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
        	// Aggiornare le informazioni
            update();
            // Disegnare su schermo con le informazioni aggiornate
            repaint();

            try {
            	// Calcoliamo quanto tempo manca prima di eseguire il prossimo ciclo di disegno
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
                // Quando il programma sta impiegando più tempo del previsto
                if (remainingTime < 0) {
                	remainingTime = 0;
                }
                // Facciamo dormire il Thread per il numero di millisecondi calcolati per mantenere 
                // Il gioco sincronizzato con l'intervallo desiderato
                Thread.sleep((long) remainingTime);
                // Programmiamo il prossimo fotogramma
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
	public void update(){
		
	}
	
	public void paintComponent(Graphics g){
			
	} 
}
