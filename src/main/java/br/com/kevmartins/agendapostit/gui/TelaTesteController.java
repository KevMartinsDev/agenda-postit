package br.com.kevmartins.agendapostit.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TelaTesteController {

    @FXML
    private Label mensagem;

    @FXML
    private void aoClicar() {
        mensagem.setText("Botão clicado!");
    }
}