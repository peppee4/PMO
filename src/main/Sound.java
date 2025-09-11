package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Classe per gestire gli effetti sonori e la musica del gioco.
 * Utilizza l'API Java Sound per riprodurre file audio in formato WAV.
 */

public class Sound {

	// Clip rappresenta il file audio attualmente caricato e pronto per la riproduzione
	private Clip clip;
	
	// Clip separata per la musica di sottofondo
	private Clip music;
	
	// Controlli del volume della musica e degli effetti sonori
    private FloatControl musicVolumeControl;
    private FloatControl soundVolumeControl;
    
    // Valori di default del volume (percentuali)
    private int currentMusicVolume = 100;  // Volume default 100%
    private int currentSoundVolume = 100;  // Volume default 100%
	
	// Array di URL che contiene i percorsi ai file audio del gioco
    // Dimensione fissa di 25 elementi per contenere diversi suoni
	private URL soundURL[] = new URL[12];
	
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
		
		// Se l'indice dell'audio non e' valido o non inizializzato esce.
		if (i < 0 || i >= soundURL.length || soundURL[i] == null) {
		    return;
		}
		 
		try {
			// Crea un AudioInputStream dal file audio specificato
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			// Ottiene un nuovo Clip dal sistema audio
			clip = AudioSystem.getClip();
			 // Apre il Clip con i dati audio dall'AudioInputStream
			clip.open(ais);
			
			// Se il Clip supporta il controllo del volume, inizializza soundVolumeControl
			if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
				soundVolumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				
				// Riapplica il volume memorizzato
		        setSoundVolume(currentSoundVolume); 
		    }
			
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
	
	// Ferma e ripulisce il Clip corrente
	// Chiude le risorse e lo rende null per evitare memory leak
	public void stopAndReset() {
	    if (clip != null) {
	        clip.stop();
	        clip.flush();
	        clip.close();
	        clip = null;
	    }
	}
	
	// Carica un file audio specifico per la musica di sottofondo
	public void setMusic(int i) {
	    try {
	    	// Se c'è già un Clip musicale aperto, fermalo e liberalo
	        if (music != null && music.isOpen()) {
	        	music.stop();
	        	music.flush();
	        	music.close();
	        }

	        // Crea un nuovo AudioInputStream per la musica selezionata
	        AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
	        music = AudioSystem.getClip();
	        music.open(ais);
	        
	        // Se il Clip supporta il controllo del volume, inizializza musicVolumeControl
	        if (music.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                musicVolumeControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
                // Riapplica il volume memorizzato
                setMusicVolume(currentMusicVolume); 
            }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	// Converte il volume percentuale (0-100) in decibel per FloatControl
	private float convertVolumeToDecibels(int volumePercent) {
        if (volumePercent <= 0) {
            return -80.0f; // Silenzio
        }
        if (volumePercent >= 100) {
            return 6.0f; // Volume massimo
        }
        // Formula logaritmica per una conversione lineare percepita in decibel
        return (float) (Math.log(volumePercent / 100.0) * 20);
    }
    
    // Imposta il volume della musica (0-100)
	public void setMusicVolume(int volumePercent) {
        currentMusicVolume = volumePercent; // Memorizza il volume
        if (musicVolumeControl != null) {
            float decibels = convertVolumeToDecibels(volumePercent);
            float min = musicVolumeControl.getMinimum();
            float max = musicVolumeControl.getMaximum();
            // Assicura che il valore rimanga entro i limiti supportati dal Clip
            decibels = Math.max(min, Math.min(max, decibels));
            musicVolumeControl.setValue(decibels);
        }
    }
    
    // Imposta il volume degli effetti sonori (0-100)
	public void setSoundVolume(int volumePercent) {
	    currentSoundVolume = volumePercent; // Memorizza il volume
	    if (soundVolumeControl != null) {
	        float decibels = convertVolumeToDecibels(volumePercent);
	        float min = soundVolumeControl.getMinimum();
	        float max = soundVolumeControl.getMaximum();
	        // Limita il valore entro i minimi e massimi del Clip
	        decibels = Math.max(min, Math.min(max, decibels));
	        soundVolumeControl.setValue(decibels);
	    }
	}
}
