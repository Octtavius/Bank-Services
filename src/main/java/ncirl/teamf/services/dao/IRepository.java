/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncirl.teamf.services.dao;

import java.util.List;
import ncirl.teamf.services.models.Customer;
import ncirl.teamf.services.models.Transaction;

/**
 *
 * @author I323506
 */
public interface IRepository {
    
    Customer login(int accountId, String password);
    
    Customer createAccount(Customer customer);
    
    boolean lodgment(int accNumber, double amound); 
    
    boolean transfer(int senderAccountNumber, double Amount, int recieverAccountNumber, int sortCode);
    
    public String checkRecipient(int accountId, int sortCode);
    
    boolean withdraw(int accountNumber, double amount);
    
    double getBalance(int customerId);
    
    List<Transaction> getTransactions(int userAccount);
}
