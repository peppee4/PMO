package main;

import java.awt.*;
import javax.swing.JPanel;
import entity.Player;
import map.TileMap;

public class GamePanel extends JPanel implements Runnable{

    // Impostazioni della finestra
	final int originalTileSize = 16;                        // 16x16 tile
	final int scale = 3;

	private final int tileSize = originalTileSize * scale;   // 48 * 48
	private final int maxScreenCol = 16 ;
	private final int maxScreenRow = 12 ;
	private final int screenWidth = tileSize * maxScreenCol;        // 768 pixels
	private final int screenHeight = tileSize * maxScreenRow;       // 576 pixels 
	
	// Impostazioni della Mappa
	private final int maxWorldCol = 50;                      // Dimensioni delle colonne della mappa
	private final int maxWorldRow = 50;						// Dimensioni delle righe della mappa
	
	// FPS
	int FPS = 60;
	
	TileMap tileM = new TileMap(this);
    KeyHandler keyH = new KeyHandler(this); // Creazione di un gestore degli eventi della tastiera

    // Creiamo il Thread per il flusso del gioco
	Thread gameThread;
	
	// Creiamo il Player
	public Player player = new Player(this,keyH);

    // Costruttore della classe
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));    // Dimensione predefinita del JPanel
        this.setBackground(Color.black);                                    // Colore di sfondo del JPanel
        this.setDoubleBuffered(true);                                       // Per ridurre il flickering
        this.addKeyListener(keyH);                                          // Aggiungiamo il Listener al pannello
        this.setFocusable(true);                                  
    }

    public void startGameThread() {
		gameThread = new Thread(this);  // Inizializzazione del Thread
		gameThread.start();             // Thread start
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
	
    // Metodo per la gestione del movimento del player
	public void update(){
		player.update();
	}
	
    // Metodo per ridisegnare il player
	public void paintComponent(Graphics g){
		
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        tileM.draw(g2);

        player.draw(g2);
        
        g2.dispose();
	} 
	
	public int getMaxWorldCol() {
		
		return this.maxWorldCol;
	}
	
	public int getMaxWorldRow() {
		
		return this.maxWorldRow;
	}
	
	public int getTileSize() {
		
		return this.tileSize;
	}
	
	public int getScreenWidth() {
		
		return this.screenWidth;
	}
	
	public int getScreenHeight() {
		
		return this.screenHeight;
	}
}
