package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.F2w1;
import utils.EMF_Creator;
import facades.F2w1Facade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("f2f1")
public class ResourceF2w1 {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://157.230.18.125:3307/f2w1",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    private static final F2w1Facade FACADE =  F2w1Facade.getF2w1Facade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"f2w1\"}";
    }
   @Path("populate")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String populate() {
        FACADE.populateF2w1s();
        return "{\"msg\":\"done!\"}";
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getF2w1ById(@PathParam ("id") int id) {
        F2w1 F2w1 = FACADE.getF2w1ByID(id);
        return GSON.toJson(F2w1);
    }
        @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getF2w1Count() {
        long count = FACADE.getF2w1Count();
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllF2w1s() {
        List<F2w1>  F2w1s = FACADE.getAllF2w1s();
        return GSON.toJson(F2w1s);
        
    }
       @Path("actorsIn/{name}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAcotorsByF2w1Name(@PathParam ("name") String name) {
        List<F2w1>  actors = FACADE.getAcotorsByF2w1Name(name);
        return GSON.toJson(actors);
        
    }

    @Path("name/{name}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getF2w1ByName(@PathParam ("name") String name) {
        List <F2w1> F2w1 = FACADE.getF2w1ByName(name);
        return GSON.toJson(F2w1);
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(F2w1 entity) {
        throw new UnsupportedOperationException();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(F2w1 entity, @PathParam("id") int id) {
        throw new UnsupportedOperationException();
    }
}
