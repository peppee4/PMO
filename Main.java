import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        
        // Creazione del Frame
        JFrame window = new JFrame();                               
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Impostiamo il comportamento per la chiusura della finestra
        window.setResizable(false);                   // Facciamo in mdoo che la finestra non possa essere ridimensionata dall'utente
        window.setTitle("The Pyros Stone");               // Titolo della finestra

        GamePanel gamePanel = new GamePanel();                  // Creiamo il pannello
        window.add(gamePanel);                                  // Aggiungiamo il pannello al frame

        window.pack();                                          // Ridimendiona la finestra del pannello

        window.setLocationRelativeTo(null);                   // Impostiamo la posizione iniziale della finestra al centro dello schermo
        window.setVisible(true);

    }
}
