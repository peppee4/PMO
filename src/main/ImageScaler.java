package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

//Classe che gestisce il ridimensionamento delle immagini
public class ImageScaler {

	// Metodo per ridimensionare un'immagine BufferedImage a larghezza e altezza specificate
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		
		// Crea una nuova immagine con le dimensioni target
        // Mantiene lo stesso tipo di colore dell'immagine originale
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		
		// Ottiene il contesto grafico per disegnare sulla nuova immagine
		Graphics2D g2 = scaledImage.createGraphics(); 
		
		// Disegna l'immagine originale ridimensionandola automaticamente
        // Parametri: immagine, x, y, larghezza, altezza, ImageObserver
		g2.drawImage(original, 0, 0, width, height, null);
		
		// Libera le risorse native del contesto grafico
		g2.dispose();
		
		// Restituisce l'immagine ridimensionata
		return scaledImage;
	}
}
