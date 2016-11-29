/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncirl.teamf.services.models;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rodrigo
 */

@XmlRootElement
public class Transaction {
    private String transactionId;
    private String transactionType;
    private Date timeStamp;
    private double transactionAmount;
    private double preBalance;
    private double posBalance;
    private String transactionDescription;
    
    public Transaction() {}

    public Transaction(String transactionId, String transactionType, Date timeStamp, double transactionAmount, double preBalance, double posBalance, String transactionDescription) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.timeStamp = timeStamp;
        this.transactionAmount = transactionAmount;
        this.preBalance = preBalance;
        this.posBalance = posBalance;
        this.transactionDescription = transactionDescription;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public double getPreBalance() {
        return preBalance;
    }

    public void setPreBalance(double preBalance) {
        this.preBalance = preBalance;
    }

    public double getPosBalance() {
        return posBalance;
    }

    public void setPosBalance(double posBalance) {
        this.posBalance = posBalance;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    
    
    
}
