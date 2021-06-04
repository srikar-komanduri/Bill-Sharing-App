package bill.share;
import Models.*;
import Services.*;
import Repositories.*;
import Exceptions.*;
import java.time.*;
import java.util.*;


/**
 *
 * @author Srikar
 */
public class BillShare {
    static UserService userService;
    static ExpenseService expenseService;
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
            throws ExpenseDoesNotExistException,UserDoesNotExistException {
        
        userService = new UserService();
        expenseService = new ExpenseService();
        
        createUser();
        // creates a hardcoded expense 
        Expense expense = createExpense(); 
        String expenseId = expense.getExpenseId();
        
        // now we will add users to the expense group with their shares
        // user who created the expense will not be added to group because he already paid his share.
        try{
            addTestUsers(expense);
        }
        catch(ExpenseDoesNotExistException e){
            System.out.println(e);
        }
        catch(UserDoesNotExistException e){
            System.out.println(e);
        }
        expense.setExpenseStatus(ExpenseStatus.PENDING);
        
        // now we will make users contribute/repay to their shares 
        Set<User> users = expense.getExpenseGroup().getGroupMembers();
        for(User currentUser:users){
            contributeToTestExpense(currentUser.getEmail(),expenseId);
        }
        
        // after everyone has contributed to the expense, we will check if it has been settled or not.
        if(expenseService.isExpenseSettled(expenseId)){
            System.out.println("Expense "+expense.getTitle()+" has been settled");
            expense.setExpenseStatus(ExpenseStatus.SETTLED);
        }
        
        //checks if there are any pending expenses in repository
        checkPendingExpenses();
        
        
        System.out.println("END OF PROGRAM");
    }
    
    private static void createUser(){
        System.out.println("*****CREATING NEW USER*****");
        System.out.println("Enter email : ");
        String email = sc.next();
        System.out.println("enter name : ");
        String name = sc.next();
        System.out.println("enter phone number : ");
        String phoneNumber = sc.next();
        
        User user1 = userService.createUser(email,name,phoneNumber);
        System.out.println("*****NEW USER CREATED SUCCESSFULLY*****");
    }
    
    private static Expense createExpense()throws UserDoesNotExistException {
        System.out.println("*****CREATING NEW EXPENSE******");
        System.out.println("Which user is creating the expense? enter their email id : ");
        String email = sc.next();        
        if(!UserRepository.userRepository.containsKey(email)){
            throw new UserDoesNotExistException("user who is trying to create expense doesn't exist");
        }
        
        User user = UserRepository.userRepository.get(email);
        String userId = user.getUserId();
        System.out.println("Enter total amount of expense : ");
        double expenseAmount = sc.nextDouble();
        System.out.println("Enter Expense title : ");
        String title = sc.next();
        System.out.println("Enter Expense description : ");
        String description = sc.next();        
        Expense expense = expenseService.createExpense(userId,expenseAmount,title,description,LocalDateTime.of(2020, Month.JUNE, 19, 12, 0));
        System.out.println("*****NEW EXPENSE CREATED SUCCESSFULLY*****");
        return expense;
    }
    
    private static void addTestUsers(Expense expense)
            throws ExpenseDoesNotExistException,UserDoesNotExistException {
        try{
            expenseService.addUserToExpense(expense.getExpenseId(), "ajay@gmail.com");
            expenseService.addUserToExpense(expense.getExpenseId(), "amit@gmail.com");
            expenseService.addUserToExpense(expense.getExpenseId(), "kamal@gmail.com");  
            
            expenseService.assignExpenseShare(expense.getExpenseId(), "ajay@gmail.com", 250.0);
            expenseService.assignExpenseShare(expense.getExpenseId(), "amit@gmail.com", 250.0);
            expenseService.assignExpenseShare(expense.getExpenseId(), "kamal@gmail.com", 250.0);
            
        }
        catch(ExpenseDoesNotExistException e){
            System.out.println(e);
        }
    }
    
    private static void contributeToTestExpense(String email,String expenseId)
            throws ExpenseDoesNotExistException,UserDoesNotExistException {
        double contributionValue = 10.0;
        LocalDateTime contributionDate = LocalDateTime.of(2020, Month.JUNE, 19, 15, 0);
        String transactionId = "T"+Instant.EPOCH;
        String transactionDescription = email + "has paid " + contributionValue;        
        Contribution contribution = new Contribution(contributionValue,contributionDate,transactionId,transactionDescription);
        Expense expense = ExpenseRepository.expenseRepository.get(expenseId);        
        userService.contributeToExpense(email, expenseId, contribution);
    }
    
    private static void checkPendingExpenses(){
        for(Map.Entry<String,Expense> entry : ExpenseRepository.expenseRepository.entrySet()){
            Expense currentExpense  = entry.getValue();
            if(currentExpense.getExpenseStatus()!=ExpenseStatus.SETTLED){
                System.out.println("The Expense "+currentExpense.getTitle()+" has not been settled yet!");
            }
        }
    }
    
}

