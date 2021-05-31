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
    public static void main(String[] args)
            throws ExpenseDoesNotExistException,UserDoesNotExistException {
        userService = new UserService();
        expenseService = new ExpenseService();
        // creates hardocded 20 users
        createTestUsers();
        // creates a hardcoded expense 
        Expense expense = createTestExpense(); 
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
    
    private static void createTestUsers(){
        User user1 = userService.createUser("bagesh@gmail.com", "bagesh", "3486199635");
        User user2 = userService.createUser("ajay@gmail.com", "ajay", "6112482630");
        User user3 = userService.createUser("amit@gmail.com", "amit", "2509699232");
        User user4 = userService.createUser("kamal@gmail.com", "kamal", "5816355154");
        User user5 = userService.createUser("neha@gmail.com", "neha", "7737316054");
        User user6 = userService.createUser("kajal@gmail.com", "kajal", "4813053349");
        User user7 = userService.createUser("jyothi@gmail.com", "jyothi", "3974178644");
        User user8 = userService.createUser("subin@gmail.com", "subin", "4768463294");
        User user9 = userService.createUser("deepak@gmail.com", "deepak", "4829338803");
        User user10 = userService.createUser("vishnu@gmail.com", "vishnu", "3384071602");
        User user11 = userService.createUser("mayank@gmail.com", "mayank", "2376951206");
        User user12 = userService.createUser("anu@gmail.com", "anu", "8478577491");
        User user13 = userService.createUser("kavya@gmail.com", "kavya", "7505888698");
        User user14 = userService.createUser("divya@gmail.com", "divya", "9587030077");
        User user15 = userService.createUser("prabhu@gmail.com", "prabhu", "3303167757");
        User user16 = userService.createUser("sangeeth@gmail.com", "sangeeth", "7409081739");
        User user17 = userService.createUser("rajesh@gmail.com", "rajesh", "2367659285");
        User user18 = userService.createUser("alamelu@gmail.com", "alamelu", "8938025834");
        User user19 = userService.createUser("aruna@gmail.com", "aruna", "8189506064");
        User user20 = userService.createUser("palani@gmail.com", "palani", "2973733105");
    }
    
    private static Expense createTestExpense(){
        String email = "bagesh@gmail.com";
        String user_id = UserRepository.userRepository.get(email).getUserId();
        Expense expense = expenseService.createExpense(user_id,1000.0,"Lunch expense","we all had lunch in Tarama",LocalDateTime.of(2020, Month.JUNE, 19, 12, 0));
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

