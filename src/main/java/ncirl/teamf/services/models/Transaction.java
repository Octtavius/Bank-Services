/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncirl.teamf.services.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table
@XmlRootElement
public class Transaction implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;
    //@ManyToOne
    private int accountId;    
    private String transactionType;
    
    //@Temporal(TemporalType.TIMESTAMP)
    private long timeStamp;
    private double transactionAmount;
    private double preBalance;
    private double posBalance;
    private String transactionDescription;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the accountId
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    /**
     * @return the transactionType
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * @param transactionType the transactionType to set
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * @return the timeStamp
     */
    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * @return the transactionAmount
     */
    public double getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * @param transactionAmount the transactionAmount to set
     */
    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * @return the preBalance
     */
    public double getPreBalance() {
        return preBalance;
    }

    /**
     * @param preBalance the preBalance to set
     */
    public void setPreBalance(double preBalance) {
        this.preBalance = preBalance;
    }

    /**
     * @return the posBalance
     */
    public double getPosBalance() {
        return posBalance;
    }

    /**
     * @param posBalance the posBalance to set
     */
    public void setPosBalance(double posBalance) {
        this.posBalance = posBalance;
    }

    /**
     * @return the transactionDescription
     */
    public String getTransactionDescription() {
        return transactionDescription;
    }

    /**
     * @param transactionDescription the transactionDescription to set
     */
    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }
    
    
    
    
    
}
