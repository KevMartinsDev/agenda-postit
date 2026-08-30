package br.com.kevmartins.agendapostit.persistencia;

import br.com.kevmartins.agendapostit.dominio.Tarefa;

import java.util.List;

public interface TarefaRepository {

    Tarefa salvar(Tarefa tarefa);

    List<Tarefa> buscarTodas();

    void atualizar(Tarefa tarefa);

    void remover(Long id);
}