package controller;

import java.awt.event.ActionEvent;

/**
 * Interfejs do przypisywania akcji przyciskom.
 */
public interface ButtonsListener
{
    void upKey(ActionEvent e);
    void downKey(ActionEvent e);
    void leftKey(ActionEvent e);
    void rightKey(ActionEvent e);
    void spaceKey(ActionEvent e);
    void enterKey(ActionEvent e);
}
