package io.github.biojj.exception;

public class UserExistingException extends RuntimeException{
    public UserExistingException( String login ){
        super("Usuário já cadastrado para o login " + login);
    }
}
