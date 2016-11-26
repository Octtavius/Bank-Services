/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncirl.teamf.services;


import com.google.gson.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author fmorais
 */
@Path("/tests")
public class TestService {
   
    @GET
    @Path("who")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String getWho(MultivaluedMap<String, String> formParams) {
        JsonObject json = new JsonObject();
        json.addProperty("Name", "Rodrigo");
        json.addProperty("Language", "Java");
        return json.toString();
    }
    
    @GET
    @Path("balance")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String getBalance(MultivaluedMap<String, String> formParams) {
        JsonObject json = new JsonObject();
        json.addProperty("Balance", "1.000.0000");
        return json.toString();
    } 
    
}
