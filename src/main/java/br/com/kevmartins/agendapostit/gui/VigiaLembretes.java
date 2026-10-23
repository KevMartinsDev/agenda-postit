package br.com.kevmartins.agendapostit.gui;

import br.com.kevmartins.agendapostit.dominio.Agenda;
import br.com.kevmartins.agendapostit.dominio.DiaSemTarefasException;
import br.com.kevmartins.agendapostit.dominio.Tarefa;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VigiaLembretes {

    private final Agenda agenda;
    private final ScheduledExecutorService agendador;
    private final Set<String> avisosExibidos = new HashSet<>();

    private static final int MINUTOS_ANTES = 10;
    private static final long JANELA_SEGUNDOS = 90;

    public VigiaLembretes(Agenda agenda) {
        this.agenda = agenda;
        this.agendador = Executors.newSingleThreadScheduledExecutor(tarefa -> {
            Thread thread = new Thread(tarefa, "vigia-lembretes");
            thread.setDaemon(true);
            return thread;
        });
    }

    public void iniciar() {
        agendador.scheduleAtFixedRate(this::verificar, 0, 15, TimeUnit.SECONDS);
    }

    private void verificar() {
        try {
            List<Tarefa> tarefasDeHoje = agenda.listarPorDia(LocalDate.now());
            LocalDateTime agora = LocalDateTime.now();

            System.out.println("Vigia: checando " + tarefasDeHoje.size() + " tarefa(s) às " + agora.toLocalTime());

            for (Tarefa tarefa : tarefasDeHoje) {
                if (tarefa.isConcluido()) {
                    continue;
                }

                LocalDateTime momentoHorario = LocalDateTime.of(LocalDate.now(), tarefa.getHorario());
                LocalDateTime momentoAviso = momentoHorario.minusMinutes(MINUTOS_ANTES);

                verificarMomento(tarefa, "antes", momentoAviso, agora);
                verificarMomento(tarefa, "agora", momentoHorario, agora);
            }
        } catch (DiaSemTarefasException e) {
            System.out.println("Vigia: nenhuma tarefa hoje.");
        } catch (Exception e) {
            System.out.println("Vigia: erro ao verificar - " + e.getMessage());
        }
    }

    private void verificarMomento(Tarefa tarefa, String tipo, LocalDateTime alvo, LocalDateTime agora) {
        String chave = tarefa.getId() + "-" + tipo;

        if (avisosExibidos.contains(chave)) {
            return;
        }

        long segundosParaAlvo = Duration.between(agora, alvo).getSeconds();

        if (segundosParaAlvo <= 0 && segundosParaAlvo > -JANELA_SEGUNDOS) {
            avisosExibidos.add(chave);
            String texto = tipo.equals("antes") ? "Em " + MINUTOS_ANTES + " minutos" : "Agora";
            System.out.println(">>> DISPAROU [" + texto + "]: " + tarefa.getTitulo());
        }
    }

    public void parar() {
        agendador.shutdownNow();
    }
}