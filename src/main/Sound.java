package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Classe per gestire gli effetti sonori e la musica del gioco.
 * Utilizza l'API Java Sound per riprodurre file audio in formato WAV.
 */

public class Sound {

	// Clip rappresenta il file audio attualmente caricato e pronto per la riproduzione
	Clip clip;
	
	// Array di URL che contiene i percorsi ai file audio del gioco
    // Dimensione fissa di 25 elementi per contenere diversi suoni
	URL soundURL[] = new URL[25];
	
	private Clip playerWalking;	
	
	// Costruttore che inizializza i percorsi dei file audio.
    // Carica i riferimenti ai file audio nelle risorse del progetto.
	public Sound() {
		
		soundURL[0] = getClass().getResource("/sounds/cursor.wav");
		soundURL[1] = getClass().getResource("/sounds/dooropen.wav");
		soundURL[2] = getClass().getResource("/sounds/gameover.wav");
		soundURL[3] = getClass().getResource("/sounds/receivedamage.wav");
		soundURL[4] = getClass().getResource("/sounds/powerup.wav");
		soundURL[5] = getClass().getResource("/sounds/unlock.wav");
		soundURL[6] = getClass().getResource("/sounds/player_walking_on_dirt.wav");
		soundURL[7] = getClass().getResource("/sounds/chest-unlocking.wav");
		soundURL[8] = getClass().getResource("/sounds/atmosphere_music.wav");
		
		//setPlayerSound();
	}
	
	// Carica un file audio specifico nel Clip per la riproduzione
	public void setFile(int i) {
		
		try {
			// Crea un AudioInputStream dal file audio specificato
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			// Ottiene un nuovo Clip dal sistema audio
			clip = AudioSystem.getClip();
			 // Apre il Clip con i dati audio dall'AudioInputStream
			clip.open(ais);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Avvia la riproduzione del suono caricato
	// Il suono viene riprodotto una sola volta
	public void play() {
		
		clip.start();
		
	}
	
	// Avvia la riproduzione in loop continuo del suono caricato
    // Utile per musica di sottofondo o effetti ambientali
	public void loop() {
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	// Ferma la riproduzione del suono corrente
    // Il Clip può essere riavviato con play() o loop()
	public void stop() {
		
		clip.stop();
		
	}
	
	/*// Metodo per impostare il suono per la camminata del player
	private void setPlayerSound() {
		try {
			// Crea un AudioInputStream dal file audio specificato
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[6]);
			// Ottiene un nuovo Clip dal sistema audio
			this.playerWalking = AudioSystem.getClip();
			 // Apre il Clip con i dati audio dall'AudioInputStream
			this.playerWalking.open(ais);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Metodo per la gestione della riproduzione della camminata del player
	public void playerWalking(boolean value) {
	    if (value) {
	        if (!this.playerWalking.isRunning()) { // evita di riavviare se già in loop
	        	this.playerWalking.loop(Clip.LOOP_CONTINUOUSLY);
	        }
	    } else {
	        if (this.playerWalking.isRunning()) {
	            this.playerWalking.stop();
	        }
	    }
	}*/
}
