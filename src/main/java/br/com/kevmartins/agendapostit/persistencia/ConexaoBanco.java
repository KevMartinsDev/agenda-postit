package br.com.kevmartins.agendapostit.persistencia;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {

    private static final String URL_PADRAO = "jdbc:postgresql://localhost:5432/agenda";
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public static Connection obterConexao() throws SQLException {
        String usuario = dotenv.get("POSTGRES_USER");
        String senha = dotenv.get("POSTGRES_PASSWORD");

        if (usuario == null || senha == null) {
            throw new IllegalStateException(
                    "Variáveis POSTGRES_USER e POSTGRES_PASSWORD não encontradas no arquivo .env."
            );
        }

        return obterConexao(URL_PADRAO, usuario, senha);
    }

    public static Connection obterConexao(String url, String usuario, String senha) throws SQLException {
        return DriverManager.getConnection(url, usuario, senha);
    }
}