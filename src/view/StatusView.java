package view;

import model.ArenaModel;

import javax.swing.*;
import java.awt.*;

/**
 * Widok statusu. Zawiera on informację o ilości zdobytych punktów.
 */
public class StatusView extends JPanel
{
    public static final int WIDTH = 600;  // szerokość panelu
    public static final int HEIGHT = 50; // wysokość panelu
    private static final Font FONT = new Font("Tahoma", Font.BOLD, 25);

    private final ArenaModel arenaModel;
    private JLabel scoreLabel;
    private int score;

    /*
     * Konstruktor StatusView przyjmuje jako argument arenaModel, z którego będzie pobierał
     * ilość zdobytych punktów
     */
    public StatusView(ArenaModel arenaModel)
    {
        this.arenaModel = arenaModel;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(175, 208, 139));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        scoreLabel = new JLabel();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(scoreLabel, gbc);

        update();
    }

    public void update()
    {
        score = arenaModel.getScore();
        scoreLabel.setText("Score: " + score);
        scoreLabel.setFont(FONT);
        scoreLabel.setForeground(new Color(54, 57, 27));
    }
}
