package br.com.kevmartins.agendapostit.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Agenda {
    private List<Tarefa> tarefas;

    public Agenda() {
        this.tarefas = new ArrayList<>();
    }

    public void adicionar(Tarefa tarefa) {
        tarefas.add(tarefa);
    }

    public List<Tarefa> listarPorDia(LocalDate data) {
        List<Tarefa> tarefasDoDia = tarefas.stream()
                .filter(t -> t.getData().equals(data))
                .sorted(Comparator.comparing(Tarefa::getHorario))
                .collect(Collectors.toList());

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
    }

    public void remover(LocalDate data, int numero) {
        Tarefa tarefa = buscarPorDiaNumero(data, numero);
        tarefas.remove(tarefa);
    }

    public List<Tarefa> obterTodas() {
        return new ArrayList<>(tarefas);
    }
}