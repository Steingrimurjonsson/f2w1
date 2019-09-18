package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import entities.Person;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class ResourcePerson {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://157.230.18.125:3307/Person",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Person\"}";
    }

    @Path("populate")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String populate() {
        FACADE.populatePersons();
        return "{\"msg\":\"done!\"}";
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonById(@PathParam("id") int id) {
        Person Person = FACADE.getPersonByID(id);
        return GSON.toJson(Person);
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonCount() {
        long count = FACADE.getPersonCount();
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersons() {
        List<Person> Persons = FACADE.getAllPersons();
        return GSON.toJson(Persons);

    }

    @Path("hobbiesIn/{name}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getHobbiesByPersonName(@PathParam("name") String name) {
        List<Person> hobbies = FACADE.getHobbiesByPersonName(name);
        return GSON.toJson(hobbies);

    }

    @Path("name/{name}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByName(@PathParam("name") String name) {
        List<Person> Person = FACADE.getPersonByName(name);
        return GSON.toJson(Person);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Person entity) {
        throw new UnsupportedOperationException();
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public void delete(@PathParam("id") int id) {
        persons.remove(id);
        return "{}";
    }

    @POST
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String editPerson(String personAsJson, @PathParam("id") int id) {
        List<Person> psj = FACADE.getPersonByName(personAsJson);
        return GSON.toJson(psj);
        PersonDTO person = GSON.fromJson(person, PersonDTO.class);
        //Fetch the Car Entity using the provided ID, make the changes
        //received in the provided DTO and merge it back
        //Convert the edited Entity object back into a DTO
        //person = //Make CarDTO from persisted Car
        return Response.ok(person).build();

    }  
    
    @POST
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String addPerson(String personAsJson, @PathParam("id") int id) {
        List<Person> psj = FACADE.getPersonByName(personAsJson);
        return GSON.toJson(psj);
        PersonDTO person = GSON.fromJson(person, PersonDTO.class);
        //Fetch the Car Entity using the provided ID, make the changes
        //received in the provided DTO and merge it back
        //Convert the edited Entity object back into a DTO
        //person = //Make CarDTO from persisted Car
        return Response.ok(person).build();

    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(Person entity, @PathParam("id") int id) {
        throw new UnsupportedOperationException();
    }
}
