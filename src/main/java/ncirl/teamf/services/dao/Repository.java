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
public class Repository implements IRepository{

    @Override
    public void createAccount(Customer customer) {
        //GENERATE ID FOR CUSTOMER. 
        //ASSIGN THE NEW ID TOT HE CUSTOMER ID AS IT IS NULL NOW.
        System.out.println("Save to database: ");
    }
    
    @Override
    public double getBalance(int customerId) {
        double temp = -1;
        
        System.out.println("Get balance from " +customerId+ " and display");
        return temp;
    }

    @Override
    public boolean lodgment(int accNumber, double amount) {
        System.out.println("Lodge " +amount+ " money in the account: " + accNumber);
        boolean lodgementSuccessful = false;
        return lodgementSuccessful;
    }

    @Override
    public String checkRecipient(int accountId, int sortCode) {
        System.out.println("Searching for user...");
        
        String recipientName = "Jim Button";
        
        //here we return Customer name or null. if null then user not found
        
        return recipientName;
    }
    
    @Override
    public boolean transfer(int senderAccountNumber, double amount, int recieverAccountNumber, int sortCode) {
        System.out.println("Transfering from " + senderAccountNumber  + " $" + amount  + " to " + recieverAccountNumber);
        
        //return success message
        Customer recipient = new Customer();
        
        return false;
    }

    @Override
    public void withdrawl(int accountNumber, double amount) {
        System.out.println("Withdraw " + amount + " from account: " + accountNumber);
    }

    @Override
    public boolean login(int accountId, String password) {
        System.out.println("Loging the users. Return true is successfully logged.");
        return false;
    }   

    
}
 