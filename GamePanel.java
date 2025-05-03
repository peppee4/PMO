import java.awt.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    // Impostazione della finestra
	final int originalTileSize = 16;                        // 16x16 tile
	final int scale = 3;

	final int tileSize = originalTileSize * scale;          // 48 * 48
	final int maxScreenCol = 16 ;
	final int maxScreenRow = 12 ;
	final int screenWidth = tileSize * maxScreenCol;        // 768 pixels
	final int screenHeight = tileSize * maxScreenRow;       // 576 pixels 

	// FPS
	int FPS = 60;
	
    // Creazione di un gestore degli eventi della tastiera
    KeyHandler keyH = new KeyHandler();

    // Creiamo il Thread per il flusso del gioco
	Thread gameThread;
	
    // Impostiamo il player nella posizione iniziale e impostiamo la sua velocità iniziale
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    // Costruttore della classe
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));    // Dimensione predefinita del JPanel
        this.setBackground(Color.black);                                    // Colore di sfondo del JPanel
        this.setDoubleBuffered(true);                                 // Per ridurre il flickering
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
	
    // Funzione per la gestione del movimento del player
	public void update(){
		if(keyH.upPressed == true){
            playerY -= playerSpeed;
        }else if(keyH.downPressed == true){
            playerY += playerSpeed;
        }else if(keyH.leftPressed == true){
            playerX -= playerSpeed;
        }else if(keyH.rightPressed == true){
            playerX += playerSpeed;
        }
	}
	
    // Funzione per ridisegnare il player
	public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.WHITE);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
	} 
}
