/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncirl.teamf.services;

import com.google.gson.Gson;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import ncirl.teamf.services.dao.Repository;
import ncirl.teamf.services.models.Customer;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import ncirl.teamf.services.models.Transaction;

/**
 *
 * @author I323506
 */

@Path("/main")
public class BankServices {
    
    Repository rep = new Repository();
    
    @POST
    @Path("/createAccount")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createAccountApi(MultivaluedMap<String, String> formParams) {
        //create  acustomer object. REMEMBER: the id is not set yet.
        //it will be generated in the right class. not here. 
        JsonObject jObj = new JsonObject();        
        
        Customer tempCustomer = createCustomerObject(formParams);
        Customer newCustomer = rep.createAccount(tempCustomer);
        
        jObj.addProperty("response", newCustomer.getId());
        
        return Response.status(200).entity(jObj.toString()).build();
    }
    
    @POST
    @Path("/balance")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getBalanceApi(MultivaluedMap<String, String> formParams) {
        int accountId = Integer.parseInt(formParams.getFirst("accountId"));
        System.out.println("id is: " + accountId);
        double balance = rep.getBalance(accountId);
        
        JsonObject j = new JsonObject();
        j.addProperty("response", balance);
        
        return Response.status(200).entity(j.toString()).build();
    }
    
    @POST
    @Path("/lodgement")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response lodgmentApi(MultivaluedMap<String, String> formParams) {
        
        int accountNum = Integer.parseInt(formParams.getFirst("accountId"));
        double amount = Double.parseDouble(formParams.getFirst("amount"));
        
        boolean response = rep.lodgment(accountNum, amount);
        
        JsonObject j = new JsonObject();
        j.addProperty("response", response);
        return Response.status(200).entity(j.toString()).build();
    }
    
    @POST
    @Path("/transfer")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response checkSenderCredentials(MultivaluedMap<String, String> formParams) {
        int recipientAccount = Integer.parseInt(formParams.getFirst("recipientAccount"));

       
       //keep amount in session storage. we will use it later to transfer it.
       
       //go and check if recipient exits or the credentials are correct
       String recipient = rep.checkRecipient(recipientAccount);
       
       if(recipient == null) {
           return Response.status(404).build();
       }
       else {
           JsonObject j = new JsonObject();
           j.addProperty("response", recipient);
       
           return Response.status(200).entity(j.toString()).build();
       }

    }
    
    @POST
    @Path("/transferExecute")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response executeTransfer(MultivaluedMap<String, String> formParams) {
        int senderAccount = Integer.parseInt(formParams.getFirst("accountId"));
        double amount = Double.parseDouble(formParams.getFirst("amount"));
        int recipientAccount = Integer.parseInt(formParams.getFirst("recipientAccount"));
        int recipientSortCode = Integer.parseInt(formParams.getFirst("recipientSortCode"));
        
        boolean response = rep.transfer(senderAccount, amount, recipientAccount, recipientSortCode);
        
        JsonObject j = new JsonObject();
        j.addProperty("response", response);
        
        return Response.status(200).entity(j.toString()).build();
    }

    @POST
    @Path("/withdraw")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response executeWithdraw(MultivaluedMap<String, String> formParams) {
        int userAccount = Integer.parseInt(formParams.getFirst("accountId"));
        double amount = Double.parseDouble(formParams.getFirst("amount"));
        
        boolean response = rep.withdraw(userAccount, amount);
        
//        JsonObject j = new JsonObject();
//        j.addProperty("response", response);
//        
//        return Response.status(200).entity(j.toString()).build();
        return null;
    }
    
    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response executeLogin(MultivaluedMap<String, String> formParams) {
        int userAccount = Integer.parseInt(formParams.getFirst("accountId"));
        String password = formParams.getFirst("password");
        
        Customer cust = rep.login(userAccount, password);
        
        Gson gson = new Gson();
        
        return Response.status(200).entity(gson.toJson(cust)).build();
    }
    
    @POST
    @Path("/getTransactions")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getAllTransactions(MultivaluedMap<String, String> formParams) {
        
        int userAccount = Integer.parseInt(formParams.getFirst("accountId"));
        List<Transaction> allTransactions = rep.getTransactions(userAccount);
        Gson gson = new Gson();
        
        return Response.status(200).entity(gson.toJson(allTransactions)).build();
    }
    
    private Customer createCustomerObject(MultivaluedMap<String, String> formParams) {
        String firstName = formParams.getFirst("firstName");
        String middleName = formParams.getFirst("middleName");
        String lastName = formParams.getFirst("lastName");
        String address = formParams.getFirst("address");
        String email = formParams.getFirst("email");
        String contactNumber = formParams.getFirst("contactNumber");
        
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setMiddleName(middleName);
        customer.setLastName(lastName);
        customer.setAddress(address);
        customer.setEmail(email);
        customer.setContactNumber(contactNumber);
        
        return customer;
    }
}
