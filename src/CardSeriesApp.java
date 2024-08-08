import Database.DatabaseHandler;
import Database.IDatabaseHandler;

import javax.swing.*;
import java.awt.*;

public class CardSeriesApp {
    private final JFrame mainFrame;
    private final JTextArea displayArea;
    private final IDatabaseHandler databaseHandler;

    public CardSeriesApp() {
        mainFrame = new JFrame("Historical Artifacts Database");
        mainFrame.setSize(400, 300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        databaseHandler = new DatabaseHandler();

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        menuBar.add(menu);

        JMenuItem viewAllCardsBySport = new JMenuItem("Se informasjon om alle samlerkort for en sport");
        JMenuItem viewNumberOfAllRegisteredCards = new JMenuItem("Få informasjon om antall samlerkort registert");
        JMenuItem viewAllCardsByState = new JMenuItem("Se imformasjon om alle samlerkort som er i «mint condition»");
        JMenuItem viewAllSamlerkortSerie = new JMenuItem("Se imformasjon om alle SamlerkortSerie");
        JMenuItem exitApp = new JMenuItem("Her avslutter du");

        menu.add(viewAllCardsBySport);
        menu.add(viewNumberOfAllRegisteredCards);
        menu.add(viewAllCardsByState);
        menu.add(viewAllSamlerkortSerie);
        menu.addSeparator();
        menu.add(exitApp);

        mainFrame.setJMenuBar(menuBar);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        mainFrame.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Action listeners for menu items using lambda expressions
        viewAllCardsBySport.addActionListener(e -> showAllCardsBySport());
        viewNumberOfAllRegisteredCards.addActionListener(e -> showNumberOfAllRegisteredCards());
        viewAllCardsByState.addActionListener(e -> showAllCardsByState());
        viewAllSamlerkortSerie.addActionListener(e -> showAllSamlerkortSerie());
        exitApp.addActionListener(e -> System.exit(0));

        mainFrame.setVisible(true);
    }

    private void showAllCardsBySport() {
        String sport = JOptionPane.showInputDialog(mainFrame, "Angi sport:");
        if (sport != null && !sport.isEmpty() &&
                (sport.trim().equalsIgnoreCase("Fotball") ||
                 sport.trim().equalsIgnoreCase("Baseball") ||
                 sport.trim().equalsIgnoreCase("Basketball"))) {
            String result = databaseHandler.getAllCardsBySport(sport);
            displayArea.setText("All artifacts would be displayed here. \n \n "+result);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Ugyldig sport", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showNumberOfAllRegisteredCards() {
        long result = databaseHandler.getNumberOfRegisteredCards();
        displayArea.setText("Number of registered cards ==> <<"+result+">>. \n \n");
    }

    private void showAllCardsByState() {
        String state = JOptionPane.showInputDialog(mainFrame, "Angi tilstand:");
        if (state != null && !state.isEmpty()) {
            String result = databaseHandler.getAllCardsByState(state);
            displayArea.setText("All cards with card state <<"+state.toUpperCase()+">> \n \n "+result);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Ugyldig tilstand", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAllSamlerkortSerie() {
        String sport = JOptionPane.showInputDialog(mainFrame, "Angi sport:");
        if (sport != null && !sport.isEmpty() &&
                (sport.trim().equalsIgnoreCase("Fotball") ||
                        sport.trim().equalsIgnoreCase("Baseball") ||
                        sport.trim().equalsIgnoreCase("Basketball"))) {
            String result = databaseHandler.getAllSamlerkortSerierBySport(sport);
            displayArea.setText("All Samlerkort Serie \n \n" + result);
        }else {
            JOptionPane.showMessageDialog(mainFrame, "Ugyldig sport", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CardSeriesApp::new);
    }
}
