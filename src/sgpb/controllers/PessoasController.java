package sgpb.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import sgpb.dao.DAOGenerico;
import sgpb.modelo.Pessoa;

public class PessoasController implements Initializable {

    @FXML
    private MenuItem itemLogout;

    @FXML
    private MenuItem ItemCadastrar;

    @FXML
    private MenuItem itemAlunos;

    @FXML
    private MenuItem itemAutenticar;

    @FXML
    private MenuItem itemEventos;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem itemAlterar;

    @FXML
    private MenuItem itemServidores;

    @FXML
    private MenuItem itemFechar;

    @FXML
    private MenuItem itemTeste;

    @FXML
    private BorderPane border;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField searchPessoa;

    @FXML
    private TableColumn<Pessoa, String> pessoaView;

    @FXML
    private TableColumn<Pessoa, Long> idView;

    @FXML
    private TableView<Pessoa> tableView;

    DAOGenerico dao = new DAOGenerico();
    List<Pessoa> listaPessoas;
    ObservableList<Pessoa> pessoas;
    FilteredList<Pessoa> filter;
    Pessoa pessoaSelecionado;

    @FXML
    public void pesquisaPessoa(KeyEvent event) {
        // 2. Set the filter Predicate whenever the filter changes.
        searchPessoa.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(a -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (a.getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Pessoa> sortedData = new SortedList<>(filter);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableView.setItems(sortedData);
    }

    @FXML
    public void itemSelecionado(MouseEvent event) {
        if (event.getClickCount() == 2) {
            pessoaSelecionado = tableView.getSelectionModel().getSelectedItem();
            Pessoa pessoa = null;
            for (Pessoa a : listaPessoas) {
                if (pessoaSelecionado.getId().equals(a.getId())) {
                    pessoa = a;
                }
            }
            Stage stage = (Stage) menuBar.getScene().getWindow();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Cadastro.fxml"));
                Parent root = (Parent) loader.load();
                CadastrarController controller = loader.<CadastrarController>getController();
                controller.setPessoa(pessoa);
                controller.setLabelNome(pessoa.getNome());
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    private void notificacao(String texto) {
        Notifications notificationBuilder = Notifications.create()
                .title("Atenção!")
                .text(texto)
                .graphic(null)
                .hideAfter(Duration.seconds(3))
                .position(Pos.TOP_RIGHT);
        notificationBuilder.showWarning();
    }

    //**METODOS DO MENUBAR**
    @FXML
    private void teste(ActionEvent event) throws IOException {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Teste.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void identificacao(ActionEvent event) throws IOException {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Identificacao.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void cadastrar(ActionEvent event) throws IOException {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Cadastro.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void alunos(ActionEvent event) throws IOException {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Pessoas.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void servidores(ActionEvent event) throws IOException {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Servidores.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void eventos(ActionEvent event) throws IOException {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Eventos.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }

    @FXML
    private void fecharApp(ActionEvent event) throws IOException {
        try {
            Platform.exit();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            listaPessoas = dao.listar(Pessoa.class);
            pessoas = FXCollections.observableArrayList(listaPessoas);

            idView.setCellValueFactory(new PropertyValueFactory<>("id"));
            pessoaView.setCellValueFactory(new PropertyValueFactory<>("nome"));
            tableView.setItems(pessoas);

            // 1. Wrap the ObservableList in a FilteredList (initially display all data).
            filter = new FilteredList(pessoas, a -> true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
