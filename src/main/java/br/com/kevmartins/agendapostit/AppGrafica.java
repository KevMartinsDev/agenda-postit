package br.com.kevmartins.agendapostit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppGrafica extends Application {

    @Override
    public void start(Stage palco) throws IOException {
        FXMLLoader carregador = new FXMLLoader(
                getClass().getResource("/br/com/kevmartins/agendapostit/tela-teste.fxml")
        );
        Parent raiz = carregador.load();

        Scene cena = new Scene(raiz, 600, 400);

        palco.setTitle("Agenda Post-it");
        palco.setScene(cena);
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}