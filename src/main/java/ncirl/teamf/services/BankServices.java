/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncirl.teamf.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import ncirl.teamf.services.dao.Repository;
import ncirl.teamf.services.models.Customer;

import com.google.gson.JsonObject;

/**
 *
 * @author I323506
 */

@Path("/main")
public class BankServices {
    
    Repository rep = new Repository();
    
    @GET
    @Path("test")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void displayMainPage(MultivaluedMap<String, String> formParams) {
        System.out.println("Hello World");
    }
    
    @POST
    @Path("/createAccount")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void createAccountApi(MultivaluedMap<String, String> formParams) {
        //create  acustomer object. REMEMBER: the id is not set yet.
        //it will be generated in the right class. not here. 
        Customer newCustomer = createCustomerObject(formParams);
        rep.createAccount(newCustomer);
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
        j.addProperty("balance", balance);
        
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
        j.addProperty("lodgementSuccess", response);
        return Response.status(200).entity(j.toString()).build();
    }
    
    @POST
    @Path("/transfer")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response checkSenderCredentials(MultivaluedMap<String, String> formParams) {
        int senderAccount = Integer.parseInt(formParams.getFirst("accountId"));
        double amount = Double.parseDouble(formParams.getFirst("amount"));
        int recipientAccount = Integer.parseInt(formParams.getFirst("recipientAccount"));
        int recipientSortCode = Integer.parseInt(formParams.getFirst("recipientSortCode"));
        
        //keep amount in session storage. we will use it later to transfer it.
        
        //go and check if recipient exits or the credentials are correct
        String recipient = rep.checkRecipient(recipientAccount, recipientSortCode);
        
        //on the check if response if string then display the recipient. everything is ok
        //if the response if null, then user couldn't be found. let user know
      JsonObject j = new JsonObject();
        j.addProperty("response", recipient);
        
        return Response.status(200).entity(j.toString()).build();
    }
    
        @POST
    @Path("/transferExecute")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public boolean executeTransfer(MultivaluedMap<String, String> formParams) {
        int senderAccount = Integer.parseInt(formParams.getFirst("accountId"));
        double amount = Double.parseDouble(formParams.getFirst("amount"));
        int recipientAccount = Integer.parseInt(formParams.getFirst("recipientAccount"));
        int recipientSortCode = Integer.parseInt(formParams.getFirst("recipientSortCode"));
        
        boolean response = rep.transfer(senderAccount, amount, recipientAccount, recipientSortCode);
        
        return response;
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
