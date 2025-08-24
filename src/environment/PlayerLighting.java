package environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class PlayerLighting {

	GamePanel gp;
	BufferedImage darknessFilter; // immagine usata come filtro di oscurità
	
	public PlayerLighting(GamePanel gp, int circleSize) {
		
		// Creiamo un'immagine della stessa dimensione dello schermo con trasparenza (ARGB)
		darknessFilter = new BufferedImage(gp.getScreenWidth(), gp.getScreenHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
		
		// Definiamo un'area che copre l'intero schermo
		Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.getScreenWidth(), gp.getScreenHeight()));
		
		// Calcoliamo il centro del giocatore (coordinate al centro della sua tile)
		int centerX = gp.player.getCenterX() + (gp.getTileSize()/2);
		int centerY = gp.player.getCenterY() + (gp.getTileSize()/2);
		
		// Calcoliamo la posizione in alto a sinistra del cerchio di luce
		double x = centerX - (circleSize/2);
		double y = centerY - (circleSize/2);
		
		// Creiamo la forma del cerchio di luce
		Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);
		
		// Convertiamo il cerchio in un'area
		Area lightArea = new Area(circleShape);
		
		// Sottraiamo il cerchio di luce dall'area dello schermo
		// screenArea rappresenta solo la parte buia
		screenArea.subtract(lightArea);
		
		// Creiamo i colori della gradazione (dal centro trasparente al bordo sempre più scuro)
		Color color[] = new Color[12];
		float fraction[] = new float[12];
		
		color[0] = new Color(0,0,0,0.1f);
		color[1] = new Color(0,0,0,0.40f);
		color[2] = new Color(0,0,0,0.50f);
		color[3] = new Color(0,0,0,0.61f);
		color[4] = new Color(0,0,0,0.69f);
		color[5] = new Color(0,0,0,0.76f);
		color[6] = new Color(0,0,0,0.82f);
		color[7] = new Color(0,0,0,0.87f);
		color[8] = new Color(0,0,0,0.91f);
		color[9] = new Color(0,0,0,0.94f);
		color[10] = new Color(0,0,0,0.96f);
		color[11] = new Color(0,0,0,0.98f);
		
		// Percentuali di gradazione (da 0 = centro a 1 = bordo del cerchio)
		fraction[0] = 0f;
		fraction[1] = 0.4f;
		fraction[2] = 0.5f;
		fraction[3] = 0.6f;
		fraction[4] = 0.65f;
		fraction[5] = 0.7f;
		fraction[6] = 0.75f;
		fraction[7] = 0.8f;
		fraction[8] = 0.85f;
		fraction[9] = 0.9f;
		fraction[10] = 0.95f;
		fraction[11] = 1f;
		
		// Creiamo la sfumatura radiale centrata sul player
		RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, (circleSize/2), fraction, color);
		
		// Applichiamo la sfumatura al pennello
		g2.setPaint(gPaint);
		
		// Disegniamo il cerchio di luce con la gradazione
		g2.fill(lightArea);
		
		// Disegniamo la parte scura (lo schermo meno il cerchio)
		g2.fill(screenArea);
		
		g2.dispose();
	}
	
	public void draw(Graphics2D g2) {
		
		g2.drawImage(darknessFilter,0, 0, null);
		
	}
}
