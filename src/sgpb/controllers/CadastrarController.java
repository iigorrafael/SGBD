package sgpb.controllers;

import com.futronic.SDKHelper.FTR_PROGRESS;
import com.futronic.SDKHelper.FarnValues;
import com.futronic.SDKHelper.FtrIdentifyRecord;
import com.futronic.SDKHelper.FtrIdentifyResult;
import com.futronic.SDKHelper.FutronicEnrollment;
import com.futronic.SDKHelper.FutronicException;
import com.futronic.SDKHelper.FutronicIdentification;
import com.futronic.SDKHelper.FutronicSdkBase;
import com.futronic.SDKHelper.IEnrollmentCallBack;
import com.futronic.SDKHelper.IIdentificationCallBack;
import com.futronic.SDKHelper.IVerificationCallBack;
import com.futronic.SDKHelper.VersionCompatible;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import sgpb.dao.DAOGenerico;
import sgpb.modelo.EntidadeBiometria;
import sgpb.modelo.Evento;
import sgpb.modelo.Pessoa;

public class CadastrarController implements Initializable, IEnrollmentCallBack, IVerificationCallBack, IIdentificationCallBack {

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
    private ImageView imageView;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    private JFXButton btnCadastrar;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private Label labelTexto;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelDedo;

    @FXML
    private JFXComboBox<String> combo;

    private Evento evento;
    DAOGenerico dao = new DAOGenerico();
    List<Pessoa> listaPessoas;
    ObservableList<Pessoa> pessoas;
    FilteredList<Pessoa> filter;
    Pessoa pessoaSelecionada;
    private FutronicSdkBase operacao;
    private Object biometria;
    EntidadeBiometria entidadeBiometria;
    private Pessoa pessoa;

    @FXML
    private void cadastrarDigital(ActionEvent event) throws FutronicException {
        if (pessoa == null) {
            setTextoLabel("Nenhuma pessoa selecionada! Seleciona uma pessoa.");
        } else {
            btnEnrollActionPerformed();
        }
    }

