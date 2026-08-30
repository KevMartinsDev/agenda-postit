package br.com.kevmartins.agendapostit.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Tarefa {
    private String titulo;
    private LocalDate data;
    private String descricao;
    private LocalTime horario;
    private boolean concluido;
    private Long id;

    public Tarefa(String titulo, LocalDate data, LocalTime horario) {
        this(titulo, data, horario, "");
    }

    public Tarefa(String titulo, LocalDate data, LocalTime horario, String descricao) {
        validarTitulo(titulo);
        validarDataHorario(data, horario);

        this.titulo = titulo;
        this.data = data;
        this.horario = horario;
        this.descricao = descricao != null ? descricao : "";
        this.concluido = false;
    }

    private void validarTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new TituloInvalidoException("Título não pode estar vazio ou conter apenas espaços");
        }
    }

    private void validarDataHorario(LocalDate data, LocalTime horario) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime tarefaMomento = LocalDateTime.of(data, horario);

        if (tarefaMomento.isBefore(agora)) {
            throw new DataNoPassadoException("Data e horário não podem estar no passado");
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDate getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setTitulo(String novoTitulo) {
        validarTitulo(novoTitulo);
        this.titulo = novoTitulo;
    }

    public void setData(LocalDate novaData) {
        validarDataHorario(novaData, this.horario);
        this.data = novaData;
    }

    public void setHorario(LocalTime novoHorario) {
        validarDataHorario(this.data, novoHorario);
        this.horario = novoHorario;
    }

    public void setDescricao(String novaDescricao) {
        this.descricao = novaDescricao != null ? novaDescricao : "";
    }

    public void concluir() {
        this.concluido = true;
    }

    @Override
    public String toString() {
        String marca = concluido ? "[✓]" : "[ ]";
        return String.format("%s %02d:%02d - %s", marca, horario.getHour(), horario.getMinute(), titulo);
    }

    public String detalhes() {
        String status = concluido ? "Concluída" : "Pendente";
        String desc = descricao.isBlank() ? "(sem descrição)" : descricao;

        return String.format(
                "Título: %s%nData: %02d/%02d/%d%nHorário: %02d:%02d%nDescrição: %s%nStatus: %s",
                titulo,
                data.getDayOfMonth(), data.getMonthValue(), data.getYear(),
                horario.getHour(), horario.getMinute(),
                desc,
                status
        );
    }
}