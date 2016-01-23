import controller.GameController;
import model.ArenaModel;
import view.ArenaView;
import view.MainView;
import view.StatusView;

/**
 * Created by hubert on 22.01.2016.
 */
public class Snake
{
    public static void main(String[] args)
    {
        ArenaModel arenaModel = new ArenaModel();
        ArenaView arenaView = new ArenaView(arenaModel);
        StatusView statusView = new StatusView(arenaModel);
        MainView mainView = new MainView(arenaView, statusView);
        GameController gameController = new GameController(arenaModel, arenaView, statusView);


    }


}
