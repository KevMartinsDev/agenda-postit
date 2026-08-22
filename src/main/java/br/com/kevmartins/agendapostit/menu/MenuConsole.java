package br.com.kevmartins.agendapostit.menu;

import br.com.kevmartins.agendapostit.dominio.Agenda;
import br.com.kevmartins.agendapostit.dominio.DataNoPassadoException;
import br.com.kevmartins.agendapostit.dominio.DiaSemTarefasException;
import br.com.kevmartins.agendapostit.dominio.NumeroListaInexistenteException;
import br.com.kevmartins.agendapostit.dominio.Tarefa;
import br.com.kevmartins.agendapostit.dominio.TituloInvalidoException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuConsole {
    private final Agenda agenda;
    private final Scanner scanner;
    private final DateTimeFormatter formatadorData;
    private final DateTimeFormatter formatadorHora;

    public MenuConsole() {
        this.agenda = new Agenda();
        this.scanner = new Scanner(System.in);
        this.formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.formatadorHora = DateTimeFormatter.ofPattern("HH:mm");
    }

    public void iniciar() {
        boolean executando = true;

        while (executando) {
            exibirMenu();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1 -> adicionarTarefa();
                case 2 -> listarTarefas();
                case 3 -> concluirTarefa();
                case 4 -> removerTarefa();
                case 0 -> {
                    executando = false;
                    System.out.println("Encerrando a agenda. Até logo!");
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void exibirMenu() {
        System.out.println("\n===== AGENDA POST-IT =====");
        System.out.println("1 - Adicionar tarefa");
        System.out.println("2 - Listar tarefas do dia");
        System.out.println("3 - Concluir tarefa");
        System.out.println("4 - Remover tarefa");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void adicionarTarefa() {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        LocalDate data = lerData();
        LocalTime horario = lerHorario();

        System.out.print("Descrição (opcional): ");
        String descricao = scanner.nextLine();

        try {
            Tarefa tarefa = new Tarefa(titulo, data, horario, descricao);
            agenda.adicionar(tarefa);
            System.out.println("Tarefa adicionada com sucesso!");
        } catch (TituloInvalidoException | DataNoPassadoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarTarefas() {
        LocalDate data = lerData();

        try {
            List<Tarefa> tarefas = agenda.listarPorDia(data);
            System.out.println("\nTarefas do dia " + data.format(formatadorData) + ":");
            int numero = 1;
            for (Tarefa tarefa : tarefas) {
                System.out.println(numero + ". " + tarefa);
                numero++;
            }
        } catch (DiaSemTarefasException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void concluirTarefa() {
        LocalDate data = lerData();

        try {
            List<Tarefa> tarefas = agenda.listarPorDia(data);
            exibirTarefasNumeradas(tarefas, data);

            System.out.print("Número da tarefa a concluir: ");
            int numero = Integer.parseInt(scanner.nextLine().trim());

            agenda.concluir(data, numero);
            System.out.println("Tarefa concluída com sucesso!");
        } catch (DiaSemTarefasException | NumeroListaInexistenteException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro: digite um número válido.");
        }
    }

    private void removerTarefa() {
        LocalDate data = lerData();

        try {
            List<Tarefa> tarefas = agenda.listarPorDia(data);
            exibirTarefasNumeradas(tarefas, data);

            System.out.print("Número da tarefa a remover: ");
            int numero = Integer.parseInt(scanner.nextLine().trim());

            agenda.remover(data, numero);
            System.out.println("Tarefa removida com sucesso!");
        } catch (DiaSemTarefasException | NumeroListaInexistenteException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro: digite um número válido.");
        }
    }

    private void exibirTarefasNumeradas(List<Tarefa> tarefas, LocalDate data) {
        System.out.println("\nTarefas do dia " + data.format(formatadorData) + ":");
        int numero = 1;
        for (Tarefa tarefa : tarefas) {
            System.out.println(numero + ". " + tarefa);
            numero++;
        }
    }

    private LocalDate lerData() {
        while (true) {
            System.out.print("Data (dd/MM/yyyy): ");
            String entrada = scanner.nextLine().trim();
            try {
                return LocalDate.parse(entrada, formatadorData);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Use dd/MM/yyyy.");
            }
        }
    }

    private LocalTime lerHorario() {
        while (true) {
            System.out.print("Horário (HH:mm): ");
            String entrada = scanner.nextLine().trim();
            try {
                return LocalTime.parse(entrada, formatadorHora);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de horário inválido. Use HH:mm.");
            }
        }
    }
}