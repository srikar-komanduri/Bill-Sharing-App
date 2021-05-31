package Services;

import Exceptions.ExpenseDoesNotExistException;
import Exceptions.*;
import Models.*;
import Repositories.*;
/**
 *
 * @author Srikar
 */
public class UserService {
    public static User createUser(String email,String name,String phoneNumber){
        User user1 = new User(email,name,phoneNumber);
        UserRepository.userRepository.putIfAbsent(user1.getEmail(),user1);
        return user1;
    }
    
    public  void contributeToExpense(String email,String expenseId,Contribution contribution) 
            throws ExpenseDoesNotExistException , UserDoesNotExistException{
        if(!ExpenseRepository.expenseRepository.containsKey(expenseId)){
            throw new ExpenseDoesNotExistException("The Expense to which you are trying to contribute does not exist");
        }
        if(!UserRepository.userRepository.containsKey(email)){
            throw new UserDoesNotExistException("User who is trying to contribute to expense does not exist");
        }
        
        
        double currentContribution = contribution.getContributionValue();
        try{
            UserShare userShare = ExpenseRepository.expenseRepository.get(expenseId).
                getExpenseGroup().getUserContributions().get(email);
            
            double userShareValue = userShare.getUserShare();
            // if current contribution exceeds usershare, we will deduct share and complete transaction
            if(currentContribution>=userShareValue){
                userShare.setUserShare(0.0);

            }
            else{
                userShareValue-=currentContribution;
                userShare.setUserShare(userShareValue);
            }
            userShare.getContributions().add(contribution);
            

        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    
}
