package br.com.kevmartins.agendapostit.persistencia;

public class RepositorioException extends RuntimeException {
    public RepositorioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}