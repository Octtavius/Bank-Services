/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncirl.teamf.services.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
    public Customer createAccount(Customer customer) {
        //GENERATE ID FOR CUSTOMER. 
        //ASSIGN THE NEW ID TOT HE CUSTOMER ID AS IT IS NULL NOW.
        tx.begin();
        em.persist(customer);
        tx.commit();
        System.out.println("Save to database: ");
        return customer;
    }
    
    @Override
    public double getBalance(int accountId) {
              
        System.out.println("Get balance from " +accountId+ " and display");
        Account temp = em.find(Account.class, accountId);
        return temp.getBalance();
    }

    @Override
    public boolean lodgment(int accountId, double amount) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Account tempAcc = em.find(Account.class, accountId);
        double preBalance = tempAcc.getBalance();
     
        Transaction trans = new Transaction();
        trans.setAccountId(accountId);
        trans.setPreBalance(preBalance);
        trans.setTransactionType("Logdment");
        trans.setTransactionDescription("");
        trans.setTransactionAmount(amount);
        trans.setTimeStamp(timestamp.getTime());
        trans.setPosBalance(preBalance + amount);
        tx.begin();
        em.persist(trans);
        tempAcc.setBalance(preBalance + amount);
        em.persist(trans); // added on 17/12
        tx.commit();
        
        System.out.println("Lodge " +amount+ " money in the account: " + accountId);
        
        return true;
    }

    @Override
    public String checkRecipient(int accountId) {
        String fullName = null;

       
       try{
           Account tempAcc = em.find(Account.class, accountId);
           int userID = tempAcc.getCustomerId();
           
           Customer tempCustomer = em.find(Customer.class, userID);
           fullName = tempCustomer.getFirstName() + " " + tempCustomer.getMiddleName() + " " + tempCustomer.getLastName();
       }catch(NullPointerException n) {
           System.out.println("THIS IS AN ERROR" );
           System.out.println(n);
       }
       
       return fullName;

    }
    
    @Override
    public boolean transfer(int senderAccountNumber, double amount, int receiverAccountNumber, int sortCode) {
        System.out.println("Transfering from " + senderAccountNumber  + " $" + amount  + " to " + receiverAccountNumber);
        
        Account senderAccount = em.find(Account.class, senderAccountNumber);
        double preBalance = senderAccount.getBalance();
        senderAccount.setBalance(preBalance - amount);
        //double posBalance = senderAccount.getBalance();
        
        Account receiverAccount = em.find(Account.class, receiverAccountNumber);
        double preBalance2 = receiverAccount.getBalance();
        receiverAccount.setBalance(preBalance2 + amount);
        //double posBalance2 = receiverAccount.getBalance();
        
        //sender
        Transaction trans = new Transaction();
        trans.setAccountId(senderAccountNumber);
        trans.setPreBalance(preBalance);
        trans.setTransactionType("Transfer");
        trans.setTransactionDescription("");
        trans.setTransactionAmount(amount);
        trans.setTimeStamp(1234);
        trans.setPosBalance(preBalance - amount);    
        
        //reciever
        Transaction trans2 = new Transaction();
        trans.setAccountId(receiverAccountNumber);
        trans.setPreBalance(preBalance2);
        trans.setTransactionType("Transfer");
        trans.setTransactionDescription("");
        trans.setTransactionAmount(amount);
        trans.setTimeStamp(1234);
        trans.setPosBalance(preBalance + amount); 
                       
        tx.begin();        
        em.persist(trans);                 
        tx.commit();
        
        System.out.println("Transfer amount: " +amount+ " from account: " + senderAccount + "to account: " + receiverAccount);
        //System.out.println("Sender account balance: " + posBalance + ", receiver account balance: "+ posBalance2 + ".");
        return true;
        
        
//        //return success message
//        Customer recipient = new Customer();
//        
//        return false;
    }

    @Override
    public boolean withdraw(int accountNumber, double amount) {
        boolean result = false;
        try{
            Account tempAcc = em.find(Account.class, accountNumber);
            double preBalance = tempAcc.getBalance();

            tempAcc.setBalance(preBalance - amount);
            
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            
            Transaction trans = new Transaction();
            trans.setAccountId(accountNumber);
            trans.setPreBalance(preBalance);
            trans.setTransactionType("Withdraw");
            trans.setTransactionDescription("");
            trans.setTransactionAmount(amount);
            trans.setTimeStamp(timestamp.getTime());
            trans.setPosBalance(preBalance - amount);
            
            tx.begin();
            em.persist(trans);
            tx.commit();
            
            result = true;
            
        }catch(NullPointerException ne) {
            System.out.println("NULL POINTER EXCEPTION\n" + ne);
            result = false;
        }
        
        return result;
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
    public List<Transaction> getTransactions(int accountId) {
        List<Transaction> allTransactions = em.createQuery(" FROM Transaction t WHERE t.accountId = :accountId order by t.timeStamp desc")
            .setParameter("accountId", accountId)
            .getResultList();
        
        return allTransactions;
    }

    
}
 