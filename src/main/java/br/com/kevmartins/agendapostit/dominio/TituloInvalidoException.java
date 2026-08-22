package br.com.kevmartins.agendapostit.dominio;

public class TituloInvalidoException extends RuntimeException {
    public TituloInvalidoException(String mensagem) {
        super(mensagem);
    }
}