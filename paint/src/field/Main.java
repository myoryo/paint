package field;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("field.fxml"));    //loading .fxml file
        Scene scene = new Scene(root);  //creating new scene
        stage.setScene(scene);  //setting scene as main stage scene
        stage.setTitle("Paint");    //setting window title
        stage.show();   //makes window visible
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
