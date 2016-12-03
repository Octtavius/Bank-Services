/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncirl.teamf.services.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import ncirl.teamf.services.models.Account;
import ncirl.teamf.services.models.Customer;
import ncirl.teamf.services.models.Transaction;

/**
 *
 * @author I323506
 */
public class Repository implements IRepository{
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bank");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();  

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
    public boolean withdraw(int accountNumber, double amount) {
        System.out.println("Withdraw " + amount + " from account: " + accountNumber);
        
        return false;
    }

    @Override
    public Customer login(int accountId, String password) {
        System.out.println("Loging the users. Return true is successfully logged.");
        
        Account tempAccount;
        
        tempAccount = em.find(Account.class, accountId);
        if(tempAccount != null){
            if(password.equals(tempAccount.getPassword())){
                return em.find(Customer.class, tempAccount.getCustomerId());
            }
        }
                
       /* Customer customer = new Customer();
        
        customer.setFirstName("Jim");
        customer.setMiddleName("Crazy");
        customer.setLastName("Button");
        customer.setAddress("Nowhere");
        customer.setEmail("sickemail@damn.com");
        customer.setContactNumber("66666");
        
        return customer; 
        */
       return null;
    }   

    @Override
    public List<Transaction> getTransactions(int userAccount) {
        List<Transaction> allTransactions = new ArrayList<>();
        Date d = new Date();
        Transaction t = new Transaction();
        t.setTransactionType("transfer");
        t.setPosBalance(100.00);
        t.setPreBalance(100.00);
        t.setTimeStamp(d.getTime());
        
        allTransactions.add(t);
        
        return allTransactions;
    }

    
}
 