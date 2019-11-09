package field;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class PaintController {
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField textBrushSize;
    @FXML
    private Slider brushSlider;
    public void initialize(){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(mouseEvent -> {
            Double size;
            try {
                size = Double.parseDouble(textBrushSize.getText());
            }catch(Exception ex){
                size=0.0;
            }
            Color color = colorPicker.getValue();
            double x=mouseEvent.getX()-size/2;
            double y=mouseEvent.getY()-size/2;
            graphicsContext.setFill(color);
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
    public void brushChange(){
        textBrushSize.setText(String.valueOf(Math.round(brushSlider.getValue())));
    }
}
