package view;

import model.ArenaModel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hubert on 22.01.2016.
 */
public class StatusView extends JPanel
{
    private final int WIDTH = 600;
    private final int HEIGHT = 50;
    private final ArenaModel arenaModel;

    private JLabel scoreLabel, livesLabel;
    private int score, lives;


    public StatusView(ArenaModel arenaModel)
    {
        this.arenaModel = arenaModel;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLUE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        scoreLabel = new JLabel();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(scoreLabel, gbc);

        livesLabel = new JLabel();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(livesLabel, gbc);

        update();
    }

    public void update()
    {
        score = arenaModel.getScore();
        lives = arenaModel.getLives();

        scoreLabel.setText("Score: " + score);
        livesLabel.setText("Lives: " + lives);
    }


}
