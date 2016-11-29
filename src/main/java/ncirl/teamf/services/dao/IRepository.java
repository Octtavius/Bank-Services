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
    
    boolean login(int accountId, String password);
    
    void createAccount(Customer customer);
    
    boolean lodgment(int accNumber, double amound); 
    
    boolean transfer(int senderAccountNumber, double Amount, int recieverAccountNumber, int sortCode);
    
    public String checkRecipient(int accountId, int sortCode);
    
    void withdrawl(int accountNumber, double amount);
    
    double getBalance(int customerId);
}
