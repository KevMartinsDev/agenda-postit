package br.com.kevmartins.agendapostit.persistencia;

import br.com.kevmartins.agendapostit.dominio.Tarefa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TarefaRepositoryJdbc implements TarefaRepository {

    private final String url;
    private final String usuario;
    private final String senha;

    public TarefaRepositoryJdbc(String url, String usuario, String senha) {
        this.url = url;
        this.usuario = usuario;
        this.senha = senha;
    }

    private Connection abrirConexao() throws SQLException {
        return ConexaoBanco.obterConexao(url, usuario, senha);
    }

    @Override
    public Tarefa salvar(Tarefa tarefa) {
        String sql = "INSERT INTO tarefas (titulo, data, horario, descricao, concluido) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tarefa.getTitulo());
            stmt.setObject(2, tarefa.getData());
            stmt.setObject(3, tarefa.getHorario());
            stmt.setString(4, tarefa.getDescricao());
            stmt.setBoolean(5, tarefa.isConcluido());

            stmt.executeUpdate();

            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    tarefa.setId(chaves.getLong(1));
                }
            }

            return tarefa;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar tarefa no banco.", e);
        }
    }

    @Override
    public List<Tarefa> buscarTodas() {
        String sql = "SELECT id, titulo, data, horario, descricao, concluido FROM tarefas";
        List<Tarefa> tarefas = new ArrayList<>();

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Tarefa tarefa = new Tarefa(
                        rs.getString("titulo"),
                        rs.getObject("data", LocalDate.class),
                        rs.getObject("horario", LocalTime.class),
                        rs.getString("descricao")
                );
                tarefa.setId(rs.getLong("id"));

                if (rs.getBoolean("concluido")) {
                    tarefa.concluir();
                }

                tarefas.add(tarefa);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tarefas no banco.", e);
        }

        return tarefas;
    }

    @Override
    public void atualizar(Tarefa tarefa) {
    }

    @Override
    public void remover(Long id) {
    }
}