package field;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ExitFromApp {
    /**
     * Метод exit(Canvas canvas)
     * Закрывает приложение
     * Перед закрытием выводит окно, в котором предлагается сохранить рисунок
     */
    public static void exit(Canvas canvas){
        Stage stage = new Stage();
        stage.setTitle("Maybe you wanna save?"); // установка заголовка
        stage.setWidth(300);            // установка ширины
        stage.setHeight(100);           // установка длины
        Button yes = new Button("yes");
        Button no = new Button("no");
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(yes,no);
        Scene scene = new Scene(root, 300, 100);
        stage.setScene(scene);
        stage.show();
        yes.setOnAction(new EventHandler<ActionEvent>() { //Обработка кнопки yes
            @Override
            public void handle(ActionEvent event) {
                stage.close();
                SaveImage.save(canvas);
                System.exit(0);
            }
        });
        no.setOnAction(new EventHandler<ActionEvent>() { //Обработки кнопки no

            @Override
            public void handle(ActionEvent event) {
                stage.close();
                System.exit(0);
            }
        });
    }
}
