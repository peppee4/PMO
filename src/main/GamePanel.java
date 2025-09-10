package main;

import java.awt.*;
import javax.swing.JPanel;
import environment.EnvironmentManager;
import map.TileMap;
import object.SuperObject;
import entity.*;

// Classe principale per la gestione del Gioco
public class GamePanel extends JPanel implements Runnable{

    // Impostazioni della finestra
	private final int originalTileSize = 20;                    // 16x16 tile
	private final int scale = 3;								// Fattore di scale

	private final int tileSize = originalTileSize * scale;      // 48 * 48
	private final int maxScreenCol = 20 ;						// Numero massimo di Colonne 
	private final int maxScreenRow = 12 ;						// Numero massimo di Righe 
	private final int screenWidth = tileSize * maxScreenCol;    // 768 pixels
	private final int screenHeight = tileSize * maxScreenRow;   // 576 pixels 
	
	// Impostazioni della Mappa
	private final int maxWorldCol = 50;                         // Dimensioni delle colonne della mappa
	private final int maxWorldRow = 50;						    // Dimensioni delle righe della mappa
	
	// FPS
	private int FPS = 60;
	
	private TileMap tileM;						                          	// Creazione della mappa
    private KeyHandler keyH = new KeyHandler(this);                         // Creazione di un gestore degli eventi della tastiera
    private CollisionChecker cChecker = new CollisionChecker(this);      	// Creazione del controllore delle collisioni
    private AssetSetter aSetter = new AssetSetter(this);   					// Creazione di un gestore per le entità
    private Sound soundManager = new Sound();								// Creazione del gestore dei suoni
    private UiManager ui = new UiManager(this);								// Creazione della classe per la gestione della luce che circonda il player
    private boolean flagTitle = false;										// Variabile booleana per settare circonfernza luce che circonda 
    																		// il player nella schermata iniziale
    private boolean flagPlay = false;										// Variabile booleana per settare circonfernza luce che circonda 
    																		// il player nel gioco
    private EnvironmentManager eManager = new EnvironmentManager(this);     // Creazione del posizionatore degli oggetti 
    
    // Creiamo il Thread per il flusso del gioco
	private Thread gameThread;			
	
	// Creiamo il Player
	private Player player = new Player(this,getKeyH());                 // Creazione del player

    // Creiamo gli oggetti
    protected SuperObject obj[] = new SuperObject[10];                     // Array di oggetti di gioco

    // Creiamo i mostri
    protected Monsters mons[] = new Monsters[30];                          // Array di mostri
    
    // Creiamo gli slime
    protected Slime slime[] = new Slime[30];                               // Array di slime   

    // Stati del gioco
    private boolean gameStatus = true;                          		// Stato del gioco (true = in corso, false = terminato)
	private int gameState;
	protected final int titleState = 0;									// Titoli iniziali
	protected final int playState = 1;									// Dentro il labirinto
	protected final int optionsState = 2;								// Impostazioni
	protected final int dialogueState = 3;								// Finestra di dialogo
	protected final int gameOverState = 4; 								// Schermata del game over
	protected final int optionsControlState = 5; 						// Impostazioni
	protected final int nextLevelState = 6;								// Schermata fine livello
	protected final int tutorialState = 7;								// Schermata tutorial
    private int levelNumber;												// Inizializziamo i livelli al primo

