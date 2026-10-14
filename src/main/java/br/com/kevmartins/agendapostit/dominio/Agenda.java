package br.com.kevmartins.agendapostit.dominio;

import br.com.kevmartins.agendapostit.persistencia.TarefaRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import java.time.YearMonth;
import java.util.Set;
import java.util.stream.Collectors;

public class Agenda {

    private final TarefaRepository repositorio;

    public Agenda(TarefaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void adicionar(Tarefa tarefa) {
        repositorio.salvar(tarefa);
    }

    public List<Tarefa> listarPorDia(LocalDate data) {
        List<Tarefa> tarefasDoDia = repositorio.buscarTodas().stream()
                .filter(t -> t.getData().equals(data))
                .sorted(Comparator.comparing(Tarefa::getHorario))
                .toList();

        if (tarefasDoDia.isEmpty()) {
            throw new DiaSemTarefasException("Não existem tarefas para a data informada.");
        }

        return tarefasDoDia;
    }

    public Tarefa buscarPorDiaNumero(LocalDate data, int numero) {
        List<Tarefa> tarefasDoDia = listarPorDia(data);

        if (numero < 1 || numero > tarefasDoDia.size()) {
            throw new NumeroListaInexistenteException("Número de tarefa inválido: " + numero);
        }

        return tarefasDoDia.get(numero - 1);
    }

    public void concluir(LocalDate data, int numero) {
        Tarefa tarefa = buscarPorDiaNumero(data, numero);
        tarefa.concluir();
        repositorio.atualizar(tarefa);
    }

    public void atualizar(Tarefa tarefa) {
        repositorio.atualizar(tarefa);
    }

    public void remover(LocalDate data, int numero) {
        Tarefa tarefa = buscarPorDiaNumero(data, numero);
        repositorio.remover(tarefa.getId());
    }

    public Set<LocalDate> diasComTarefa(YearMonth mes) {
        return repositorio.buscarTodas().stream()
                .map(Tarefa::getData)
                .filter(data -> YearMonth.from(data).equals(mes))
                .collect(Collectors.toSet());
    }
}