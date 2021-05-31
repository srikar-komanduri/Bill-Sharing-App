package Repositories;

import java.util.*;
import Models.Expense;
/**
 *
 * @author Srikar
 */
public class ExpenseRepository {
    // It is repository of all expenses. It stores expenseId : expense
    public static Map<String,Expense> expenseRepository = new HashMap<>();     
}
