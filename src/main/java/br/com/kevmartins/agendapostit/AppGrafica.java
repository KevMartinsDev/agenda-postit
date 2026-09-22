package br.com.kevmartins.agendapostit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AppGrafica extends Application {

    @Override
    public void start(Stage palco) {
        Label mensagem = new Label("Agenda Post-it — interface gráfica");

        StackPane raiz = new StackPane();
        raiz.getChildren().add(mensagem);

        Scene cena = new Scene(raiz, 600, 400);

        palco.setTitle("Agenda Post-it");
        palco.setScene(cena);
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}