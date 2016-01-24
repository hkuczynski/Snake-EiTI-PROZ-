package view;


import javax.swing.*;
import java.awt.*;

/**
 * Główny widok programu.
 */
public class MainView extends JFrame
{
    private final JPanel contentPanel;

    /*
     * Konstruktor MainView przyjmuje jako argumenty widoki, ktore będzie wyświetlał
     */
    public MainView(ArenaView arenaView, StatusView statusView)
    {
        contentPanel = new JPanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Snake");
        setResizable(false);
        add(contentPanel);

        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(new Color(175, 208, 139));
        GridBagConstraints gbc = new GridBagConstraints();

        // Ustawianie położenia ArenaView
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        contentPanel.add(arenaView,gbc);

        // Ustawianie położenia statusView
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(statusView,gbc);

        pack();
        setLayout(new GridLayout(1, 1, 0, 0));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