    // Costruttore della classe
    public GamePanel() {
    	
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));    // Dimensione predefinita del JPanel
        this.setBackground(Color.black);                                    // Colore di sfondo del JPanel
        this.setDoubleBuffered(true);                                       // Per ridurre il flickering
        this.addKeyListener(getKeyH());                                     // Aggiungiamo il Listener al pannello
        this.setFocusable(true);                                  			// Rende il pannello “focusable”, cioè capace di ricevere eventi da tastiera
         
    }

    // Metodo per impostare gli oggetti di gioco
    public void setupGame() {
    	
    	this.setLevelNumber(1); 									// Inizializza il numero di livello a 1
    	
    	this.tileM = new TileMap(this);							// Inizializzazione della mappa
    	
    	aSetter.setObject();    								// Posizioniamo gli oggetti
        aSetter.setMonster();   								// Posizioniamo i mostri
        
        this.soundManager.setMusicVolume(ui.getMusicVolume());		// Imposta il volume della musica in base al valore scelto nell'interfaccia utente
        this.soundManager.setSoundVolume(ui.getSoundVolume());		// Imposta il volume degli effetti sonori in base al valore scelto nell'interfaccia utente
        
        this.gameState = titleState;        					// Imposta lo stato iniziale del gioco sul schermata del titolo (schermata del titolo/menù principale)
    }
    
    // Metodo che resetta i valori a fine gioco
    public void reset() {
    	
    	this.tileM = new TileMap(this);	// Ricreiamo la mappa
    	aSetter.setObject();   		    // Riposizioniamo gli oggetti
        aSetter.setMonster();   		// Riposizioniamo i mostri
        player.restorePlayerValues();   // Reseta i valori iniziali del player ad una nuova partita     
        
    }
    
    // Metodo per avviare il thread di gioco
    public void startGameThread() {
		gameThread = new Thread(this);  // Inizializzazione del Thread
		gameThread.start();             // Thread start
    }
    
    // Metodo principale del thread del gioco
    @Override
    public void run() {
    	
    	// Intervallo di tempo (in nanosecondi) tra un disegno e l’altro
        // Calcolato come 1 secondo (1.000.000.000 ns) diviso il numero di FPS desiderati
        double drawInterval = 1000000000 / FPS;
        
        // Indica quando dovrebbe avvenire il prossimo disegno
        double nextDrawTime = System.nanoTime() + drawInterval;
     
        // Ciclo principale del gioco (game loop)
        // Continua a girare finché il thread non viene terminato e il gioco è attivo
        while (gameThread != null && this.gameStatus == true) {
            
        	// 1. Aggiorna la logica del gioco (posizioni, collisioni, input, ecc.)
            update();
            
            // 2. Ridisegna il pannello con le informazioni aggiornate
            repaint();

            try {
            	// Calcola quanto tempo rimane prima del prossimo frame
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
                
                // Se il calcolo risulta negativo (cioè il frame è in ritardo), lo forziamo a 0
                // in modo da non avere attese negative
                if (remainingTime < 0) {
                	remainingTime = 0;
                }
                
                // Mette in pausa il thread per il tempo calcolato
                // Serve a mantenere costante la velocità del gioco in base agli FPS
                Thread.sleep((long) remainingTime);
                
             // Aggiorna il tempo previsto per il prossimo frame
                nextDrawTime += drawInterval;
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
    // Metodo per aggiornare le informazioni del gioco
	public void update(){
		
		// Gestione musica in base allo stato del gioco
		
	    // Se siamo nel menu principale e non è già partita la musica del menu principale
		if(gameState == titleState && flagTitle == false) {
	        this.soundManager.setMusic(8);				// Carica la traccia musicale 8 (es. musica menu)
	        this.soundManager.loop();					// La riproduce in loop
	        
	        // Aggiorna i flag per evitare di ricaricare la musica ad ogni ciclo
	        flagTitle = true;
        	flagPlay = false;
        	
        // Se siamo nello stato di gioco e non è già partita la musica di gioco	
		}else if(gameState == playState && flagPlay == false) {
		    this.soundManager.setMusic(11);				// Carica la traccia musicale 11 (es. musica gameplay)
		    this.soundManager.loop();					// La riproduce in loop
		    
		 // Aggiorna i flag
		    flagTitle = false;
        	flagPlay = true;
	    }
		
		// --- Logica durante la partita ---
		
		if(gameState == playState) {
			
			// Aggiorna lo stato del giocatore (movimento, collisioni, ecc.)
			player.update();

			// Aggiorna i mostri
	        for(int i = 0; i < this.mons.length; i++){
	        	
	            if(this.mons[i] != null){
	            	
	                this.mons[i].update();  			// Logica di movimento/attacco del mostro
	                
	                // Se il mostro è morto, lo rimuove dall'array
	                if(!this.mons[i].isAlive()) {
	                	this.mons[i] = null;
	                }
	            }

	            // Caso particolare: se il mostro è uno SlimeMonster
	            // può rilasciare slime (nuove entità o proiettili sul campo)
                if(this.mons[i] instanceof SlimeMonster){
                    SlimeMonster s = (SlimeMonster)this.mons[i];
                    s.releaseSlime();
                }
	        }

	        // Gestione degli slime (es. effetti o proiettili degli SlimeMonster)
            for(int i = 0; i < this.getSlime().length; i++){
                int count = 0;

                if(this.getSlime()[i] != null){
                	// Controlla collisione tra slime e giocatore
                    if(this.cChecker.checkPlayer(this.getSlime()[i])){
                        count++;
                    }
                }
    
                // Se ci sono collisioni con slime, il giocatore viene rallentato
                player.isSlow(count);
            }
		}
		
		// --- Aggiornamento degli oggetti di gioco (door, chests, ecc.) ---
		
		for(int i = 0; i < this.obj.length; i++) {
			if(this.obj[i] != null) {
				this.obj[i].update();
			}
		}
		
		// --- Caso: dialoghi ---
	    // Aggiorna comunque lo stato degli oggetti (per animazioni, effetti, ecc.)
		if(gameState == this.dialogueState) {
			for(int i = 0; i < this.obj.length; i++) {
				if(this.obj[i] != null) {
					this.obj[i].update();
				}
			}
		}
	}
	
    // Metodo per ridisegnare
	public void paintComponent(Graphics g){
		
		// Inizializzazione della grafica
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        // Setta la luce soffusa nel menu principale
        if(gameState == titleState && flagTitle == false) {
        	
        	eManager.setLight(1200);
        }
        // Setta la luce soffusa nel gioco
        else if(gameState == playState && flagPlay == false) {
        	
        	eManager.setLight(400);
        }
        
        // Stampa GUI menu principale
        if(gameState == titleState) {
        	
        	ui.draw(g2);
        	
        	eManager.draw(g2);
        	
        	ui.drawVersion();
        	
        }else {
        	// Stampa GUI nel gioco
        	
        	// Mappa
            this.tileM.draw(g2);

            // Oggetti
            for(int i = 0; i < this.obj.length; i++){
                if(this.obj[i] != null){
                    this.obj[i].draw(g2);
                }
            }

            // Slime
            for(int i = 0; i < this.getSlime().length; i++){
                if(this.getSlime()[i] != null){
                    this.getSlime()[i].draw(g2);
                }
            }

            // Mostri
            for(int i = 0; i < this.mons.length; i++){
                if(this.mons[i] != null && this.mons[i].isAlive()){
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

	// Metodo per riprodurre il suono di un effetto nel gioco
	public void playSoundEffect(int i) {
	
		this.soundManager.setFile(i);
		this.soundManager.play();
	
	}
	
	// ----- GETTER AND SETTER -----
	
	public CollisionChecker getCChecker() {
		return this.cChecker;
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
		return this.tileM;
	}

	public UiManager getUi() {
		return this.ui;
	}
	
	public Sound getSoundManager() {
		return this.soundManager;
	}
	
	public EnvironmentManager getEManger() {
		return this.eManager;
	}
	
	public KeyHandler getKeyH() {
		return keyH;
	}

	public boolean isFlagTitle() {
		return flagTitle;
	}

	public void setFlagTitle(boolean flagTitle) {
		this.flagTitle = flagTitle;
	}

	public boolean isFlagPlay() {
		return flagPlay;
	}

	public void setFlagPlay(boolean flagPlay) {
		this.flagPlay = flagPlay;
	}

	public int getPlayState() {
		return this.playState;
	}

	public int getDialogueState() {
		return this.dialogueState;
	}

	public int getNextLevelState() {
		return this.nextLevelState;
	}
    
	public int getGameOverState() {
		return this.gameOverState;
	}

	public Slime[] getSlime() {
		return slime;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}
}
