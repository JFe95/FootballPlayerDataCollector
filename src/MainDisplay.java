import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class MainDisplay extends JFrame{
    private JButton addPlayerButton;
    private JButton removePlayerButton;
    private JTextField newPlayerNameField;
    private JPanel mainPanel;
    private JPanel fullListPanel;
    private JPanel dataButtonPanel;
    private JList <String> team1List;
    private JList <String> team2List;
    private JButton addToTeam1;
    private JButton addToTeam2;
    private JSpinner team1Score;
    private JSpinner team2Score;
    private JButton submitMatchButton;
    private JList <String> fullPlayerList;

    private PlayerList pList;
    private PlayerList team1;
    private PlayerList team2;
    private int score1;
    private int score2;

    MainDisplay(PlayerList pList) {

        setContentPane(mainPanel);
        setVisible(true);
        setResizable(false);
        setTitle("Football Player Data Collector");

        //Center Window
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        this.pList = pList;
        team1 = new PlayerList();
        team2 = new PlayerList();

        //Populate pListList
        refreshLists();

        //Score spinners
        SpinnerNumberModel sm1 = new SpinnerNumberModel(0,0,999,1);
        team2Score.setModel(sm1);
        SpinnerNumberModel sm2 = new SpinnerNumberModel(0,0,999,1);;
        team1Score.setModel(sm2);

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

        //on saveButton
        submitMatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    onSaveButton();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

        addToTeam1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onAddtoTeam1();
            }
        });
        addToTeam2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onAddtoTeam2();
            }
        });
        pack();
    }

    private void refreshLists(){
        DefaultListModel<String> lm = new DefaultListModel<>();
        pList.sort();
        for (int i = 0; i < pList.size(); i++){
            lm.add(i,pList.getPlayerName(i));
        }
        fullPlayerList.setModel(lm);
        fullPlayerList.setSelectedIndex(0);

        if(team1.size() > 0) {
            DefaultListModel<String> lm1 = new DefaultListModel<>();
            team1.sort();
            for (int i = 0; i < team1.size(); i++) {
                lm1.add(i, team1.getPlayerName(i));
            }

            team1List.setModel(lm1);
            team1List.setSelectedIndex(0);
        }

        if (team2.size() > 0) {

            DefaultListModel<String> lm2 = new DefaultListModel<>();
            team2.sort();
            for (int i = 0; i < team2.size(); i++) {
                lm2.add(i, team2.getPlayerName(i));
            }

            team2List.setModel(lm2);
            team2List.setSelectedIndex(0);
        }

    }

    private void onAddPlayerButton(){
        String newName = newPlayerNameField.getText();
        if (!newName.equals("")) {
            if (pList.addPlayer(new Player(newName))) {
                newPlayerNameField.setText("");
            }
        }
        refreshLists();
    }

    private void onRemovePlayerButton(){
        if(pList.removePlayer(fullPlayerList.getSelectedValue())){
            fullPlayerList.clearSelection();
            refreshLists();
            newPlayerNameField.setText("");
        }
    }

    private void onAddtoTeam1(){
        List<String> toAdd = fullPlayerList.getSelectedValuesList();

        for (String s : toAdd){
            team1.addPlayer(new Player(s));
        }
        refreshLists();
    }

    private void onAddtoTeam2(){
        List<String> toAdd = fullPlayerList.getSelectedValuesList();

        for (String s : toAdd){
            team2.addPlayer(new Player(s));
        }
        refreshLists();
    }

    private void onSaveButton() {
        Match m = new Match(team1, team2, (int) team1Score.getValue(), (int) team2Score.getValue());

        try {
            m.saveToFile();
            JOptionPane.showMessageDialog(this, "Result Saved.");
            pList.updatePlayerStats(m);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