    @FXML
    private void cancelar(ActionEvent event) throws FutronicException {
        btnStopActionPerformed();
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

    @FXML
    private void comboBox() {

    }

    //**METODOS DO MENUBAR**
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
            Logger
                    .getLogger(LoginController.class
                            .getName()).log(Level.SEVERE, null, e);
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
            Logger
                    .getLogger(LoginController.class
                            .getName()).log(Level.SEVERE, null, e);
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
            Logger
                    .getLogger(LoginController.class
                            .getName()).log(Level.SEVERE, null, e);
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
            Logger
                    .getLogger(LoginController.class
                            .getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(LoginController.class
                    .getName()).log(Level.SEVERE, null, e);
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
        combo.getItems().add("Polegar Esquerdo");
        combo.getItems().add("Indicador Esquerdo");
        combo.getItems().add("Médio Esquerdo");
        combo.getItems().add("Anelar Esquerdo");
        combo.getItems().add("Mínino Esquerdo");
        combo.getItems().add("Polegar Direito");
        combo.getItems().add("Indicador Direito");
        combo.getItems().add("Médio Direito");
        combo.getItems().add("Anelar Direito");
        combo.getItems().add("Mínino Direito");
        combo.setPromptText("Selecione o dedo");
        Image img = new Image("file:///C:\\Users\\CEDI\\Desktop\\Igor\\SGPB\\src\\sgpb\\imagem\\digitalImage.jpg", 160.0, 240.0, true, true);
        imageView.setImage(img);        
    }

    public void inicio() {

        // **SETANDO OS PARAMETROS**
        try {
            FutronicEnrollment dummy = new FutronicEnrollment();
            for (FarnValues m_FarnValue2Index1 : m_FarnValue2Index) {
                if (dummy.getFARnLevel() == m_FarnValue2Index1) {
                }
            }
        } catch (FutronicException e) {
            e.printStackTrace();
        }
        operacao = null;

    }

    private void btnStopActionPerformed() {
        operacao.OnCalcel();
    }

    ////////////////////////////////////////////////////////////////////
    // ICallBack interface implementation
    ////////////////////////////////////////////////////////////////////
    //**EXECUTADO QUANDO PRECISA COLOCAR O DEDO NOVAMENTE**
    @Override
    public void OnPutOn(FTR_PROGRESS Progress) {
        Platform.runLater(() -> {
            setTextoLabel("Por favor! Coloque seu dedo no leitor.");
        });
    }

    //**EXECUTADO QUANDO PRECISA TIRAR O DEDO**
    @Override
    public void OnTakeOff(FTR_PROGRESS Progress) {
        Platform.runLater(() -> {
            setTextoLabel("Por favor! Tire o seu dedo do leitor.");
        });
    }

    /**
     * The "Show the current fingerprint image" event. MOSTRAR A IMAGEM DA
     * DIGITAL
     *
     **
     * @param Bitmap the instance of Bitmap class with fingerprint image.
     */
    @Override
    public void UpdateScreenImage(java.awt.image.BufferedImage Bitmap) {
        try {
            Image image = SwingFXUtils.toFXImage(Bitmap, null);
            Platform.runLater(() -> {
                imageView.setImage(image);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The "Fake finger detected" event.
     *
     * @param Progress the fingerprint image.
     *
     * @return <code>true</code> if the current indetntification operation
     * should be aborted, otherwise is <code>false</code>
     */
    @Override
    public boolean OnFakeSource(FTR_PROGRESS Progress) {
//        int nResponse;
//        nResponse = JOptionPane.showConfirmDialog( this,
//                "Fonte desconhecida encontrada! Deseja continuar?",
//                getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
//        return (nResponse == JOptionPane.NO_OPTION);
        return false;
    }

    ////////////////////////////////////////////////////////////////////
    // ICallBack interface implementation
    ////////////////////////////////////////////////////////////////////
    /**
     * The "Enrollment operation complete" event.
     *
     * @param bSuccess <code>true</code> if the operation succeeds, otherwise is
     * <code>false</code>.
     * @param nResult
     * @param The Futronic SDK return code (see FTRAPI.h).
     */
    // **METODO DE CADASTRO NO BANCO DE DADOS**
    @Override
    public void OnEnrollmentComplete(boolean bSuccess, int nResult) {
        if (bSuccess) {

            dao = new DAOGenerico();
            ArrayList<EntidadeBiometria> Users = (ArrayList<EntidadeBiometria>) dao.listar(EntidadeBiometria.class
            );

            //**CADASTRAR NO USUARIO**//
            Platform.runLater(() -> {
                setTextoLabel("Captura realizada com sucesso.");
                try {
                    Thread.sleep(1500);

                } catch (InterruptedException ex) { 
                    Logger.getLogger(CadastrarController.class.getName()).log(Level.SEVERE, null, ex);
                }
                setTextoLabel("Qualidade: " + ((FutronicEnrollment) operacao).getQuality());
            });

            // // // SETANDO O TEMPLATE // 
            entidadeBiometria = new EntidadeBiometria();
            entidadeBiometria.setTemplate(((FutronicEnrollment) operacao).getTemplate());
            try {
                //SALVANDO // 
                dao = new DAOGenerico();
                entidadeBiometria.setPessoa(pessoa);
                dao.biometria(entidadeBiometria);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            Platform.runLater(() -> {
                setTextoLabel("Cadastro realizado com sucesso.");
            });

        } else {
            Platform.runLater(() -> {
                setTextoLabel("Houve um erro no cadastro!");
                try {
                    Thread.sleep(1500);

                } catch (InterruptedException ex) {
                    Logger.getLogger(CadastrarController.class.getName()).log(Level.SEVERE, null, ex);                }
                setTextoLabel("Erro: " + FutronicSdkBase.SdkRetCode2Message(nResult));
            });

        }

        biometria = null;
        operacao.Dispose();

    }

    /**
     * The "Verification operation complete" event.
     *
     * @param bSuccess <code>true</code> if the operation succeeds, otherwise is
     * <code>false</code>
     * @param nResult the Futronic SDK return code.
     * @param bVerificationSuccess if the operation succeeds (bSuccess is
     * <code>true</code>), this parameters shows the verification operation
     * result. <code>true</code> if the captured from the attached scanner
     * template is matched, otherwise is <code>false</code>.
     */
    @Override
    public void OnVerificationComplete(boolean bSuccess,
            int nResult,
            boolean bVerificationSuccess) {

        if (bSuccess) {
            if (bVerificationSuccess) {
                Platform.runLater(() -> {
                    setTextoLabel("Verificação completada!");
                    try {
                        Thread.sleep(1500);

                    } catch (InterruptedException ex) {
                        Logger.getLogger(CadastrarController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    setTextoLabel("Nome: " + pessoa.getNome());
                });

            } else {
                Platform.runLater(() -> {
                    setTextoLabel("A verificação falhou!");
                });
            }
        } else {
            Platform.runLater(() -> {
                setTextoLabel("Processo de verificação falhou!");
                try {
                    Thread.sleep(1500);

                } catch (InterruptedException ex) {
                    Logger.getLogger(CadastrarController.class.getName()).log(Level.SEVERE, null, ex);
                }
                setTextoLabel("Erro: " + (FutronicSdkBase.SdkRetCode2Message(nResult)));
            });

        }
        biometria = null;
    }

    /**
     * The "Get base template operation complete" event.
     *
     * @param bSuccess <code>true</code> if the operation succeeds, otherwise is
     * <code>false</code>.
     * @param nResult The Futronic SDK return code.
     */
    @Override
    public void OnGetBaseTemplateComplete(boolean bSuccess, int nResult) {
        if (bSuccess) {
            Platform.runLater(() -> {
                setTextoLabel("Iniciando identificação...");
            });

            DAOGenerico dao = new DAOGenerico();
            ArrayList<EntidadeBiometria> users = (ArrayList<EntidadeBiometria>) biometria;
            FtrIdentifyRecord[] rgRecords = new FtrIdentifyRecord[users.size()];
            for (int iUsers = 0; iUsers < users.size(); iUsers++) {
                rgRecords[iUsers] = users.get(iUsers).getFtrIdentifyRecord();
            }

            FtrIdentifyResult result = new FtrIdentifyResult();

            nResult = ((FutronicIdentification) operacao).Identification(rgRecords, result);

            System.out.println(nResult);

            if (nResult == FutronicSdkBase.RETCODE_OK) {
                Platform.runLater(() -> {
                    setTextoLabel("Identificação completada! Nome: ");
                    try {
                        Thread.sleep(1500);

                    } catch (InterruptedException ex) {
                        Logger.getLogger(CadastrarController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                if (result.m_Index != -1) {
                    Platform.runLater(() -> {
                        setTextoLabel((users.get(result.m_Index).getPessoa().getNome()));
                    });
                } else {
                    Platform.runLater(() -> {
                        setTextoLabel("Não encontrou!");
                    });
                }
            } else {
                Platform.runLater(() -> {
                    setTextoLabel("Identificação falhou!");
                });
            }
        } else {
            System.out.println("Não é possível recuperar modelo base.");
            System.out.println("Erro: ");
            System.out.println(FutronicSdkBase.SdkRetCode2Message(nResult));
        }
        biometria = null;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    public void btnIdentifyActionPerformed() {
        DAOGenerico dao = new DAOGenerico();
        ArrayList<EntidadeBiometria> Users = (ArrayList<EntidadeBiometria>) dao.listar(EntidadeBiometria.class
        );

        if (Users.isEmpty()) {
            Platform.runLater(() -> {
                setTextoLabel("Usuario não encontrado! Por favor execute o cadastro dele.");
            });
            return;
        }

        biometria = Users;

        try {
            operacao = new FutronicIdentification();

            switch (1) {
                case 0:
                    operacao.setVersion(VersionCompatible.ftr_version_previous);
                    break;

                case 1:
                    operacao.setVersion(VersionCompatible.ftr_version_current);
                    break;

                default:
                    operacao.setVersion(VersionCompatible.ftr_version_compatible);
                    break;
            }

            // INICIANDO A VERIFICAÇÃO
            ((FutronicIdentification) operacao).GetBaseTemplate(this);
        } catch (FutronicException e) {
            Platform.runLater(() -> {
                setTextoLabel("Não é possível iniciar a operação de inscrição.");
                try {
                    Thread.sleep(1500);

                } catch (InterruptedException ex) {
                    Logger.getLogger(CadastrarController.class.getName()).log(Level.SEVERE, null, ex);
                }
                setTextoLabel(e.getMessage());
            });
            biometria = null;

        }

        operacao.Dispose();
    }

    void btnEnrollActionPerformed() {
        try {

            if (operacao == null) {
                operacao = new FutronicEnrollment();
            }

            switch (1) {
                case 0:
                    operacao.setVersion(VersionCompatible.ftr_version_previous);
                    break;

                case 1:
                    operacao.setVersion(VersionCompatible.ftr_version_current);
                    break;

                default:
                    operacao.setVersion(VersionCompatible.ftr_version_compatible);
                    break;
            }

            // INICIAR PROCESSO**
            //start enrollment process
            System.out.println(Thread.currentThread().toString());

            ((FutronicEnrollment) operacao).Enrollment(this);

        } catch (FutronicException | IllegalArgumentException | IllegalStateException | NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private static final FarnValues[] m_FarnValue2Index
            = {
                FarnValues.farn_low,
                FarnValues.farn_below_normal,
                FarnValues.farn_normal,
                FarnValues.farn_above_normal,
                FarnValues.farn_high,
                FarnValues.farn_max,
                FarnValues.farn_custom
            };

    public void setTextoLabel(String texto) {
        try {
            labelTexto.setText(texto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLabelNome(String texto) {
        try {
            labelNome.setText(texto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

}
