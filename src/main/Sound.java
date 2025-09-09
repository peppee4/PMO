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
	
	Clip music;
	
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
		soundURL[4] = getClass().getResource("/sounds/invisiblePower.wav");
		soundURL[5] = getClass().getResource("/sounds/unlock.wav");
		soundURL[6] = getClass().getResource("/sounds/spit.wav");
		soundURL[7] = getClass().getResource("/sounds/chest-unlocking.wav");
		soundURL[8] = getClass().getResource("/sounds/atmosphere_music.wav");
		soundURL[9] = getClass().getResource("/sounds/fuse.wav");
		soundURL[10] = getClass().getResource("/sounds/explosion.wav");
		soundURL[11] = getClass().getResource("/sounds/ambient_dungeon.wav");
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
		
		music.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	// Ferma la riproduzione del suono corrente
    // Il Clip può essere riavviato con play() o loop()
	public void stop() {
		
		clip.stop();
		
	}
	
	// Metodo per ripulire la traccia in esecuzione
	public void stopAndReset() {
	    if (clip != null) {
	        clip.stop();
	        clip.flush();
	        clip.close();
	        clip = null;
	    }
	}
	
	// Metodo per settare la musica da riprodurre
	public void setMusic(int i) {
	    try {
	        if (music != null && music.isOpen()) {
	        	music.stop();
	        	music.flush();
	        	music.close();
	        }

	        AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
	        music = AudioSystem.getClip();
	        music.open(ais);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
