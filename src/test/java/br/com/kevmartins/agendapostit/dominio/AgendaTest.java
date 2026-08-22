package br.com.kevmartins.agendapostit.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AgendaTest {
    private Agenda agenda;
    private LocalDate hoje;
    private LocalDate amanha;

    @BeforeEach
    public void setUp() {
        agenda = new Agenda();
        hoje = LocalDate.now();
        amanha = LocalDate.now().plusDays(1);
    }

    @Test
    public void deveAdicionarTarefa() {
        Tarefa tarefa = new Tarefa("Estudar Java", amanha, LocalTime.of(10, 0));

        agenda.adicionar(tarefa);

        assertTrue(agenda.obterTodas().contains(tarefa));
    }

    @Test
    public void deveListarTarefasDeUmDiaOrdenadas() {
        Tarefa t1 = new Tarefa("Primeira", amanha, LocalTime.of(14, 0));
        Tarefa t2 = new Tarefa("Segunda", amanha, LocalTime.of(10, 0));
        Tarefa t3 = new Tarefa("Terceira", amanha, LocalTime.of(12, 0));

        agenda.adicionar(t1);
        agenda.adicionar(t2);
        agenda.adicionar(t3);

        List<Tarefa> tarefasDoDia = agenda.listarPorDia(amanha);

        assertEquals(3, tarefasDoDia.size());
        assertEquals(t2, tarefasDoDia.get(0));
        assertEquals(t3, tarefasDoDia.get(1));
        assertEquals(t1, tarefasDoDia.get(2));
    }

    @Test
    public void deveRecusarListarDiaSemTarefas() {
        assertThrows(DiaSemTarefasException.class, () -> {
            agenda.listarPorDia(amanha);
        });
    }

    @Test
    public void devePermitirConcluirTarefa() {
        Tarefa tarefa = new Tarefa("Tarefa", amanha, LocalTime.of(10, 0));
        agenda.adicionar(tarefa);

        assertFalse(tarefa.isConcluido());
        agenda.concluir(amanha, 1);
        assertTrue(tarefa.isConcluido());
    }

    @Test
    public void deveRecusarConcluirComNumeroInvalido() {
        Tarefa tarefa = new Tarefa("Tarefa", amanha, LocalTime.of(10, 0));
        agenda.adicionar(tarefa);

        assertThrows(NumeroListaInexistenteException.class, () -> {
            agenda.concluir(amanha, 5);
        });
    }

    @Test
    public void devePermitirRemoverTarefa() {
        Tarefa tarefa = new Tarefa("Tarefa", amanha, LocalTime.of(10, 0));
        agenda.adicionar(tarefa);

        assertEquals(1, agenda.obterTodas().size());
        agenda.remover(amanha, 1);
        assertEquals(0, agenda.obterTodas().size());
    }

    @Test
    public void deveRecusarRemoverComNumeroInvalido() {
        Tarefa tarefa = new Tarefa("Tarefa", amanha, LocalTime.of(10, 0));
        agenda.adicionar(tarefa);

        assertThrows(NumeroListaInexistenteException.class, () -> {
            agenda.remover(amanha, 3);
        });
    }

    @Test
    public void deveRemoverApenasDoListaDodia() {
        Tarefa t1 = new Tarefa("Amanhã", amanha, LocalTime.of(10, 0));
        Tarefa t2 = new Tarefa("Depois", amanha.plusDays(1), LocalTime.of(10, 0));

        agenda.adicionar(t1);
        agenda.adicionar(t2);

        agenda.remover(amanha, 1);

        assertEquals(1, agenda.obterTodas().size());
        assertTrue(agenda.obterTodas().contains(t2));
    }

    @Test
    public void deveListarMultiplosDiasIndependentes() {
        Tarefa t1 = new Tarefa("Amanhã", amanha, LocalTime.of(10, 0));
        Tarefa t2 = new Tarefa("Depois", amanha.plusDays(1), LocalTime.of(10, 0));

        agenda.adicionar(t1);
        agenda.adicionar(t2);

        List<Tarefa> tarefasAmanha = agenda.listarPorDia(amanha);
        List<Tarefa> tarefasDepois = agenda.listarPorDia(amanha.plusDays(1));

        assertEquals(1, tarefasAmanha.size());
        assertEquals(1, tarefasDepois.size());
        assertEquals(t1, tarefasAmanha.get(0));
        assertEquals(t2, tarefasDepois.get(0));
    }
}