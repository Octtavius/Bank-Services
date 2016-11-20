/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncirl.teamf.services.dao;

import ncirl.teamf.services.models.Customer;

/**
 *
 * @author I323506
 */
public interface IRepository {
    
    boolean login(String accountId, String password);
    
    void createAccount(Customer customer);
    
    void createAnotherAccount(Customer customer);
    
    void lodgment(String accNumber, double amound); 
    
    void transfer(String senderAccountNumber, double Amount, String recieverAccountNumber, int sortCode);
    
    void withdrawl(String accountNumber, double amount);
    
    double getBalance(String customerId);
}
