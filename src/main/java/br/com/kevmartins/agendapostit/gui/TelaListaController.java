package br.com.kevmartins.agendapostit.gui;

import br.com.kevmartins.agendapostit.dominio.Tarefa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TelaListaController {

    @FXML
    private DatePicker seletorData;

    @FXML
    private ListView<Tarefa> listaTarefas;

    @FXML
    private void aoMudarData() {
        LocalDate dataEscolhida = seletorData.getValue();

        if (dataEscolhida == null) {
            return;
        }

        List<Tarefa> tarefasDoDia = buscarTarefasFicticias(dataEscolhida);

        ObservableList<Tarefa> itens = FXCollections.observableArrayList(tarefasDoDia);
        listaTarefas.setItems(itens);
    }

    private List<Tarefa> buscarTarefasFicticias(LocalDate data) {
        Tarefa t1 = new Tarefa("Reunião de equipe", data, LocalTime.of(10, 0));
        Tarefa t2 = new Tarefa("Almoço com cliente", data, LocalTime.of(12, 30));
        Tarefa t3 = new Tarefa("Revisar relatório", data, LocalTime.of(15, 0), "Trimestre fechado");

        return List.of(t1, t2, t3);
    }
}