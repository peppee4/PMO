package main;

import java.awt.*;
import javax.swing.JPanel;
import environment.EnvironmentManager;
import map.TileMap;
import object.SuperObject;
import entity.*;
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
	
	private TileMap tileM = new TileMap(this);                          // Creazione della mappa
    KeyHandler keyH = new KeyHandler(this);                             // Creazione di un gestore degli eventi della tastiera
    public CollisionChecker cChecker = new CollisionChecker(this);      // Creazione del controllore delle collisioni
    public AssetSetter aSetter = new AssetSetter(this, keyH);   		// Creazione di un gestore per le entità
    private Sound soundManager = new Sound();							// Creazione del gestore dei suoni
    private UiManager ui = new UiManager(this);							// Creazione della classe per la gestione della luce che circonda il player
    private boolean flagTitle = false;									// Variabile booleana per settare circonfernza luce che circonda 
    																	// il player nella schermata iniziale
    private boolean flagPlay = false;									// Variabile booleana per settare circonfernza luce che circonda 
    																	// il player nel gioco
    EnvironmentManager eManager = new EnvironmentManager(this);         // Creazione del posizionatore degli oggetti 
    
    // Creiamo il Thread per il flusso del gioco
	Thread gameThread;
	
	// Creiamo il Player
	private Player player = new Player(this,keyH);                      // Creazione del player

    // Creiamo gli oggetti
    public SuperObject obj[] = new SuperObject[10];                     // Array di oggetti di gioco

    // Creiamo i mostri
    public Monsters mons[] = new Monsters[10];                          // Array di mostri
    
    // Creiamo gli slime
    public Slime slime[] = new Slime[30];                               // Array di slime   

    // Stati del gioco
    private boolean gameStatus = true;                          		// Stato del gioco (true = in corso, false = terminato)
	private int gameState;
	public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int optionsState = 3;
    public final int dialogueState = 4;

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
        
        this.gameState = titleState;
        
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
                int count = 0;

                if(this.slime[i] != null){
                    if(this.cChecker.checkPlayer(this.slime[i])){
                        count++;
                    }
                }
    
                player.isSlow(count);
            }
		}
		
		for(int i = 0; i < this.obj.length; i++) {
			if(this.obj[i] != null) {
				this.obj[i].update(this);
			}
		}
		
		if(gameState == this.dialogueState) {
			for(int i = 0; i < this.obj.length; i++) {
				if(this.obj[i] != null) {
					this.obj[i].update(this);
				}
			}
		}
		
		//System.out.println(this.getGameState());
	}
	
    // Metodo per ridisegnare il player
	public void paintComponent(Graphics g){
		
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        if(gameState == titleState && flagTitle == false) {
        	eManager.setLight(1200);
        	flagTitle = true;
        	
        }
        else if(gameState == playState && flagPlay == false) {
        	eManager.setLight(400);
        	flagTitle = true;
        }
        
        if(gameState == titleState) {
        	
        	ui.draw(g2);
        	
        	eManager.draw(g2);
        	
        	ui.drawVersion();
        	
        }else {
        	 // Tile
            this.tileM.draw(g2);

            // Oggetti
            for(int i = 0; i < this.obj.length; i++){
                if(this.obj[i] != null){
                    this.obj[i].draw(g2, this);
                }
            }

            // Slime
            for(int i = 0; i < this.slime.length; i++){
                if(this.slime[i] != null){
                    this.slime[i].draw(g2);
                }
            }

            // Mostri
            for(int i = 0; i < this.mons.length; i++){
                if(this.mons[i] != null){
                    this.mons[i].draw(g2, this);
                }
            } 

            // Player
            this.player.draw(g2);
            
            // Ambiente
            eManager.draw(g2);
            
            // UI
            ui.draw(g2);
            
        }
       
        // Disposizione delle risorse
        g2.dispose();
	} 
	
	// Metodo per riprodurre un suono all'infinto
	public void playMusic(int i) {
		
		this.soundManager.setFile(i);
		this.soundManager.play();
		this.soundManager.loop();
	}
	
	// Metodo per fermare il suono
	public void stopMusic() {
		
		this.soundManager.stop();
	}

	// Metodo per riprodurre il suono di un effetto nel gioco
	public void playSoundEffect(int i) {
	
		this.soundManager.setFile(i);
		this.soundManager.play();
	
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
	
	public Player getPlayer() {
		return this.player;
	}
	
	public TileMap getMap() {
		return tileM;
	}

	public UiManager getUi() {
		return ui;
	}
    
}
