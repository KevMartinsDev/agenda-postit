package br.com.kevmartins.agendapostit.dominio;

public class DataNoPassadoException extends RuntimeException {
    public DataNoPassadoException(String mensagem) {
        super(mensagem);
    }
}