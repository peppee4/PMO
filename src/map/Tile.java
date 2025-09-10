package map;

import java.awt.image.BufferedImage;

public class Tile {

	/* QUESTA CLASSE RAPPRESENTA IL SINGOLO TILE */
	
	protected BufferedImage image;               /* IMMAGINE DEL TILE */
	protected boolean collision = false;         /* VARIABILE BOOLEANA UTILIZZATA PER VERIFICARE 
											     	EVENTUALI COLLISIONI CON LE ENTITA' */
	public boolean isCollision() {
		return collision;
	}	
}
