/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import dto.PersonDTO;
import entities.Person;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
@Disabled
public class ResourcePersonTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1, p2;

    private static final String TEST_DB = "jdbc:mysql://localhost:3307/person_test";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);

        //NOT Required if you use the version of EMF_Creator.createEntityManagerFactory used above        
        //System.setProperty("IS_TEST", TEST_DB);
        //We are using the database on the virtual Vagrant image, so username password are the same for all dev-databases
        httpServer = startServer();

        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;

        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 = new Person("TestGuy1", "TestLN1", "99977");
        p2 = new Person("TestGuy2", "TestLN2", "66677");
        try {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM PERSON WHERE ID > 0").executeUpdate();
            em.createNativeQuery("ALTER TABLE PERSON AUTO_INCREMENT = 1;").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/person/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("PersonWorks"));
    }

   // @Test
   // public void testGetAllPersons() {
   //     System.out.println("getAllPersons");
   //     List<PersonDTO> personsDtos;
   //     personsDtos = given()
   //             .contentType("application/json")
   //             .when()
   //             .get("/person/all")
   //             .then()
   //             .extract().body().jsonPath().getList("all", PersonDTO.class);
   //     PersonDTO p1DTO = new PersonDTO(p1);
   //     PersonDTO p2DTO = new PersonDTO(p2);
   //     assertThat(personsDtos, containsInAnyOrder(p1DTO, p2DTO));
   // }

    @Test
    public void testGetPerson() {
        System.out.println("getPerson");
        given()
                .contentType("application/json")
                .get("/person/1")
                .then().log().body().assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("lastName", anyOf(is("TestLN1"), is("TestLN2")));
    }

    @Test
    public void testAddPerson() {

        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("firstName", "testNameR");
        userDetails.put("lastName", "testNameRl");
        userDetails.put("phone", "0987");

        Response response = given()
                .contentType("application/json")
                .accept("application/json")
                .body(userDetails)
                .when()
                .post("person/add")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response();

        System.out.println("Reponse: " + response.asString());
        String id = response.jsonPath().getString("id");
        System.out.println("ID is: " + id);
        String phone = response.jsonPath().getString("phone");
        assertNotNull(id);
        assertEquals("0987", phone);
    }

    @Test
    public void testAddPerson2() {
        given()
                .contentType("application/json")
                .body(new Person("testNameR2", "testNameRl2", "0987"))
                .when()
                .post("person/add")
                .then()
                .body("fName", equalTo("testNameR2"))
                .body("lName", equalTo("testNameRl2"))
                .body("id", notNullValue());
    }

    @Test
    public void testGetPersonWithWrongId() {
        given()
                .contentType("application/json")
                .get("/person/99990").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("message", equalTo("No person with provided id found"));
    }

}
