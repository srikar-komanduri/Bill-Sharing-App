/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.UUID;

/**
 *
 * @author Srikar
 */
public class User {
    private String name;
    private String email;
    private String phoneNumber;
    private String userId;
    
    
    public User(String name,String email,String phoneNumber){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userId = UUID.randomUUID().toString();
        
    } 
    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public String getuserId(){
        return this.userId;
    }
    
}
