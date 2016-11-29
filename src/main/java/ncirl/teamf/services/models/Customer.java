/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncirl.teamf.services.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rodrigo
 */
@XmlRootElement
public class Customer {
    private String customerId;
    private String firstName;
    private String middleName;
    private String lastName;    
    private String address;
    private String email;
    private String contactNumber;
    
    public Customer() {}

//    public Customer(String customerId, String firstName, String middleName, String lastName, String address, String email, String contactNumber) {
//        this.customerId = customerId;
//        this.firstName = firstName;
//        this.middleName = middleName;
//        this.lastName = lastName;
//        this.address = address;
//        this.email = email;
//        this.contactNumber = contactNumber;
//    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    
    
    
    
}
