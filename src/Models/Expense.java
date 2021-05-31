package Models;

import java.time.*;
import java.util.*;
import lombok.*;

/**
 *
 * @author Srikar
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Expense {
    private String expenseId;
    private String userId;
    private double expenseAmount;
    private LocalDateTime expenseDate;
    private String title,description;
    private ExpenseStatus expenseStatus;
    private ExpenseGroup expenseGroup;
    
    public Expense(String userId,double expenseAmount,String title,String description,LocalDateTime expenseDate){
        this.userId = userId;
        this.expenseAmount = expenseAmount;
        this.title = title;
        this.description = description;
        this.expenseDate = expenseDate;
        this.expenseId = UUID.randomUUID().toString();    
        this.expenseStatus = ExpenseStatus.CREATED;
        this.expenseGroup = new ExpenseGroup();
    }
       
    
    
}
