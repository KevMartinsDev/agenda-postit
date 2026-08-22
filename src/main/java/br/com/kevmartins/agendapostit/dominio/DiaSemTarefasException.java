package br.com.kevmartins.agendapostit.dominio;

public class DiaSemTarefasException extends RuntimeException {
    public DiaSemTarefasException(String mensagem) {
        super(mensagem);
    }
}