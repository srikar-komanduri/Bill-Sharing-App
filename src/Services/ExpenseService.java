package Services;

import Models.*;
import Exceptions.*;
import java.time.*;
import Repositories.*;
import java.util.Map;
/**
 *
 * @author Srikar
 */
public class ExpenseService {    
    public Expense createExpense(String userId,double expenseAmount,String title,String description,LocalDateTime expenseDate){
        // create new expense
        Expense expense1 = new Expense(userId,expenseAmount,title,description,expenseDate);  
        
        // add created expense to repository
        ExpenseRepository.expenseRepository.putIfAbsent(expense1.getExpenseId(),expense1);
        
        return expense1;
    }
    
    public void addUserToExpense(String expenseId,String email) 
            throws ExpenseDoesNotExistException,UserDoesNotExistException {
        if(!ExpenseRepository.expenseRepository.containsKey(expenseId)){
            throw new ExpenseDoesNotExistException("The Expense to which you are trying to add user does not exist");
        }
        if(!UserRepository.userRepository.containsKey(email)){
            throw new UserDoesNotExistException("User you are tring to add to expense does not exist");
        }
        ExpenseRepository.expenseRepository.get(expenseId).
                getExpenseGroup().getGroupMembers().add(UserRepository.userRepository.get(email));
        
    }
    
    public void assignExpenseShare(String expenseId,String email,double userShare)
            throws ExpenseDoesNotExistException , UserDoesNotExistException{
        if(!ExpenseRepository.expenseRepository.containsKey(expenseId)){
            throw new ExpenseDoesNotExistException("The Expense to which you are trying to assign share does not exist");
        }
        if(!UserRepository.userRepository.containsKey(email)){
            throw new UserDoesNotExistException("User you are tring to assign share to expense does not exist");
        }
        
        Expense expense  = ExpenseRepository.expenseRepository.get(expenseId);
        String userId = UserRepository.userRepository.get(email).getUserId();
        // now we assign how much user owes to the expense creator i.e userShare. 
        try{
            ExpenseGroup grp = expense.getExpenseGroup();
            grp.getUserContributions().putIfAbsent(email,new UserShare(userId,userShare));
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }
    
    
    public boolean isExpenseSettled(String expenseId){
        ExpenseGroup currentGroup = ExpenseRepository.expenseRepository.get(expenseId).getExpenseGroup();
        Map<String,UserShare>currentContributions = currentGroup.getUserContributions();
        for(Map.Entry<String,UserShare> entry:currentContributions.entrySet()){
            UserShare currentUserShare = entry.getValue();
            if(currentUserShare.getUserShare()!=0){
                return false;
            }
        }
        return true;
    }
    
    public void setExpenseStatus(String expenseId,ExpenseStatus expenseStatus)
            throws ExpenseDoesNotExistException{
        if(!ExpenseRepository.expenseRepository.containsKey(expenseId)){
            throw new ExpenseDoesNotExistException("The Expense to which you are trying to set status of, does not exist");
        }
        Expense expense  = ExpenseRepository.expenseRepository.get(expenseId);
        expense.setExpenseStatus(expenseStatus);        
    }
    
     
}
