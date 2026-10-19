package br.com.kevmartins.agendapostit;

import br.com.kevmartins.agendapostit.dominio.Agenda;
import br.com.kevmartins.agendapostit.gui.TelaCalendarioController;
import br.com.kevmartins.agendapostit.persistencia.TarefaRepository;
import br.com.kevmartins.agendapostit.persistencia.TarefaRepositoryJdbc;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class AppGrafica extends Application {

    @Override
    public void start(Stage palco) throws IOException {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String url = "jdbc:postgresql://localhost:5432/agenda";
        String usuario = dotenv.get("POSTGRES_USER");
        String senha = dotenv.get("POSTGRES_PASSWORD");

        TarefaRepository repositorio = new TarefaRepositoryJdbc(url, usuario, senha);
        Agenda agenda = new Agenda(repositorio);

        FXMLLoader carregador = new FXMLLoader(
                getClass().getResource("/br/com/kevmartins/agendapostit/tela-calendario.fxml")
        );
        Parent raiz = carregador.load();

        TelaCalendarioController controller = carregador.getController();
        controller.setAgenda(agenda);

        Scene cena = new Scene(raiz, 800, 480);
        cena.getStylesheets().add(
                getClass().getResource("/br/com/kevmartins/agendapostit/estilos.css").toExternalForm()
        );

        palco.getIcons().add(new Image(getClass().getResourceAsStream(
                "/br/com/kevmartins/agendapostit/imagens/app-icon.png")));

        palco.setTitle("Agenda Post-it");
        palco.setScene(cena);
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}