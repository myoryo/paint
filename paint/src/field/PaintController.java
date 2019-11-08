package field;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleGroup;

import java.awt.*;

public class PaintController {
    @FXML
    private Canvas canvas;

    public void initialize(){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(mouseEvent -> {
            int size=5;
            double x=mouseEvent.getX()-size/2;
            double y=mouseEvent.getY()-size/2;
            graphicsContext.fillOval(x,y,size,size);
        });
    }
    public void onSave(){

    }
    public void onSaveAs(){

    }
    public void onOpen(){

    }
    public void onExit(){
        System.exit(0);
    }
    public void onUndo(){

    }
    public void onRedo(){

    }
    public void onClear(){

    }
    public void onCircle(){

    }
    public void onSquare(){

    }
}
