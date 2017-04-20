package ru.kpfu.itis.notebook;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.kpfu.itis.notebook.util.HibernateUtil;

import java.io.IOException;

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
//        String notebookMainPath = "/ru/kpfu/itis/notebook/view/NotebookMain.fxml";
        String notebookMainPath = "/ru/kpfu/itis/notebook/view/LoginWindow.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(notebookMainPath));
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

//    CREATE TABLE `notebook`.`users` (
//        `user_id` bigint NOT NULL AUTO_INCREMENT,
//        `login` varchar(80) NOT NULL unique,
//        `password` varchar(255) NOT NULL,
//        PRIMARY KEY (`user_id`)
//        );
//
//        CREATE TABLE `notebook`.`events` (
//        `event_id` bigint NOT NULL AUTO_INCREMENT,
//        `name` varchar(80),
//        `description` text,
//        `date` datetime NOT NULL,
//        `user_id` bigint NOT NULL,
//        PRIMARY KEY (`event_id`),
//        KEY `fk_user` (`user_id`),
//        CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `notebook`.`users` (`user_id`)
//        );
