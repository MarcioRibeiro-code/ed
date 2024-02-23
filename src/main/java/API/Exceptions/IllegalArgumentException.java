package API.Exceptions;

/**
 * Excepção que é lançada quando um argumento é inválido.
 *  @author Márcio Ribeiro - 8200408
 *  @author Hugo Ribeiro - 8200441
 */
public class IllegalArgumentException extends RuntimeException{
    public IllegalArgumentException(String message) {
        super(message);
    }
}
