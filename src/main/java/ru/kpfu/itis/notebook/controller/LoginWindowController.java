package ru.kpfu.itis.notebook.controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.kpfu.itis.notebook.entity.User;
import ru.kpfu.itis.notebook.model.UserModel;
import ru.kpfu.itis.notebook.util.HibernateUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController implements Initializable {
    @FXML
    private TextField tLogin;
    @FXML
    private PasswordField tPassword;
    @FXML
    private Button bSignIn;
    @FXML
    private Label isCorrect;

    private UserModel userService = new UserModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signIn();
    }

    public void signIn() {
        bSignIn.setOnAction(e -> {
            isCorrect.setText(null);

            String login = tLogin.getText();
            String pass = tPassword.getText();

            User user = userService.getUser(login, pass);
            if (user == null) {
                isCorrect.setText("Login or password incorrect");
                System.out.println("Can't find user"); //ToDo exception
                return;
            }
            System.out.printf("User %s finded.", login);
            try {
                ((Node) e.getSource()).getScene().getWindow().hide(); //hide login window
                getUserWindow(user);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void getUserWindow(User user) throws IOException {
        String userWindowPath = "/ru/kpfu/itis/notebook/view/NotebookMain.fxml";
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource(userWindowPath).openStream());

        NotebookMainController userController = loader.getController();
        userController.setUser(user);

        Scene scene = new Scene(root);
        primaryStage.setOnCloseRequest(event -> {
            HibernateUtil.close();
            primaryStage.close();
            Platform.exit();
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
