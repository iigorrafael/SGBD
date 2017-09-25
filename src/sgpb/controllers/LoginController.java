package sgpb.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class LoginController implements Initializable {

    @FXML
    private BorderPane border;

    @FXML
    private JFXTextField login;

    @FXML
    private JFXPasswordField senha;

    @FXML
    private JFXButton entrar;

    Parent root = null;

    @FXML
    private void makeLogin(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/Eventos.fxml"));
        } catch (IOException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("SGPB");
        stage.setResizable(false);
        stage.show();
//        if (login.getText().equals("igor") && senha.getText().equals("123")) {
//            System.out.println("Hello World!");
//            Stage stage = (Stage) node.getScene().getWindow();
//            
//            try {
//                root = FXMLLoader.load(getClass().getResource("/fxml/Template.fxml"));
//                } catch (IOException e) {
//                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
//            }
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
//        } else {
//            System.out.println("Senha Login/Senha errada");
//            notificacao(event);
//        }
    }

    @FXML
    private void notificacao(ActionEvent event) {
        Notifications notificationBuilder = Notifications.create()
                .title("ERRO!")
                .text("Senha/E-mail incorreto!")
                .graphic(null)
                .hideAfter(Duration.seconds(3))
                .position(Pos.TOP_RIGHT);
        notificationBuilder.showWarning();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
