package br.com.kevmartins.agendapostit.gui;

import br.com.kevmartins.agendapostit.dominio.Agenda;
import br.com.kevmartins.agendapostit.dominio.DataNoPassadoException;
import br.com.kevmartins.agendapostit.dominio.Tarefa;
import br.com.kevmartins.agendapostit.dominio.TituloInvalidoException;
import br.com.kevmartins.agendapostit.persistencia.RepositorioException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TelaFormularioController {

    @FXML
    private TextField campoTitulo;

    @FXML
    private DatePicker campoData;

    @FXML
    private TextField campoHorario;

    @FXML
    private TextArea campoDescricao;

    private Agenda agenda;
    private boolean salvou = false;

    private final DateTimeFormatter formatadorHora = DateTimeFormatter.ofPattern("HH:mm");

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public boolean isSalvou() {
        return salvou;
    }

    @FXML
    private void aoSalvar() {
        String titulo = campoTitulo.getText();
        LocalDate data = campoData.getValue();
        String textoHorario = campoHorario.getText();
        String descricao = campoDescricao.getText();

        if (data == null) {
            mostrarErro("Escolha uma data.");
            return;
        }

        LocalTime horario;
        try {
            horario = LocalTime.parse(textoHorario.trim(), formatadorHora);
        } catch (DateTimeParseException e) {
            mostrarErro("Horário inválido. Use o formato HH:mm (ex: 14:30).");
            return;
        }

        try {
            Tarefa tarefa = new Tarefa(titulo, data, horario, descricao);
            agenda.adicionar(tarefa);
            salvou = true;
            fecharJanela();
        } catch (TituloInvalidoException | DataNoPassadoException e) {
            mostrarErro(e.getMessage());
        } catch (RepositorioException e) {
            mostrarErro("Erro de acesso ao banco de dados. Verifique se o banco está em execução.");
        }
    }

    @FXML
    private void aoCancelar() {
        fecharJanela();
    }

    private void fecharJanela() {
        Stage janela = (Stage) campoTitulo.getScene().getWindow();
        janela.close();
    }

    private void mostrarErro(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}