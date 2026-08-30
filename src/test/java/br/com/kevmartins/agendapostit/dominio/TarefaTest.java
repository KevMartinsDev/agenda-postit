package br.com.kevmartins.agendapostit.dominio;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class TarefaTest {

    @Test
    public void deveCriarTarefaComDadosValidos() {
        LocalDate amanha = LocalDate.now().plusDays(1);
        LocalTime horario = LocalTime.of(10, 30);

        Tarefa tarefa = new Tarefa("Estudar Java", amanha, horario, "Revisão OOP");

        assertEquals("Estudar Java", tarefa.getTitulo());
        assertEquals(amanha, tarefa.getData());
        assertEquals(horario, tarefa.getHorario());
        assertEquals("Revisão OOP", tarefa.getDescricao());
        assertFalse(tarefa.isConcluido());
    }

    @Test
    public void deveRecusarTituloVazio() {
        LocalDate amanha = LocalDate.now().plusDays(1);
        LocalTime horario = LocalTime.of(10, 30);

        assertThrows(TituloInvalidoException.class, () -> {
            new Tarefa("", amanha, horario);
        });
    }

    @Test
    public void deveRecusarTituloComApenasEspacos() {
        LocalDate amanha = LocalDate.now().plusDays(1);
        LocalTime horario = LocalTime.of(10, 30);

        assertThrows(TituloInvalidoException.class, () -> {
            new Tarefa("   ", amanha, horario);
        });
    }

    @Test
    public void deveRecusarDataNoPassado() {
        LocalDate ontem = LocalDate.now().minusDays(1);
        LocalTime horario = LocalTime.of(10, 30);

        assertThrows(DataNoPassadoException.class, () -> {
            new Tarefa("Tarefa atrasada", ontem, horario);
        });
    }

    @Test
    public void deveRecusarHorarioNoPassado() {
        LocalDate hoje = LocalDate.now();
        LocalTime umaHoraAtras = LocalTime.now().minusHours(1);

        assertThrows(DataNoPassadoException.class, () -> {
            new Tarefa("Tarefa atrasada", hoje, umaHoraAtras);
        });
    }

    @Test
    public void deveCriarTarefaSemDescricao() {
        LocalDate amanha = LocalDate.now().plusDays(1);
        LocalTime horario = LocalTime.of(10, 30);

        Tarefa tarefa = new Tarefa("Tarefa simples", amanha, horario);

        assertEquals("", tarefa.getDescricao());
    }

    @Test
    public void devePermitirAlterarTitulo() {
        LocalDate amanha = LocalDate.now().plusDays(1);
        LocalTime horario = LocalTime.of(10, 30);
        Tarefa tarefa = new Tarefa("Título antigo", amanha, horario);

        tarefa.setTitulo("Título novo");

        assertEquals("Título novo", tarefa.getTitulo());
    }

    @Test
    public void deveRecusarAlterarTituloParaVazio() {
        LocalDate amanha = LocalDate.now().plusDays(1);
        LocalTime horario = LocalTime.of(10, 30);
        Tarefa tarefa = new Tarefa("Título", amanha, horario);

        assertThrows(TituloInvalidoException.class, () -> {
            tarefa.setTitulo("");
        });
    }

    @Test
    public void devePermitirAlterarDescricao() {
        LocalDate amanha = LocalDate.now().plusDays(1);
        LocalTime horario = LocalTime.of(10, 30);
        Tarefa tarefa = new Tarefa("Tarefa", amanha, horario, "Descrição antiga");

        tarefa.setDescricao("Descrição nova");

        assertEquals("Descrição nova", tarefa.getDescricao());
    }

    @Test
    public void deveConcluirTarefa() {
        LocalDate amanha = LocalDate.now().plusDays(1);
        LocalTime horario = LocalTime.of(10, 30);
        Tarefa tarefa = new Tarefa("Tarefa", amanha, horario);

        assertFalse(tarefa.isConcluido());
        tarefa.concluir();
        assertTrue(tarefa.isConcluido());
    }

    @Test
    public void deveFormatarToStringCorretamente() {
        LocalDate amanha = LocalDate.now().plusDays(1);
        LocalTime horario = LocalTime.of(9, 5);
        Tarefa tarefa = new Tarefa("Comprar pão", amanha, horario);

        String esperado = "[ ] 09:05 - Comprar pão";
        assertEquals(esperado, tarefa.toString());

        tarefa.concluir();
        esperado = "[✓] 09:05 - Comprar pão";
        assertEquals(esperado, tarefa.toString());
    }

    @Test
    public void idDeveComecarNuloEPoderSerDefinido() {
        LocalDate amanha = LocalDate.now().plusDays(1);
        LocalTime horario = LocalTime.of(10, 30);
        Tarefa tarefa = new Tarefa("Tarefa", amanha, horario);

        assertNull(tarefa.getId());

        tarefa.setId(1L);
        assertEquals(1L, tarefa.getId());
    }
}