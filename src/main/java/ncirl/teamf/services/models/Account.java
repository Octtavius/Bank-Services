/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncirl.teamf.services.models;

/**
 *
 * @author Rodrigo
 */
public class Account {
    private String accountNumber;    
    private String sortCode;
    private String accountType;
    private String description;
    private double balance;
    private String password;
    
    public Account() {}

    public Account(String accountNumber, String sortCode, String accountType, String description, double balance, String password) {
        this.accountNumber = accountNumber;
        this.sortCode = sortCode;
        this.accountType = accountType;
        this.description = description;
        this.balance = balance;
        this.password = password;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    
    
    
}
