package hamid;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    @FXML private Text done;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("File Organizer by Hamid");
        Scene scene = new Scene(root,575,375);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
        //done.setFont(new Font(20.0));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
