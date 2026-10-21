package br.com.kevmartins.agendapostit.gui;

import br.com.kevmartins.agendapostit.dominio.Tarefa;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.format.DateTimeFormatter;

public class PostIt {

    private final Stage janela;
    private static final DateTimeFormatter FORMATADOR_HORA = DateTimeFormatter.ofPattern("HH:mm");

    public PostIt(Tarefa tarefa, String aviso) {
        janela = new Stage();
        janela.initStyle(StageStyle.TRANSPARENT);
        janela.setAlwaysOnTop(true);

        Label rotuloAviso = new Label(aviso);
        rotuloAviso.getStyleClass().add("postit-aviso");

        Label titulo = new Label(tarefa.getTitulo());
        titulo.getStyleClass().add("postit-titulo");

        Label horario = new Label(tarefa.getHorario().format(FORMATADOR_HORA));
        horario.getStyleClass().add("postit-horario");

        Button botaoFechar = new Button("x");
        botaoFechar.getStyleClass().add("postit-fechar");
        botaoFechar.setOnAction(e -> janela.close());

        HBox cabecalho = new HBox(rotuloAviso);
        HBox.setHgrow(rotuloAviso, javafx.scene.layout.Priority.ALWAYS);
        Region espaco = new Region();
        HBox.setHgrow(espaco, javafx.scene.layout.Priority.ALWAYS);
        cabecalho.getChildren().addAll(espaco, botaoFechar);
        cabecalho.setAlignment(Pos.CENTER_LEFT);

        VBox conteudo = new VBox(8, cabecalho, horario, titulo);

        if (tarefa.getDescricao() != null && !tarefa.getDescricao().isBlank()) {
            Label descricao = new Label(tarefa.getDescricao());
            descricao.getStyleClass().add("postit-descricao");
            descricao.setWrapText(true);
            conteudo.getChildren().add(descricao);
        }

        conteudo.getStyleClass().add("postit-conteudo");

        Scene cena = new Scene(conteudo, 280, 150);
        cena.setFill(Color.TRANSPARENT);
        cena.getStylesheets().add(
                getClass().getResource("/br/com/kevmartins/agendapostit/estilos.css").toExternalForm()
        );

        janela.setScene(cena);
    }

    public void exibir() {
        janela.show();
    }

    public Stage getJanela() {
        return janela;
    }
}
