/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncirl.teamf.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author I323506
 */
@Path("/")
public class BankServices {
    
    @GET
    @Path("test")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String displayMainPage(MultivaluedMap<String, String> formParams) {
        return "MainPage";
    }
}
