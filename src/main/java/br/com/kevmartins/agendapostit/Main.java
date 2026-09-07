package br.com.kevmartins.agendapostit;

import br.com.kevmartins.agendapostit.dominio.Agenda;
import br.com.kevmartins.agendapostit.menu.MenuConsole;
import br.com.kevmartins.agendapostit.persistencia.ConexaoBanco;
import br.com.kevmartins.agendapostit.persistencia.TarefaRepository;
import br.com.kevmartins.agendapostit.persistencia.TarefaRepositoryJdbc;

import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        String url = "jdbc:postgresql://localhost:5432/agenda";
        String usuario = dotenv.get("POSTGRES_USER");
        String senha = dotenv.get("POSTGRES_PASSWORD");

        if (usuario == null || senha == null) {
            System.out.println("Erro: credenciais do banco não encontradas no arquivo .env.");
            return;
        }

        TarefaRepository repositorio = new TarefaRepositoryJdbc(url, usuario, senha);
        Agenda agenda = new Agenda(repositorio);
        MenuConsole menu = new MenuConsole(agenda);

        menu.iniciar();
    }
}