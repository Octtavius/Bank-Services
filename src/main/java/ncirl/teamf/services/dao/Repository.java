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
        System.out.println("Save to database");
    }

    @Override
    public void createAnotherAccount(Customer customer) {
        System.out.println("Createing another account");
    }
    
    @Override
    public double getBalance(String customerId) {
        double temp = 0.0;
        
        System.out.println("Get balance from " +customerId+ " and display");
        return temp;
    }

    @Override
    public void lodgment(String accNumber, double amount) {
        System.out.println("Lodge " +amount+ " money in the account: " + accNumber);
    }

    @Override
    public void transfer(String senderAccountNumber, double amount, String recieverAccountNumber, int sortCode) {
        System.out.println("Transfering from " + senderAccountNumber  + " $" + amount  + " to " + recieverAccountNumber);
    }

    @Override
    public void withdrawl(String accountNumber, double amount) {
        System.out.println("Withdraw " + amount + " from account: " + accountNumber);
    }

    @Override
    public boolean login(String accountId, String password) {
        System.out.println("Loging the users. Return true is successfully logged.");
        return false;
    }   
}
 