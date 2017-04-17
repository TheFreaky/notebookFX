package ru.kpfu.itis.notebook;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.kpfu.itis.notebook.util.HibernateUtil;

import java.io.IOException;

/**
 * Created by Максим on 13.04.2017.
 */
public class App extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Notebook");

        primaryStage.setOnCloseRequest(event -> close());

        showPersonOverview();
    }

    public void showPersonOverview() {
        try {
            // Загружаем сведения об адресатах.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLDocument.fxml"));
            AnchorPane eventOverview = loader.load();

            Scene scene = new Scene(eventOverview);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        HibernateUtil.close();
        primaryStage.close();
        Platform.exit();
    }
}
