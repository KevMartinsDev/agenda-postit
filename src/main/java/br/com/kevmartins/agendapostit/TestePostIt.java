package br.com.kevmartins.agendapostit;

import br.com.kevmartins.agendapostit.dominio.Tarefa;
import br.com.kevmartins.agendapostit.gui.PostIt;
import javafx.application.Application;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class TestePostIt extends Application {
    @Override
    public void start(Stage palco) {
        Tarefa tarefa = new Tarefa("Reunião de equipe", LocalDate.now().plusDays(1),
                LocalTime.of(14, 30), "Sala 3, trazer o relatório");

        PostIt postit = new PostIt(tarefa, "Em 10 minutos");
        postit.exibir();
    }

    public static void main(String[] args) {
        launch(args);
    }
}