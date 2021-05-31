package Exceptions;

/**
 *
 * @author Srikar
 */
public class UserDoesNotExistException extends Exception{
    public UserDoesNotExistException(String message){
        super(message);
    }
}
