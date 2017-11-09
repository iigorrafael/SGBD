package sgpb.controllers;

import com.futronic.SDKHelper.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import sgpb.dao.DAOGenerico;
import sgpb.modelo.*;

public class IdentificacaoController implements Initializable, IEnrollmentCallBack, IVerificationCallBack, IIdentificationCallBack {

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
    private ImageView imageView;

    @FXML
    private Label labelInstrucoes;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    private JFXButton btnIdentificar;

    @FXML
    private JFXButton btnShowPresentes;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label labelEvento;

    @FXML
    private Label labelNome;

    private StackPane lockPane;

    private boolean cancelar;
    private Atividades evento;
    DAOGenerico dao = new DAOGenerico();
    List<Pessoa> listaPessoas;
    ObservableList<Pessoa> pessoas;
    FilteredList<Pessoa> filter;
    Pessoa pessoaSelecionada;
    private FutronicSdkBase operacao;
    ArrayList<EntidadeBiometria> users;

    EntidadeBiometria entidadeBiometria;
    Pessoa pessoa;
    Date horaInicio;
    Date horaFinal;

    //**Metodos dos botões**
    @FXML
    private void identificar(ActionEvent event) throws FutronicException {
        if (evento == null) {
            setLabelInstrucoes("Nenhum evento selecionado! Por favor, selecione um evento.");
        } else {
            lockPane = new StackPane();
            lockPane.setPrefHeight(600.0);
            lockPane.setPrefWidth(906.0);
            lockPane.setLayoutX(-6.0);
            lockPane.setLayoutY(-29.0);
            cancelar = false;
            dao = new DAOGenerico();
            users = (ArrayList<EntidadeBiometria>) dao.listar(EntidadeBiometria.class);
            btnIdentifyActionPerformed();
            anchorPane.getChildren().add(lockPane);
            lockPane.setOnMouseClicked((MouseEvent event1) -> {
                JFXDialogLayout content = new JFXDialogLayout();
                content.setHeading(new Text("Autenticação Requerida!"));
                JFXPasswordField senha = new JFXPasswordField();
                content.setBody(senha);
                JFXButton btnCancelar1 = new JFXButton("Cancelar");
                JFXButton btnOk = new JFXButton("OK");
                JFXDialog dialog = new JFXDialog(lockPane, content, JFXDialog.DialogTransition.CENTER);
                btnOk.setDefaultButton(true);
                btnOk.setOnAction((ActionEvent event2) -> {
                    if (senha.getText().equals("123")) {
                        anchorPane.getChildren().remove(lockPane);
                        dialog.close();

                    } else {
                        System.out.println("Senha Login/Senha errada");
                        notificacaoErro("ERRO", "Senha Incorreta");
                    }
                });
                btnCancelar1.setCancelButton(true);
                btnCancelar1.setOnAction((ActionEvent event3) -> {
                    dialog.close();
                });
                content.setActions(btnCancelar1, btnOk);
                dialog.show();
            });

        }

    }

    @FXML
    private void cancelar(ActionEvent event) throws FutronicException {
        btnStopActionPerformed();
    }

