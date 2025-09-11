import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.Sound;

import org.junit.jupiter.api.BeforeEach;
import javax.sound.sampled.Clip;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SoundTest {

    private Sound sound;

    @BeforeEach
    void setUp() {
        sound = new Sound();
    }

    @Test
    void testCostruttoreInizializzaArrayURL() {
        // Verifica che il costruttore inizializza correttamente l'array degli URL
        assertNotNull(sound);
        // Possiamo verificare indirettamente che gli URL sono stati caricati
        // provando a impostare un file valido
        assertDoesNotThrow(() -> sound.setFile(0));
    }

    @Test
    void testSetFileConIndiceValido() {
        // Verifica che setFile funzioni con un indice valido
        assertDoesNotThrow(() -> sound.setFile(0));
        
        // Verifica che il clip sia stato inizializzato
        // Usiamo reflection per accedere al campo privato clip
        try {
            Field clipField = Sound.class.getDeclaredField("clip");
            clipField.setAccessible(true);
            Clip clip = (Clip) clipField.get(sound);
            assertNotNull(clip);
        } catch (Exception e) {
            fail("Impossibile accedere al campo clip: " + e.getMessage());
        }
    }

    @Test
    void testSetFileConIndiceNonValido() {
        // Verifica che setFile gestisca gracefully un indice non valido
        assertDoesNotThrow(() -> sound.setFile(100));
    }

    @Test
    void testSetMusicConIndiceValido() {
        // Verifica che setMusic funzioni con un indice valido
        assertDoesNotThrow(() -> sound.setMusic(8));
        
        // Verifica che il music clip sia stato inizializzato
        try {
            Field musicField = Sound.class.getDeclaredField("music");
            musicField.setAccessible(true);
            Clip music = (Clip) musicField.get(sound);
            assertNotNull(music);
        } catch (Exception e) {
            fail("Impossibile accedere al campo music: " + e.getMessage());
        }
    }

    @Test
    void testSetVolumeMusica() {
        // Imposta prima la musica per inizializzare il controllo del volume
        sound.setMusic(8);
        
        // Verifica che il volume della musica possa essere impostato
        assertDoesNotThrow(() -> sound.setMusicVolume(50));
        
        // Verifica che il valore corrente del volume sia stato aggiornato
        try {
            Field volumeField = Sound.class.getDeclaredField("currentMusicVolume");
            volumeField.setAccessible(true);
            int currentVolume = (int) volumeField.get(sound);
            assertEquals(50, currentVolume);
        } catch (Exception e) {
            fail("Impossibile accedere al campo currentMusicVolume: " + e.getMessage());
        }
    }

    @Test
    void testSetVolumeSuono() {
        // Imposta prima un file audio per inizializzare il controllo del volume
        sound.setFile(0);
        
        // Verifica che il volume degli effetti sonori possa essere impostato
        assertDoesNotThrow(() -> sound.setSoundVolume(75));
        
        // Verifica che il valore corrente del volume sia stato aggiornato
        try {
            Field volumeField = Sound.class.getDeclaredField("currentSoundVolume");
            volumeField.setAccessible(true);
            int currentVolume = (int) volumeField.get(sound);
            assertEquals(75, currentVolume);
        } catch (Exception e) {
            fail("Impossibile accedere al campo currentSoundVolume: " + e.getMessage());
        }
    }

    @Test
    void testStopAndReset() {
        // Imposta un file audio
        sound.setFile(0);
        
        // Verifica che stopAndReset funzioni senza eccezioni
        assertDoesNotThrow(() -> sound.stopAndReset());
        
        // Verifica che il clip sia stato resettato
        try {
            Field clipField = Sound.class.getDeclaredField("clip");
            clipField.setAccessible(true);
            Clip clip = (Clip) clipField.get(sound);
            assertNull(clip);
        } catch (Exception e) {
            fail("Impossibile accedere al campo clip: " + e.getMessage());
        }
    }

    @Test
    void testConversioneVolumeInDecibel() {
        // Verifica la conversione del volume in decibel
        // Usiamo reflection per testare il metodo privato
        try {
            Method convertMethod = Sound.class.getDeclaredMethod("convertVolumeToDecibels", int.class);
            convertMethod.setAccessible(true);
            
            // Testa alcuni valori noti
            float silence = (float) convertMethod.invoke(sound, 0);
            assertEquals(-80.0f, silence, 0.001f);
            
            float maxVolume = (float) convertMethod.invoke(sound, 100);
            assertEquals(6.0f, maxVolume, 0.001f);
            
            float halfVolume = (float) convertMethod.invoke(sound, 50);
            // Verifica che il valore sia tra il minimo e il massimo
            assertTrue(halfVolume > silence && halfVolume < maxVolume);
            
        } catch (Exception e) {
            fail("Impossibile testare convertVolumeToDecibels: " + e.getMessage());
        }
    }

    @Test
    void testVolumeValoriEstremi() {
        // Verifica che i valori estremi di volume siano gestiti correttamente
        sound.setMusic(8);
        
        // Volume minimo
        assertDoesNotThrow(() -> sound.setMusicVolume(0));
        
        // Volume massimo
        assertDoesNotThrow(() -> sound.setMusicVolume(100));
        
        // Valori fuori range
        assertDoesNotThrow(() -> sound.setMusicVolume(-10));
        assertDoesNotThrow(() -> sound.setMusicVolume(150));
    }
}
