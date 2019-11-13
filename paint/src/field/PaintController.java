package field;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class PaintController {
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPicker;
    private Color color;
    double size;
    @FXML
    private TextField brushSize;
    @FXML
    private Slider brushSlider;
    @FXML
    private TextField canvasSize;
    @FXML
    private Slider canvasSlider;
    @FXML
    private ToggleButton eraserBt;
    @FXML
    private ToggleButton ellipseBt;
    @FXML
    private ToggleButton rectangleBt;
    @FXML
    private ToggleButton penBt;
    @FXML
    private ToggleButton fillBt;
    @FXML
    private MenuItem redo;
    @FXML
    private MenuItem undo;

    private Ellipse ellipse;
    private Rectangle rectangle;
    private ArrayDeque<WritableImage>  undoStack= new ArrayDeque<WritableImage>();
    private ArrayDeque<WritableImage> redoStack= new ArrayDeque<WritableImage>();
    private GraphicsContext graphicsContext;
    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        /**
         * /*Обработка клика
         */
        canvas.setOnMouseClicked(mouseEvent -> {
            try {
                size = Double.parseDouble(brushSize.getText());
            } catch (Exception ex) {
                size = 0;
            }
            double x = mouseEvent.getX() - size / 2;
            double y = mouseEvent.getY() - size / 2;
            color = colorPicker.getValue();
            graphicsContext.setFill(color);
            if(penBt.isSelected()){
                graphicsContext.fillOval(x, y, size, size);
            } else if(eraserBt.isSelected()){
                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillOval(x,y,size,size);
                //graphicsContext.clearRect(x,y,size,size);
            }
        });
        /**
         * Обработка перетаскивания
         */
        canvas.setOnMouseDragged(mouseEvent -> {
            graphicsContext.setStroke(color);
            try {
                size = Double.parseDouble(brushSize.getText());
            } catch (Exception ex) {
                size = 0;
            }
            double x = mouseEvent.getX() - size / 2;
            double y = mouseEvent.getY() - size / 2;
            color = colorPicker.getValue();
            graphicsContext.setFill(color);
            if(penBt.isSelected()){
                graphicsContext.fillOval(x, y, size, size);
            } else if(eraserBt.isSelected()){
                //graphicsContext.clearRect(x,y,size,size);
                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillOval(x,y,size,size);
            }
        });
        /**
         * Обработка зажатия
         */
        canvas.setOnMousePressed(mouseEvent -> {
            undoStackChange();
            redoStack.clear();
            redo.setDisable(true);
            if(ellipseBt.isSelected()) {
                ellipse=new Ellipse();
                ellipse.setCenterX(mouseEvent.getX());
                ellipse.setCenterY(mouseEvent.getY());
                redoStack.clear();
            }else if(rectangleBt.isSelected()){
                rectangle=new Rectangle();
                rectangle.setX(mouseEvent.getX());
                rectangle.setY(mouseEvent.getY());
            }
        });
        /**
         * Обработка отпускания
         */
        canvas.setOnMouseReleased(mouseEvent -> {
            if(ellipseBt.isSelected()) {
                ellipse.setRadiusX(Math.abs(mouseEvent.getX() - ellipse.getCenterX()));
                ellipse.setRadiusY(Math.abs(mouseEvent.getY() - ellipse.getCenterY()));
                if(ellipse.getCenterX() > mouseEvent.getX()) {
                    ellipse.setCenterX(mouseEvent.getX());
                }
                if(ellipse.getCenterY() > mouseEvent.getY()) {
                    ellipse.setCenterY(mouseEvent.getY());
                }
                graphicsContext.setLineWidth(size);
                graphicsContext.strokeOval(ellipse.getCenterX(), ellipse.getCenterY(), ellipse.getRadiusX(), ellipse.getRadiusY());
            } else if(rectangleBt.isSelected()){
                rectangle.setWidth(Math.abs(mouseEvent.getX()-rectangle.getX()));
                rectangle.setHeight(Math.abs(mouseEvent.getY()-rectangle.getY()));
                if(rectangle.getX() > mouseEvent.getX()) {
                    rectangle.setX(mouseEvent.getX());
                }
                if(rectangle.getY() > mouseEvent.getY()) {
                    rectangle.setY(mouseEvent.getY());
                }
                graphicsContext.setLineWidth(size);
                graphicsContext.strokeRect(rectangle.getX(),rectangle.getY(),rectangle.getWidth(),rectangle.getHeight());
            }
        });
    }

    /**
     * Метод onNew()
     * Обрабатывает MenuItem с текстом New
     */
    public void onNew() {
        onSaveAs();
        NewCanvas.create(canvas);
        onClear();
        undoStack.clear();
        redoStack.clear();
        undo.setDisable(true);
        redo.setDisable(true);
    }

    /**
     * Метод onSaveAs()
     * Обрабатывает MenuItem с текстом OnSave
     */
    @FXML
    private void onSaveAs(){
        SaveImage.save(canvas);
    }

    /**
     * Метод onOpen()
     * Обрабатывает MenuItem с текстом Open
     * Открывает окно, в котором можно выбрать файл для открытия в приложении
     */
    public void onOpen(){
        FileChooser openFile = new FileChooser();
        openFile.setTitle("Open File");
        File file = openFile.showOpenDialog(new Stage());
        if (file != null) {
            try {
                InputStream io = new FileInputStream(file);
                Image img = new Image(io);
                canvas.setHeight(img.getHeight());
                canvas.setWidth(img.getWidth());
                graphicsContext.drawImage(img, 0, 0);
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }

    }

    /**
     * Метод onExit()
     * Обрабатывает MenuItem с текстом Exit
     */
    @FXML
    private void onExit(){
        ExitFromApp.exit(canvas);
    }

    /**
     * Метод onClear()
     * Обрабатывает MenuItem Clear
     * Очищает canvas
     */
    @FXML
    private void onClear(){
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.WHITE);
        //graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Метод sliderBrushSizeChange()
     * При вызове изменяет размер кисти
     */
    @FXML
    private void sliderBrushSizeChange(){
        brushSize.setText(String.valueOf(Math.round(brushSlider.getValue())));
    }

    /**
     * Метод textBrushSizeChange()
     * При вызове изменяет размер кисти
     */
    @FXML
    private void textBrushSizeChange(){
        try {
            brushSlider.setValue(Integer.parseInt(brushSize.getText()));
        }catch(Exception ex){
            brushSize.setText("");
            brushSlider.setValue(0);
        }
    }

    /**
     * Метод undoStackChange()
     * Добавляет в undoStack предыдущее состояние
     */
    private void undoStackChange(){
        if(undoStack.size()==20)
            undoStack.pollFirst();
        undoStack.push(getSnap());
        if(undoStack.isEmpty())
            undo.setDisable(true);
        else
            undo.setDisable(false);
    }
    /**
     * Метод redoStackChange()
     * Добавляет в redoStack предыдущее состояние
     */


    /**
     * Метод onRedo()
     * Обработка MenuItem с текстом "Redo"
     *
     */
    @FXML
    private void onRedo(){
        if (!redoStack.isEmpty()) {
            undoStack.push(getSnap());
            canvas.setHeight(redoStack.peek().getHeight());
            canvas.setWidth(redoStack.peek().getWidth());
            graphicsContext.drawImage(redoStack.pop(), 0, 0);
            undo.setDisable(false);
            if(redoStack.isEmpty())
                redo.setDisable(true);
        }
    }


    /**
     * Метод onUndo()
     * Обрабатывает MenuItem undo
     * Перерисовывает canvas
     */
    @FXML
    private void onUndo(){
        if (!undoStack.isEmpty()) {
            redoStack.push(getSnap());
            canvas.setHeight(undoStack.peek().getHeight());
            canvas.setWidth(undoStack.peek().getWidth());
            graphicsContext.drawImage(undoStack.pop(), 0, 0);
            redo.setDisable(false);
            if(undoStack.isEmpty())
                undo.setDisable(true);
        }
    }

    /**
     * Метод getSnap()
     * Возвращает снимок canvas'a в типе WritableImage
     */
    private WritableImage getSnap(){
        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        WritableImage snap = canvas.snapshot(null, writableImage);
        return snap;
    }

}
