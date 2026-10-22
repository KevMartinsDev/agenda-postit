package br.com.kevmartins.agendapostit.gui;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VigiaLembretes {

    private final ScheduledExecutorService agendador;

    public VigiaLembretes() {
        this.agendador = Executors.newSingleThreadScheduledExecutor(tarefa -> {
            Thread thread = new Thread(tarefa, "vigia-lembretes");
            thread.setDaemon(true);
            return thread;
        });
    }

    public void iniciar() {
        agendador.scheduleAtFixedRate(this::verificar, 0, 30, TimeUnit.SECONDS);
    }

    private void verificar() {
        System.out.println("Vigia: verificando tarefas... " + java.time.LocalTime.now());
    }

    public void parar() {
        agendador.shutdownNow();
    }
}