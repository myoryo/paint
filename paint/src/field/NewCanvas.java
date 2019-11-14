package field;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class NewCanvas {

    /**
     * Метод create(Canvas canvas)
     * Дает выбрать новый размер для Canvas
     */
    public static Stage create(Canvas canvas, Stage stage){
        TextField widthField = new TextField("Width");
        TextField heightField = new TextField("Height");
        Button create = new Button("Create");
        create.setDisable(true);
        FlowPane root = new FlowPane();
        stage.setTitle("New"); // установка заголовка
        stage.setWidth(300);            // установка ширины
        stage.setHeight(100);           // установка длины
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(heightField,widthField,create);
        Scene scene = new Scene(root, 300, 100);
        stage.setScene(scene);
        stage.show();
        heightField.setOnKeyTyped(e->{
            try{
                if(4000<Integer.parseInt(heightField.getText())) {
                    throw new SizeException("Too high resolution");
                }else {
                    create.setDisable(false);
                }
            } catch (SizeException ex){
                heightField.setText(ex.getMessage());
                create.setDisable(true);
            } catch(Exception ex){
                heightField.setText("");
                create.setDisable(true);
            }
        });
        widthField.setOnKeyTyped(e->{
            try{
                if(4000<Integer.parseInt(widthField.getText())){
                    throw new SizeException("Too high resolution");
                }else {
                    create.setDisable(false);
                }
            } catch (SizeException ex){
                widthField.setText(ex.getMessage());
                create.setDisable(true);
            }catch(Exception ex){
                widthField.setText("");
                create.setDisable(true);
            }
        });
        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    canvas.setHeight(Double.parseDouble(heightField.getText()));
                    canvas.setWidth(Double.parseDouble(widthField.getText()));
                    GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
                    graphicsContext.setFill(Color.WHITE);
                    graphicsContext.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
                    stage.close();
                }catch(Exception  ex){
                    create.setDisable(true);
                }
            }
        });
        return stage;
    }
}


