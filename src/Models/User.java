/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.UUID;
import lombok.*;
@Getter

/**
 *
 * @author Srikar
 */
public class User {
    private String name;
    private String email;
    private String phoneNumber;
    private String userId;
    
    
    public User(String email,String name,String phoneNumber){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userId = UUID.randomUUID().toString();
        
    }     
    
}
