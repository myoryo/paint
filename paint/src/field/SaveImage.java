package field;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;


public class SaveImage {
    /**
     *  Метод save(Canvas canvas)
     *  Вызывает окно, в котором можно выбрать папку и название для рисунка
     */
    public static void save(Canvas canvas){
        FileChooser saveFile = new FileChooser();
        saveFile.setTitle("Save File");

        File file = saveFile.showSaveDialog(new Stage());
        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }

    }
}
