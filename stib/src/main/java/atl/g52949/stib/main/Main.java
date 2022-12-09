package atl.g52949.stib.main;

import atl.g52949.stib.model.Djikstra.PathFinder;
import atl.g52949.stib.presenter.Presenter;
import atl.g52949.stib.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author gracz
 */
public class Main extends Application{

    /**
     * Entry points to the <code> StibRide </code> application..
     *
     * @param args no arguments needed.
     */
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        PathFinder model = new PathFinder();

        MainView view = new MainView(stage);

        Presenter presenter = new Presenter(model, view);

        view.showStage();
    }
    
}
