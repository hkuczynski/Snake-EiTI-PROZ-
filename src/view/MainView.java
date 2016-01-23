package view;


import javax.swing.*;
import java.awt.*;

/**
 * Created by hubert on 22.01.2016.
 */
public class MainView extends JFrame
{
    private final ArenaView arenaView;
    private final StatusView statusView;

    private final JPanel contentPanel;

    public MainView(ArenaView arenaView, StatusView statusView)
    {
        this.arenaView = arenaView;
        this.statusView = statusView;
        contentPanel = new JPanel();


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Snake by Hubert");
        setResizable(false);
        add(contentPanel);


        init();
    }

    private void init()
    {
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Setting arenaView location
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(arenaView,gbc);

        // Setting statusView location
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        contentPanel.add(statusView,gbc);

        pack();


        setLayout(new GridLayout(1, 1, 0, 0));
        setLocationRelativeTo(null);
        setVisible(true);
    }




}
