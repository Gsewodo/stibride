package atl.g52949.stib.view;

import atl.g52949.stib.model.dto.FavoriDto;
import atl.g52949.stib.model.dto.StationsDto;
import atl.g52949.stib.presenter.Presenter;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 *
 */
public class MainView {
    
    private static Stage primaryStage;
    private static Scene scene;

    private static HomeController homeController;

    static void setHomeController(HomeController homeController) {
        MainView.homeController = homeController;
    }

    public MainView(Scene scene) throws IOException {
        this.scene = scene;
    }

    public MainView(Stage stage) throws IOException {
        this.scene = new Scene(loadFXML(), 800, 760);
        this.primaryStage = stage;
        this.primaryStage.setScene(scene);
        this.primaryStage.setMinHeight(800);
        this.primaryStage.setMinWidth(760);
        Image logo = new Image(getClass().getClassLoader().getResourceAsStream("images/logo.png"));
        this.primaryStage.getIcons().add(logo);
    }

    public void initialize(Presenter presenter) throws IOException {
        setPresenter(presenter);
    }

    public void initChoiceBox(List<String> stations) throws IOException {
        homeController.initChoiceBox(stations);
    }

    public void initFavoriteTripsChoiceBox(List<FavoriDto> favoriteRoutes) throws IOException {
        homeController.initFavoriteTripsChoiceBox(favoriteRoutes);
    }

    public void updateTableView(List<StationsDto> stations) throws IOException {
        homeController.updateTableView(stations);
    }

    /**
     * Method showStage, shows the stage.
     */
    public void showStage() {
        this.primaryStage.show();
    }

    private void setPresenter(Presenter presenter) throws IOException {
        homeController.setPresenter(presenter);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML());
    }

    private static Parent loadFXML() throws IOException {   
       FXMLLoader fxmlLoader = new FXMLLoader(MainView.class.getResource("/stibride/view/primary.fxml"));
        return fxmlLoader.load();
    }
}
