package Models;

import java.time.*;
import java.util.*;
import lombok.*;
/**
 *
 * @author Srikar
 */
@Getter
public class Contribution {
    private String contributionId;
    private double contributionValue;
    private LocalDateTime contributionDate;
    private String transactionId; 
    private String transactionDescription;
    
    public Contribution(double contributionValue,LocalDateTime contributionDate,String transactionId,String transactionDescription){
        this.contributionValue=contributionValue;
        this.contributionDate=contributionDate;
        this.transactionId=transactionId;
        this.transactionDescription=transactionDescription;
        this.contributionId = UUID.randomUUID().toString();
        
    }
}
