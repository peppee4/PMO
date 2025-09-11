package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Entità di base del gioco.
 * Definisce posizione, velocità, direzione e riferimenti ai frame sprite
 * per l’animazione nelle quattro direzioni.
 */
public abstract class Entity {

    // Coordinate nel mondo di gioco (non relative allo schermo)
    protected int worldX;           // Posizione orizzontale
    protected int worldY;           // Posizione verticale

    // Velocità di movimento dell'entità
    private double speed;

    // Area solida usata per rilevare collisioni con altri oggetti o entità
    protected Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    // Posizione di default dell'area solida (usata per il reset)
    protected int solidAreaDefaultX;
    protected int solidAreaDefaultY;

    // Flag che indica se l'entità è attualmente in collisione
    protected boolean collisionOn = false;

    // Frame dell’animazione per ogni direzione di movimento
    protected BufferedImage up1, up2;
    protected BufferedImage down1, down2;
    protected BufferedImage left1, left2;
    protected BufferedImage right1, right2;
    protected BufferedImage stop; 				// Frame statico (es. quando l'entità è ferma)

    // Direzione corrente dell'entità (es. "up", "down", "left", "right")
    private String direction;

    // Contatore per gestire il tempo tra i cambi di frame animati
    protected int spriteCounter = 0;

    // Numero del frame corrente (1 o 2, per alternare le immagini)
    protected int spriteNum = 1;

    // ---- Getter e Setter -----

    public int getWorldX() {
        return this.worldX;
    }

    public int getWorldY() {
        return this.worldY;
    }

    public void setWorldY(int value) {
        this.worldY = value;
    }

    public void setWorldX(int value) {
        this.worldX = value;
    }

    public void setSpeed(double value) {
        this.speed = value;
    }

    public double getSpeed() {
        return this.speed;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    // Metodi per gestire l'area solida (collision box)

    public int getSolidAreaX() {
        return solidArea.x;
    }

    public void setSolidAreaX(int x) {
        solidArea.x = x;
    }

    public int getSolidAreaY() {
        return solidArea.y;
    }

    public void setSolidAreaY(int y) {
        solidArea.y = y;
    }

    public int getSolidAreaWidth() {
        return solidArea.width;
    }

    public void setSolidAreaWidth(int width) {
        solidArea.width = width;
    }

    public int getSolidAreaHeight() {
        return solidArea.height;
    }

    public void setSolidAreaHeight(int height) {
        solidArea.height = height;
    }

    // Imposta lo stato di collisione
    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }
}