package io.github.biojj.exception;

public class EmailExistingException extends RuntimeException {
    public EmailExistingException(String email) {
        super("Cliente já cadastrado para o email " + email);
    }
}
