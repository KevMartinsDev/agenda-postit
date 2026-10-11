package br.com.kevmartins.agendapostit.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class TelaCalendarioController {

    @FXML
    private Label rotuloMes;

    @FXML
    private GridPane gradeDias;

    private YearMonth mesAtual;

    @FXML
    private void initialize() {
        mesAtual = YearMonth.now();
        desenharCalendario();
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
            Button botaoDia = new Button(String.valueOf(dia));
            botaoDia.setPrefSize(60, 50);

            gradeDias.add(botaoDia, coluna, linha);

            coluna++;
            if (coluna > 6) {
                coluna = 0;
                linha++;
            }
        }
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
}