package br.com.kevmartins.agendapostit.dominio;

import br.com.kevmartins.agendapostit.persistencia.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendaTest {

    @Mock
    private TarefaRepository repositorio;

    private Agenda agenda;
    private LocalDate amanha;

    @BeforeEach
    public void setUp() {
        agenda = new Agenda(repositorio);
        amanha = LocalDate.now().plusDays(1);
    }

    @Test
    public void deveAdicionarTarefa() {
        Tarefa tarefa = new Tarefa("Estudar Java", amanha, LocalTime.of(10, 0));

        agenda.adicionar(tarefa);

        verify(repositorio).salvar(tarefa);
    }

    @Test
    public void deveListarTarefasDeUmDiaOrdenadas() {
        Tarefa t1 = new Tarefa("Primeira", amanha, LocalTime.of(14, 0));
        Tarefa t2 = new Tarefa("Segunda", amanha, LocalTime.of(10, 0));
        Tarefa t3 = new Tarefa("Terceira", amanha, LocalTime.of(12, 0));

        when(repositorio.buscarTodas()).thenReturn(List.of(t1, t2, t3));

        List<Tarefa> tarefasDoDia = agenda.listarPorDia(amanha);

        assertEquals(3, tarefasDoDia.size());
        assertEquals(t2, tarefasDoDia.get(0));
        assertEquals(t3, tarefasDoDia.get(1));
        assertEquals(t1, tarefasDoDia.get(2));
    }

    @Test
    public void deveRecusarListarDiaSemTarefas() {
        when(repositorio.buscarTodas()).thenReturn(List.of());

        assertThrows(DiaSemTarefasException.class, () -> {
            agenda.listarPorDia(amanha);
        });
    }

    @Test
    public void devePermitirConcluirTarefa() {
        Tarefa tarefa = new Tarefa("Tarefa", amanha, LocalTime.of(10, 0));
        when(repositorio.buscarTodas()).thenReturn(List.of(tarefa));

        agenda.concluir(amanha, 1);

        assertTrue(tarefa.isConcluido());
        verify(repositorio).atualizar(tarefa);
    }

    @Test
    public void deveRecusarConcluirComNumeroInvalido() {
        Tarefa tarefa = new Tarefa("Tarefa", amanha, LocalTime.of(10, 0));
        when(repositorio.buscarTodas()).thenReturn(List.of(tarefa));

        assertThrows(NumeroListaInexistenteException.class, () -> {
            agenda.concluir(amanha, 5);
        });
    }

    @Test
    public void devePermitirRemoverTarefa() {
        Tarefa tarefa = new Tarefa("Tarefa", amanha, LocalTime.of(10, 0));
        tarefa.setId(42L);
        when(repositorio.buscarTodas()).thenReturn(List.of(tarefa));

        agenda.remover(amanha, 1);

        verify(repositorio).remover(42L);
    }

    @Test
    public void deveRecusarRemoverComNumeroInvalido() {
        Tarefa tarefa = new Tarefa("Tarefa", amanha, LocalTime.of(10, 0));
        when(repositorio.buscarTodas()).thenReturn(List.of(tarefa));

        assertThrows(NumeroListaInexistenteException.class, () -> {
            agenda.remover(amanha, 3);
        });
    }

    @Test
    public void deveBuscarTarefaPorDiaNumero() {
        Tarefa t1 = new Tarefa("Primeira", amanha, LocalTime.of(9, 0));
        Tarefa t2 = new Tarefa("Segunda", amanha, LocalTime.of(11, 0));
        when(repositorio.buscarTodas()).thenReturn(List.of(t1, t2));

        Tarefa encontrada = agenda.buscarPorDiaNumero(amanha, 2);

        assertEquals(t2, encontrada);
    }
}