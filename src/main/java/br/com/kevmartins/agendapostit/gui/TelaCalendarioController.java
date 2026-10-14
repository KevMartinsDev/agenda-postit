package br.com.kevmartins.agendapostit.gui;

import br.com.kevmartins.agendapostit.dominio.Agenda;
import br.com.kevmartins.agendapostit.dominio.DiaSemTarefasException;
import br.com.kevmartins.agendapostit.dominio.Tarefa;
import br.com.kevmartins.agendapostit.persistencia.RepositorioException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class TelaCalendarioController {

    @FXML
    private Label rotuloMes;

    @FXML
    private GridPane gradeDias;

    @FXML
    private Label rotuloDiaSelecionado;

    @FXML
    private ListView<Tarefa> listaTarefas;

    private YearMonth mesAtual;
    private Agenda agenda;
    private LocalDate dataSelecionada;

    private final DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private void initialize() {
        mesAtual = YearMonth.now();
        desenharCalendario();
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    private void desenharCalendario() {
        gradeDias.getChildren().clear();
        atualizarRotulo();
        desenharCabecalhoDias();
        desenharDiasDoMes();
    }

    private void atualizarRotulo() {
        String nomeMes = mesAtual.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
        nomeMes = nomeMes.substring(0, 1).toUpperCase() + nomeMes.substring(1);
        rotuloMes.setText(nomeMes + " " + mesAtual.getYear());
    }

    private void desenharCabecalhoDias() {
        String[] diasSemana = {"Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"};
        for (int coluna = 0; coluna < 7; coluna++) {
            Label cabecalho = new Label(diasSemana[coluna]);
            gradeDias.add(cabecalho, coluna, 0);
        }
    }

    private void desenharDiasDoMes() {
        int diasNoMes = mesAtual.lengthOfMonth();
        LocalDate primeiroDia = mesAtual.atDay(1);
        int colunaInicial = primeiroDia.getDayOfWeek().getValue() % 7;

        int linha = 1;
        int coluna = colunaInicial;

        for (int dia = 1; dia <= diasNoMes; dia++) {
            LocalDate dataDoDia = mesAtual.atDay(dia);

            Button botaoDia = new Button(String.valueOf(dia));
            botaoDia.setPrefSize(60, 50);
            botaoDia.setOnAction(evento -> aoClicarDia(dataDoDia));

            gradeDias.add(botaoDia, coluna, linha);

            coluna++;
            if (coluna > 6) {
                coluna = 0;
                linha++;
            }
        }
    }

    private void aoClicarDia(LocalDate data) {
        dataSelecionada = data;
        rotuloDiaSelecionado.setText("Tarefas de " + data.format(formatadorData));

        try {
            List<Tarefa> tarefasDoDia = agenda.listarPorDia(data);
            ObservableList<Tarefa> itens = FXCollections.observableArrayList(tarefasDoDia);
            listaTarefas.setItems(itens);
        } catch (DiaSemTarefasException e) {
            listaTarefas.setItems(FXCollections.observableArrayList());
        } catch (RepositorioException e) {
            mostrarErro("Erro de acesso ao banco de dados. Verifique se o banco está em execução.");
        }
    }

    private void mostrarErro(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    private void mesAnterior() {
        mesAtual = mesAtual.minusMonths(1);
        desenharCalendario();
    }

    @FXML
    private void mesSeguinte() {
        mesAtual = mesAtual.plusMonths(1);
        desenharCalendario();
    }

    @FXML
    private void aoClicarNovaTarefa() {
        try {
            FXMLLoader carregador = new FXMLLoader(
                    getClass().getResource("/br/com/kevmartins/agendapostit/tela-formulario.fxml")
            );
            Parent raiz = carregador.load();

            TelaFormularioController controllerForm = carregador.getController();
            controllerForm.setAgenda(agenda);

            Stage janelaForm = new Stage();
            janelaForm.setTitle("Nova tarefa");
            janelaForm.setScene(new Scene(raiz));
            janelaForm.initModality(Modality.APPLICATION_MODAL);
            janelaForm.showAndWait();

            if (controllerForm.isSalvou() && dataSelecionada != null) {
                aoClicarDia(dataSelecionada);
            }
        } catch (IOException e) {
            mostrarErro("Não foi possível abrir o formulário.");
        }
    }
}