package main;

import java.awt.*;
import javax.swing.JPanel;
import entity.Player;
import entity.Slime;
import entity.SlimeMonster;
import environment.EnvironmentManager;
import map.TileMap;
import object.SuperObject;
import entity.Monsters;

public class GamePanel extends JPanel implements Runnable{

    // Impostazioni della finestra
	final int originalTileSize = 20;                            // 16x16 tile
	final int scale = 3;

	private final int tileSize = originalTileSize * scale;      // 48 * 48
	private final int maxScreenCol = 20 ;
	private final int maxScreenRow = 12 ;
	private final int screenWidth = tileSize * maxScreenCol;    // 768 pixels
	private final int screenHeight = tileSize * maxScreenRow;   // 576 pixels 
	
	// Impostazioni della Mappa
	private final int maxWorldCol = 50;                         // Dimensioni delle colonne della mappa
	private final int maxWorldRow = 50;						    // Dimensioni delle righe della mappa
	
	// FPS
	int FPS = 60;
	
	public TileMap tileM = new TileMap(this);                                  // Creazione della mappa
    KeyHandler keyH = new KeyHandler(this);                             // Creazione di un gestore degli eventi della tastiera
    public CollisionChecker cChecker = new CollisionChecker(this);      // Creazione del controllore delle collisioni
    public AssetSetter aSetter = new AssetSetter(this);   
    public UiManager ui = new UiManager(this);
    EnvironmentManager eManager = new EnvironmentManager(this);         // Creazione del posizionatore degli oggetti 
    
    // Creiamo il Thread per il flusso del gioco
	Thread gameThread;
	
	// Creiamo il Player
	public Player player = new Player(this,keyH);                       // Creazione del player

    // Creiamo gli oggetti
    public SuperObject obj[] = new SuperObject[10];                     // Array di oggetti di gioco

    // Creiamo i mostri
    public Monsters mons[] = new Monsters[10];                          // Array di mostri
    
    // Creiamo gli slime
    public Slime slime[] = new Slime[30];                               // Array di slime   

    // Stati del gioco
    private boolean gameStatus = true;                          // Stato del gioco (true = in corso, false = terminato)
	private int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    // Costruttore della classe
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));    // Dimensione predefinita del JPanel
        this.setBackground(Color.black);                                    // Colore di sfondo del JPanel
        this.setDoubleBuffered(true);                                       // Per ridurre il flickering
        this.addKeyListener(keyH);                                          // Aggiungiamo il Listener al pannello
        this.setFocusable(true);                                  
    }

    // Metodo per impostare gli oggetti di gioco
    public void setupGame() {
    	
    	aSetter.setObject();    // Posizioniamo gli oggetti
        aSetter.setMonster();   // Posizioniamo i mostri
        
        this.gameState = playState;
        
        eManager.setup();       // Aggiunge l'effetto di luce soffusa attorno al player
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

        while (gameThread != null && this.gameStatus == true) {
            // 1.
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
	
    // Metodo per aggiornare le informazioni del gioco
	public void update(){
		
		if(gameState == playState) {
			player.update();

	        for(int i = 0; i < this.mons.length; i++){
	            if(this.mons[i] != null){
	                this.mons[i].update();
	            }

                if(this.mons[i] instanceof SlimeMonster){
                    SlimeMonster s = (SlimeMonster)this.mons[i];
                    s.releaseSlime();
                }
	        }

            for(int i = 0; i < this.slime.length; i++){
                if(this.slime[i] != null){
                    this.slime[i].effect(player);
                }
            }
		}
		
		if(gameState == pauseState) {
			
		}
	}
	
    // Metodo per ridisegnare il player
	public void paintComponent(Graphics g){
		
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        // Tile
        this.tileM.draw(g2);

        // Oggetti
        for(int i = 0; i < this.obj.length; i++){
            if(this.obj[i] != null){
                this.obj[i].draw(g2, this);
            }
        }

        // Mostri
        for(int i = 0; i < this.mons.length; i++){
            if(this.mons[i] != null){
                this.mons[i].draw(g2, this);
            }
        }

        // Slime
        for(int i = 0; i < this.slime.length; i++){
            if(this.slime[i] != null){
                this.slime[i].draw(g2);
            }
        }

        // Player
        this.player.draw(g2);
        
        // Ambiente
        eManager.draw(g2);
        
        // UI
        ui.draw(g2);
        
        // Disposizione delle risorse
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

    public boolean gameStatus() {
        return this.gameStatus;
    }

    public void setGameStatus(boolean gameStatus) {
        this.gameStatus = gameStatus;
    }
    
    public int getGameState() {
		return gameState;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
	}
    
}