    @FXML
    private void showPresentes(ActionEvent event) throws FutronicException {
        btnStopActionPerformed();
        Stage stage = (Stage) menuBar.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Presentes.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    //**Metodos Notificações push**
    @FXML
    private void notificacaoSucesso(String titulo, String texto
    ) {
        Notifications notificationBuilder = Notifications.create()
                .title(titulo)
                .text(texto)
                .graphic(null)
                .hideAfter(Duration.seconds(4))
                .position(Pos.TOP_RIGHT);
        notificationBuilder.showConfirm();

    }

    @FXML
    private void notificacaoErro(String titulo, String texto
    ) {
        Notifications notificationBuilder = Notifications.create()
                .title(titulo)
                .text(texto)
                .graphic(null)
                .hideAfter(Duration.seconds(4))
                .position(Pos.TOP_RIGHT);
        notificationBuilder.showError();
    }
   
    //**METODOS DO MENUBAR**
    @FXML
    private void identificacao(ActionEvent event) throws IOException {
        btnStopActionPerformed();
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
        btnStopActionPerformed();
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
        btnStopActionPerformed();
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
    private void eventos(ActionEvent event) throws IOException {
        btnStopActionPerformed();
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
        btnStopActionPerformed();
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

    //** METODOS DO SDK DO LEITOR DE DIGITAL**
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

    }

    //**Metodo para cancelar a operação no SDK**
    private void btnStopActionPerformed() {
        if (operacao != null) {
            operacao.OnCalcel();
            cancelar = true;
        }

    }

    ////////////////////////////////////////////////////////////////////
    // ICallBack interface implementation
    ////////////////////////////////////////////////////////////////////
    //**EXECUTADO QUANDO PRECISA COLOCAR O DEDO NOVAMENTE**
    @Override
    public void OnPutOn(FTR_PROGRESS Progress) {
        Platform.runLater(() -> {
            setLabelInstrucoes("Por favor! Coloque seu dedo no leitor.");
        });
    }

    //**EXECUTADO QUANDO PRECISA TIRAR O DEDO**
    @Override
    public void OnTakeOff(FTR_PROGRESS Progress) {
        Platform.runLater(() -> {
            setLabelInstrucoes("Por favor! Tire o seu dedo do leitor.");
        });
    }

    //**Metodo que faz a atualização da imagem mais recente na tela**
    /**
     * The "Show the current fingerprint image" event. MOSTRAR A IMAGEM DA
     * DIGITAL
     *
     **
     * @param Bitmap the instance of Bitmap class with fingerprint image. Bitmap
     * é a instancia do classe com a impressão digital
     */
    @Override
    public void UpdateScreenImage(java.awt.image.BufferedImage Bitmap) {
        try {
//String format = "PNG";
//ImageIO.write(Bitmap, format, new File("C:\\Users\\CEDI\\Desktop\\Igor\\SGPB\\src\\BIOIMAGE.png"));

            Image image = SwingFXUtils.toFXImage(Bitmap, null);
            Platform.runLater(() -> {
                imageView.setImage(image);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //**Metodo que realiza a verificação do dedo falso, fake finger**
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

            //**CADASTRAR NO USUARIO**//
            Platform.runLater(() -> {
                System.out.println("Captura realizada com sucesso.");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(IdentificacaoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Qualidade: " + ((FutronicEnrollment) operacao).getQuality());
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
                System.out.println("Cadastro realizado com sucesso.");
            });

        } else {
            Platform.runLater(() -> {
                System.out.println("Houve um erro no cadastro!");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(IdentificacaoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Erro: " + FutronicSdkBase.SdkRetCode2Message(nResult));
            });

        }
        operacao.Dispose();

    }

    //**Metodo de transição que verifica se a captura foi realizada com sucesso**
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
                    System.out.println("Verificação completada!");
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(IdentificacaoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Nome: " + pessoa.getNome());
                });

            } else {
                Platform.runLater(() -> {
                    System.out.println("A verificação falhou!");
                });
            }
        } else {
            Platform.runLater(() -> {
                System.out.println("Processo de verificação falhou!");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(IdentificacaoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Erro: " + (FutronicSdkBase.SdkRetCode2Message(nResult)));
            });

        }
    }

    //**Método final da identificação, com as informações que seram mandadas para a interface***
    /**
     * The "Get base template operation complete" event.
     *
     * @param bSuccess <code>true</code> if the operation succeeds, otherwise is
     * <code>false</code>.
     * @param nResult The Futronic SDK return code.
     */
    @Override
    public void OnGetBaseTemplateComplete(boolean bSuccess, int nResult) {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Long horaAtual = null;
        try {
            horaAtual = sdf.parse(sdf.format(cal.getTime())).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(IdentificacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Long inicio = evento.getHorarioInicio().getTime();
        Long fim = evento.getHorarioFim().getTime();
        Long antesInicio = inicio - (evento.getToleranciaAntesInicio() * 60000);
        Long depoisInicio = inicio + (evento.getToleranciaAposInicio() * 60000);
        Long antesFim = (fim - evento.getToleranciaFim());

        if (bSuccess) {
            if (antesInicio <= horaAtual && depoisInicio >= horaAtual || antesFim <= horaAtual) {
                Platform.runLater(() -> {
                    setLabelInstrucoes("Iniciando identificação...");
                });

                cancelar = false;

                FtrIdentifyRecord[] rgRecords = new FtrIdentifyRecord[users.size()];
                for (int iUsers = 0; iUsers < users.size(); iUsers++) {
                    rgRecords[iUsers] = users.get(iUsers).getFtrIdentifyRecord();
                }

                FtrIdentifyResult result = new FtrIdentifyResult();

                nResult = ((FutronicIdentification) operacao).Identification(rgRecords, result);

                if (nResult == FutronicSdkBase.RETCODE_OK) {

                    if (result.m_Index != -1) {
                        Platform.runLater(() -> {
                            setLabelInstrucoes("Identificação completada!");
                            try {
                                //Pausa para mostrar as informções
                                Thread.sleep(1000);
                                //Buscar a hora no momento
                                Date in = new Date();
                                LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
                                Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
                                //cadastro no banco de dados
                                AutenticadoEvento inscrito = new AutenticadoEvento();
                                inscrito.setAtividades(evento);
                                inscrito.setPessoa(users.get(result.m_Index).getPessoa());
                                inscrito.setDataHora(out);
                                dao.inserir(inscrito);
                                //mostrar as informações na interface
                                setLabelNome("Nome: " + (users.get(result.m_Index).getPessoa().getNome()));
                                //chamar notificação de sucesso
                                notificacaoSucesso("Presença confirmada!", users.get(result.m_Index).getPessoa().getNome());
                            } catch (InterruptedException ex) {
                                Logger.getLogger(IdentificacaoController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    } else {
                        Platform.runLater(() -> {
                            setLabelInstrucoes("Não encontrou!");
                            setLabelNome("");
                            notificacaoErro("ERRO!", "Não encontrou!");
                        });
                    }
                } else {
                    Platform.runLater(() -> {
                        setLabelNome("Identificação falhou!");
                    });
                }

            } else {
//                btnStopActionPerformed();
                Platform.runLater(() -> {
                    setLabelInstrucoes("Horário não permitido!");
                    notificacaoErro("ERRO!", "HORÁRIO NÃO PERMITIDO!");
//                    btnIdentifyActionPerformed();
                });

            }

        } else {
            String erro;
            if (cancelar == true) {
                erro = ("Operação cancelada pelo Usuário");
            } else {
                erro = ("Erro: " + FutronicSdkBase.SdkRetCode2Message(nResult));
            }

            Platform.runLater(() -> {
                setLabelInstrucoes("Não é possível recuperar modelo base.");
                try {
                    Thread.sleep(1000);
                    setLabelInstrucoes(erro);
                } catch (InterruptedException ex) {
                    Logger.getLogger(IdentificacaoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        if (cancelar == false) {
            try {
                btnIdentifyActionPerformed();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            operacao.Dispose();
        }

    }

    //**Metodo que inicia o processo de identificaçã**
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    public void btnIdentifyActionPerformed() {

        if (users.isEmpty()) {
            Platform.runLater(() -> {
                setLabelInstrucoes("Usuario não encontrado! Por favor execute o cadastro dele.");
            });
            return;
        }

        try {
            if (operacao != null) {
                operacao.Dispose();
//                operacao = null;
            }
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
                setLabelInstrucoes("Não é possível iniciar a operação de inscrição.");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(IdentificacaoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                setLabelInstrucoes(e.getMessage());
            });

        }
    }

    //**Metodo que inicia o processo de captura da digital para cadastramento**
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("file:///C:\\Users\\CEDI\\Desktop\\Igor\\SGPB\\src\\sgpb\\imagem\\digitalImage.jpg", 160.0, 240.0, true, true);
        imageView.setImage(img);
    }

    public void setLabelInstrucoes(String texto) {
        try {
            labelInstrucoes.setText(texto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLabelEvento(String texto) {
        try {
            labelEvento.setText(texto);
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

    public void setEvento(Atividades evento) {
        this.evento = evento;
    }

}
