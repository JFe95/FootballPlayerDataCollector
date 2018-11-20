import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainDisplay extends JFrame{
    private JList<String> pListList;
    private JButton addPlayerButton;
    private JButton removePlayerButton;
    private JTextField newPlayerNameField;
    private JSpinner gamesPlayedSpinner;
    private JSpinner gamesWonSpinner;
    private JSpinner goalsSpinner;
    private JSpinner goalsConcSpinner;
    private JPanel mainPanel;
    private JPanel statsPanel;
    private JButton saveButton;
    private JButton clearButton;
    private JPanel listPanel;
    private JPanel dataButtonPanel;

    private PlayerList pList;


    public MainDisplay(PlayerList pList) throws Exception {

        setContentPane(mainPanel);
        setVisible(true);
        setResizable(false);
        setTitle("Football Player Data Collector");

        //Center Window
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        this.pList = pList;

        //Populate pListList
        populatePListList();
        pListList.setBorder(new LineBorder(Color.BLACK));

        //on addPlayerButton
        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onAddPlayerButton();
            }
        });

        //onRemovePlayerButton
        removePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onRemovePlayerButton();
            }
        });

        //setup spinners
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0,0,999,1);
        gamesPlayedSpinner.setModel(spinnerModel);
        SpinnerNumberModel spinnerModel1 = new SpinnerNumberModel(0,0,999,1);
        gamesWonSpinner.setModel(spinnerModel1);
        SpinnerNumberModel spinnerModel2 = new SpinnerNumberModel(0,0,999,1);
        goalsSpinner.setModel(spinnerModel2);
        SpinnerNumberModel spinnerModel3 = new SpinnerNumberModel(0,0,999,1);
        goalsConcSpinner.setModel(spinnerModel3);

        populateSpinners();

        pListList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (pListList.getSelectedValue() != null) {
                    try {
                        populateSpinners();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //on saveButton
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    onSaveButton();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onClearButton();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try {
                    pList.saveToFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });

    }

    private void populatePListList(){
        DefaultListModel<String> lm = new DefaultListModel<>();
        pList.sort();
        for (int i = 0; i < pList.size(); i++){
            lm.add(i,pList.getPlayerName(i));
        }
        pListList.setModel(lm);
        pListList.setSelectedIndex(0);
        pack();
    }

    private void onAddPlayerButton(){
        String newName = newPlayerNameField.getText();
        if (!newName.equals("")) {
            if (pList.addPlayer(new Player(newName, (int) gamesPlayedSpinner.getValue(),
                    (int) gamesWonSpinner.getValue(), (int) goalsSpinner.getValue(), (int) goalsConcSpinner.getValue()))) {
                newPlayerNameField.setText("");
            }
        }
        populatePListList();
    }

    private void onRemovePlayerButton(){
        if(pList.removePlayer(pListList.getSelectedValue())){
            pListList.clearSelection();
            populatePListList();
            newPlayerNameField.setText("");
        }
    }

    private void onSaveButton() throws Exception {
        if (pList.size() > 0) {
            Player updatedPlayer = new Player(pListList.getSelectedValue(), (int) gamesPlayedSpinner.getValue(),
                    (int) gamesWonSpinner.getValue(), (int) goalsSpinner.getValue(), (int) goalsConcSpinner.getValue());
            pList.removePlayer(pListList.getSelectedValue());
            pList.addPlayer(updatedPlayer);
            populatePListList();
        }
    }

    private void onClearButton(){
        gamesPlayedSpinner.setValue(0);
        gamesWonSpinner.setValue(0);
        goalsSpinner.setValue(0);
        goalsConcSpinner.setValue(0);
    }

    private void populateSpinners() throws Exception {
        if (pList.size() > 0) {
            gamesPlayedSpinner.setValue(pList.getPlayer(pListList.getSelectedValue()).getGamesPlayed());
            gamesWonSpinner.setValue(pList.getPlayer(pListList.getSelectedValue()).getWins());
            goalsSpinner.setValue(pList.getPlayer(pListList.getSelectedValue()).getGoals());
            goalsConcSpinner.setValue(pList.getPlayer(pListList.getSelectedValue()).getGoalsConceded());
        }
    }
}
