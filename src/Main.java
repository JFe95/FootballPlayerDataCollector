import javax.swing.*;

public class Main {

    public static void main(String[] args) throws Exception {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        PlayerList pL = new PlayerList("PlayerData.csv");
        new MainDisplay(pL);
    }

}
