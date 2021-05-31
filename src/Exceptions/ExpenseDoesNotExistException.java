package Exceptions;

/**
 *
 * @author Srikar
 */
public class ExpenseDoesNotExistException extends Exception {
    public ExpenseDoesNotExistException(String message){
        super(message);
    }
    
}
