/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgpb;

import com.futronic.SDKHelper.FutronicSdkBase;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sgpb.controllers.TesteController;

public class Main extends Application {

    TesteController teste;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        this.stage = stage;
        stage.show();
        stage.setOnCloseRequest((WindowEvent we) -> {
            try {
                Platform.exit();
                System.exit(0);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public Stage getStage() {
        return stage;
    }

}
