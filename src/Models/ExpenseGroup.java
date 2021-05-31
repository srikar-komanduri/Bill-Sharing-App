package Models;

import java.util.*;
import lombok.*;

/**
 *
 * @author Srikar
 */
@Getter
@Setter
@Builder
@AllArgsConstructor

public class ExpenseGroup {
    private String expenseGroupId;
    private Set<User>groupMembers;
    private Map<String,UserShare>userContributions; // email : usershare
    
    public ExpenseGroup(){
        this.expenseGroupId = UUID.randomUUID().toString();
        this.groupMembers = new HashSet();
        this.userContributions = new HashMap();
    }
    
    
}
