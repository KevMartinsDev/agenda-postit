package br.com.kevmartins.agendapostit.persistencia;

import br.com.kevmartins.agendapostit.dominio.Tarefa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class TarefaRepositoryJdbcTest {

    @Container
    private static final PostgreSQLContainer postgres =
            new PostgreSQLContainer("postgres:16")
                    .withDatabaseName("agenda_teste")
                    .withUsername("teste_user")
                    .withPassword("teste_pass");

    private TarefaRepositoryJdbc repository;

    @BeforeEach
    public void setUp() throws Exception {
        String url = postgres.getJdbcUrl();
        String usuario = postgres.getUsername();
        String senha = postgres.getPassword();

        try (Connection conexao = ConexaoBanco.obterConexao(url, usuario, senha);
             Statement stmt = conexao.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS tarefas");
            stmt.execute("""
                CREATE TABLE tarefas (
                    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    titulo VARCHAR(255) NOT NULL,
                    data DATE NOT NULL,
                    horario TIME NOT NULL,
                    descricao TEXT,
                    concluido BOOLEAN NOT NULL DEFAULT FALSE
                )
                """);
        }

        repository = new TarefaRepositoryJdbc(url, usuario, senha);
    }

    @Test
    public void deveSalvarTarefaEGerarId() {
        Tarefa tarefa = new Tarefa("Estudar JDBC", LocalDate.now().plusDays(1), LocalTime.of(10, 0));

        Tarefa salva = repository.salvar(tarefa);

        assertNotNull(salva.getId());
    }

    @Test
    public void deveBuscarTarefaSalva() {
        Tarefa tarefa = new Tarefa("Estudar Testcontainers", LocalDate.now().plusDays(1), LocalTime.of(14, 30), "Revisar isolamento");
        repository.salvar(tarefa);

        List<Tarefa> tarefas = repository.buscarTodas();

        assertEquals(1, tarefas.size());
        assertEquals("Estudar Testcontainers", tarefas.get(0).getTitulo());
        assertEquals("Revisar isolamento", tarefas.get(0).getDescricao());
    }

    @Test
    public void deveComecarComBancoVazio() {
        List<Tarefa> tarefas = repository.buscarTodas();

        assertTrue(tarefas.isEmpty());
    }
}