package br.com.kevmartins.agendapostit.dominio;

public class NumeroListaInexistenteException extends RuntimeException {
    public NumeroListaInexistenteException(String mensagem) {
        super(mensagem);
    }
}