/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import utils.EMF_Creator;
import entities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    public PersonFacadeTest() {
    }

  //  @BeforeAll
   // public static void setUpClass() {
   //     emf = EMF_Creator.createEntityManagerFactory(
   //             "pu",
   //             "jdbc:mysql://localhost:3307/person_test",
   //             "dev",
   //             "ax2",
   //             EMF_Creator.Strategy.CREATE);
   //     facade = PersonFacade.getPersonFacade(emf);
   // }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM PERSON").executeUpdate();
            em.persist(new Person("TestGuy1", "TestLN1", "99977"));
            em.persist(new Person("TestGuy2", "TestLN2", "66677"));
           // em.persist(new Person("TestGuy3", "TestLN3", "88899"));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testGetAPerson() {
        assertThat(facade.getPerson(1).getFirstName(), anyOf(is("TestGuy1"), is("TestGuy2")));
    }

    @Test
    public void testGetAllPersons() {
        assertEquals(2, facade.getAllPersons().size());
    }

    @Test
    public void testAddPerson() {
        facade.addPerson("TestGuy4", "TestLN3", "44455");
        assertEquals(3, facade.getAllPersons().size());
    }

    @Test
    public void testEditPerson() {
     Person p = facade.getPerson(2);
       Person edited = facade.editPerson(p);
        edited.setPhone("88888");
      assertEquals("88888", edited.getPhone());
       
        System.out.println(edited);
    }

    @Test
    public void testDeletePerson() {
        Person p = facade.deletePerson(1);
        assertThat(p.getFirstName(), anyOf(is("TestGuy1"), is("TestGuy2")));
    }

}
