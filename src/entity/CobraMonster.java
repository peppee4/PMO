package entity;

import main.GamePanel;

import java.io.IOException;
import javax.imageio.ImageIO;

// Classe che rappresenta un mostro specifico chiamato CobraMonster
public class CobraMonster extends Monsters {

    // Riferimento al pannello di gioco, necessario per accedere a metodi e proprietà globali
    private GamePanel gp;

    // Flag per controllare il cambio di visibilità (luce) durante l'effetto di accecamento
    private boolean flag;

    // Timer per gestire la durata dell'effetto di accecamento
    private int blindTime = 0;

    // Costruttore del CobraMonster
    public CobraMonster(GamePanel gp) {
        // Chiama il costruttore della superclasse Monsters con nome e riferimento al GamePanel
        super("CobraMonster", gp);

        // Configura il mostro (immagini, dimensioni, danno, ecc.)
        setup();

        // Salva il riferimento al GamePanel
        this.gp = gp;

        // Imposta il flag iniziale per il controllo della luce
        this.flag = true;
    }

    // Metodo di configurazione del mostro
    private void setup() {
        // Carica le immagini del CobraMonster per ogni direzione
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/monsters/CobraMonster/CobraMonster_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/monsters/CobraMonster/CobraMonster_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/monsters/CobraMonster/CobraMonster_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/monsters/CobraMonster/CobraMonster_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/monsters/CobraMonster/CobraMonster_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/monsters/CobraMonster/CobraMonster_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/monsters/CobraMonster/CobraMonster_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/monsters/CobraMonster/CobraMonster_right_2.png"));
        } catch (IOException e) {
            // In caso di errore nella lettura delle immagini, stampa l'eccezione
            System.out.println(e);
        }

        // Imposta le dimensioni del mostro
        width = 40;
        height = 40;

        // Imposta il danno che il mostro infligge
        damage = 0.5;

        // Imposta la posizione iniziale dell'area solida per la collisione
        setSolidAreaX(0);
        setSolidAreaY(0);

        // Imposta le dimensioni dell'area solida
        setSolidAreaWidth(35);
        setSolidAreaHeight(20);

        // Salva la posizione di default dell'area solida
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        // Imposta la velocità di movimento del mostro
        this.setSpeed(2);

        // Imposta la direzione iniziale del mostro
        this.setDirection("left");

        // Imposta lo stato del mostro come vivo
        alive = true;
    }

    // Metodo chiamato ad ogni aggiornamento del gioco
    @Override
    public void update() {
        // Se il giocatore è in contatto con il mostro o l'effetto accecamento è attivo
        if ((gp.getCChecker().checkPlayer(this) == true) || this.blindTime > 0) {

            // Incrementa il timer dell'effetto accecamento
            this.blindTime++;

            // Cast esplicito non necessario, ma presente
            CobraMonster c = (CobraMonster)this;

            // Se il timer è attivo ma non ha ancora raggiunto il limite
            if (this.blindTime > 0 && this.blindTime < 1000) {
                c.blind(true); 			// Attiva l'effetto accecamento
                this.blindTime++;
            } else {
                c.blind(false); 		// Disattiva l'effetto accecamento
                this.blindTime = 0; 	// Reset del timer
            }
        }

        // Chiama il metodo update della superclasse per gestire il movimento e altre logiche
        super.update();
    }

    // Metodo che gestisce l'effetto di accecamento (modifica la luce e riproduce suono)
    private void blind(boolean value) {

        // Se si attiva l'effetto e il flag è true (cioè non è già attivo)
        if (value && this.flag) {
            gp.playSoundEffect(6); 				// Riproduce effetto sonoro
            gp.getEManger().setLight(150); 		// Riduce la luce (accecamento)
            this.flag = false; 					// Imposta il flag per evitare ripetizioni
        }
        // Se si disattiva l'effetto e il flag è false (cioè era attivo)
        else if (!value && !this.flag) {
            gp.getEManger().setLight(400); 		// Ripristina la luce normale
            this.flag = true; 					// Reset del flag
        }
    }
}