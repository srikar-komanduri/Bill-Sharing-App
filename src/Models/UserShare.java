package Models;

import java.util.*;
import lombok.*;
@Getter
@Setter
/**
 *
 * @author Srikar
 */
public class UserShare {
    private String userId;
    private double userShare;
    private List<Contribution>contributions; // list of contributions made to repay the userShare
    
    public UserShare(String userId,double userShare){
        this.userId = userId;
        this.userShare = userShare;
        this.contributions = new ArrayList<>();
    }
}
