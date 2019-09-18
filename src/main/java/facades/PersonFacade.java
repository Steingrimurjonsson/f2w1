package facades;

import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * RefirstName Class to a relevant firstName Add add relevant facade methods
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    private PersonFacade() {
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public interface IPersonFacade {

        public Person addPerson(String firstName, String lastName, String hobbies);

        public Person deletePerson(int id);

        public Person getPerson(int id);

        public List<Person> getAllPersons();

        public Person editPerson(Person p);
    }

    public long getPersonCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long PersonCount = (long) em.createQuery("SELECT COUNT(m) FROM Person m").getSingleResult();
            return PersonCount;
        } finally {
            em.close();
        }

    }

    public Person getPersonByID(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Person Person = em.find(Person.class, id);
            return Person;
        } finally {
            em.close();
        }
    }

    public List<Person> getPersonByName(String firstName) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query
                    = em.createQuery("Select m from Person m where m.firstName =:firstName", Person.class);
            return query.setParameter("firstName", firstName).getResultList();
        } finally {
            em.close();
        }
    }

    public Person addPerson(String lastName, String firstName, String[] hobbies) {
        Person Person = new Person();
        Person = new Person(lastName, firstName, hobbies);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(Person);
            em.getTransaction().commit();
            return Person;
        } finally {
            em.close();
        }
    }

    public List<Person> getAllPersons() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery query
                    = em.createQuery("Select m from Person m", Person.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

  /*  public List<Person> getHobbiesByPersonName(String firstName) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query
                    = em.createQuery("Select hobbies from Person m where m.firstName =:firstName", Person.class);
            return query.setParameter("firstName", firstName).getResultList();
        } finally {
            em.close();
        }
    }*/

    public void populatePersons() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(new Person("Firstname1", "Lastname1", new String[]{"h1", "h2", "h3"}));
            em.persist(new Person("Firstname2", "Lastname2", new String[]{"h4", "h5", "h6"}));
            em.persist(new Person("Firstname3", "Lastname3", new String[]{"h7", "h8", "h9"}));
            em.persist(new Person("Firstname4", "Lastname4", new String[]{"h10", "h11", "h12"}));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
